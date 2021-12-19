(ns credit-card.core
  (:require [credit-card.db :as c.db]
            [credit-card.logic :as c.logic]))

(let [compras (get-in c.db/cliente [:cartao :compras])
      resumo (c.logic/resumo-valor-por-categoria compras)]
  (println resumo))

(let [compras (get-in c.db/cliente [:cartao :compras])
      resumo (c.logic/busca-compra-por-filtro compras :estabelecimento "loja def")]
  (println resumo))

(let [compras (get-in c.db/cliente [:cartao :compras])
      resumo (c.logic/busca-compra-por-filtro compras :valor 230.5)]
  (println resumo))

(let [cartao (:cartao c.db/cliente)
      nova-compra {:data            "01/02/2021"
                   :valor           29.90
                   :estabelecimento "renner"
                   :categoria       "vestuario"}
      resultado (c.logic/adiciona-compra cartao nova-compra)]
  (println resultado))

(let [compras (c.logic/compras-do-cliente c.db/cliente)]
  (println compras))