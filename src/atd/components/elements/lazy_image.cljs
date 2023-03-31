(ns atd.components.elements.lazy-image
  (:require [helix.core :refer [$]]
            [helix.hooks :as hooks]
            [helix.dom :as d]

            [atd.lib.defnc :refer [defnc]]))

(defn preload-image
  [url on-success on-error]
  (let [image (js/Image.)]
    (set! (.-src image) url)
    (set! (.-onload image) on-success)
    (set! (.-onerror image) on-error)))

(defnc lazy-image
  "Renders an image element with lazy loading. The image will only load when the `should-load?` flag is set to true.
  
  Parameters:
  - src: The image source URL.
  - focal-point: An optional vector of three numbers representing the focal point of the image, e.g., [0.5 0.5 5] for x, y, and z.
  - debug?: A boolean value for enabling debug mode in the image URL.
  - should-load?: A boolean value determining if the image should be loaded.

  Returns:
  - An image element if the image is loaded, otherwise a div element with a gray background.

  Example usage:
  (lazy-image {:src \"https://example.com/image.jpg\"
               :focal-point [0.5 0.5 5]
               :debug? true
               :should-load? true})"
  [{:keys [src focal-point debug? should-load?]}]
  (let [ref (hooks/use-ref "lazy-image-ref")
        [loaded? set-loaded!] (hooks/use-state false)
        on-success-handler (hooks/use-callback
                            :once
                            (fn [_]
                              (set-loaded! true)))
        on-error-handler (hooks/use-callback
                          :once
                          (fn [_]
                            (set-loaded! false)))]

    (hooks/use-effect
     [src focal-point should-load?]
     (when (and should-load?
                (not loaded?))
       (preload-image src on-success-handler on-error-handler)))

    (let [debug-param (if debug? "&fp-debug=true" "")
          [fp-x fp-y fp-z] focal-point
          fp-param (str "fp-x=" fp-x "&fp-y=" fp-y "&fp-z=" fp-z)

          has-focal-point? (seq focal-point)

          fp-img-src (str src "?w=640&h=640&fit=crop&crop=focalpoint&" fp-param debug-param)
          default-img-src (str src "?w=640&h=640&fit=crop")

          img-src (if has-focal-point? fp-img-src default-img-src)

          fp-img-src-set (str src "?w=1024&h=1024&fit=crop&crop=focalpoint&" fp-param debug-param " 1024w, "
                              src "?w=640&h=640&fit=crop&crop=focalpoint&" fp-param debug-param " 640w, "
                              src "?w=480&h=480&fit=crop&crop=focalpoint&" fp-param debug-param " 480w")

          default-img-src-set (str src "?w=1024&h=1024&fit=crop" " 1024w, "
                                   src "?w=640&h=640&fit=crop" fp-param debug-param " 640w, "
                                   src "?w=480&h=480&fit=crop" fp-param debug-param " 480w")
          img-src-set (if has-focal-point? fp-img-src-set default-img-src-set)]

      (if loaded?
        (d/img {:ref ref
                :class "object-cover w-full h-full"
                :srcSet img-src-set
                :src img-src
                :sizes "(min-width: 36em) 33.3vw, 100vw"})
        (d/div {:class "w-full h-full bg-gray-200"})))))







