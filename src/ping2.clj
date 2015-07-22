(ns ping2
  (:require [ring.adapter.jetty :as jetty]
            [clojure.string :as str]
            [ring.util.response :as res]
            [compojure.core :refer [defroutes GET]]
            [compojure.route :as route]
            ring.middleware.keyword-params
            ring.middleware.params))

(defn ping-handler
  [request]
  (res/response (-> request
                    :params
                    :value
                    str/upper-case)))

(defroutes ping-routes
  (GET "/ping" request (ping-handler request))
  (GET "/pong/:value" [value] (res/response (str/upper-case value)))
  (route/not-found "<h1>Page not found</h1>"))

(defn ping-server
  "Launches and returns a Jetty server for the ping handler.  Save the result and invoke .stop to stop it."
  []
  (jetty/run-jetty
    (-> ping-routes
        ring.middleware.keyword-params/wrap-keyword-params
        ring.middleware.params/wrap-params)
    {:port 8080 :join? false}))