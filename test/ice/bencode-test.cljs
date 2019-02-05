(ns ice.bencode-test
  (:require
    [cljs.test :refer [deftest testing is]]
    [ice.bencode :as bencode]))

(deftest decode
  (testing "transforms clone request string into a map"
    (is (= (bencode/decode "d2:op5:clonee") {:op "clone"}))))
