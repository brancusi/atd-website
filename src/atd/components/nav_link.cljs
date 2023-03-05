(ns atd.components.nav-link
  (:require [helix.core :refer [$]]
            [helix.hooks :as hooks]
            [helix.dom :as d]
            [atd.components.ui.playable-text :refer [playable-text]]
            [applied-science.js-interop :as j]
            [atd.lib.defnc :refer [defnc]]))

(defnc nav-link [{:keys [on-click-handler
                         on-mouse-over-handler
                         on-mouse-out-handler
                         title
                         writing
                         section-id]}]
  (let [ref (hooks/use-ref "link-ref")
        [is-hovering? set-is-hovering!] (hooks/use-state false)]
    #_(d/svg {:class "justify-self-start"
              :height "4em"


              :on-mouse-over (fn []
                               (on-mouse-over-handler {:section-id section-id}))
              :on-mouse-out (fn []
                              (on-mouse-out-handler {:section-id section-id}))
              :on-click (fn []
                          (on-click-handler {:section-id section-id}))}

             (d/text {:class "yotext"

                      :text-anchor "start"
                      :alignment-baseline "middle"
                      :y "50%"
                      :x "0"} title))
    (d/div (d/a {:key section-id
                 :ref ref
                 :class "
                  hero-nav-links
                  cursor-pointer
                  font-fira-code
                  text-6xl"
                 :on-mouse-over (fn []
                                  (set-is-hovering! true)
                                  (on-mouse-over-handler {:section-id section-id}))
                 :on-mouse-out (fn []
                                 (set-is-hovering! false)
                                 (on-mouse-out-handler {:section-id section-id}))
                 :on-click (fn []
                             (on-click-handler {:section-id section-id}))}

                title
                #_(d/svg {:class "border-2 border-green-500"

                          :height "1em"}

                         (d/text {:class "yotext"

                                  :text-anchor "start"
                                  :alignment-baseline "middle"
                                  :y "50%"
                                  :x "0"} title)))
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

;; <svg version= "1.1" xmlns= "//www.w3.org/2000/svg" xmlns:xlink= "//www.w3.org/1999/xlink" width= "100%" >
;; <text class= "stroke-text" x= "50%" y= "50%" >SVG STROKE TEXT</text>
;; </svg>