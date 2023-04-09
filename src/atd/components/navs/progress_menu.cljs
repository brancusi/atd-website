(ns atd.components.navs.progress-menu
  (:require
   [atd.lib.defnc :refer [defnc]]
   [helix.core :refer [$]]
   [atd.utils.for-indexed :refer [for-indexed]]
   [atd.hooks.use-scroll-progress :refer [use-scroll-progress]]
   [helix.dom :as d]))

(defnc progress-menu
  [{:keys [total-sections]}]
  (let [current-active-index (use-scroll-progress 6 {:throttle-interval 10})]
    (d/div {:class ""}
           (for-indexed [_ idx (range total-sections)]
                        (d/div {:key idx
                                :class (str "w-2 h-2 bg-white rounded-full my-2"
                                            (when (= idx current-active-index)
                                              " opacity-100")
                                            (when (not= idx current-active-index)
                                              " opacity-50"))})))))
