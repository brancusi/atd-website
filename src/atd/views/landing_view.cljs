(ns atd.views.landing-view
  (:require [atd.components.hero-header :refer [hero-header]]
            [atd.lib.defnc :refer [defnc]]
            [atd.reducers.requires]
            [helix.core :refer [$]]
            [helix.dom :as d]))

(defnc landing-section [{:keys [section-id title]}]
  (d/section {:id section-id
              :class "relative h-screen w-full orange-grad"}
             (d/p {:class "text-4xl"} title)))

(defnc landing-view []
  ($ :div
     ($ hero-header)
     ($ landing-section {:section-id "art"
                         :title "Art"})
     ($ landing-section {:section-id "tech"
                         :title "Tech"})
     ($ landing-section {:section-id "design"
                         :title "Design"})))