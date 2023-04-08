(ns atd.components.sections.contact-section
  (:require [helix.core :refer [$]]
            [helix.hooks :as hooks]
            [helix.dom :as d]
            ["gsap" :refer [gsap]]
            [atd.components.elements.rotating-lazy-image-gallery :refer [rotating-lazy-image-gallery]]
            [atd.hooks.use-scroll-trigger :refer [use-scroll-trigger]]
            ["gsap/SplitText" :refer [SplitText]]
            [atd.api.cms :refer [get-gallery-images!] :as cms]
            [atd.components.elements.lazy-image :refer [lazy-image]]
            [applied-science.js-interop :as j]
            [atd.lib.defnc :refer [defnc]]))

(defnc contact-section [{:keys [gradient-class
                                is-visible?
                                force-on?]}]
  (let [outer-ctx (hooks/use-ref "outer-ctx")
        [background-images set-background-images!] (hooks/use-state nil)

        [visited? is-active?] (use-scroll-trigger outer-ctx)]

    (hooks/use-effect
     :once
     (get-gallery-images! "various-tech-shots" set-background-images!))

    (d/section {:ref outer-ctx
                :class "h-screen 
                    w-screen
                    flex
                    items-end
                    justify-center
                    
                    bg-slate-700
                    relative"}

               (hooks/use-memo
                [is-active? visited? background-images]
                (d/div {:class "z-10 absolute w-full h-full"}
                       ($ rotating-lazy-image-gallery {:images background-images
                                                       :transition {:duration 0.3
                                                                    :opacity 1}
                                                       :should-play? is-active?
                                                       :should-load? visited?
                                                       :rate 3000})))


               (d/div
                {:class "flex flex-col w-full h-2/5 z-20 items-center justify-center bg-black/50 backdrop-blur-sm"} ; Add items-center and justify-center here
                (d/div {:class "flex flex-col justify-between w-4/5 h-4/5"}
                       (d/div
                        {:class "
                                  font-fira-code
                                  font-light
                                  italic
                                  text-white
                                  text-md
                                  "}
                        "If you want to know something. Become that thing, and then study yourself.")

                       (d/div
                        (d/div
                         {:class "font-fira-code
                                  font-light
                                  text-white
                                  text-2xl"}
                         "Why not reach out?")

                        (d/a
                         {:href "mailto:az@atd.dev?subject=Let's%20build"
                          :target "_blank"
                          :class "font-fira-code
                      font-light
                      text-slate-300 
                      text-2xl
                      "}
                         "az@atd.dev"))

                       (d/div {:class "flex"}
                              (d/span {:class "text-xs text-white font-fira-code font-light"}
                                      "Copyright © 2023 Art Tech Design Inc.")))))))