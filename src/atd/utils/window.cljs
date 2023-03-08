(ns atd.utils.window)

(defn center-coords
  []
  (let [x (/ (.-innerWidth js/window) 2)
        y (/ (.-innerHeight js/window) 2)]
    [x y]))

(defn width
  []
  (.-innerWidth js/window))

(defn height
  []
  (.-innerHeight js/window))