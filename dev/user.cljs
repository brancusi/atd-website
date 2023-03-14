(ns user
  "Commonly used symbols for easy access in the ClojureScript REPL during
  development."
  (:require
   [cljs.repl :refer (Error->map apropos dir doc error->str ex-str ex-triage
                                 find-doc print-doc pst source)]
   [portal.web :as p]
   [clojure.pprint :refer (pprint)]
   ["@heroicons/react/24/outline" :as icons]

   [clojure.string :as str]))

(defn tap-icon-names
  []
  (tap> (keys (js->clj icons))))

(comment
  (tap-icon-names)


  (p/open))