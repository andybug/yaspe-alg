(ns yaspe-algos.redis
  (:require [taoensso.carmine :as car :refer (wcar)])
  (:gen-class))

; setup connection to redis server
(def redis-server nil)
(defmacro wcar* [& body] `(car/wcar redis-server ~@body))

(defn ping []
  (println (wcar* (car/ping))))
