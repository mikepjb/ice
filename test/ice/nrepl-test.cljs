(ns ice.nrepl-test
  (:require
    [cljs.test :refer [deftest testing is]]
    [ice.nrepl :as nrepl]))

;; (deftest connect
;;   ;; (testing "connects to nREPL on specified port"
;;   ;;   (is (= (nrepl/connect "connected!"))))
;; 
;;   (testing "returns a connection map"
;;     (is (= {:port 9999 :session-id "x"} (nrepl/connect 9999)))))
;; 
;; (deftest net-connect
;;   (testing "node net can start a client"
;;     (is (= (nrepl/net-example) "a cool message"))))

;; (deftest )
