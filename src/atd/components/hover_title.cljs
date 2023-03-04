(ns atd.components.hover-title
  (:require [helix.core :refer [$]]
            [helix.hooks :as hooks]
            [helix.dom :as d]
            [applied-science.js-interop :as j]
            [atd.lib.defnc :refer [defnc]]))

(defnc hover-title [{:keys [title hover-title-ref]}]
  (d/div {:class "absolute massive-title"
          :ref hover-title-ref}
         title))