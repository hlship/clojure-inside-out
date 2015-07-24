(ns ping4.web-server
  (:require [com.stuartsierra.component :as component]
            [ring.adapter.jetty :as jetty]
            ring.middleware.keyword-params
            ring.middleware.params)
  (:import [org.eclipse.jetty.server Server]))

(defrecord WebServer [routing port ^Server server]

  component/Lifecycle

  (start [component]
    (assoc component :server
                     (jetty/run-jetty (-> routing
                                          :handler
                                          ring.middleware.keyword-params/wrap-keyword-params
                                          ring.middleware.params/wrap-params)
                                      {:port port :join? false})))

  (stop [component]
    (.stop server)
    component))

(defn new-web-server
  [port]
  (-> {:port port}
      map->WebServer
      (component/using [:routing])))