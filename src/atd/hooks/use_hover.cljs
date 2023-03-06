(ns atd.hooks.use-hover
  (:require [applied-science.js-interop :as j]
            [helix.hooks :as hooks]))

(defn use-hover
  "Mouse hover hook. 
   
   Returns a is-hovering? flag that will be true when the referenced dom element is being hovered.

   Example: (use-hover dom-element-ref)"
  [wrapper-container]
  (let [[is-hovering? set-is-hovering!] (hooks/use-state false)
        mouse-over-handler (hooks/use-callback :once #(set-is-hovering! true))
        mouse-leave-handler (hooks/use-callback :once #(set-is-hovering! false))]

    (hooks/use-effect [wrapper-container]
                      (when @wrapper-container
                        (let [el @wrapper-container]
                          (.addEventListener el "mouseenter" mouse-over-handler)
                          (.addEventListener el "mouseleave" mouse-leave-handler)
                          (fn []
                            (.removeEventListener el "mouseenter" mouse-over-handler)
                            (.removeEventListener el "mouseleave" mouse-leave-handler)))))
    is-hovering?))