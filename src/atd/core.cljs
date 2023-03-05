(ns atd.core
  (:require [helix.core :refer [$]]
            [helix.hooks :as hooks]
            [helix.dom :as d]
            [atd.components.hero-header :refer [hero-header]]
            [applied-science.js-interop :as j]
            ["gsap" :refer [gsap]]
            ["gsap/ScrollToPlugin" :refer [ScrollToPlugin]]
            ["gsap/ScrollTrigger" :refer [ScrollTrigger]]
            ["gsap/SplitText" :refer [SplitText]]
            ["react-dom/client" :as rdom]

            [atd.lib.defnc :refer [defnc]]
            [mount.core :as mount]))

(defnc landing-section [{:keys [section-id title]}]
  (d/section {:id section-id
              :class "h-screen w-full bg-red-500"}
             (d/p {:class "text-4xl"} title)))

(defnc app []
  ($ :div
     ($ hero-header {})
     ($ landing-section {:section-id "art"
                         :title "Art"})
     ($ landing-section {:section-id "tech"
                         :title "Tech"})
     ($ landing-section {:section-id "design"
                         :title "Design"})))

(defonce root (rdom/createRoot (js/document.getElementById "app")))

(defn start
  []
  (js/console.log "Calling start")
  (.render root ($ app)))

(defn init!
  []
  (js/console.log "Calling init")
  (.registerPlugin gsap ScrollToPlugin)
  (.registerPlugin gsap ScrollTrigger)
  (.registerPlugin gsap SplitText)
  (mount/start)
  (start))