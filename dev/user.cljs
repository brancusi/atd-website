(ns user
  "Commonly used symbols for easy access in the ClojureScript REPL during
  development."
  (:require-macros
   [mount.core :refer [defstate]])
  (:require
   [cljs.repl :refer (Error->map apropos dir doc error->str ex-str ex-triage
                                 find-doc print-doc pst source)]
   [portal.web :as p]
   [mount.core :as mount]
   [clojure.pprint :refer (pprint)]
   [portal.web :as p]
   ["@heroicons/react/24/outline" :as icons]

   [clojure.string :as str]))

;; (mount/defstate ^{:on-reload :noop} portal
;;   :start
;;   (do
;;     (js/console.log "Starting portal")
;;     (add-tap #'p/submit)
;;     (p/open))

;;   :stop
;;   (when portal
;;     (js/console.log "Stopping portal")
;;     (remove-tap #'p/submit)
;;     (p/close)))

(defn tap-icon-names
  []
  (tap> (keys (js->clj icons))))

(comment
  (tap-icon-names)
  (tap> 3)

  (p/open)

  ;; 
  )