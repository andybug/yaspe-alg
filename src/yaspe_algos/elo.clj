(ns yaspe-algos.elo
  (:require [yaspe-algos.redis :as redis])
  (:gen-class))

(def elo-default 1200)

(def elo-current {})

(defn init [last-season]
  (doseq [team (redis/get-teams last-season)]
    (assoc elo-current team 1200)))

(defn process-round [season round games]
  (doseq [game games]
    (println "elo processing" game)))
