(ns credit-card.logic
  (:require [schema.core :as s]
            [credit-card.model :as c.model]))

(defn total-das-compras [compras]
  (->> compras
       (map :compra/valor)
       (reduce +)))

(s/defn gasto-total-por-categoria :- c.model/ValoresCategoria
  [[categoria compras]]
  {:categoria categoria
   :valor-total      (total-das-compras compras)})

(s/defn resumo-valor-por-categoria :- [c.model/ValoresCategoria]
  [compras :- [c.model/Compra]]
  (->> compras
       (group-by :compra/categoria)
       (mapv gasto-total-por-categoria)))

(s/defn busca-compra-por-filtro :- [c.model/Compra]
  [compras :- [c.model/Compra]
   filtro :- s/Keyword
   valor]
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

(s/defn compras-do-cliente :- [c.model/Compra]
  [cliente :- c.model/Cliente]
  (get-in cliente [:cliente/cartao :cartao/compras]))