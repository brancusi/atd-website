(ns atd.views.landing-view
  (:require [atd.components.section :refer [section]]
            [atd.components.sections.quote-section :refer [quote-section]]
            [atd.components.sections.video-section :refer [video-section]]
            [atd.components.sections.what-section :refer [what-section]]
            [atd.components.sections.contact-section :refer [contact-section]]
            [atd.lib.defnc :refer [defnc]]
            [atd.reducers.requires]
            [atd.components.hero-header :refer [hero-header]]
            [atd.components.navs.progress-menu :refer [progress-menu]]
            [atd.components.sections.mobile-hero-section :refer [mobile-hero-section]]
            [atd.hooks.use-media-query :refer [use-media-query]]
            [atd.components.playful-titles :refer [playful-titles]]

            [helix.core :refer [$]]
            [helix.dom :as d]
            [helix.hooks :as hooks]))

(defnc landing-view []
  (let [container-ref (hooks/use-ref "container-ref")
        #_#_current-index (use-scroll-progress 6 container-ref {:throttle-interval 200})
        is-desktop? (use-media-query :md)]

    ($ :div {:ref container-ref
             :class ""}

       (d/div {:class "fixed z-20 justify-center items-center top-1/2 -translate-y-1/2 left-2"}
              ($ progress-menu {:total-sections 6}))

       (if is-desktop?
         ($ section
            {:key "video"
             :section-id "video"}
            (d/div {:class "w-screen h-screen relative"}
                   (d/div {:class "absolute w-full h-full"}
                          ($ video-section {:playback-id "l02cq1uS4sXBEGdQJNdYVDL7KoTNEreRDJymmk01NSN7c"}))
                   (d/div {:class "absolute w-full h-full pointer-events-none"}
                          ($ playful-titles))))
         ($ section
            {:key "mobile-hero"
             :section-id "mobile-hero"}
            ($ mobile-hero-section)))

       (when is-desktop?
         ($ section
            {:key "hero"
             :section-id "hero"}
            ($ hero-header)))

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
                         (d/span {:class "font-medium text-pink-600"} ":art ") "may seem to have no place here, but it's art that's at the root.")

                    (d/p {:class " mb-8"}
                         "It's what helps us break with the status quo as individuals and as a business."))))


       ($ section
          {:key "video-section"
           :section-id "video-section"}
          (d/div {:class "w-screen h-screen relative"}
                 (d/div {:class "absolute w-full h-full"}
                        ($ video-section {:playback-id "4xg96n14D7TLhM5S02g2v4kUD00gpNMpyYLNGGcyk8U3k"}))))

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
                            :header "What I Love"
                            :quote ["Making immutable data move."
                                    "Making moving images that make people stop."]}))


       ($ section
          {:key "what"
           :section-id "what"}
          ($ what-section {:class ""
                           :gradient-class "purple-grad"
                           :section-id "what"}))


       ($ section
          {:key "contact"
           :section-id "contact"}
          ($ contact-section {:force-on? false
                              :section-id "contact"})))))