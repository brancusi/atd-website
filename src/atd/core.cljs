(ns atd.core
  (:require ["gsap" :refer [gsap]]
            ["gsap/ScrollToPlugin" :refer [ScrollToPlugin]]
            ["gsap/ScrollTrigger" :refer [ScrollTrigger]]
            ["gsap/SplitText" :refer [SplitText]]
            ["react-dom/client" :as rdom]

            [atd.lib.defnc :refer [defnc]]
            [atd.providers.main-provider :refer [MainProvider]]
            [atd.reducers.requires]
            [atd.services.router :refer [router]]
            [atd.views.landing-view :refer [landing-view]]
            [helix.core :refer [$]]
            [helix.dom :as d]
            [helix.hooks :as hooks]
            [mount.core :as mount]
            [portal.web :as p]))

(defnc app []
  ($ MainProvider {:default-state {:current-section "hero"
                                   :current-subsection "start"}}
     ($ router
        ($ landing-view))))

(defonce root (rdom/createRoot (js/document.getElementById "app")))

(mount/defstate ^{:on-reload :noop} portal
  :start
  (do
    (js/console.log "Starting portal")
    (p/open)
    (add-tap #'p/submit))

  :stop
  (when portal
    (js/console.log "Stopping portal")
    (remove-tap #'p/submit)
    (p/close)))

(defn start
  []
  (js/console.log "Calling start")

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