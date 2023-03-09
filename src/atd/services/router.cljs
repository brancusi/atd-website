(ns atd.services.router
  (:require

   [atd.utils.gsap :as gsap]
   [helix.dom :as d]
   [atd.utils.window :as win-utils]
   ["@heroicons/react/24/outline" :as icons]
   [helix.hooks :as hooks]
   [atd.providers.main-provider :refer [use-main-state]]
   [atd.hooks.use-hover-animations :refer [use-hover-animations]]
   [atd.hooks.use-toggle-animations :refer [use-toggle-animations]]
   [atd.lib.defnc :refer [defnc]]
   [atd.hooks.use-toggle :refer [use-toggle]]
   [helix.core :refer [$]]))

(defnc circle-nav
  [{:keys [is-open?]}]
  (let [comp-ref (hooks/use-ref "comp-ref")]
    (hooks/use-layout-effect
     [is-open? comp-ref]
     (if is-open?
       (gsap/to comp-ref {:x 300
                          :y -300
                          :width (/ (win-utils/width) 2)
                          :height (/ (win-utils/width) 2) :duration 0.4})
       (gsap/to comp-ref {:width 0
                          :height 0
                          :duration 0.3})))

    (d/div {:class "fixed
                  z-10
                  right-16
                  top-16
                  "}
           (d/div {:ref comp-ref
                   :class "
                  relative
                  rounded-full
                  w-10
                  h-10
                  
                  p-2
                  text-white
                  
                  opacity-95
                  bg-teal-600
                           
                           "}
                  (d/div {:class "flex  h-full border-2 justify-center items-center"}
                         (d/div
                          {:class "border-2 text-white"}
                          "Yo son"))))))

(defnc menu-button
  []
  (let [comp-ref (hooks/use-ref "comp-ref")
        [menu-open? toggle-menu-open!] (use-toggle)]
    (use-hover-animations comp-ref
                          :over {:scale 1.1
                                 :rotation -180}
                          :out {:scale 1
                                :rotation 0})

    (use-toggle-animations
     comp-ref
     :on {:background-color "#FF01D6"}
     :off {:background-color "#67C5CB"}
     :is-on? menu-open?
     :initial true)

    (d/div
     (d/div {:ref comp-ref
             :on-click toggle-menu-open!
             :class "fixed
                  z-20
                  cursor-pointer
                  rounded-full
                  w-10
                  h-10
                  z-10
                  right-16
                  top-16
                  p-2
                  text-white
                  border-4
                  opacity-90
                  bg-teal-600"}

            ($ icons/ArrowUpRightIcon))
     ($ circle-nav {:is-open? menu-open?}))))

(defnc router
  [{:keys [children]}]
  (let [[state dispatch!] (use-main-state)]

    (hooks/use-effect
     [state]
     (js/console.log "Firing state change in router")
     (gsap/window-to {:scrollTo (str "#" (:current-section state))})
     #_(.to gsap
            js/window
            #js{:scrollTo (str "#" (:current-section state))}))

    (d/div {:class "relative"}
           ($ menu-button)
           (d/div {:class "z-0"}
                  children))))
