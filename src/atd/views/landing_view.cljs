(ns atd.views.landing-view
  (:require [atd.components.section :refer [section]]
            [atd.components.sections.quote-section :refer [quote-section]]
            [atd.hooks.use-scroll-snap :refer [use-scroll-snap]]
            [atd.lib.defnc :refer [defnc]]
            [atd.reducers.requires]
            [atd.components.hero-header :refer [hero-header]]
            [helix.core :refer [$]]
            [helix.dom :as d]
            [helix.hooks :as hooks]))

(defnc landing-section [{:keys [section-id title]}]
  (d/section {:id section-id
              :class " h-full w-full orange-grad"}
             (d/p {:class "text-4xl"} title)))

(defnc landing-view []
  (let [container-ref (hooks/use-ref "container-ref")]
    (use-scroll-snap container-ref)

    ($ :div {:ref container-ref}

       ($ section
          {:key "hero"
           :section-id "hero"}
          ($ hero-header))

       ($ quote-section {:key "art"
                         :section-id "art"
                         :quote "The way you do anything is the way you do everything."})
       ($ quote-section {:key "art2"
                         :section-id "art"
                         :quote "The way you do anything is the way you do everything."})
       ($ quote-section {:key "art3"
                         :section-id "art"
                         :quote "The way you do anything is the way you do everything."})
       ($ quote-section {:key "art4"
                         :section-id "art"
                         :quote "The way you do anything is the way you do everything."})

       ($ section
          {:key "tech"
           :section-id "tech"}
          ($ landing-section {:section-id "tech"
                              :title "Tech"}))

       ($ section
          {:key "design"
           :section-id "design"}
          ($ landing-section {:section-id "design"
                              :title "Design"})))))