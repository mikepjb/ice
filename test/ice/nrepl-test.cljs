(ns ice.nrepl-test
  (:require (cljs.test)))

(deftest connect
  (testing "connects to nREPL on specified port"
    (is (= 1 2))))
