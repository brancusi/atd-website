(ns atd.reducers.main-methods.core
  (:require [atd.reducers.api :refer [main-reducer]]))

(defmethod main-reducer :navigate!
  [state [_ section-id]]

  (let [new-state (assoc state
                         :current-section section-id)]
    new-state))

(defmethod main-reducer :enter-route!
  [state [_ match]]

  (let [new-state (assoc state
                         :current-route match)]
    (js/console.log {:from "inside reducer"
                     :match match
                     :old-state state
                     :new-state new-state})
    new-state))