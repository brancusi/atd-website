(ns atd.components.hover-title
  (:require [helix.core :refer [$]]
            [helix.hooks :as hooks]
            [helix.dom :as d]
            [applied-science.js-interop :as j]
            [atd.lib.defnc :refer [defnc]]))

(defnc hover-title [{:keys [title hover-title-ref]}]
  (d/div {:class "absolute massive-title h-full w-full"
          :ref hover-title-ref}
         (d/svg {:class "justify-self-start"
                 :height "100%"
                 :width "100%"}
                (d/text {:class "yotext font-fira-code font-bold"
                         :text-anchor "start"
                         :alignment-baseline "middle"
                         :y "50%"
                         :x "50%"} title))))