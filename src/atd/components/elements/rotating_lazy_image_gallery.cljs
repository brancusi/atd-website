(ns atd.components.elements.rotating-lazy-image-gallery
  (:require [helix.core :refer [$]]
            [helix.hooks :as hooks]
            [helix.dom :as d]
            [atd.components.elements.lazy-image :refer [lazy-image]]
            [atd.lib.defnc :refer [defnc]]))

(defnc rotating-lazy-image-gallery
  [{:keys [images should-load?]}]
  (let [[current-image-index set-current-image-index] (hooks/use-state 0)
        [image-loaded? set-image-loaded?] (hooks/use-state false)
        timer-id (atom nil)]

    (hooks/use-effect
     []
     (fn []
       (when should-load?
         (reset! timer-id
                 (js/setInterval #(set-current-image-index
                                   (fn [prev-index]
                                     (if (< prev-index (dec (count images)))
                                       (inc prev-index)
                                       0)))
                                 5000)))
       (fn []
         (js/clearInterval @timer-id))))

    (d/div
     (map-indexed
      (fn [index {:keys [src opts]}]
        ($ lazy-image {:src src
                       :should-load? (and should-load? (= index current-image-index))
                       :on-loaded (fn []
                                    (set-image-loaded? true))}))
      images))))
