(ns ping4.system
  (:require [com.stuartsierra.component :as component]
            [ping4.web-server :as ws]
            [ping4.routing :as routing]))

(defn launch
  "Creates a new system. Invoke shutdown on the result to stop it."
  ([]
   (launch 8080))
  ([port]
   (-> (component/system-map :web-server (ws/new-web-server port)
                             :routing (routing/new-routing))
       component/start)))

(defn shutdown
  [system]
  (component/stop system))