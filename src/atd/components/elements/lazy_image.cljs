(ns atd.components.elements.lazy-image
  (:require [helix.core :refer [$]]
            [helix.hooks :as hooks]
            [helix.dom :as d]
            [atd.utils.gsap :as gsap]
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
  [{:keys [src w h fp-x fp-y focal-point should-load? transition on-intro-completed]}]
  (let [ref (hooks/use-ref "lazy-image-ref")
        [loaded? set-loaded!] (hooks/use-state false)
        on-success-handler (hooks/use-callback
                            [ref]
                            (fn [_]
                              (set-loaded! true)))
        on-error-handler (hooks/use-callback
                          :once
                          (fn [_]
                            (set-loaded! false)))

        img-src (str src "?w=" w "&h=" h "&fit=crop" "&fp-x=" fp-x "&fp-y=" fp-y "&format=auto ")


        img-src-set (str src "?w=" w "&h=" h "&fit=crop" "&fp-x=" fp-x "&fp-y=" fp-y "&format=auto " w "w, "
                         src "?w=" (int (/ w 1.6)) "&h=" (int (/ h 1.6)) "&fit=crop" "&fp-x=" fp-x "&fp-y=" fp-y "&format=auto " (int (/ w 1.6)) "w, "
                         src "?w=" (int (/ w 2)) "&h=" (int (/ h 2)) "&fit=crop" "&fp-x=" fp-x "&fp-y=" fp-y "&format=auto " (int (/ w 2)) "w")]

    (hooks/use-effect
     [src focal-point should-load?]
     (when (and should-load?
                (not loaded?))
       (preload-image img-src on-success-handler on-error-handler)))

    (hooks/use-layout-effect
     [ref loaded? on-intro-completed]
     (when loaded?
       (gsap/to ref (merge
                     transition
                     {:onComplete on-intro-completed}))))

    (d/div {:ref ref
            :class "w-full h-full opacity-0"}
           (if loaded?
             (d/img {:class "object-cover w-full h-full"
                     :srcSet img-src-set
                     :src img-src
                     :sizes "(min-width: 36em) 33.3vw, 100vw"})
             (d/div {:class "w-full h-full bg-gray-200"})))))







