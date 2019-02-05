(ns ice.nrepl
  ;; (:require ["net"] :as net)
  ;; (:require js/net :as net)
  ;; (:require [net :as net])
  (:require net)
  )

(defn connect
  "returns a connection map, containing port and session id to communicate with a nREPL."
  [port]
  {:port port :session-id "x"})

;; (defn net-example []
;;   (.createServer net (fn [socket]
;;                        (.write socket ""))))


(defn net-example []
  ;; (def require (js* "require"))
  ;; (def net (require "net"))
  (println "running net-example")

  (def client (new net/Socket))

  (def message "nothing")

  (.connect
    client
    9999
    "127.0.0.1"
    (fn []
      (println "Connected")
      (.write client "d2:op5:clonee")))

  (.on
    client
    "data"
    (fn [data]
      (def message data)
      (println (str "Received: " data))
      ;; d11:new-session36:2268a436-b946-44a2-b6d3-e0c759f42f067:session36:3ef758ee-2c4c-4ebd-9b0e-8d13b85cc2f46:statusl4:doneee
      (.destroy client)))

  (.on client "close" (fn [] (println "Connection closed")))

  message)
