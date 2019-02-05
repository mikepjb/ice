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
  ;; (def net (require "net"))

  (def client (new (.Socket net)))

  (def message "nothing")

  (.connect
    client
    34633
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
      (.destroy client)))

  (.on client "close" (fn [] (println "Connection closed")))

  message)
