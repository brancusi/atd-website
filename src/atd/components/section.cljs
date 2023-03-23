(ns atd.components.section
  (:require [atd.lib.defnc :refer [defnc]]
            [helix.dom :as d]))

(defnc section
  [{:keys [section-id children]}]

  (d/div
   {:class (str "snap-start
                 h-screen 
                 w-screen")}
   children))
