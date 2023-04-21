(ns atd.components.fragments.about-me
  (:require
   [atd.lib.defnc :refer [defnc]]
   [helix.dom :as d]))

(defnc about-me
  []
  (d/div {:class "text-slate-800 flex justify-center flex-col w-4/5 md:w-1/2 bg-white/50 backdrop-blur-md p-8"}
         (d/p {:class "text-2xl md:text-4xl mb-4 "} "Hello. I'm Aram")
         (d/p {:class "text-md md:text-xl mb-4"} "I'm a filmmaker and entrepreneur.")
         (d/p {:class "text-md md:text-xl mb-4"} "The collision of technology drives change.")
         (d/p {:class "text-md md:text-xl"} "Things are colliding.")
         (d/p {:class "text-md md:text-xl"} "Business is changing.")))