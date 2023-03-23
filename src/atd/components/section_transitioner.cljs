(ns atd.components.section-transitioner
  (:require [atd.lib.defnc :refer [defnc]]
            [atd.providers.main-provider :refer [use-main-state]]
            [atd.utils.for-indexed :refer [for-indexed]]
            [helix.core :refer [$]]
            [helix.dom :as d]
            [helix.hooks :as hooks]
            [nano-id.core :refer [nano-id]]))

(defnc section-transitioner
  []
  (let [[state _] (use-main-state)
        [view-stack set-view-stack!] (hooks/use-state [])

        current-state-view-data (-> state
                                    :current-route
                                    :data)

        state-view (:view current-state-view-data)
        state-view-id (:name current-state-view-data)

        has-state-view? (keyword? state-view-id)

        handle-layer-intro-completed (hooks/use-callback
                                      [view-stack]
                                      (fn [uuid]))

        handle-layer-outro-completed (hooks/use-callback
                                      [view-stack]
                                      (fn [uuid]
                                        (let [purged-stack (filterv (fn [l] (not= (:uuid l) uuid)) view-stack)]
                                          (set-view-stack! purged-stack))))]

    ;; Manage the view stack, and decorate the layers
    (hooks/use-effect
     [state-view state-view-id view-stack]
     (let [peek-view-stack (last view-stack)
           peeked-id (:id peek-view-stack)]
       (when (not= peeked-id state-view-id)
         (let [new-layer {:view state-view
                          :uuid (nano-id)
                          :id state-view-id}
               new-view-stack (conj
                               view-stack
                               new-layer)]

           (set-view-stack! new-view-stack)))))

    (d/div {:class "relative"}
           (when has-state-view?
             (d/p (str "hi" state-view-id))
             (for-indexed [{:keys [view uuid]} idx view-stack]
                          (let [stack-size (count view-stack)
                                is-active? (= idx (- stack-size 1))]
                            (d/div {:key uuid
                                    :class "absolute"}
                                   ($ view {:active is-active?
                                            :intro-complete-callback (partial handle-layer-intro-completed uuid)
                                            :outro-complete-callback (partial handle-layer-outro-completed uuid)}))))))))