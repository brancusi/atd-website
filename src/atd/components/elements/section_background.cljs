(ns atd.components.elements.section-background
  (:require [helix.core :refer [$]]
            [helix.hooks :as hooks]
            [helix.dom :as d]
            ["@heroicons/react/24/outline" :as icons]
            [applied-science.js-interop :as j]
            [atd.lib.defnc :refer [defnc]]))

(defnc section-background [{:keys []}]
  (d/div {:class "absolute my-grad w-full h-full section-background"}))