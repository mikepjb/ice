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

;; (def client (new net/Socket))

(def log (atom []))

(defn session-id
  "collect session-id from log"
  [retries]
  (println (count @log))
  (if (not= 0 (count @log))
    (:new-session (last @log))
    (if (< 99 retries) (recur (inc retries)))))

(defn setup-connection [addr port]
  (let [client (new net/Socket)]
    (.connect
      client port addr (fn [] (.write client (bencode/encode {:op "clone"}))))
    (.on client "data"
         (fn [response] (swap! log conj (bencode/decode (.toString response)))))
    (.on client "close"
         (fn [] (println "connection closed")))
    {:client client
     :session-id (session-id 0)}))

(defn net-example []
  (let [client (setup-connection "127.0.0.1" 9999)]
    ;; (loop []
    ;;   (if (= 1 (count @log))
    ;;     (let [last-message (last @log)
    ;;           payload (bencode/encode [{:op "eval"
    ;;                                     :code "(def special-value 5)"
    ;;                                     :session (:new-session last-message)
    ;;                                     :ns "boot.user"}])]
    ;;       (.write client payload))
    ;;     (recur)))
    ))

(defn send [client]
  (let [last-message (last @log)
        payload (bencode/encode [{:op "eval"
                                  :code "(def special-value 5)"
                                  :session (:new-session last-message)
                                  :ns "boot.user"}])]
    (.write client payload)))
