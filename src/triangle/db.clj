(ns triangle.db
  (:use [korma.db]
        [korma.core]
        [clojure.repl]))

(def db (mysql {:db "triangle"
                :host "localhost"
                :port "3306" 
                :user "root"
                :password ""}))
(defdb korma-db db)


(defentity file)

(select file)

;(insert file 
;  (values {:img_id "john" :original_file_url "doe"}))