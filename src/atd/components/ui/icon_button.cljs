(ns atd.components.ui.icon-button
  (:require [helix.core :refer [$]]
            [helix.hooks :as hooks]
            [helix.dom :as d]
            [applied-science.js-interop :as j]
            [atd.lib.defnc :refer [defnc]]))

(defnc icon-button [{:keys [on-click-handler
                            on-mouse-over-handler
                            on-mouse-out-handler
                            icon]}]
  ($ icon {:on-mouse-over #(on-mouse-over-handler)
           :on-mouse-out #(on-mouse-out-handler)
           :on-click #(on-click-handler)})
  #_(let [resolved-icon (resolve '(symbol (str "icons/" icon)))]

      #_($ resolved-icon {:on-mouse-over #(on-mouse-over-handler)
                          :on-mouse-out #(on-mouse-out-handler)
                          :on-click #(on-click-handler)})))


#_(resolve 'icons/BookOpenIcon)



