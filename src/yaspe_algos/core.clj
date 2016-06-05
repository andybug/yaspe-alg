(ns yaspe-algos.core
  (:require [yaspe-algos.redis :as redis])
  (:require [yaspe-algos.elo])
  (:gen-class))

(def algorithms '("elo"))

(defn gen-init-function [a]
  (resolve (symbol (str "yaspe-algos." a) "init")))

(defn init-algorithms [teams]
  (map (fn [x] (x teams)) (map gen-init-function algorithms)))

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

(defn -main [& args]
  (let [seasons (redis/get-seasons)
        teams (redis/get-teams (last (sort seasons)))]
    (println (process-seasons (list (resolve (symbol "yaspe-algos.elo" "process-game"))) (init-algorithms teams) seasons))))
