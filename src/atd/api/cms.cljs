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

(defn gallery-query
  [id]
  (str "*[_type == \"gallery\" && title == \"" id "\" && !(_id match \"drafts.*\")] {
  title,
  \"images\": images[] {
    \"url\": asset->url,
    \"fp\": hotspot {
      x,
      y
    }
  }
}"))

(defn what-query
  []
  "*[_type == 'what-section'] {
  title,
  \"whats\": whats[]-> {
    title,
    \"things\": things[] {
      name,
      copy,
      url
    }
  }
}")



(defn get-gallery-images!
  [collection callback]
  (axios/get-request
   {:url (str sanity-endpoint (js/encodeURIComponent (gallery-query collection)))}
   {:on-success (fn [result]
                  (let [raw (-> result
                                :result
                                first
                                :images)
                        images (mapv (fn [image]
                                       {:src (:url image)
                                        :fp (:fp image)})
                                     raw)]
                    (callback images)))
    :on-error (fn [error] (tap> {:error error}))}))

(defn get-what-copy!
  [callback]
  (axios/get-request
   {:url (str sanity-endpoint (js/encodeURIComponent (what-query)))}
   {:on-success (fn [result]
                  (let [raw (-> result
                                :result
                                first
                                :whats)]

                    (callback raw)))
    :on-error (fn [error] (tap> {:error error}))}))


(comment
  (get-gallery-images! tap>)

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