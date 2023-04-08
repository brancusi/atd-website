(ns atd.components.elements.rotating-lazy-image-gallery
  (:require [helix.core :refer [$]]
            [helix.hooks :as hooks]
            [atd.utils.window :as window-utils]
            [helix.dom :as d]
            [nano-id.core :refer [nano-id]]
            [atd.utils.gsap :as gsap]
            [atd.utils.for-indexed :refer [for-indexed]]
            [atd.components.elements.lazy-image :refer [lazy-image]]
            [atd.lib.defnc :refer [defnc]]))

(defn get-next-image-index
  [current-image-index images]
  (let [next-image-index (inc current-image-index)
        is-at-end? (>= next-image-index (count images))]
    (if is-at-end?
      0
      next-image-index)))

(defn push-to-stack
  [image-data idx view-stack]
  (let [needs-culling? (> (count view-stack) 2)
        next-view-stack (if needs-culling?
                          (rest view-stack)
                          view-stack)
        new-view (merge image-data
                        {:id (nano-id)
                         :index idx})]

    {:new-view new-view
     :new-stack (conj (vec next-view-stack) new-view)}))

(defnc rotating-lazy-image-gallery
  [{:keys [transition
           images
           rate
           should-load?
           should-play?]}]
  (let [[view-stack set-view-stack!] (hooks/use-state [])
        [ready-for-next? set-ready-for-next!] (hooks/use-state false)
        [current-image-index set-current-image-index!] (hooks/use-state 0)
        [current-image-view set-current-image-view!] (hooks/use-state nil)
        on-intro-complete (hooks/use-callback
                           :once
                           (fn []
                             (set-ready-for-next! true)))]

    ;; Bootstrap
    (hooks/use-effect
     [images view-stack should-load? should-play? current-image-index]
     (when (and should-load?
                should-play?
                (not= (:index current-image-view) current-image-index)
                (> (count images) 0))
       (let [new-stack-data (push-to-stack
                             (nth images current-image-index)
                             current-image-index
                             view-stack)]
         (set-view-stack! (:new-stack new-stack-data))
         (set-current-image-view! (:new-view new-stack-data)))))

    (hooks/use-effect
     [images should-play? ready-for-next? current-image-index]
     (let [interval-id (js/setInterval
                        (fn []
                          (when (and should-play?
                                     ready-for-next?)
                            (set-ready-for-next! false)
                            (set-current-image-index! (get-next-image-index current-image-index images))))
                        rate)]
       (fn []
         (js/clearInterval interval-id))))

    (d/div {:class "relative w-full h-full"}
           (when should-load?
             (mapv (fn
                     [{:keys [id src fp]}]
                     (d/div {:key id
                             :class "absolute w-full h-full"}
                            ($ lazy-image
                               {:src src
                                :fp-x (:x fp)
                                :fp-y (:y fp)
                                :w (window-utils/width)
                                :h (window-utils/height)
                                :transition transition
                                :on-intro-completed on-intro-complete
                                :should-load? true})))
                   view-stack)))))