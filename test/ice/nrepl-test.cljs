(ns ice.nrepl-test
  (:require
    [cljs.test :refer [deftest testing is]]
    [ice.nrepl :as nrepl]))

(deftest connect
  ;; (testing "connects to nREPL on specified port"
  ;;   (is (= (nrepl/connect "connected!"))))

  (testing "returns a connection map"
    (is (= {:port 9999 :session-id "x"} (nrepl/connect 9999)))))
