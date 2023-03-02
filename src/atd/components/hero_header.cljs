(ns atd.components.hero-header
  (:require [helix.core :refer [$]]
            [helix.hooks :as hooks]
            [helix.dom :as d]
            ["hls.js" :as Hls]

            [atd.components.nav-link :refer [nav-link]]
            [applied-science.js-interop :as j]
            ["gsap" :refer [gsap]]
            [atd.lib.defnc :refer [defnc]]))

(defnc hero-header
  []
  (let [video-ref (hooks/use-ref "video-ref")
        ref (hooks/use-ref "yo")

        listener (fn []
                   (.play @video-ref))
        nav-click-handler (hooks/use-callback
                           :auto-deps
                           (fn
                             [{:keys [section-id]}]
                             (.to gsap
                                  js/window
                                  #js{:scrollTo (str "#" section-id)})))]

    (hooks/use-effect
     :always
     (let [vidhls (new Hls)]
       (.loadSource vidhls "https://stream.mux.com/l02cq1uS4sXBEGdQJNdYVDL7KoTNEreRDJymmk01NSN7c.m3u8")
       (.attachMedia vidhls @video-ref)
       (fn []
         (.destroy vidhls))))

    (d/div {:ref ref
            :class "relative h-screen overflow-hidden items-center flex justify-items-center justify-center"}

           (d/video {:ref video-ref
                     :muted true
                     :autoPlay true
                     :controls false
                     :loop true
                     :class "w-full h-full object-cover"})

           (d/div {:class "absolute flex flex-col"}
                  ($ nav-link {:title "art"
                               :section-id "art"
                               :on-click-handler nav-click-handler})
                  ($ nav-link {:title "tech"
                               :section-id "tech"
                               :on-click-handler nav-click-handler})
                  ($ nav-link {:title "design"
                               :section-id "design"
                               :on-click-handler nav-click-handler})))))

;; <mux-player
;; stream-type= "on-demand"
;; playback-id= "6BCPWfUt02MYR020102GNrBdZFpN7HmGhTH4LxMIl00BZh02A"
;; metadata-video-title= "Placeholder (optional)"
;; metadata-viewer-user-id= "Placeholder (optional)"
;; primary-color= "#FFFFFF"
;; secondary-color= "#000000" >
;; </mux-player>
