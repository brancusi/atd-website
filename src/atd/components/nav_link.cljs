(ns atd.components.nav-link
  (:require [helix.core :refer [$]]
            [helix.hooks :as hooks]
            [helix.dom :as d]
            [applied-science.js-interop :as j]
            [atd.lib.defnc :refer [defnc]]))

(defnc nav-link [{:keys [on-click-handler title section-id]}]
  (let [ref (hooks/use-ref "link-ref")]
    (d/a {:ref ref
          :class "hero-nav-links pointer font-fira-code text-4xl"
          :on-click (fn []
                      (on-click-handler {:section-id section-id}))}
         title)))