(ns atd.hooks.use-toggle
  (:require [applied-science.js-interop :as j]
            [helix.hooks :as hooks]))

(defn use-toggle
  "
   (use-toggle & {:keys [initial-state]
                :or {initial-state false}})

A custom React Hook that returns a tuple of a boolean `is-on?` and a `toggle-handler` function. 

The `initial-state` parameter is optional, defaults to `false`, and determines the initial state of the `is-on?` value.

The `toggle-handler` function can be used as an event handler to toggle the state of `is-on?` between `true` and `false`.

Example usage:
(let [[is-on? toggle] (use-toggle)]
  [:button {:on-click toggle} (if is-on? \"On\" \"Off\")])

   "
  [& {:keys [initial-state]
      :or {initial-state false}}]
  (let [[is-on? set-is-on!] (hooks/use-state initial-state)
        toggle-handler (hooks/use-callback
                        [is-on?]
                        (fn []
                          (set-is-on! (not is-on?))))]
    [is-on? toggle-handler]))