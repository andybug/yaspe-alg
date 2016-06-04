(ns yaspe-algos.core
  (:require [yaspe-algos.redis :as redis])
  (:require [yaspe-algos.elo])
  (:gen-class))

(def algorithms '("elo"))

(defn gen-init-function [a]
  (resolve (symbol (str "yaspe-algos." a) "init")))

(defn init-algorithms [teams]
  (map (fn [x] (x teams)) (map gen-init-function algorithms)))

(defn -main [& args]
  (let [seasons (redis/get-seasons)
        teams (redis/get-teams (last (sort seasons)))]
    (println (init-algorithms teams))))
