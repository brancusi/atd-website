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
               end "top 100px"}}]

  (let [[is-active? set-is-active!] (hooks/use-state false)]
    (hooks/use-effect
     [ref scroll-ref]
     (let [st (.create ScrollTrigger #js{:trigger @ref

                                         :start start
                                         :end end
                                         :onRefresh (fn [_])
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

    is-active?))


    ;; (hooks/use-layout-effect
    ;;  [ref]

#_(when (.isInViewport ScrollTrigger @ref)
    (set-is-active! true))

    ;; (hooks/use-layout-effect
    ;;  [ref]
    ;;  (let [st (.create ScrollTrigger
    ;;                    #js{:trigger @ref
    ;;                        :onRefreshInit
    ;;                        (fn [self]
    ;;                          (let [{:as size-info
    ;;                                 :keys [current-scroll trigger-start]} (check-positioning self)]
    ;;                            (when debug?
    ;;                              (aset js/window "ATD_DEBUG_$1" self)
    ;;                              (tap> {:size-info size-info}))
    ;;                            (when (> current-scroll trigger-start)
    ;;                              (set-is-active! true))))})]

    ;;    (fn []
    ;;      (.kill st))))
