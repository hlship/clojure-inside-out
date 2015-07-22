(ns ping3
  (:require [ring.adapter.jetty :as jetty]
            [clojure.string :as str]
            [ring.util.response :as res]
            [compojure.core :refer [defroutes GET rfn]]
            [hiccup.core :refer [html]]
            ring.middleware.keyword-params
            ring.middleware.params))

(defn ping-handler
  [request]
  (res/response (-> request
                    :params
                    :value
                    str/upper-case)))

(defn- page-not-found
  [request]
  (html
    [:html
     [:head
      [:title "Page not found"]]
     [:body
      [:h1 "Page not found"

       [:p "The page you requested "
        [:b (:uri request)]
        " does not exist."]]]]))

(defroutes ping-routes
  (GET "/ping" request (ping-handler request))
  (GET "/pong/:value" [value] (res/response (str/upper-case value)))
  (rfn request (page-not-found request)))

(defn ping-server
  "Launches and returns a Jetty server for the ping handler.  Save the result and invoke .stop to stop it."
  []
  (jetty/run-jetty
    (-> ping-routes
        ring.middleware.keyword-params/wrap-keyword-params
        ring.middleware.params/wrap-params)
    {:port 8080 :join? false}))