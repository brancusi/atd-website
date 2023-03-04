(ns atd.components.hero-header
  (:require [helix.core :refer [$]]
            [helix.hooks :as hooks]
            [helix.dom :as d]
            ["hls.js" :as Hls]
            [atd.components.hover-title :refer [hover-title]]
            [atd.components.ui.audio-toggle :refer [audio-toggle]]

            [atd.components.nav-link :refer [nav-link]]
            [atd.components.ui.icon-button :refer [icon-button]]

            ["@heroicons/react/24/outline" :as icons]
            ["react-svg" :refer [ReactSVG]]
            [applied-science.js-interop :as j]
            ["gsap" :refer [gsap]]
            [atd.lib.defnc :refer [defnc]]))

(defnc hero-header
  []
  (let [video-ref (hooks/use-ref "video-ref")
        ref (hooks/use-ref "yo")
        hover-title-ref (hooks/use-ref "hover-title-ref")

        [current-section set-current-section!] (hooks/use-state nil)
        [audio-muted? set-audio-muted!] (hooks/use-state true)

        listener (fn []
                   (.play @video-ref))

        nav-click-handler (hooks/use-callback
                           :auto-deps
                           (fn
                             [{:keys [section-id]}]
                             (.to gsap
                                  js/window
                                  #js{:scrollTo (str "#" section-id)})))

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
                                          :duration 0.2})))

        toggle-audio (hooks/use-callback
                      [video-ref audio-muted?]
                      (fn
                        [_]
                        (set-audio-muted! (not audio-muted?))))]

    (hooks/use-effect
     :once
     (let [vidhls (new Hls)]
       (.loadSource vidhls "https://stream.mux.com/l02cq1uS4sXBEGdQJNdYVDL7KoTNEreRDJymmk01NSN7c.m3u8")
       (.attachMedia vidhls @video-ref)
       (fn []
         (.destroy vidhls))))

    (d/div {:ref ref
            :class "relative h-screen overflow-hidden items-center flex justify-items-center justify-center"}

           ($ hover-title
              {:hover-title-ref hover-title-ref
               :title current-section})

           (d/video {:ref video-ref
                     :muted audio-muted?
                     :autoPlay true
                     :controls false
                     :loop true
                     :class "w-full h-full object-cover"})

           (d/div {:class "p-2 cursor-pointer absolute right-4 bottom-4 flex middle hover:text-white text-slate-300"
                   :on-click toggle-audio}
                  ($
                   (if audio-muted?
                     icons/SpeakerWaveIcon
                     icons/SpeakerXMarkIcon) {:class "w-6 h-6"}))

           (d/div {:class "absolute flex flex-col"}
                  (map (fn [id]
                         ($ nav-link
                            {:title id
                             :section-id id
                             :on-mouse-over-handler nav-mouse-over-handler
                             :on-mouse-out-handler nav-mouse-out-handler
                             :on-click-handler nav-click-handler}))
                       ["art" "tech" "design"])))))

