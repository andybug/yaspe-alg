(ns yaspe-algos.redis
  (:require [taoensso.carmine :as car :refer (wcar)])
  (:gen-class))

(use 'clojure.walk)

; setup connection to redis server
(def redis-server nil)
(defmacro wcar* [& body] `(car/wcar redis-server ~@body))

(defn ping []
  (println (wcar* (car/ping))))


(defn get-seasons []
  (wcar* (car/lrange "seasons" 0 -1)))

(defn get-rounds [season]
  (wcar* (car/lrange (str "games:" season) 0 -1)))

(defn get-games [round-key]
  (wcar* (car/lrange round-key 0 -1)))

(defn get-game [key]
  (keywordize-keys (apply hash-map (wcar* (car/hgetall key)))))

(defn get-teams [season]
  (wcar* (car/lrange (str "teams:" season) 0 -1)))


(defn set-value [key value]
  (wcar* (car/set key value)))
