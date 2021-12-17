(ns credit-card.core
  (:require [credit-card.db :as c.db]
            [credit-card.logic :as c.logic]))

(let [compras (get-in c.db/cliente [:cartao :compras])
      resumo (c.logic/resumo-valor-por-categoria compras)]
      (println resumo))
