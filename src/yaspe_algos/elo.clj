(ns yaspe-algos.elo
  (:require [yaspe-algos.redis :as redis])
  (:gen-class))

(def elo-default 1200)

(def ^:dynamic elo-current {})

(defn init [last-season]
  (doseq [team (redis/get-teams last-season)]
    (def elo-current (assoc elo-current team 1200))))

(defn process-game [game]
  (let [home (get game :home_team)
        away (get game :away_team)]
    (printf "%s(%d) vs %s(%d)%n" home (elo-current home) away (elo-current away))))

(defn process-round [season round games]
  (doseq [game games]
    (process-game (redis/get-game game))))
