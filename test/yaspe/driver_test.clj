(ns yaspe.driver-test
  (:require [clojure.test :refer :all]
            [yaspe.driver :refer :all]))

(deftest info-fn-lookup-test
  (testing "algorithm info fn symbol resolution"
    (is (= nil (resolve-info-fn-symbol "fakealgo")))
    (is (= #'yaspe.elo/info (resolve-info-fn-symbol "elo")))))

(deftest info-map-validation
  (testing "test info map validator"
    (is (= nil (validate-info-map nil)))
    (is (= nil (validate-info-map {})))
    (let [info-map {:name nil :init nil :pre-season-hook nil :post-season-hook nil}]
      (is (= nil (validate-info-map info-map))))
    (let [info-map {:name nil :init nil :pre-season-hook nil :post-season-hook nil
                    :pre-round-hook nil :post-round-hook nil :game-hook nil}]
      (is (= info-map (validate-info-map info-map))))))
