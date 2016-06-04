(ns yaspe-algos.elo
  (:require [yaspe-algos.redis :as redis])
  (:gen-class))

(def elo-default 1200)

(defn init-team [team]
  (hash-map team elo-default))

(defn init [teams]
  (apply conj (map init-team teams)))

;(defn process-game [game]
;  (let [home (get game :home_team)
;        away (get game :away_team)]
;    (printf "%s(%d) vs %s(%d)%n" home (elo-current home) away (elo-current away))))
;
;(defn process-round [season round games]
;  (doseq [game games]
;    (process-game (redis/get-game game))))
