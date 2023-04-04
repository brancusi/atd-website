(ns atd.components.elements.video-background
  (:require ["@heroicons/react/24/outline" :as icons]
            ["hls.js" :as Hls :refer [Events]]
            [atd.components.elements.lazy-image :refer [lazy-image]]
            [atd.hooks.use-can-play-background-video :refer [use-can-play-background-video]]
            [atd.lib.defnc :refer [defnc]]
            [helix.core :refer [$]]
            [helix.dom :as d]
            [helix.hooks :as hooks]))

(defnc video-background
  []
  (let [video-ref (hooks/use-ref "video-ref")
        video-test-container (hooks/use-ref "video-test-container")
        {:keys [can-play?
                check-completed?]} (use-can-play-background-video video-test-container)

        [audio-muted? set-audio-muted!] (hooks/use-state true)

        toggle-audio (hooks/use-callback
                      [video-ref audio-muted?]
                      (fn
                        [_]
                        (set-audio-muted! (not audio-muted?))))]

    (hooks/use-effect
     [check-completed? can-play?]
     (when (and check-completed? can-play?)
       (let [vidhls (new Hls)]
         (.on vidhls (aget Events "ERROR") (fn []
                                             (tap> "Error yo")))

         (.on vidhls (aget Events "MEDIA_ATTACHED") (fn []
                                                      (tap> "Media Attached")))

         (.loadSource vidhls "https://stream.mux.com/l02cq1uS4sXBEGdQJNdYVDL7KoTNEreRDJymmk01NSN7c.m3u8")
         (.attachMedia vidhls @video-ref)
         (fn []
           (.destroy vidhls)))))

    (d/div {:class "w-full h-full"}
           (d/video {:ref video-test-container
                     :muted audio-muted?
                     :autoPlay true
                     :controls false
                     :loop false
                     :class "absolute opacity-0 w-1 h-1 top-0 left-0"})

           (when check-completed?
             (if can-play?
               (d/video {:ref video-ref
                         :poster "https://assets.imgix.net/unsplash/bridge.jpg?w=640&h=640&fit=crop"
                         :muted audio-muted?
                         :autoPlay false
                         :controls false
                         :loop true
                         :class "w-full h-full object-cover"})
               ($ lazy-image {:src "https://assets.imgix.net/unsplash/bridge.jpg?w=640&h=640&fit=crop"
                              :fp 2
                              :should-load? true})))

           (d/div {:class "p-2 cursor-pointer absolute right-4 bottom-4 flex middle hover:text-white text-slate-300"
                   :on-click toggle-audio}
                  ($
                   (if audio-muted?
                     icons/SpeakerWaveIcon
                     icons/SpeakerXMarkIcon) {:className "w-6 h-6"})))))