(ns atd.components.nav-link
  (:require [helix.core :refer [$]]
            [helix.hooks :as hooks]
            [helix.dom :as d]
            [applied-science.js-interop :as j]
            [atd.lib.defnc :refer [defnc]]))

(defnc nav-link [{:keys [on-click-handler
                         on-mouse-over-handler
                         on-mouse-out-handler
                         title
                         section-id]}]
  (let [ref (hooks/use-ref "link-ref")]
    (d/a {:key section-id
          :ref ref
          :class "hero-nav-links
                  cursor-pointer
                  font-fira-code
                  text-6xl"
          :on-mouse-over (fn []
                           (on-mouse-over-handler {:section-id section-id}))
          :on-mouse-out (fn []
                          (on-mouse-out-handler {:section-id section-id}))
          :on-click (fn []
                      (on-click-handler {:section-id section-id}))}
         title)))