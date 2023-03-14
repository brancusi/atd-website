(ns atd.utils.for-indexed)

(defmacro for-indexed [[item index coll] & body]
  `(for [i# (range (count ~coll))]
     (let [~item (nth ~coll i#)
           ~index i#]
       ~@body)))