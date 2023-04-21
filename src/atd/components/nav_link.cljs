(ns atd.components.nav-link
  (:require [helix.core :refer [$]]
            [helix.hooks :as hooks]
            [helix.dom :as d]
            [atd.components.ui.playable-text :refer [playable-text]]
            [atd.lib.defnc :refer [defnc]]))

(defnc nav-link [{:keys [on-click-handler
                         on-mouse-over-handler
                         on-mouse-out-handler
                         title
                         writing
                         section-id]}]
  (let [ref (hooks/use-ref "link-ref")
        [is-hovering? set-is-hovering!] (hooks/use-state false)]

    (d/div (d/a {:key section-id
                 :ref ref
                 :class "
                  hero-nav-links
                  cursor-pointer
                  font-fira-code
                  font-medium
                  text-6xl"
                 :on-mouse-over (fn []
                                  (tap> "mouse over")
                                  (set-is-hovering! true)
                                  (on-mouse-over-handler {:section-id section-id}))
                 :on-mouse-out (fn []
                                 (set-is-hovering! false)
                                 (on-mouse-out-handler {:section-id section-id}))
                 :on-click (fn []
                             (on-click-handler {:section-id section-id}))}

                title)
           (d/div {:class "whitespace-nowrap
                                                absolute
                                                translate-x-full
                                                bottom-2
                                                
                                                self-baseline
                                                right-0
                                                 pl-6
                                                "}
                  ($ playable-text {:text writing
                                    :is-playing? is-hovering?})))))