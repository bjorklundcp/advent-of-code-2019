(ns advent-of-code-2019.day2
  (:require [clojure.string :as str]))

(def puzzle (->>
              (str/split "1,0,0,3,1,1,2,3,1,3,4,3,1,5,0,3,2,6,1,19,1,19,10,23,2,13,23,27,1,5,27,31,2,6,31,35,1,6,35,39,2,39,9,43,1,5,43,47,1,13,47,51,1,10,51,55,2,55,10,59,2,10,59,63,1,9,63,67,2,67,13,71,1,71,6,75,2,6,75,79,1,5,79,83,2,83,9,87,1,6,87,91,2,91,6,95,1,95,6,99,2,99,13,103,1,6,103,107,1,2,107,111,1,111,9,0,99,2,14,0,0"
                         #",")
              (mapv #(Integer/parseInt %))))

(defn process-addition
  [puzzle pos-1 pos-2 location]
  (assoc puzzle location (+ (nth puzzle pos-1) (nth puzzle pos-2))))

(defn process-multiplication
  [puzzle pos-1 pos-2 location]
  (assoc puzzle location (* (nth puzzle pos-1) (nth puzzle pos-2))))

(defn solve-puzzle
  [puzzle]
  (loop [puzzle puzzle
         pos 0]
    (if (= 99 (nth puzzle pos))
      (nth puzzle 0)
      (if (= 1 (nth puzzle pos))
        (recur (process-addition puzzle
                                 (nth puzzle (+ 1 pos))
                                 (nth puzzle (+ 2 pos))
                                 (nth puzzle (+ 3 pos)))
               (+ pos 4))
        (recur (process-multiplication puzzle
                                       (nth puzzle (+ 1 pos))
                                       (nth puzzle (+ 2 pos))
                                       (nth puzzle (+ 3 pos)))
               (+ pos 4))))))

(defn part-1
  []
  (solve-puzzle (assoc puzzle 1 12 2 2)))

(defn part-2
  []
  (loop [puzzle puzzle
         noun 0
         verb 0]
    (if (= 19690720 (solve-puzzle (assoc puzzle 1 noun 2 verb)))
      (-> (* 100 noun)
          (+ verb))
      (if (= 99 verb)
        (recur puzzle (inc noun) 0)
        (recur puzzle noun (inc verb))))))
