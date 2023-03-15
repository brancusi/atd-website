(ns atd.hooks.use-scroll-snap
  (:require ["gsap/ScrollTrigger" :refer [ScrollTrigger]]
            [helix.hooks :as hooks]
            [atd.utils.window :as win-utils]))

(defn use-scroll-snap
  [ref & {:keys [markers?]
          :or {markers? false}}]

  (hooks/use-effect
   [ref]

   (let [total-children (-> @ref .-childNodes .-length)
         st (.create ScrollTrigger #js{:trigger @ref
                                       :snap (/ 1 total-children)
                                       :scrub 1
                                       :start "top"
                                       :end (fn []
                                              (str "+=" (* total-children (win-utils/height))))
                                       :onRefresh (fn [self])
                                       :markers markers?})]

     (fn []
       (.kill st)))))