(ns atd.hooks.use-can-play-background-video
  (:require ["hls.js" :as Hls :refer [Events]]
            [helix.hooks :as hooks]))

(defn use-can-play-background-video
  [container-ref]
  (let [[can-play? set-can-play!] (hooks/use-state false)
        [check-completed? set-check-completed!] (hooks/use-state false)]
    (hooks/use-effect
     :once
     (let [vidhls (new Hls)]
       (.on vidhls (aget Events "MEDIA_ATTACHED") (fn []
                                                    (set-can-play! true)
                                                    (set-check-completed! true)
                                                    (tap> "Media Attached")))

       (.on vidhls (aget Events "ERROR") (fn []
                                           (set-can-play! false)
                                           (set-check-completed! true)
                                           (tap> "Error yo")))

       (.on vidhls (aget Events "MEDIA_ATTACHED") (fn []
                                                    (tap> "Media Attached")))

       (.loadSource vidhls "https://stream.mux.com/auOPdAVC9yXYZS6LYNDlLCw6h5AgmtYYaaDvIlFIB02c.m3u8")
       (.attachMedia vidhls @container-ref)
       (fn []
         (.destroy vidhls))))
    {:check-completed? check-completed?
     :can-play? can-play?}))

