(ns ice.bencode
  "encode and decode bencoded strings"
  (:require [clojure.string :as str]))

(defn- is-map? [bstr]
  (and (= (first bstr) "d")
       (= (last bstr) "e")))

(defn- remove-map-marker [bstr]
  "removes the first and last characters of a bstr, begins with d, ends with e"
  (str/join "" (drop 1 (drop-last bstr))))

(defn- split-at-position [bstr x]
  "splits a string in two at position x, must be in core somewhere.."
  [(subs bstr 0 x) (subs bstr x (count bstr))])

(defn- seq->map [seq]
  (reduce (fn [col e] (conj {(keyword (first e)) (second e)} col))
          {}
          (partition 2 seq)))

(defn decode [bstr & [col]]
  (if (is-map? bstr) (decode (remove-map-marker bstr))
    (let [message (if col col [])]
      (if (not (empty? bstr))
        (let [[length remaining-bstr] (str/split bstr #":" 2)]
          (let [[element remaining-remaining-bstr] (split-at-position remaining-bstr length)]
            (decode remaining-remaining-bstr (conj message element))))
        (seq->map message)))))
