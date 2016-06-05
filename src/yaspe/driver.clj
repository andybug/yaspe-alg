(ns yaspe.driver
  (:gen-class))

;; algorithms ********************************************************

;; list of algorithms to run
;; each name must match ns: yaspe.<name>

(def algorithms '("elo"))

;; each algorithm must provide a function "info" that
;; returns a map with the following keys:
;; :name "alg1"
;; :init #'yaspe.alg1/init
;; :pre-season-hook #'yaspe.alg1/pre-season-hook
;; :post-season-hook ...
;; :pre-round-hook ...
;; :post-round-hook ...
;; :game-hook ...

(def info-keys '(:name :init :pre-season-hook :post-season-hook
                       :pre-round-hook :post-round-hook :game-hook))

(defn resolve-info-fn-symbol [a]
  (resolve (symbol (str "yaspe." a) "info")))

(defn validate-info-map [info]
  ;; make sure the map is not nil or empty
  (if (or (= info {}) (not (some? info)))
    nil)
  ;; make sure each key at least exists
  (if (every? true? (map (fn [k] (contains? info k)) info-keys))
    info
    nil))

(defn get-algorithms
  "Calls info function for each algorithm, validates, then
  returns the list of info maps"
  []
  (for [alg-info (map resolve-info-fn-symbol algorithms)]
    (if (not (some? alg-info))
      (throw (Exception. "no info function for algorithm"))
      (validate-info-map (alg-info)))))

(defn init-algorithms
  "Calls init function for each algorithm and returns the
  mutated algorithm list with the new state"
  [teams]
  ;;(map (fn [a] ((get a :init) teams)) (get-algorithms))
  (for [a (get-algorithms)]
    (assoc a :state ((get a :init) teams))))


;; execution ---------------------------------------------------------

(defn run [])
