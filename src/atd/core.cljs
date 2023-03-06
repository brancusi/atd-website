(ns atd.core
  (:require [helix.core :refer [$]]
            [helix.hooks :as hooks]
            [atd.providers.main-provider :refer [MainProvider]]
            [helix.dom :as d]
            [atd.components.hero-header :refer [hero-header]]
            [atd.components.elements.section-background :refer [section-background]]
            [applied-science.js-interop :as j]
            ["gsap" :refer [gsap]]
            ["gsap/ScrollToPlugin" :refer [ScrollToPlugin]]
            ["gsap/ScrollTrigger" :refer [ScrollTrigger]]
            ["gsap/SplitText" :refer [SplitText]]
            ["react-dom/client" :as rdom]
            [atd.services.router :refer [router]]

            [atd.reducers.requires]

            [atd.lib.defnc :refer [defnc]]
            [mount.core :as mount]))

(defnc landing-section [{:keys [section-id title]}]
  (d/section {:id section-id
              :class "relative h-screen w-full orange-grad"}
             (d/p {:class "text-4xl"} title)))

(defnc app []
  ($ MainProvider {:default-state {:current-section "landing"
                                   :current-subsection "start"}}
     ($ router)
     ($ :div
        ($ landing-section {:section-id "art"
                            :title "Art"})
        ($ hero-header)
        ($ landing-section {:section-id "tech"
                            :title "Tech"})
        ($ landing-section {:section-id "design"
                            :title "Design"}))))

(defonce root (rdom/createRoot (js/document.getElementById "app")))

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