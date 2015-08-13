(ns dots-crawler.core
  (:require [net.cgrand.enlive-html :as html]
            [clojurewerkz.propertied.properties :as p]
            [clojure.java.io :as io])
  (:use [clojure.repl])
  (:import (org.apache.http.impl.client HttpClients)
           (org.apache.http.client HttpClient)
           (org.apache.http.client.methods HttpGet)
           (java.nio.file Files)
           (java.nio.file Paths)
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
  (let [pages (map (fn [i] {:id i, :page (str (:show urls) "/" i)}) ids)
        gets (map (fn [i] {:id (:id i), :get (new HttpGet (:page i))}) pages)
        entities (map (fn [i] {:id (:id i), :entity (. (. (client) execute (:get i)) getEntity)}) gets)
        contents (map (fn [i] {:id (:id i), :content (. (:entity i) getContent)}) entities)
        tags (map (fn [i] {:id (:id i), :tag (-> (html/html-resource (:content i)) (html/select [(html/id= "highres")]) first)})  contents)
        ]
    (map (fn [i] {:id (:id i), :url (str "https:" (get-in (:tag i) [:attrs :href]))}) tags)))

(defn retrieve [id-url-list dst]
  (map #(let [img-id (:id %)
              img-url (:url %)
              entity  (. (. (client) execute (new HttpGet img-url)) getEntity)
              content (. entity getContent)
              ext (subs (. (. entity getContentType) getValue) 6)
              img-file (Paths/get dst (into-array [(str img-id "." ext) ]))
              ] 
          (io/copy content (. img-file toFile))
         ) 
       id-url-list))

(defn -main []
  (fetch-target-ids))
