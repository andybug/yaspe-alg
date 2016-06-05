(ns yaspe.elo
  (:require [yaspe.redis :as redis])
  (:gen-class))

(def elo-default 1200)

(defn info []
  {:name "elo"})

(defn init-team [team]
  (hash-map team elo-default))

(defn init [teams]
  (apply conj (map init-team teams)))

(defn process-game [state game]
  (let [hteam (get game :home_team)
        ateam (get game :away_team)
        helo (get state hteam)
        aelo (get state ateam)
        hscore (read-string (get game :home_score))
        ascore (read-string (get game :away_score))]
    (if (> hscore ascore)
        (assoc state hteam (inc helo) ateam (dec aelo))
        (assoc state hteam (dec helo) ateam (inc aelo)))))

;(defn process-game [game]
;  (let [home (get game :home_team)
;        away (get game :away_team)]
;    (printf "%s(%d) vs %s(%d)%n" home (elo-current home) away (elo-current away))))
;
;(defn process-round [season round games]
;  (doseq [game games]
;    (process-game (redis/get-game game))))
