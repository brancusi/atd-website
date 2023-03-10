(ns atd.hooks.use-window-resize
  (:require
   [helix.hooks :as hooks]
   [atd.utils.window :as win-utils]))

(defn use-window-resize
  [deps handler & {:keys [fire-on-mount?]
                   :or {fire-on-mount? false}}]
  (let [resize-handler (fn [& _]
                         (handler {:width (win-utils/width)
                                   :height (win-utils/height)}))]
    (hooks/use-effect
     deps
     (.addEventListener js/window "resize" resize-handler)
     (when fire-on-mount?
       (resize-handler))

     (fn []
       (.removeEventListener js/window "resize" resize-handler)))))