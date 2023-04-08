(ns atd.hooks.use-media-query
  (:require
   [helix.hooks :as hooks]
   [atd.utils.window :as win-utils]))

(def breakpoint-map {:xs "640px"
                     :md "768px"
                     :lg "1024px"
                     :xl "1280px"
                     :2xl "1536px"})

(defn use-media-query
  [breakpoint]

  (let [[matches? set-matches!] (hooks/use-state false)]

    (hooks/use-effect
     [breakpoint]

     (let [query (str "(min-width: " (breakpoint breakpoint-map) ")")
           media-query-list (js/window.matchMedia query)
           change-handler (fn [_]
                            (set-matches! (.-matches media-query-list)))]

       (.addEventListener
        media-query-list
        "change"
        change-handler)

       (change-handler nil)

       (fn []
         (.removeEventListener media-query-list "change" change-handler))))
    matches?))
