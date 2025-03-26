(ns atd.components.hero-header
  (:require
   [atd.components.fragments.about-me :refer [about-me]]
   [atd.components.sections.quote-section :refer [quote-section]]
   [atd.components.sections.video-section :refer [video-section]]
   [atd.hooks.use-scroll-trigger :refer [use-scroll-trigger]]
   [atd.lib.defnc :refer [defnc]]
   [helix.core :refer [$]]
   [helix.dom :as d]
   [helix.hooks :as hooks]))

(defnc hero-header
  []
  (let [outer-ctx (hooks/use-ref "outer-ctx")
        [visited? is-active?] (use-scroll-trigger outer-ctx {:end "bottom"})]

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
                         ($ video-section {:playback-id "Izp5007Abkc00t4Ubns7pAiqq2zG7JIp01tvAoaVOny7O00"}))
                  (d/div {:class "z-20 absolute w-full h-full"}
                         (d/div {:class "w-full h-full absolute pink-grad opacity-30"})
                         ($ quote-section {:section-id "main-quote"
                                           :from {:opacity 0
                                                  :duration 0.5
                                                  :ease "expo.inOut",
                                                  :stagger 0.02}}
                            ($ about-me)))))))