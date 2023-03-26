(ns atd.components.elements.lazy-image
  (:require [helix.core :refer [$]]
            [helix.hooks :as hooks]
            [helix.dom :as d]

            [atd.lib.defnc :refer [defnc]]))

(defn preload-image
  [url on-success on-error]
  (let [image (js/Image.)]
    (js/console.log "calling preload image")
    (set! (.-src image) url)
    (set! (.-onload image) on-success)
    (set! (.-onerror image) on-error)))

(defnc lazy-image [{:keys [src fp should-load?]}]
  (let [ref (hooks/use-ref "lazy-image-ref")
        [loaded? set-loaded!] (hooks/use-state false)

        on-success-handler (hooks/use-callback
                            :once
                            (fn
                              [_]
                              (js/console.log "loaded image")
                              (set-loaded! true)))

        on-error-handler (hooks/use-callback
                          :once
                          (fn
                            [_]
                            (js/console.log "error loading image")
                            (set-loaded! false)))]

    (hooks/use-effect
     [src fp should-load?]
     (when (and should-load?
                (not loaded?))
       (preload-image src on-success-handler on-error-handler)))

    (if loaded?
      (d/img {:ref ref
              :class "object-cover w-full h-full"
              :srcSet "https://assets.imgix.net/unsplash/bridge.jpg?w=1024&h=1024&fit=crop 1024w, 
                   https://assets.imgix.net/unsplash/bridge.jpg?w=640&h=640&fit=crop 640w,
                   https://assets.imgix.net/unsplash/bridge.jpg?w=480&h=480&fit=crop 480w"
              :src "https://assets.imgix.net/unsplash/bridge.jpg?w=640&h=640&fit=crop"
              :sizes "(min-width: 36em) 33.3vw, 100vw"})
      (d/div {:class "w-full h-full bg-gray-200"}))))


