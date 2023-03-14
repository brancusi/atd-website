(ns atd.components.hero-header
  (:require ["@heroicons/react/24/outline" :as icons]
            ["gsap" :refer [gsap]]
            ["hls.js" :as Hls]
            [atd.providers.main-provider :refer [use-main-state]]
            [atd.components.ui.playable-text :refer [playable-text]]
            [atd.components.hover-title :refer [hover-title]]
            [atd.components.nav-link :refer [nav-link]]
            [atd.hooks.use-hover :refer [use-hover]]
            [atd.lib.defnc :refer [defnc]]
            [helix.core :refer [$]]
            [helix.dom :as d]
            [helix.hooks :as hooks]))

(defnc hero-header
  []
  (let [[state dispatch!] (use-main-state)
        video-ref (hooks/use-ref "video-ref")
        ref (hooks/use-ref "yo")
        hover-title-ref (hooks/use-ref "hover-title-ref")

        #_#_[current-section set-current-section!] (hooks/use-state nil)
        [current-section set-current-section!] (hooks/use-state nil)
        [audio-muted? set-audio-muted!] (hooks/use-state true)

        listener (fn []
                   (.play @video-ref))

        nav-click-handler (hooks/use-callback
                           :auto-deps
                           (fn
                             [{:keys [section-id]}]
                             (dispatch! [:navigate! section-id])
                             #_(.to gsap
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
            :id "hero"
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
                     icons/SpeakerXMarkIcon) {:className "w-6 h-6"}))

           (d/div {:class "absolute "}
                  (map (fn [[id writing]]
                         (d/div {:key id
                                 :class "flex"}
                                (d/div {:class "relative flex"}
                                       ($ nav-link
                                          {:title id
                                           :writing writing
                                           :section-id id
                                           :on-mouse-over-handler nav-mouse-over-handler
                                           :on-mouse-out-handler nav-mouse-out-handler
                                           :on-click-handler nav-click-handler})

                                       #_(d/div {:class "whitespace-nowrap
                                                absolute
                                                translate-x-full
                                                bottom-2
                                                
                                                self-baseline
                                                right-0
                                                 pl-6
                                                "}
                                                ($ playable-text {:text "Hi there my sweet"
                                                                  :is-playing? true})))))
                       [[(str "art") "something danger"] ["tech" "another fault"] ["design" "magic"]])))))

