(ns atd.services.router
  (:require [atd.lib.defnc :refer [defnc]]
            [atd.providers.main-provider :refer [use-main-state]]
            [atd.views.about-view :as about-view]
            [atd.views.contact-view :as contact-view]
            [atd.views.landing-view :as landing-view]
            [atd.views.services-view :as services-view]
            [atd.views.screening-view :as screening-view]
            [helix.hooks :as hooks]
            [reitit.frontend :as rf]
            [reitit.frontend.controllers :as rfc]
            [reitit.frontend.easy :as rfe]))

(defn log-fn [& params]
  (fn [_]
    (apply js/console.log params)))

(def site-map [{:id ::services
                :path "services"
                :title "Services"
                :view services-view/services-view}
               {:id ::about
                :path "about"
                :title "About"
                :view about-view/about-view}
               {:id ::screening
                :path "screening"
                :title "Screening"
                :view screening-view/screening-view}
               {:id ::contact
                :path "contact"
                :title "Contact"
                :view contact-view/contact-view}])

(defn routes
  []
  (into ["/"
         [""
          {:name :home
           :view landing-view/landing-view
           :controllers [{:start (log-fn "start" "landing controller")
                          :stop (log-fn "stop" "landing controller")}]}]]
        (map (fn [{:keys [id path view]}]
               [path
                {:name id
                 :view view
                 :controllers [{:start (log-fn (str "start!!!" id))
                                :stop (log-fn (str "stop!!!" id))}]}])
             site-map)))

(comment

  (routes)

;;Keep from folding
  )

(defnc router
  [{:keys [children]}]

  (let [[state dispatch!] (use-main-state)
        [is-ready? set-is-ready!] (hooks/use-state false)

        route-change-callback (hooks/use-callback
                               [state]
                               (fn [new-route]
                                 (js/console.log new-route)
                                 (let [old-route (:current-route state)]
                                   (rfc/apply-controllers old-route new-route))
                                 (dispatch! [:enter-route! new-route])))]

    (hooks/use-effect
     :once

     (rfe/start!
      (rf/router
       (routes)
       {:data {:controllers [{:start (log-fn "start" "root-controller")
                              :stop (log-fn "stop" "root controller")}]}})
      route-change-callback
      {:use-fragment false})

     (set-is-ready! true))

    (when is-ready?
      children)))
