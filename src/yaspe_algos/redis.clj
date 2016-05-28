(ns yaspe-algos.redis
  (:require [taoensso.carmine :as car :refer (wcar)])
  (:gen-class))

(use 'clojure.walk)

; setup connection to redis server
(def redis-server nil)
(defmacro wcar* [& body] `(car/wcar redis-server ~@body))

(defn ping []
  (println (wcar* (car/ping))))

(defn get-game [key]
  (keywordize-keys (apply hash-map (wcar* (car/hgetall key)))))
