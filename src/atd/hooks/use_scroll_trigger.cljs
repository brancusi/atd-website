(ns atd.hooks.use-scroll-trigger
  (:require ["gsap/ScrollTrigger" :refer [ScrollTrigger]]
            [helix.hooks :as hooks]))

(defn use-scroll-trigger
  [ref & {:keys [on-toggle
                 on-enter
                 markers?]
          :or {markers? false}}]

  (let [[is-active? set-is-active!] (hooks/use-state false)]
    (hooks/use-effect
     [ref]
     (let [st (.create ScrollTrigger #js{:trigger @ref
                                         :start "top center"
                                         :end "top 100px"
                                         :onRefresh (fn [self])
                                         :onEnter (fn [self]
                                                    (when on-enter
                                                      (on-enter self)))
                                         :onToggle (fn [self]
                                                     (set-is-active! (.-isActive self))
                                                     (when on-toggle
                                                       (on-toggle self)))
                                         :markers markers?})]

       (fn []
         (.kill st))))

    (hooks/use-layout-effect
     [ref]
     (when (.isInViewport ScrollTrigger @ref)
       (set-is-active! true)))
    is-active?))
