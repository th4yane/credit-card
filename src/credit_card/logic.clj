(ns credit-card.logic
  (:require [schema.core :as s]
            [credit-card.model :as c.model]))

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

(defn tenta-adicionar-compra [compras nova-compra]
  (conj compras nova-compra))

(s/defn adiciona-compra [compras :- [c.model/Compra]
                       nova-compra :- c.model/Compra]
  (if-let [compras-atualizadas (tenta-adicionar-compra compras nova-compra)]
    {:compras compras-atualizadas :resultado :sucesso}
    {:compras compras :resultado :nao-foi-possivel-adicionar-compra}))