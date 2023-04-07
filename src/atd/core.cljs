(ns atd.core

  (:require ["gsap" :refer [gsap]]
            ["gsap/ScrollToPlugin" :refer [ScrollToPlugin]]
            ["gsap/ScrollTrigger" :refer [ScrollTrigger]]
            ["gsap/SplitText" :refer [SplitText]]
            ["react-dom/client" :as rdom]
            [atd.components.navs.logo-nav :refer [logo-nav]]
            [atd.components.navs.side-nav :refer [side-nav]]
            [atd.components.section-transitioner :refer [section-transitioner]]
            [atd.lib.defnc :refer [defnc]]
            [atd.providers.main-provider :refer [MainProvider]]
            [atd.reducers.requires]
            [atd.services.router :refer [router]]
            [helix.core :refer [$]]
            [mount.core :as mount]))

(defnc app []
  ($ MainProvider {:default-state {:current-section "hero"
                                   :current-subsection "start"}}
     ($ router
        ($ logo-nav)
        #_($ side-nav)
        ($ section-transitioner))))

(defonce root (rdom/createRoot (js/document.getElementById "app")))

(defn start
  []
  (tap> "Starting app")
  ;; Register all gsap plugins
  (.registerPlugin gsap ScrollToPlugin)
  (.registerPlugin gsap ScrollTrigger)
  (.registerPlugin gsap SplitText)

  (.render root ($ app)))

(defn init!
  []
  (mount/start)
  (start))
