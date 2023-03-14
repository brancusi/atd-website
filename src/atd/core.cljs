(ns atd.core
  (:require ["gsap" :refer [gsap]]
            ["gsap/ScrollToPlugin" :refer [ScrollToPlugin]]
            ["gsap/ScrollTrigger" :refer [ScrollTrigger]]
            ["gsap/SplitText" :refer [SplitText]]
            ["react-dom/client" :as rdom]
            [atd.components.section-transitioner :refer [section-transitioner]]


            [atd.lib.defnc :refer [defnc]]
            [atd.providers.main-provider :refer [MainProvider]]
            [atd.components.navs.side-nav :refer [side-nav]]
            [atd.reducers.requires]
            [atd.services.router :refer [router]]

            [helix.core :refer [$]]
            [helix.dom :as d]
            [helix.hooks :as hooks]
            [mount.core :as mount]
            [portal.web :as p]))

(defnc app []
  ($ MainProvider {:default-state {:current-section "hero"
                                   :current-subsection "start"}}
     ($ router
        ($ side-nav)
        ($ section-transitioner)
        #_($ landing-view))))

(defonce root (rdom/createRoot (js/document.getElementById "app")))

#_(mount/defstate ^{:on-reload :noop} portal
    :start
    (do
      (js/console.log "Starting portal")
      (add-tap #'p/submit)
      (p/open))

    :stop
    (when portal
      (js/console.log "Stopping portal")
      #_(remove-tap #'p/submit)
      #_(p/close)))

(defn start
  []
  (js/console.log "Calling start")

  (add-tap #'p/submit)
  (p/open)

  ;; Register all gsap plugins
  (.registerPlugin gsap ScrollToPlugin)
  (.registerPlugin gsap ScrollTrigger)
  (.registerPlugin gsap SplitText)



  (.render root ($ app)))



(defn init!
  []
  (js/console.log "Calling init")

  (mount/start)
  (start))