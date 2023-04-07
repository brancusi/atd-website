(ns atd.components.hero-header
  (:require ["gsap" :refer [gsap]]
            [atd.components.elements.video-background :refer [video-background]]
            [atd.components.hover-title :refer [hover-title]]
            [atd.components.nav-link :refer [nav-link]]
            [atd.components.elements.rotating-lazy-image-gallery :refer [rotating-lazy-image-gallery]]
            [atd.api.cms]
            [atd.lib.defnc :refer [defnc]]
            [atd.providers.main-provider :refer [use-main-state]]
            [atd.components.sections.quote-section :refer [quote-section]]
            [helix.core :refer [$]]
            [atd.hooks.use-scroll-trigger :refer [use-scroll-trigger]]
            [atd.components.elements.lazy-image :refer [lazy-image]]

            [atd.api.cms :refer [get-hero-images!] :as cms]
            [helix.dom :as d]
            [helix.hooks :as hooks]))

(def titles
  [["art" "art without tech is a trap of the generation"]
   ["tech" "tech without design is asking for trouble"]
   ["design" "design without art is the termination"]])

(def images
  [{:src "https://atddev.imgix.net/output-lg.gif?fm=jpg&w=500&q=60"
    :opts {}}
   {:src "https://atddev.imgix.net/1.jpg?fm=jpg&h=500&q=60"
    :opts {}}
   {:src "https://atddev.imgix.net/2.jpg?fm=jpg&h=500&q=60"
    :opts {}}
   {:src "https://atddev.imgix.net/3.jpg?fm=jpg&h=500&q=60"
    :opts {}}
   {:src "https://atddev.imgix.net/4.jpg?fm=jpg&h=500&q=60"
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
        outer-ctx (hooks/use-ref "outer-ctx")
        [visited? is-active?] (use-scroll-trigger outer-ctx {:end "bottom"})
        [hero-images set-hero-images!] (hooks/use-state [])

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

    (hooks/use-effect
     :once
     (get-hero-images! set-hero-images!))

    (d/div {:id "hero"
            :ref outer-ctx
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
                  (d/div {:class "z-10 absolute w-full h-full"}
                         ($ rotating-lazy-image-gallery {:images hero-images
                                                         :transition {:duration 0.3
                                                                      :opacity 1}
                                                         :should-play? is-active?
                                                         :should-load? visited?
                                                         :rate 2000}))
                  (d/div {:class "z-20 absolute"}
                         ($ quote-section {:section-id "main-quote"}
                            (d/div {:class "text-slate-900 flex items-center justify-center h-full flex-col md:w-3/4 w-3/4"}
                                   (d/p {:class "text-xl md:text-4xl mb-4"} "Hi, I'm Aram I'm a software developer with a passion for merging art tech and design.")
                                   (d/p {:class "text-2xl md:text-4xl"} "Let's build your next project together."))))))))
