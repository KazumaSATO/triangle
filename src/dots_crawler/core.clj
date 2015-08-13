(ns dots-crawler.core
  (:require [net.cgrand.enlive-html :as html]
            [clojurewerkz.propertied.properties :as p]
            [clojure.java.io :as io])
  (:use [clojure.repl])
  (:import (org.apache.http.impl.client HttpClients)
           (org.apache.http.client HttpClient)
           (org.apache.http.client.methods HttpGet)
           (org.apache.http HttpEntity)))

(defn hoge []
  (let [^HttpClient client (. HttpClients createDefault)
        ^HttpGet target (new HttpGet (-> (p/properties->map (-> (io/resource "keys.properties") p/load-from) true) :root))
        ^HttpEntity  entity (. (. client execute target) getEntity)
        haha (-> (html/html-resource (. entity getContent))
               (html/select [:span [:a (html/attr-starts :href "/post")]]))
        ]
    (doseq [img haha]
        (println (get-in img [:attrs :href]))))
  )
  
(defn fetch-url [url]
  (let [^java.net.URL raw (java.net.URL. url)]
  (html/html-resource raw)))

(defn -main []
  (fetch-url base-url))
