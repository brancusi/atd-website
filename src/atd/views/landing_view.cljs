(ns atd.views.landing-view
  (:require [atd.components.hero-header :refer [hero-header]]
            [atd.lib.defnc :refer [defnc]]
            [atd.reducers.requires]
            [atd.components.section :refer [section]]

            [helix.core :refer [$]]
            [helix.dom :as d]))

(defnc landing-section [{:keys [section-id title]}]
  (d/section {:id section-id
              :class " h-full w-full orange-grad"}
             (d/p {:class "text-4xl"} title)))

(defnc landing-view []
  ($ :div

     ($ section
        {:key "hero"
         :section-id "hero"}
        ($ hero-header))

     ($ section
        {:key "art"
         :section-id "art"}
        ($ landing-section {:section-id "art"
                            :title "Art"}))

     ($ section
        {:key "tech"
         :section-id "tech"}
        ($ landing-section {:section-id "tech"
                            :title "Tech"}))

     ($ section
        {:key "design"
         :section-id "design"}
        ($ landing-section {:section-id "design"
                            :title "Design"}))))