(ns atd.components.navs.side-nav
  (:require ["@heroicons/react/24/outline" :as icons]
            [atd.hooks.use-hover-animations :refer [use-hover-animations]]
            [atd.hooks.use-toggle :refer [use-toggle]]
            [atd.hooks.use-toggle-animations :refer [use-toggle-animations]]
            [atd.hooks.use-window-resize :refer [use-window-resize]]
            [atd.lib.defnc :refer [defnc]]
            [atd.providers.main-provider :refer [use-main-state]]
            [atd.services.router :refer [site-map]]
            [atd.utils.gsap :as gsap]
            [atd.utils.window :as win-utils]
            [helix.core :refer [$]]
            [helix.dom :as d]
            [helix.hooks :as hooks]
            [reitit.frontend.easy :as rfe]))

(defn calculate-menu-closed-position
  []
  (win-utils/width))

(defn calculate-menu-open-position
  []
  (let [width (win-utils/width)]
    (cond
      (> width 1024) (- (win-utils/width) (/ (win-utils/width) 4))
      (> width 768) (- (win-utils/width) (/ (win-utils/width) 2))
      :else 0)))

(defnc menu-link
  [{:keys [id path title]}]
  (let [[state dispatch!] (use-main-state)]
    (d/li
     {:key id
      :class "py-2
              font-fira-code
              cursor-pointer 
              text-white
              
              sm:text-4xl
              text-2xl
              hover:text-fuchsia-900
              "}

     (d/a {:href (rfe/href id)}
          title))))

(defnc flyout-menu [{:keys [is-open?]}]
  (let [comp-ref (hooks/use-ref "comp-ref")]
    (hooks/use-layout-effect
     [is-open? comp-ref]
     (if is-open?
       (gsap/to comp-ref
                {:x (calculate-menu-open-position)
                 :duration 0.2})
       (gsap/to comp-ref {:x (calculate-menu-closed-position)
                          :duration 0.2})))

    (use-window-resize
     [is-open? comp-ref]
     (fn [_]
       (gsap/to comp-ref {:x (if is-open?
                               (calculate-menu-open-position)
                               (calculate-menu-closed-position))
                          :duration 0})))

    (hooks/use-effect
     [comp-ref]
     (gsap/to comp-ref {:x (if is-open?
                             (calculate-menu-open-position)
                             (calculate-menu-closed-position))
                        :duration 0}))

    (d/div {:ref comp-ref
            :class "
                    fixed
                    z-20
                    w-full
                    md:w-1/2
                    lg:w-1/4
                    h-screen
                    
                    opacity-95
                    bg-slate-900 
                           "}
           (d/ul {:class "flex flex-col h-full justify-center items-center"}
                 (map (fn [{:as section
                            :keys [id]}]
                        ($ menu-link {:key id & section}))
                      site-map)))))

(defnc side-nav
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

    (d/div {:class "relative"}

           (d/div
            (d/div {:ref comp-ref
                    :on-click toggle-menu-open!
                    :class "fixed
                  z-30
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
            ($ flyout-menu {:is-open? menu-open?})))))



