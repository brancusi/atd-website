(ns atd.reducers.main-methods.core
  (:require [atd.reducers.api :refer [main-reducer]]))

(defmethod main-reducer :navigate!
  [state [_ section-id]]
  (let [new-state (assoc state
                         :current-section section-id)]
    new-state))