(ns atd.hooks.use-scroll-trigger
  (:require ["gsap/ScrollTrigger" :refer [ScrollTrigger]]
            [helix.hooks :as hooks]))

(defn check-positioning
  [st]
  (let [current-scroll (-> st .scroll)
        trigger-start (-> st .-start)
        trigger-end (-> st .-end)]
    {:trigger-start trigger-start
     :trigger-end trigger-end
     :current-scroll current-scroll}))

(defn use-scroll-trigger
  [ref & {:keys [on-toggle
                 on-enter
                 start
                 end
                 scroll-ref
                 markers?
                 debug?]
          :or {markers? false
               debug? false
               start "top center"
               end "bottom"}}]

  (let [[is-active? set-is-active!] (hooks/use-state false)
        [visited? set-visited!] (hooks/use-state false)]
    (hooks/use-effect
     [ref scroll-ref]
     (let [st (.create ScrollTrigger #js{:trigger @ref
                                         :start start
                                         :end end
                                         :onRefresh (fn [_])
                                         :onEnter (fn [self]
                                                    (set-visited! true)
                                                    (when on-enter
                                                      (on-enter self)))
                                         :onToggle (fn [self]
                                                     (set-is-active! (.-isActive self))
                                                     (when on-toggle
                                                       (on-toggle self)))
                                         :markers markers?})]

       (fn [] (.kill st))))
    [visited? is-active?]))