(ns atd.reducers.api)

(defmulti main-reducer
  (fn [_ action] (first action)))