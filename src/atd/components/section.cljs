(ns atd.components.section
  (:require
   [atd.utils.gsap :as gsap]
   [helix.dom :as d]
   [atd.utils.window :as win-utils]
   ["@heroicons/react/24/outline" :as icons]
   [helix.hooks :as hooks]
   ["gsap/ScrollTrigger" :refer [ScrollTrigger]]
   [atd.providers.main-provider :refer [use-main-state]]
   [atd.hooks.use-hover-animations :refer [use-hover-animations]]
   [atd.hooks.use-toggle-animations :refer [use-toggle-animations]]
   [atd.hooks.use-window-resize :refer [use-window-resize]]
   [atd.lib.defnc :refer [defnc]]
   [atd.hooks.use-toggle :refer [use-toggle]]
   [helix.core :refer [$]]))

(defnc section
  [{:keys [section-id children]}]

  (let [comp-ref (hooks/use-ref "comp-ref")
        [is-active? set-is-active!] (hooks/use-state false)]
    (hooks/use-effect
     [comp-ref]
     (let [st (.create ScrollTrigger #js{:trigger @comp-ref
                                         :start "top center"
                                         :end "top 100px"
                                         :id section-id
                                         :onToggle (fn [self]
                                                     (set-is-active! (.-isActive self)))
                                         #_#_:markers true})]

       (fn []
         (.kill st))))

    (d/div
     {:ref comp-ref
      :class (str "h-screen w-screen border-2 " (when is-active? "border-red-500"))}

     children)))
