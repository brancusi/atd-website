(ns atd.views.landing-view
  (:require [atd.components.section :refer [section]]
            [atd.components.sections.quote-section :refer [quote-section]]
            [atd.components.sections.what-section :refer [what-section]]
            [atd.components.sections.contact-section :refer [contact-section]]
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

    ($ :div {:ref container-ref
             :class ""}

       ($ section
          {:key "hero"
           :section-id "hero"}
          ($ hero-header))

       ($ section
          {:key "about-tech"
           :section-id "about-tech"}
          ($ quote-section {:section-id "tech-quote"
                            :gradient-class "grey-grad"
                            :from {:opacity 0
                                   :duration 0.5
                                   :ease "expo.inOut",
                                   :stagger 0.01}
                            :to {:opacity 1
                                 :duration 0.1
                                 :ease "expo.inOut",
                                 :stagger 0.1}}
             (d/div {:class "text-slate-300 font-light flex justify-center h-full flex-col md:w-3/4 w-3/4 text-lg md:text-2xl"}
                    (d/p {:class "mb-8 italic"} "The universe is a technology factory.")
                    (d/p {:class "mb-8"} (d/span {:class "font-medium text-pink-600"} ":tech ") "is always going to happen, and the key is to think about tech as something you introduce as you grow.")

                    (d/p {:class " mb-8"}
                         "What is good" (d/span {:class "font-medium text-pink-600"} " :design ") "and why does it matter?")

                    (d/p {:class " mb-8"}
                         (d/span {:class "font-medium text-pink-600"} ":art ") "may seem to have no place here but it's art that's at the root.")

                    (d/p {:class " mb-8"}
                         "It's what helps us break with the status quo as individuals and as a business."))))


       ($ section
          {:key "main-quote"
           :section-id "main-quote"}
          ($ quote-section {:section-id "main-quote"
                            :gradient-class "orange-grad"
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