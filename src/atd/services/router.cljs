(ns atd.services.router
  (:require ["@heroicons/react/24/outline" :as icons]
            ["gsap" :refer [gsap]]
            [atd.providers.main-provider :refer [use-main-state]]
            [atd.lib.defnc :refer [defnc]]
            [helix.core :refer [$]]
            [helix.dom :as d]
            [helix.hooks :as hooks]))


(defnc router
  []
  (let [[state dispatch!] (use-main-state)]
    (hooks/use-effect
     [state]
     (js/console.log state))))