(ns gol-cloj.core)

(def relative-neighbors
  [{:x -1 :y -1} {:x 0 :y -1}  {:x 1 :y -1}
   {:x -1 :y 0}                {:x 1 :y 0}
   {:x -1 :y 1} {:x 0 :y 1}  {:x 1 :y 1}])

(defn alive? [point board]
  "Given a point that is a map of the form {:x somex :y somey}
   see if that point is alive on the board"
  (contains? board point))

(defn shift-by-relative [point rel-point]
  "Given two maps in the form {:x intval :y intval}
  add the x and y values to create a new map"
  {:x (+ (:x rel-point) (:x point))
   :y (+ (:y rel-point) (:y point))})

(defn neighbors [point]
  "Given a point on a coordinate plane represent by {:x :y},
  find of the neighbors around it"
  (map (fn [rel-point] (shift-by-relative point rel-point)) relative-neighbors))


(defn interesting-cells
  ([board]
   "Given a game board (set of live cells), find the set of cells that
   includes all live cells and cells that surround live cells"
   (interesting-cells board #{}))

  ([board interesting]
   "Use recursion to build up our list of interesting cells"
   (let [head (first board) tail (rest board)]
     (if (nil? head)
       interesting
       (interesting-cells
         tail (into (conj interesting head)
                    (neighbors head)))))))

(defn future-alive? [point board ]
  "Given a {:x :y} point on the board, figure out if it should be
  alive or dead next round"
  (let [alive-count (count (filter (fn [x] (alive? x board)) (neighbors point)))]
    (if (alive? point board)
      (or (= alive-count 3) (= alive-count 2))
      (= alive-count 3))))

(defn next-board [current]
  "Mutate the board from one state to the next"
  (into #{}
        (filter #(future-alive? % current)
                (interesting-cells current))))

