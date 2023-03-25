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
                        font-medium
                        font-fira-code
                        flex
                        items-center
                        bg-black
                        relative
                        
                        "}
               (d/img {:src "images/portraits/az.jpg"
                       :class "z-10
                               object-cover w-full h-full absolute"})

               (d/div
                {:class "
                         w-1/4
                         
                         font-fira-code
                         font-light
                         text-white 
                         flex
                         flex-col
                         space-y-8
                         
                         z-20"}
                (d/input {:class "text-black bg-white border-8 border-teal-500 outline-none p-4 bg-opacity-90 focus:bg-opacity-100"
                          :placeholder "Name"})
                (d/input {:class "text-black bg-white border-8 border-teal-500 outline-none p-4 bg-opacity-90 focus:bg-opacity-100"
                          :placeholder "Email"})
                (d/textarea {:class "text-black bg-white border-8 border-teal-500 outline-none p-4 bg-opacity-90 focus:bg-opacity-100"
                             :rows 10
                             :placeholder "Tell Me"})
                (d/button
                 {:class "bg-red-500 w-full h-14"
                  :placeholder "Tell Me"}
                 "Do it")))))