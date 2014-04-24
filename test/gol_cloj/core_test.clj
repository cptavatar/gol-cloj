(ns gol-cloj.core-test
  (:require [clojure.test :refer :all]
            [gol-cloj.core :refer :all]))

(deftest alive-tests
  (is (alive? {:x 1 :y 1} #{{:x 1 :y 1}}))
  (is (not (alive? {:x 1 :y 0} #{{:x 1 :y 1}}))))

(deftest shift-by-relative-test
  (is (= {:x 2 :y 2} (shift-by-relative {:x 1 :y 0} {:x 1 :y 2})))
  )

(deftest neighbors-test
  (is (=
        #{{:x -1 :y -1} {:x 0 :y -1}  {:x 1 :y -1}
          {:x -1 :y 0}                {:x 1 :y 0}
          {:x -1 :y 1} {:x 0 :y 1}  {:x 1 :y 1}}
        (into #{} (neighbors {:x 0 :y 0})))))

(deftest interesting-cells-test
  (is (=
        #{{:x -1 :y -1} {:x 0 :y -1}  {:x 1 :y -1}
          {:x -1 :y 0}  {:x 0 :y 0} {:x 1 :y 0}
          {:x -1 :y 1} {:x 0 :y 1}  {:x 1 :y 1}}
        (interesting-cells #{{:x 0 :y 0}}))))

(deftest future-alive-test
  (is (future-alive? {:x 1 :y 1} #{{:x 1 :y 1} {:x 2 :y 1}{:x 1 :y 2}} ))
  (is (future-alive? {:x 1 :y 1} #{{:x 0 :y 1} {:x 2 :y 1}{:x 1 :y 2}} ))
  (is (not (future-alive? {:x 1 :y 1} #{{:x 1 :y 1} {:x 1 :y 2}} ))))

(deftest next-board-test
  (is (=
        #{{:x 1 :y 2} {:x 2 :y 2} }
        (next-board #{{:x 1 :y 1} {:x 2 :y 2} {:x 1 :y 3} }))))



