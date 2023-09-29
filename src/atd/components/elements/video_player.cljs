(ns atd.components.elements.video-player
  (:require ["@heroicons/react/24/outline" :as icons]
            ["@mux/mux-player-react$default" :as MuxPlayer]
            [applied-science.js-interop :as j]
            [atd.components.elements.lazy-image :refer [lazy-image]]
            [atd.hooks.use-can-play-background-video :refer [use-can-play-background-video]]
            [atd.lib.defnc :refer [defnc]]
            [helix.core :refer [$]]
            [helix.dom :as d]
            [helix.hooks :as hooks]))

(defnc VideoPlayer
  [{:keys [playback-id]}]
  ($ MuxPlayer
     {:playbackId playback-id
      :class "w-full h-full object-cover"
      :playsInline ""
      :streamType "on-demand"
      :preferplayback "mse"}))
