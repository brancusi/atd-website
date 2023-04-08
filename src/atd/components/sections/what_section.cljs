(ns atd.components.sections.what-section
  (:require [helix.core :refer [$ <>]]
            [helix.hooks :as hooks]
            [helix.dom :as d]
            ["gsap" :refer [gsap]]
            [atd.utils.for-indexed :refer [for-indexed]]
            [atd.hooks.use-scroll-trigger :refer [use-scroll-trigger]]
            ["gsap/SplitText" :refer [SplitText]]
            [applied-science.js-interop :as j]
            [atd.api.cms :refer [get-what-copy!]]
            [atd.lib.defnc :refer [defnc]]))

(defnc thing-block
  [{:keys [is-last? name copy url]}]
  (let [has-copy? copy]
    (<>
     (d/span {:class (if has-copy? "font-light my-buddy" "text-white")}
             name)
     (when (not is-last?)
       (d/span
        ", ")))))

(defnc what-section [{:keys [gradient-class
                             is-visible?
                             force-on?]}]
  (let [outer-ctx (hooks/use-ref "outer-ctx")
        text-ref (hooks/use-ref "text-ref")
        [what-data set-what-data!] (hooks/use-state [])
        has-data? (seq what-data)
        [tl _] (hooks/use-state (new (.-timeline gsap) #js{:paused true}))
        [visited? is-active?] (use-scroll-trigger outer-ctx)]

    (hooks/use-effect
     :once
     (get-what-copy! set-what-data!))

    (hooks/use-layout-effect
     [text-ref what-data visited?]
     (let [splitter (when @text-ref
                      (new SplitText
                           @text-ref
                           #js{:type "words"
                               :charsClass "what-chars"
                               :wordsClass "what-words"}))
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
     [visited? force-on?]

     (when (or force-on? visited?)
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

               (when (and visited?
                          has-data?)
                 (d/div {:ref text-ref
                         :class "text-white flex items-center justify-center h-full flex-col w-4/5 "}
                        (d/p {:class "text-2xl font-bold mb-2"} "What")
                        (d/div {:class "flex flex-col w-full"}
                               (d/div {:class "text-lg space-y-4 w-full "}
                                      (mapv (fn [{:keys [title things]}]
                                              (d/div {:key title
                                                      :class "flex "}
                                                     (d/div {:class "flex-grow"}
                                                            (str ":" title))
                                                     (d/div {:class "w-3/5"}
                                                            (for-indexed [{:keys [name copy url]} idx things]
                                                                         (let [is-last? (= idx (dec (count things)))]
                                                                           ($ thing-block
                                                                              {:key idx
                                                                               :name name
                                                                               :copy copy
                                                                               :url url
                                                                               :is-last? is-last?}))))))
                                            what-data)))))

               #_(d/div {:ref text-ref
                         :class "text-white flex items-center justify-center h-full flex-col w-4/5"}
                        (d/p {:class "text-2xl font-bold mb-2"} "What")
                        (d/div {:class "flex flex-col"}
                               (d/div {:class "text-lg space-y-4"}
                                      (d/div {:class "flex "}
                                             (d/div {:class "flex-grow"}
                                                    ":build")
                                             (d/div {:class "w-3/5"}
                                                    "apps, systems, processes"))
                                      (d/div {:class "flex"}
                                             (d/div {:class "flex-grow"}
                                                    ":with")
                                             (d/div {:class "w-3/5"}
                                                    "clj, cljs, ts, js"))
                                      (d/div {:class "flex "}
                                             (d/div {:class "flex-grow"}
                                                    ":store")
                                             (d/div {:class "w-3/5"}
                                                    "postgres, datomic, datascript, asami"))
                                      (d/div {:class "flex "}
                                             (d/div {:class "flex-grow"}
                                                    ":proto")
                                             (d/div {:class "w-3/5"}
                                                    "retool, make, fibery, coda"))
                                      (d/div {:class "flex "}
                                             (d/div {:class "flex-grow"}
                                                    ":finish")
                                             (d/div {:class "w-3/5"}
                                                    "helix, fulcro, reframe, remix, nextjs"))
                                      (d/div {:class "flex "}
                                             (d/div {:class "flex-grow"}
                                                    ":for")
                                             (d/div {:class "w-3/5"}
                                                    "startups, smb"))
                                      (d/div {:class "flex "}
                                             (d/div {:class "flex-grow"}
                                                    ":where")
                                             (d/div {:class "w-3/5"}
                                                    "beauty matters"))))))))