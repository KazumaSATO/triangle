(ns triangle.core-test
  (:require [triangle.core :as c])
  (:use [clojure.test]))

;;; feature 1 index offset limit ignore_cache(default false)
;;; feature 1 index only unretrieved
;;; feature 2 query input
;; As a .., I want to .. so that I can ...


(deftest main-args-test
 (testing "create request object from main argments" 
   (testing "create daily-request object which has offset and limit"
            (let [model (#'c/create-request "daily" "--offset=10" "--limit=10")]
              (is (some? (:offset model)))
              (is (some? (:limit model)))
              (is (some? (:query model)))
              (is (some? (:duplicate model)))))
   (testing (str "create daily-request object from no argment" 
                 ", but it limits the number of images to be retrieved to 10000")
               (let [model (#'c/create-request "")]
                 (is (some? (:offset model)))))
   (testing "create daily-request object which has offset and limit"
               (let [model (#'c/create-request "--query=misaki_kurehito --offset=0 --limit=10")]
                 (is (some? (:offset model)))))
   ))

