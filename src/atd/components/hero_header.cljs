(ns atd.components.hero-header
  (:require [atd.api.cms]
            [atd.api.cms :refer [get-gallery-images!] :as cms]
            [atd.components.elements.rotating-lazy-image-gallery :refer [rotating-lazy-image-gallery]]
            [atd.components.sections.quote-section :refer [quote-section]]
            [atd.hooks.use-scroll-trigger :refer [use-scroll-trigger]]
            [atd.lib.defnc :refer [defnc]]
            [helix.core :refer [$]]
            [helix.dom :as d]
            [helix.hooks :as hooks]))

(defnc hero-header
  []
  (let [outer-ctx (hooks/use-ref "outer-ctx")
        [visited? is-active?] (use-scroll-trigger outer-ctx {:end "bottom"})
        [hero-images set-hero-images!] (hooks/use-state [])]

    (hooks/use-effect
     :once
     (get-gallery-images! "clojure-poem" set-hero-images!))

    (d/div {:id "hero"
            :ref outer-ctx
            :class "relative
                    h-screen
                    w-screen
                    overflow-hidden"}
           (d/div {:class "h-full
                           w-full
                           relative 
                           flex items-center
                           justify-items-center justify-center"}
                  (d/div {:class "z-10 absolute w-full h-full"}
                         ($ rotating-lazy-image-gallery {:images hero-images
                                                         :transition {:duration 0.2
                                                                      :opacity 1}
                                                         :should-play? is-active?
                                                         :should-load? visited?
                                                         :rate 50}))
                  (d/div {:class "z-20 absolute w-full h-full"}
                         (d/div {:class "w-full h-full absolute pink-grad opacity-30"})
                         ($ quote-section {:section-id "main-quote"
                                           :from {:opacity 0
                                                  :duration 0.5
                                                  :ease "expo.inOut",
                                                  :stagger 0.02}}
                            (d/div {:class "text-slate-800 flex items-center justify-center flex-col md:w-4/5 w-4/5 bg-white/50 backdrop-blur-md p-8"}
                                   (d/p {:class "text-md md:text-xl mb-4"} "I'm Aram. Hello. I help companies build their internal software, systems, and processes so they can run more effeciently.")
                                   (d/p {:class "text-md md:text-xl mb-4"} "I believe in balancing tech and design, supported by the promise of art over science.")
                                   (d/p {:class "text-md md:text-xl"} "Let me help you bring the right solutions into your business."))))))))