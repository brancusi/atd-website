(ns atd.providers.main-provider
  (:require
   ["react" :as react]
   [helix.core :refer [defnc provider]]
   [helix.hooks :as hooks]
   [atd.hooks.use-main-reducer :refer [use-main-reducer]]))

(def main-context (react/createContext))

(defn use-main-state
  "Gives you a tuple of the following [state dispatch!] = (use-main-state)"
  []
  (hooks/use-context main-context))

(defnc MainProvider [{:keys [default-state
                             children]}]

  (provider
   {:context main-context
    :value (use-main-reducer default-state)}
   children))
