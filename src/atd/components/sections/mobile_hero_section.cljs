(ns atd.components.sections.mobile-hero-section
  (:require [atd.components.elements.video-background :refer [video-background]]
            [atd.hooks.use-scroll-trigger :refer [use-scroll-trigger]]
            [atd.lib.defnc :refer [defnc]]
            [atd.providers.main-provider :refer [use-main-state]]
            [atd.components.writing-card :refer [writing-card]]
            [helix.core :refer [$]]
            [helix.dom :as d]
            [helix.hooks :as hooks]))

(defnc mobile-hero-section
  []
  (let [[state dispatch!] (use-main-state)
        outer-ctx (hooks/use-ref "outer-ctx")
        [visited? is-active?] (use-scroll-trigger outer-ctx {:end "bottom"})]

    (d/div {:id "video"
            :ref outer-ctx
            :class "relative
                    h-full
                    w-full
                    
                    overflow-hidden"}

           (d/div {:class "w-screen h-screen 
                           flex
                           relative 
                           flex items-center justify-items-center justify-center"}

                  ($ video-background {:playback-id "l02cq1uS4sXBEGdQJNdYVDL7KoTNEreRDJymmk01NSN7c"
                                       :should-play? is-active?})

                  ($ writing-card
                     (d/div {:class "text-slate-800 flex justify-center flex-col w-4/5 md:w-auto bg-white/50 backdrop-blur-md p-8"}
                            (d/p {:class "text-md md:text-xl mb-4"} "I'm Aram. Hello.")
                            (d/p {:class "text-md md:text-xl mb-4"} "I help companies build their internal software, systems, and processes so they can run more efficiently.")
                            (d/p {:class "text-md md:text-xl mb-4"} "I believe in balancing tech and design, supported by the promise of art over science.")
                            (d/p {:class "text-md md:text-xl"} "Let me help you bring the right solutions into your business.")))))))