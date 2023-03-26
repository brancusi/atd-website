(ns atd.components.sections.contact-section
  (:require [helix.core :refer [$]]
            [helix.hooks :as hooks]
            [helix.dom :as d]
            ["gsap" :refer [gsap]]
            [atd.hooks.use-scroll-trigger :refer [use-scroll-trigger]]
            ["gsap/SplitText" :refer [SplitText]]
            [applied-science.js-interop :as j]
            [atd.lib.defnc :refer [defnc]]))

(defnc contact-section [{:keys [gradient-class
                                is-visible?
                                force-on?]}]
  (let [outer-ctx (hooks/use-ref "outer-ctx")
        text-ref (hooks/use-ref "text-ref")
        [tl _] (hooks/use-state (new (.-timeline gsap) #js{:paused true}))
        is-active? (use-scroll-trigger outer-ctx)]

    (hooks/use-layout-effect
     [text-ref is-visible?]
     (let [splitter (when @text-ref
                      (new SplitText
                           @text-ref
                           #js{:type "words,chars"
                               :charsClass "playable-type-char"}))
           words (when splitter
                   (.-words splitter))

           ctx (.context gsap (fn []
                                (-> tl
                                    (.from words #js{:opacity 0
                                                     :duration 0.5
                                                     :ease "expo.inOut",
                                                     :stagger 0.1})
                                    (.to words #js{:opacity 1

                                                   :duration 0.15
                                                   :ease "expo.inOut",
                                                   :stagger 0.025})))
                         outer-ctx)]
       (fn [] (.revert ctx))))

    (hooks/use-effect
     [is-active? force-on?]

     (when (or force-on? is-active?)
       (.play tl)))

    (d/section {:ref outer-ctx
                :class "h-screen 
                    w-screen
                    flex
                    items-center
                    bg-black
                    relative"}
               (d/img {:src "https://atddev.imgix.net/az-portrait.tif?fm=jpg&w=1500&q=60"
                       :class "z-10
                           object-cover w-full h-full absolute"})

               (d/div
                {:class "flex flex-col w-1/2 z-20 items-center justify-center"} ; Add items-center and justify-center here

                (d/div
                 {:class "flex flex-col w-auto justify-center"} ; Change flex-1 to w-auto
                 (d/div
                  {:class "font-fira-code
                      font-light
                      text-white
                      text-4xl"}
                  "Let's play")

                 (d/a
                  {:href "mailto:az@atd.dev?subject=Let's%20play"
                   :target "_blank"
                   :class "font-fira-code
                      font-light
                      text-fuchsia-600 
                      text-4xl
                      "}
                  "az@atd.dev"))))))