(ns yaspe-algos.core
  (:require [yaspe-algos.redis :as redis])
  (:require [yaspe-algos.elo])
  (:gen-class))

(def algorithm-inits '(#'yaspe-algos.elo/init))

(defn init-algorithms [teams]
  (yaspe-algos.elo/init {} teams))

(defn -main [& args]
  (let [seasons (redis/get-seasons)
        teams (redis/get-teams (last (sort seasons)))]
    (println (init-algorithms teams))))
