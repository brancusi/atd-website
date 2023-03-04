(ns atd.components.ui.audio-toggle
  (:require [helix.core :refer [$]]
            [helix.hooks :as hooks]
            [helix.dom :as d]
            ["@heroicons/react/24/outline" :as icons]
            [applied-science.js-interop :as j]
            [atd.lib.defnc :refer [defnc]]))

(defnc audio-toggle [{:keys [on-click-handler
                             on-mouse-over-handler
                             on-mouse-out-handler]}]
  (d/div
   {:class "w-6 h-6"
    ;; :on-mouse-over #(on-mouse-over-handler)
    ;; :on-mouse-out #(on-mouse-out-handler)
    ;; :on-click #(on-click-handler)
    }
   ($ icons/SpeakerWaveIcon)))