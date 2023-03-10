(ns atd.utils.gsap
  (:require
   ["gsap" :refer [gsap]]))

(defn to
  [ref args]
  (.to gsap
       @ref
       (clj->js args)))

(defn fromTo
  [ref from-args to-args]
  (.fromTo gsap
           @ref
           (clj->js from-args)
           (clj->js to-args)))

(defn window-to
  [args]
  (.to gsap
       js/window
       (clj->js args)))