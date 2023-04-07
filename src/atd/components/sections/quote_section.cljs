(ns atd.components.sections.quote-section
  (:require [helix.core :refer [$]]
            [helix.hooks :as hooks]
            [helix.dom :as d]
            ["gsap" :refer [gsap]]
            [atd.hooks.use-scroll-trigger :refer [use-scroll-trigger]]
            ["gsap/SplitText" :refer [SplitText]]
            [applied-science.js-interop :as j]
            [atd.lib.defnc :refer [defnc]]))

(defnc quote-section [{:keys [gradient-class
                              quote
                              header
                              is-visible?
                              children
                              from
                              to]}]
  (let [from-transition (if from
                          (clj->js from)
                          #js{:opacity 0
                              :duration 0.5
                              :ease "expo.inOut",
                              :stagger 0.1})

        to-transition (if to
                        (clj->js to)
                        #js{:opacity 1
                            :duration 0.15
                            :ease "expo.inOut",
                            :stagger 0.025})

        outer-ctx (hooks/use-ref "outer-ctx")
        text-ref (hooks/use-ref "text-ref")
        [tl _] (hooks/use-state (new (.-timeline gsap) #js{:paused true}))

        on-active-callback (fn [_] (.play tl))

        [visited? is-active?] (use-scroll-trigger outer-ctx)]

    (hooks/use-layout-effect
     [quote text-ref is-visible?]
     (let [splitter (when @text-ref
                      (new SplitText
                           @text-ref
                           #js{:type "words,chars"
                               :charsClass "playable-type-char"}))
           chars (when splitter
                   (.-chars splitter))

           ctx (.context gsap (fn []
                                (-> tl
                                    (.from chars from-transition)
                                    (.to chars to-transition)))
                         outer-ctx)]
       (fn [] (.revert ctx))))

    (hooks/use-effect
     [is-active?]

     (when is-active?
       (.play tl)))

    (d/section {:ref outer-ctx
                :class (str "h-screen 
                             w-screen
                             font-medium
                             font-fira-code
                             flex
                             items-center
                             justify-center"
                            " "
                            (when gradient-class
                              gradient-class))}

               (d/div {:ref text-ref
                       :class "w-full h-full items-center justify-center flex"}
                      (if children
                        children
                        (d/div {:class "text-white flex items-center justify-center h-full flex-col md:w-3/4 w-3/4"}
                               (d/p {:class "text-4xl md:text-5xl font-bold mb-2"} header)
                               (d/div (mapv (fn [line]
                                              (d/p {:key line
                                                    :class "text-2xl md:text-4xl"} line))
                                            quote))))))))