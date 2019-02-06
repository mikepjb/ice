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

(def client (new net/Socket))

(def log (atom []))

(defn net-example []
  (.connect
    client
    9999
    "127.0.0.1"
    (fn []
      (.write client "d2:op5:clonee")
      (println "Sending code for evaluation")
      (when (contains? (last @log) :status)
        (println "last log contains a status key.")
        (let [last-message (last @log)
              payload (str "d2:op4:eval4:code31:(def boot.user/special-value 5)7:session36:" (:new-session last-message) "e")]
          (println payload)
          (.write client payload)))))

  (.on
    client
    "data"
    (fn [response]
      (println "on method call.")
      (swap! log conj (bencode/decode (.toString response)))
      (println (bencode/decode (.toString response)))
      ;; (.destroy client)
      ))

  (.on client "close" (fn [] (println "Connection closed")))

  (when (contains? (last @log) :status)
    (.destroy client))

  )
