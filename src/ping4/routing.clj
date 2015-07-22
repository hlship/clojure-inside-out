(ns ping4.routing
  (:require [com.stuartsierra.component :as component]
            [compojure.core :refer [routes GET rfn]]
            [ping4.handlers :refer [ping-handler page-not-found]]
            [ring.util.response :as res]
            [clojure.string :as str]))


;; This component has neither dependencies, not real internal state.
;; However, ina  real application, it might have dependencies that
;; could be passed down to handlers via assoc into the request map.

(defrecord Routing [handler]

  component/Lifecycle

  (start [component]
    (assoc component :handler (routes
                                (GET "/ping" request (ping-handler request))
                                (GET "/pong/:value" [value] (res/response (str/upper-case value)))
                                (rfn request (page-not-found request)))))

  (stop [component] component))

(defn new-routing []
  (map->Routing {}))