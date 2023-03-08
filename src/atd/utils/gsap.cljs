(ns atd.utils.gsap
  (:require
   ["gsap" :refer [gsap]]))

(defn to
  [ref args]
  (.to gsap
       @ref
       (clj->js args)))

(defn window-to
  [args]
  (.to gsap
       js/window
       (clj->js args)))