(ns atd.components.hero-header
  (:require ["gsap" :refer [gsap]]
            [atd.components.elements.video-background :refer [video-background]]
            [atd.components.hover-title :refer [hover-title]]
            [atd.components.nav-link :refer [nav-link]]
            [atd.components.elements.rotating-lazy-image-gallery :refer [rotating-lazy-image-gallery]]

            [atd.lib.defnc :refer [defnc]]
            [atd.providers.main-provider :refer [use-main-state]]
            [helix.core :refer [$]]
            [atd.components.elements.lazy-image :refer [lazy-image]]
            [helix.dom :as d]
            [helix.hooks :as hooks]))

(def titles
  [["art" "art without tech is a trap of the generation"]
   ["tech" "tech without design is asking for trouble"]
   ["design" "design without art is the termination"]])

(def images
  [{:src "https://atddev.imgix.net/output-lg.gif?fm=jpg&w=1500&q=60"
    :opts {}}
   {:src "https://atddev.imgix.net/1.jpg?fm=jpg&h=1500&q=60"
    :opts {}}
   {:src "https://atddev.imgix.net/2.jpg?fm=jpg&h=1500&q=60"
    :opts {}}
   {:src "https://atddev.imgix.net/3.jpg?fm=jpg&h=1500&q=60"
    :opts {}}
   {:src "https://atddev.imgix.net/4.jpg?fm=jpg&h=1500&q=60"
    :opts {}}])

(defnc hero-menu
  [{:keys [data over out click]}]

  (d/div {:class "absolute font-fira-code"}
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

(defnc hero-header
  []
  (let [[state dispatch!] (use-main-state)

        hover-title-ref (hooks/use-ref "hover-title-ref")

        [current-section set-current-section!] (hooks/use-state nil)

        nav-click-handler (hooks/use-callback
                           :auto-deps
                           (fn
                             [{:keys [section-id]}]
                             (dispatch! [:navigate! section-id])))

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
                                 [{:keys [section-id]}]
                                 (.to gsap
                                      @hover-title-ref
                                      #js{:opacity 0
                                          :duration 0.2})))]

    (d/div {:id "hero"
            :class "relative
                    h-screen
                    overflow-hidden"}

          ;;  Desktop
           (d/div {:class "w-screen h-screen 
                           hidden md:flex 
                           relative 
                           flex items-center justify-items-center justify-center"}

                  ($ video-background)

                  ($ hover-title
                     {:hover-title-ref hover-title-ref
                      :title current-section})

                  ($ hero-menu {:data titles
                                :over nav-mouse-over-handler
                                :out nav-mouse-out-handler
                                :click nav-click-handler}))

          ;;  Mobile  
           (d/div {:class "h-screen 
                           md:hidden 
                           relative 
                           flex items-center
                           justify-items-center justify-center"}
                  ($ rotating-lazy-image-gallery {:images images
                                                  :should-load? true})
                  #_($ lazy-image {:class "w-full h-full object-cover"
                                   :src "https://atddev.imgix.net/output-lg.gif?fm=jpg&w=1500&q=60"
                                   :focal-point [0.4, 0.7, 1]
                                   :should-load? true})))))
