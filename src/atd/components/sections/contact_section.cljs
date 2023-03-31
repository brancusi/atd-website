(ns atd.components.sections.contact-section
  (:require [helix.core :refer [$]]
            [helix.hooks :as hooks]
            [helix.dom :as d]
            ["gsap" :refer [gsap]]
            [atd.hooks.use-scroll-trigger :refer [use-scroll-trigger]]
            ["gsap/SplitText" :refer [SplitText]]
            [atd.components.elements.lazy-image :refer [lazy-image]]
            [applied-science.js-interop :as j]
            [atd.lib.defnc :refer [defnc]]))

(defnc contact-section [{:keys [gradient-class
                                is-visible?
                                force-on?]}]
  (let [outer-ctx (hooks/use-ref "outer-ctx")
        [visited? is-active?] (use-scroll-trigger outer-ctx)]

    (d/section {:ref outer-ctx
                :class "h-screen 
                    w-screen
                    flex
                    items-center
                    bg-black
                    relative"}

               (hooks/use-memo
                [visited?]
                ($ lazy-image {:class "z-10 absolute"
                               :src "https://atddev.imgix.net/az-portrait.tif?fm=jpg&w=1500&q=60"
                               :focal-point [0.7, 0.7, 1]
                               :should-load? visited?}))
               #_(d/img {:src "https://atddev.imgix.net/az-portrait.tif?fm=jpg&w=1500&q=60"
                         :class "z-10
                           object-cover w-full h-full absolute"})

               #_(d/div
                  {:class "flex flex-col w-1/2 z-20 items-center justify-center"} ; Add items-center and justify-center here

                  (d/div
                   {:class "flex flex-col md:w-auto justify-center"} ; Change flex-1 to w-auto
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