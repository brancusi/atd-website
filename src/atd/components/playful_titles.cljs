(ns atd.components.playful-titles
  (:require ["gsap" :refer [gsap]]
            [atd.components.elements.video-background :refer [video-background]]
            [atd.components.hover-title :refer [hover-title]]
            [atd.components.nav-link :refer [nav-link]]
            [atd.hooks.use-scroll-trigger :refer [use-scroll-trigger]]
            [atd.lib.defnc :refer [defnc]]
            [atd.providers.main-provider :refer [use-main-state]]

            [helix.core :refer [$]]
            [helix.dom :as d]
            [helix.hooks :as hooks]))

(def titles
  [["art" "art without tech is a trap of the generation"]
   ["tech" "tech without design is asking for trouble"]
   ["design" "design without art is the termination"]])

(defnc hero-menu
  [{:keys [data over out click]}]

  (d/div {:class "absolute 
                  font-fira-code
                  pointer-events-auto"}
         (map (fn [[id writing]]
                (d/div {:key id
                        :class "flex"}
                       (d/div {:class "relative flex"}
                              ($ nav-link
                                 {:title id
                                  :writing writing
                                  :section-id id
                                  :on-mouse-over-handler over
                                  :on-mouse-out-handler out
                                  :on-click-handler click}))))
              data)))

(defnc playful-titles
  []
  (let [[state dispatch!] (use-main-state)


        hover-title-ref (hooks/use-ref "hover-title-ref")

        [current-section set-current-section!] (hooks/use-state nil)


        nav-mouse-over-handler (hooks/use-callback
                                [hover-title-ref]
                                (fn
                                  [{:keys [section-id]}]
                                  (set-current-section! section-id)
                                  (.to gsap
                                       @hover-title-ref
                                       #js{:opacity 0.8
                                           :duration 0.2})))
        nav-mouse-out-handler (hooks/use-callback
                               [hover-title-ref]
                               (fn
                                 []
                                 (.to gsap
                                      @hover-title-ref
                                      #js{:opacity 0
                                          :duration 0.2})))]

    (d/div {:class "
                    relative
                    w-full 
                    h-full 
                    flex
                    items-center
                    justify-items-center
                    justify-center"}


           ($ hover-title
              {:hover-title-ref hover-title-ref
               :title current-section})

           ($ hero-menu {:data titles
                         :over nav-mouse-over-handler
                         :out nav-mouse-out-handler}))))