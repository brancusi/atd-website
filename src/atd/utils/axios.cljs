(ns atd.utils.axios
  (:require ["axios/dist/axios.min.js" :as axios]
            [cljs.reader :refer [read-string]]))

(def default-headers
  {:content-type "application/json"
   :accept "application/json"})

(defn parse-response
  [response]
  (let [response-clj (js->clj response :keywordize-keys true)
        data (:data response-clj)]
    data))

(defn- make-request
  [request method & {:as opts
                     :keys [on-success
                            on-error]}]

  (let [updated-request (assoc request :method method)
        {:keys [data url headers]} updated-request

        payload (cond-> {:method method
                         :url url
                         :headers (merge default-headers headers)}
                  data (assoc :data data))

        promise (-> (axios (clj->js payload))
                    (.then (fn [result] (when on-success (on-success (parse-response result)))))
                    (.catch (fn [error] (when on-error (on-error error)))))]

    promise))

(defn get-request
  "
   (get-request {:url \"http://hi.com\"
                  :header {:x-hi-header 5}}
                 :callback (fn [yo] (tap> yo)))
   "
  [request & {:as opts
              :keys [on-success
                     on-error]}]

  (make-request request "get" opts))

(defn post-request
  "
   (post-request {:url \"http://hi.com\"
                  :data {:abc 1}
                  :header {:x-hi-header 5}}
                 :callback (fn [yo] (tap> yo)))
   "
  [request & {:as opts
              :keys [on-success
                     on-error]}]
  (make-request request "post" opts))