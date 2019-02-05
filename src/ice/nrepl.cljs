(ns ice.nrepl
  (:require ["net" :as net]
            [ice.bencode :as bencode])
  (:refer-clojure :exclude [clone]))

(defn connect
  "returns a connection map, containing port and session id to communicate with a nREPL."
  [port]
  {:port port :session-id "x"})

(defn clone
  "To start an nREPL connection, send a clone request and return the session id"
  []
  {:session-id "x"})

(defn net-example []
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
      ;; (println (bencode/decode data)) ;; doesn't work due to failing test
      (.destroy client)))

  (.on client "close" (fn [] (println "Connection closed")))

  message)
