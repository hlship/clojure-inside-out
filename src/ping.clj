(ns ping
  (require [ring.util.response :as res]
           [clojure.string :as str]
           [ring.adapter.jetty :as jetty]
           ring.middleware.keyword-params
           ring.middleware.params))

(defn ping-handler
  [request]
  (res/response (-> request
                    :params
                    :value
                    str/upper-case)))

(defn ping-server
  "Launches and returns a Jetty server for the ping handler.  Save the result and invoke .shutdown to stop it."
  (jetty/run-jetty
    (-> ping-handler
        ring.middleware.keyword-params/wrap-keyword-params
        ring.middleware.params/wrap-params)
    {:port 8080 :join? false}))