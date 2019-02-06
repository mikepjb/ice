(ns ice.nrepl
  (:require ["net" :as net]
            [ice.bencode :as bencode]
            [cljs.pprint :as pprint])
  (:refer-clojure :exclude [clone]))

(defn connect
  "returns a connection map, containing port and session id to communicate with a nREPL."
  [port]
  {:port port :session-id "x"})

(defn clone
  "To start an nREPL connection, send a clone request and return the session id"
  []
  {:session-id "x"})

(def log (atom []))

(defn net-example []
  (def client (new net/Socket))

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
    (fn [response]
      (swap! log conj (bencode/decode (.toString response)))
      (println (bencode/decode (.toString response)))
      (.destroy client)))

  (.on client "close" (fn [] (println "Connection closed")))

  (when (not= [] @log)
    (println "heeeeyyy")
    (def client (new net/Socket))

    (.connect
      client
      9999
      "127.0.0.1"
      (fn []
        (println "Connected")
        (let [last-message (last @log)
              payload (str "d2:op4:eval4:code33:(def ice.bencode/special-value 5)e7:session36:" (:new-session last-message) "e")]
          (println payload)
          (.write client payload))))

    (.on
      client
      "data"
      (fn [response]
        (swap! log conj (bencode/decode (.toString response)))
        (println (bencode/decode (.toString response)))
        (.destroy client)))

    (.on client "close" (fn [] (println "Connection closed")))
    )
  )
