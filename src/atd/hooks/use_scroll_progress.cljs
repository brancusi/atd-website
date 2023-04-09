(ns atd.hooks.use-scroll-progress
  (:require
   [applied-science.js-interop :as j]
   [goog.functions :as gfunctions]
   [helix.hooks :as hooks]))

(defn use-scroll-progress [total-sections & {:keys [throttle-interval]
                                             :or {throttle-interval 500}}]
  (let [[current-index set-current-index!] (hooks/use-state 0)]
    (hooks/use-effect
     [total-sections]
     (let [update-progress (fn []
                             (let [scroll-top (j/get js/document.documentElement "scrollTop")
                                   scroll-height (j/get js/document.documentElement "scrollHeight")
                                   viewport-height (j/get js/window "innerHeight")
                                   total-scroll (- scroll-height viewport-height)
                                   progress-percentage (/ scroll-top total-scroll)
                                   index (int (* progress-percentage total-sections))]
                               (set-current-index! index)))
           throttled-update-progress (gfunctions/throttle update-progress throttle-interval)]

       (.addEventListener js/window "scroll" throttled-update-progress)
       (fn [] (.removeEventListener js/window "scroll" throttled-update-progress))))

    current-index))