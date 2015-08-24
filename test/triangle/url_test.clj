(ns triangle.url-test
   (:use [clojure.test]
         [triangle.url]))

(deftest url-test
  (testing "Urls"
    (testing "root url is defined"
      (is (some? (:root urls))))
    (testing "latest uploaded images is defined"
      (is (some? (:index urls))))
    (testing "image specific url is defined"
      (is (some? (:show urls))))))
