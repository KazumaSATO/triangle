(ns triangle.url
  (:require [clojure.java.io :as io]
            [clojurewerkz.propertied.properties :as p])
  (:use [clojure.repl]))

(defrecord Urls [root index show])


(def 
  ^{:doc "keys: root, index"}
  urls 
  (let [mp (p/properties->map (-> (io/resource "keys.properties") p/load-from) true)] 
  (->Urls (:root mp) (str (:root mp) "/post/index") (str (:root mp) "/post/show"))))