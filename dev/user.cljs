(ns user
  "Commonly used symbols for easy access in the ClojureScript REPL during
  development."
  (:require
   [cljs.repl :refer (Error->map apropos dir doc error->str ex-str ex-triage
                                 find-doc print-doc pst source)]
   [portal.web :as p]
   [clojure.pprint :refer (pprint)]
   [clojure.string :as str]))


(comment

  (p/open)

  (-> (js->clj icons)
      keys)


;;Keep from folding
  )