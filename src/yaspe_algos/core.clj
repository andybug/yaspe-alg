(ns yaspe-algos.core
  (:require [yaspe-algos.redis])
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (yaspe-algos.redis/ping))
