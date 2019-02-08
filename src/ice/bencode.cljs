(ns ice.bencode
  "encode and decode bencoded strings"
  (:require [clojure.string :as str]))

(defn- remove-marker [bstr]
  "removes the first and last characters of a bstr, begins with d/l, ends with e"
  (str/join "" (drop 1 (drop-last bstr))))

(defn- split-at-position [bstr x]
  "splits a string in two at position x, must be in core somewhere.."
  [(subs bstr 0 x) (subs bstr x (count bstr))])

(defn- seq->map [seq]
  (reduce (fn [col e] (conj {(keyword (first e)) (second e)} col))
          {}
          (partition 2 seq)))

(defn- next-type [bstr]
  (let [first-char (first bstr)]
    (cond (str/includes? "0123456789" first-char) :element
          (= "d" first-char) :map
          (= "l" first-char) :list)))

(defn decode [bstr & [col]]
  (let [message (if col col [])]
    (if (not (empty? bstr))
      (if (not= (next-type bstr) :element)
        (decode (remove-marker bstr) col)
        (let [[length remaining-bstr] (str/split bstr #":" 2)]
          (let [[element remaining-remaining-bstr] (split-at-position remaining-bstr length)]
            (decode remaining-remaining-bstr (conj message element)))))
      (seq->map message))))

(defn encode 
  "transforms message seq into bencoded string"
  [message]
  (let [string-seq (map name (flatten (vec (first message))))
        bstr (reduce (fn [map-str x] (str map-str (str (count x) ":" x))) "" string-seq)]
    (str "d" bstr "e")))
