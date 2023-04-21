(ns atd.components.sections.video-section
  (:require [atd.components.elements.video-background :refer [video-background]]
            [atd.hooks.use-scroll-trigger :refer [use-scroll-trigger]]
            [atd.lib.defnc :refer [defnc]]
            [atd.providers.main-provider :refer [use-main-state]]
            [helix.core :refer [$]]
            [helix.dom :as d]
            [helix.hooks :as hooks]))

(defnc video-section
  [{:keys [playback-id]}]
  (let [[state dispatch!] (use-main-state)
        outer-ctx (hooks/use-ref "outer-ctx")
        [visited? is-active?] (use-scroll-trigger outer-ctx {:end "bottom"})]

    (d/div {:id "video"
            :ref outer-ctx
            :class "absolute
                    h-full
                    w-full
                    overflow-hidden"}

           (d/div {:class "h-full
                           w-full
                           flex
                           relative 
                           flex items-center justify-items-center justify-center"}

                  ($ video-background {:playback-id playback-id
                                       :should-play? is-active?})))))