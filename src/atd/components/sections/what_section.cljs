(ns atd.components.sections.what-section
  (:require [helix.core :refer [$]]
            [helix.hooks :as hooks]
            [helix.dom :as d]
            ["gsap" :refer [gsap]]
            [atd.hooks.use-scroll-trigger :refer [use-scroll-trigger]]
            ["gsap/SplitText" :refer [SplitText]]
            [applied-science.js-interop :as j]
            [atd.lib.defnc :refer [defnc]]))

(defnc what-section [{:keys [gradient-class
                             is-visible?
                             force-on?]}]
  (let [outer-ctx (hooks/use-ref "outer-ctx")
        text-ref (hooks/use-ref "text-ref")
        [tl _] (hooks/use-state (new (.-timeline gsap) #js{:paused true}))
        [visited? is-active?] (use-scroll-trigger outer-ctx)]

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
                :class (str "h-screen 
                             w-screen
                             font-medium
                             font-fira-code
                             flex
                             items-center
                             justify-center"
                            " "
                            (if gradient-class
                              gradient-class
                              "orange-grad"))}

               (d/div {:ref text-ref
                       :class "text-white flex items-center justify-center h-full flex-col"}
                      (d/p {:class "text-5xl font-bold mb-2"} "What")
                      (d/div {:class "text-2xl space-y-4"}
                             (d/div {:class "flex "}
                                    (d/div {:class "mr-4 w-32"}
                                           ":build")
                                    (d/div
                                     "apps, systems, processes"))

                             (d/div {:class "flex "}
                                    (d/div {:class "mr-4 w-32"}
                                           ":with")
                                    (d/div
                                     "clj, cljs, ts, js"))

                             (d/div {:class "flex "}
                                    (d/div {:class "mr-4 w-32"}
                                           ":store")
                                    (d/div
                                     "postgres, datomic, datascript, asami"))

                             (d/div {:class "flex "}
                                    (d/div {:class "mr-4 w-32"}
                                           ":proto")
                                    (d/div
                                     "retool, make, fibery, coda"))

                             (d/div {:class "flex "}
                                    (d/div {:class "mr-4 w-32"}
                                           ":finish")
                                    (d/div
                                     "helix, fulcro, reframe, remix, nextjs"))

                             (d/div {:class "flex "}
                                    (d/div {:class "mr-4 w-32"}
                                           ":for")
                                    (d/div
                                     "startups, smb"))

                             (d/div {:class "flex "}
                                    (d/div {:class "mr-4 w-32"}
                                           ":where")
                                    (d/div
                                     "beauty matters")))))))