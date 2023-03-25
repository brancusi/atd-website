(ns atd.views.landing-view
  (:require [atd.components.section :refer [section]]
            [atd.components.sections.quote-section :refer [quote-section]]
            [atd.components.sections.what-section :refer [what-section]]
            [atd.components.sections.contact-section :refer [contact-section]]
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
    #_(use-scroll-snap container-ref)

    ($ :div {:ref container-ref
             :class ""}

       ($ section
          {:key "hero"
           :section-id "hero"}
          ($ hero-header))

       ($ section
          {:key "main-quote"
           :section-id "main-quote"}
          ($ quote-section {:section-id "main-quote"
                            :quote ["The way you do anything"
                                    "Is the way you do everything"]}))

       ($ section
          {:key "doing"
           :section-id "doing"}
          ($ quote-section {:class ""
                            :gradient-class "blue-grad"
                            :section-id "doing"
                            :header "Doing"
                            :quote ["Working with people I love"
                                    "Building beautiful things"]}))

       ($ section
          {:key "what"
           :section-id "what"}
          ($ what-section {:class ""
                           :force-on? false
                           :gradient-class "purple-grad"
                           :section-id "what"}))


       ($ section
          {:key "contact"
           :section-id "contact"}
          ($ contact-section {:force-on? true
                              :section-id "contact"})))))