(ns credit-card.logic)

(defn usuario? [cliente usuario]
  (= (:usuario cliente) usuario))

(defn total-das-compras [compras]
  (->> compras
       (map :valor)
       (reduce +)))

(defn gasto-total-por-categoria
  [[categoria compras]]
  {:categoria   categoria
   :valor-total (total-das-compras compras)})


(defn resumo-valor-por-categoria [compras]
  (->> compras
       (group-by :categoria)
       (map gasto-total-por-categoria)))