(ns atd.components.ui.playable-text
  (:require [helix.core :refer [$]]
            [helix.hooks :as hooks]
            [helix.dom :as d]
            ["gsap" :refer [gsap]]
            ["gsap/SplitText" :refer [SplitText]]
            [applied-science.js-interop :as j]
            [atd.lib.defnc :refer [defnc]]))

(defnc playable-text [{:keys [text is-playing?]}]
  (let [outer-ctx (hooks/use-ref "outer-ctx")
        background-ref (hooks/use-ref "background-ref")
        text-ref (hooks/use-ref "text-ref")
        [tl _] (hooks/use-state (new (.-timeline gsap) #js{:paused true}))]

    (hooks/use-layout-effect
     [text text-ref is-playing?]
     (let [splitter (when @text-ref
                      (new SplitText
                           @text-ref
                           #js{:type "words,chars"
                               :charsClass "playable-type-char"}))
           chars (when splitter
                   (.-chars splitter))

           ctx (.context gsap (fn []
                                (-> tl
                                    (.from @background-ref #js{:width "0"
                                                               :duration 0.15
                                                               :ease "expo.inOut"})
                                    (.to @background-ref #js{:width "100%"
                                                             :duration 0.15
                                                             :ease "expo.inOut"})
                                    (.from chars #js{:opacity 0
                                                     :duration 0.15
                                                     :ease "expo.inOut",
                                                     :stagger 0.025})
                                    (.to chars #js{:opacity 1

                                                   :duration 0.15
                                                   :ease "expo.inOut",
                                                   :stagger 0.025})))
                         outer-ctx)]
       (fn [] (.revert ctx))))

    (hooks/use-effect
     [is-playing?]

     (if is-playing?
       (.play tl)
       (.reverse tl)))

    (d/div {:ref outer-ctx}
           (d/div {:ref text-ref
                   :class "relative"}
                  (d/div {:ref background-ref
                          :class "absolute h-full playable-text-background"})
                  (d/p {:class "playable-text-chars p-2"} text)))))