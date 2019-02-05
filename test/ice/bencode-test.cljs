(ns ice.bencode-test
  (:require
    [cljs.test :refer [deftest testing is]]
    [ice.bencode :as bencode]))

(def new-session-bstr
  "d11:new-session36:2d2d78b1-24ab-43b6-8ef5-84e1d9e833677:session36:8fcca80c-7514-4b2b-82ff-f0915b90f7536:statusl4:doneee")

(deftest decode
  (testing "transforms clone request string into a map"
    (is (= (bencode/decode "d2:op5:clonee") {:op "clone"})))

  ;; detect next element type by reading the next char in seq => d is map, l is
  ;; list and a number is element in current type.
  (testing "transforms new session reponse into a map
    (is (= (bencode/decode new-session-bstr)
           {:new-session "2d2d78b1-24ab-43b6-8ef5-84e1d9e83367"
            :session "8fcca80c-7514-4b2b-82ff-f0915b90f753"
            :status "done"}))))
