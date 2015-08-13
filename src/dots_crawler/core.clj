(ns dots-crawler.core
  (:require [net.cgrand.enlive-html :as html]
            [clojurewerkz.propertied.properties :as p]
            [clojure.java.io :as io])
  (:use [clojure.repl])
  (:import (org.apache.http.impl.client HttpClients)
           (org.apache.http.client HttpClient)
           (org.apache.http.client.methods HttpGet)
           (org.apache.http HttpEntity)))

(def 
  ^{:doc "keys: root, index"}
  urls (p/properties->map (-> (io/resource "keys.properties") p/load-from) true))

(defn
  ^{:doc "client"}
  client [] (. HttpClients createDefault))

(defn fetch-target-ids []
  ""
  (let [^HttpGet target (new HttpGet (:index urls))
        ^HttpEntity  entity (. (. (client) execute target) getEntity)
        haha (-> (html/html-resource (. entity getContent))
               (html/select [:span [:a (html/attr-starts :href "/post")]]))
        ]
  (map #(-> (re-seq #"\d+" (get-in % [:attrs :href])) first) haha)
  ))

(defn fetch-img-urls [ids]
  (let [pages (map #(str (:show urls) "/" %) ids)
        gets (map #(new HttpGet %) pages)
        entities (map #(. (. (client) execute %) getEntity) gets)
        contents (map #(. % getContent) entities)
        tags (map #(-> (html/html-resource %) (html/select [(html/id= "highres")]) first) contents)
        ]
    (map #(str "https:" (get-in % [:attrs :href]) tags))))

(defn -main []
  (fetch-target-ids))
