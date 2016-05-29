(ns yaspe-algos.core
  (:require [yaspe-algos.redis :as redis])
  (:require [yaspe-algos.elo])
  (:gen-class))

(defn -main [& args]
  (doseq [season (redis/get-seasons)]
    (doseq [round (redis/get-rounds season)]
      (println season round)
      (yaspe-algos.elo/process-round season round (redis/get-games round)))))
