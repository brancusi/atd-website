(ns atd.hooks.use-toggle-animations
  (:require
   [helix.hooks :as hooks]
   ["gsap" :refer [gsap]]))

(defn use-toggle-animations

  [ref & {:keys [on off initial is-on?]
          :or {initial false}}]
  (let [[last-state set-last-state!] (hooks/use-state initial)
        on-handler (hooks/use-callback
                    [ref]
                    (fn []
                      (set-last-state! true)
                      (.to gsap @ref
                           (clj->js on))))
        off-handler (hooks/use-callback
                     [ref]
                     (fn []
                       (set-last-state! false)
                       (.to gsap @ref
                            (clj->js off))))]

    (hooks/use-effect
     [is-on? last-state]
     (if (= is-on? last-state)
       :noop
       (if is-on?
         (on-handler)
         (off-handler))))))