(ns atd.hooks.use-main-reducer
  (:require [helix.hooks :as hooks]
            [atd.reducers.api :refer [main-reducer]]))

(defn use-main-reducer
  [default-state]
  (let [[state dispatch!] (hooks/use-reducer main-reducer default-state)]
    [state dispatch!]))