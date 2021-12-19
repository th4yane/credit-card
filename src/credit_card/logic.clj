(ns credit-card.logic)

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

(defn busca-compra-por-filtro[compras filtro valor]
  (->> compras
       (group-by filtro)
       (#(get % valor))))

(defn adiciona-compra [compras nova-compra]
  (conj compras nova-compra))