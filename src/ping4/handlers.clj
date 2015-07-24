(ns ping4.handlers
  (:require [ring.util.response :as res]
            [clojure.string :as str]
            [hiccup.core :refer [html]]))

(defn ping-handler
  [request]
  (res/response (-> request
                    :params
                    :value
                    str/upper-case)))

(defn page-not-found
  [request]
  (-> [:html
       [:head
        [:title "Page not found"]]
       [:body
        [:h1 "Page not found"

         [:p "The page you requested "
          [:b (:uri request)]
          " does not exist."]]]]
      html
      res/response
      (res/status 404)))