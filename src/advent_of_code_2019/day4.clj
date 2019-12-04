(ns advent-of-code-2019.day4
  (:require [clojure.string :as str]))

(def low-end 248345)
(def high-end 746315)

(defn break-up-number
  [number]
  (as-> number n
        (str n)
        (str/split n #"")
        (map #(Integer. %) n)))

(defn always-increase?
  [number]
  (loop [number (break-up-number number)]
    (if (next number)
      (if (<= (first number) (first (next number)))
        (recur (next number))
        false)
      true)))

(defn contains-identical-neighbors?
  [number]
  (loop [number (break-up-number number)]
    (if (next number)
      (if (= (first number) (first (next number)))
        true
        (recur (next number)))
      false)))

(defn get-identical-neighbors
  [number]
  (loop [number (break-up-number number)
         matching-pairs '()
         current-run '()]
    (if (next number)
      (if (= (first number) (first (next number)))
        (if (empty? current-run)
          (recur (next number) matching-pairs (conj current-run (first number) (first (next number))))
          (recur (next number) matching-pairs (conj current-run (first (next number)))))
        (if (empty? current-run)
          (recur (next number) matching-pairs '())
          (recur (next number) (conj matching-pairs current-run) '())))
      (if (empty? current-run)
        matching-pairs
        (conj matching-pairs current-run)))))

(defn part-2-rule-valid?
  [neighbor-sets]
  (-> (filter #(= 2 (count %)) neighbor-sets)
      empty?
      not))

(defn part-1
  []
  (-> (reduce (fn
                [passwords possibility]
                (if (and (always-increase? possibility) (contains-identical-neighbors? possibility))
                  (conj passwords possibility)
                  passwords))
              #{}
              (range low-end (inc high-end)))
      count))

(defn part-2
  []
  (-> (reduce (fn
                [passwords possibility]
                (if (and (always-increase? possibility)
                         (contains-identical-neighbors? possibility)
                         (part-2-rule-valid? (get-identical-neighbors possibility)))
                  (conj passwords possibility)
                  passwords))
              #{}
              (range low-end (inc high-end)))
      count))
