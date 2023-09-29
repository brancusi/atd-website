(ns atd.components.elements.video-background
  (:require ["@heroicons/react/24/outline" :as icons]
            ["@mux/mux-player-react$default" :as MuxPlayer]
            [applied-science.js-interop :as j]
            [atd.components.elements.lazy-image :refer [lazy-image]]
            [atd.hooks.use-can-play-background-video :refer [use-can-play-background-video]]
            [atd.lib.defnc :refer [defnc]]
            [helix.core :refer [$]]
            [helix.dom :as d]
            [helix.hooks :as hooks]))

(defnc video-background
  [{:keys [should-play? playback-id]}]
  (let [video-ref (hooks/use-ref "video-ref")
        [audio-muted? set-audio-muted!] (hooks/use-state true)
        toggle-audio (hooks/use-callback
                      [video-ref audio-muted?]
                      (fn
                        [_]
                        (set-audio-muted! (not audio-muted?))))]

    (hooks/use-effect
     [should-play? video-ref]
     (when @video-ref
       (if should-play?
         (.play @video-ref)
         (when (not (j/get @video-ref :paused))
           (.pause @video-ref)))))

    (d/div {:class "w-full h-full relative background-video"}
           ($ MuxPlayer
              {:playbackId playback-id
               :ref video-ref
               :class "w-full h-full object-cover"
               :muted audio-muted?
               :loop true
               :playsInline ""
               :autoPlay false
               :streamType "on-demand"
               :preferplayback "mse"})

           (d/div {:class "p-2 cursor-pointer absolute right-4 bottom-4 flex middle hover:text-white text-slate-300"
                   :on-click toggle-audio}
                  ($
                   (if audio-muted?
                     icons/SpeakerWaveIcon
                     icons/SpeakerXMarkIcon) {:className "w-6 h-6"})))))