(ns yaspe.core
  (:require [yaspe.redis :as redis])
  (:require [yaspe.elo])
  (:require [yaspe.driver])
  (:gen-class))

(defn call-algorithms [algos states game]
  (for [a algos
        s states]
    (a s game)))

(defn process-games [algos states games]
  (if (= 0 (count games))
    states
    (let [newstates (call-algorithms algos states (redis/get-game (first games)))]
      (process-games algos newstates (rest games)))))

(defn process-rounds [algos states rounds]
  (if (= 0 (count rounds))
    states
    (let [newstates (process-games algos states (redis/get-games (first rounds)))]
      (process-rounds algos newstates (rest rounds)))))

(defn process-seasons [algos states seasons]
  (if (= 0 (count seasons))
    states
    (let [newstates (process-rounds algos states (redis/get-rounds (first seasons)))]
      (process-seasons algos newstates (rest seasons)))))

(defn -main [& args])
