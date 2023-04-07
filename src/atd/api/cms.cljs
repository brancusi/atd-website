(ns atd.api.cms
  (:require
   #_["axios/dist/axios.min.js" :as axios]
   [atd.config :refer [sanity-endpoint]]
   [atd.utils.axios :as axios]))

(def get-entries
  "*[_type == \"entry\"] {
  \"image\": image.asset->url,
  title
}")

(defn get-hero-images!
  [callback]
  (axios/get-request
   {:url (str sanity-endpoint (js/encodeURIComponent get-entries))}
   {:on-success (fn [result]
                  (let [raw (-> result
                                :result)
                        images (mapv (fn [image]
                                       {:src (:image image)})
                                     raw)]
                    (callback images)))
    :on-error (fn [error] (tap> {:error error}))}))


(comment
  (get-hero-images! tap>)

  (axios/get-request
   {:url "https://sfsmp6cu.api.sanity.io/v2021-10-21/data/query/production?query=*%5B_type%20%3D%3D%20%22entry%22%5D%20%7B%0A%20%20%22image%22%3A%20image.asset-%3Eurl%2C%0A%20%20%20%20%0A%20%20title%20%20%0A%20%20%0A%7D%0A%0A%0A%0A%0A%0A%0A%0A"}
   {:on-success (fn [result] (tap> {:result (-> result
                                                :result)}))
    :on-error (fn [error] (tap> {:error error}))})

  ;; => :repl/exception!


  ;; => :repl/exception!

  ;; => :repl/exception!

  ;; => :repl/exception!




;;Keep from folding
  )