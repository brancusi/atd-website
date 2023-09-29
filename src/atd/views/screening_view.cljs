(ns atd.views.screening-view
  (:require ["gsap" :refer [gsap]]
            [atd.components.elements.video-player :refer [VideoPlayer]]
            [atd.lib.defnc :refer [defnc]]
            [atd.lib.defnc :refer [defnc]]
            [atd.providers.main-provider :refer [use-main-state]]
            [atd.reducers.requires]
            [atd.reducers.requires]
            [helix.core :refer [$]]
            [helix.core :refer [$]]
            [helix.dom :as d]
            [helix.hooks :as hooks]))

(defnc screening-view
  [{:keys [active
           intro-complete-callback
           outro-complete-callback]}]

  (let [comp-ref (hooks/use-ref "comp-ref")
        [state _] (use-main-state)
        current-route-production-id (get-in state [:current-route :query-params :id])
        title (or (get-in state [:current-route :query-params :title])
                  "")
        date (or (get-in state [:current-route :query-params :date])
                 "")

        shot-by (get-in state [:current-route :query-params :by])
        contact (get-in state [:current-route :query-params :contact])]

    (hooks/use-effect
     [active]
     (if active
       (intro-complete-callback)
       (.to gsap
            @comp-ref
            #js{:opacity 0
                :onComplete outro-complete-callback
                :duration 1})))

    (d/div {:class "flex flex-col w-screen min-h-screen h-full bg-zinc-100 border-red-500"
            :ref comp-ref}

           (d/div {:ref comp-ref
                   :class "fixed opacity-50 pl-4 pt-4 w-24"}
                  (d/img {:src "images/graphics/medium_pink.png"}))

           (d/div {:class "flex min-w-full h-full items-center justify-center p-4 grow"}

                  (d/div {:class "w-full sm:w-3/4"}
                         (d/p {:class "text-xl md:text-3xl font-fira-code"}
                              title)
                         (d/p {:class "text-md md:text-xl font-fira-code mb-4"}
                              (str date))
                         (d/div {:class "my-4"}
                                ($ VideoPlayer {:playback-id current-route-production-id}))
                         (d/div {:class "flex flex-col text-md md:text-xl font-fira-code"}
                                (when shot-by (d/p {:class "text-md md:text-xl font-fira-code"}
                                                   (str "Shot by: " shot-by)))
                                (when contact
                                  (d/div
                                   (d/span {:class "text-md md:text-xl font-fira-code"}
                                           "Contact: ")
                                   (d/a {:class "text-md md:text-xl font-fira-code"
                                         :href (str "mailto:" contact)}
                                        contact)))))))))