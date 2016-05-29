(ns yaspe-algos.core
  (:require [yaspe-algos.redis :as redis])
  (:require [yaspe-algos.elo])
  (:gen-class))

(defn init-algos [last-season]
  (yaspe-algos.elo/init last-season))

(defn -main [& args]
  (let [seasons (redis/get-seasons)]
    (init-algos (last (sort seasons)))
    (doseq [season (redis/get-seasons)]
      (doseq [round (redis/get-rounds season)]
        (yaspe-algos.elo/process-round season round (redis/get-games round))))))
