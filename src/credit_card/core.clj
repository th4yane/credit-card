(ns credit-card.core
  (:require [datomic.api :as d]
            [credit-card.db :as c.db]
            [credit-card.logic :as c.logic]
            [credit-card.model :as model])
  (:import (java.util Date UUID)))

(c.db/apaga-banco)

(def conn (c.db/abre-conexao))

(c.db/cria-schema conn)

(let [cartao (model/novo-cartao "1234567890123456", "123", "01/22", 1000M, [])
      cliente (model/novo-cliente (UUID/randomUUID), "Thayane", "01202312312", cartao)]
  (c.db/salva-cliente conn cliente))

(let [compra1 (model/nova-compra (UUID/randomUUID) (Date.) 29.90M "mcdonalds" :alimentacao)
      compra2 (model/nova-compra (UUID/randomUUID) (Date.) 35.90M "mcdonalds" :alimentacao)
      compra3 (model/nova-compra (UUID/randomUUID) (Date.) 100M "renner" :vestuario)]
  (c.db/salva-compra conn "1234567890123456" compra1)
  (c.db/salva-compra conn "1234567890123456" compra2)
  (c.db/salva-compra conn "1234567890123456" compra3))

(let [compras (c.db/todas-as-compras-por-cartao (d/db conn) "1234567890123456")
      resumo (c.logic/resumo-valor-por-categoria compras)]
  (println "Valor das compras por categoria: ")
  (println resumo))

(let [compras (c.db/todas-as-compras-por-cartao (d/db conn) "1234567890123456")
      resumo (c.logic/busca-compra-por-filtro compras :compra/estabelecimento "mcdonalds")]
  (println "Compras pelo filtro de estabelecimento:")
  (println resumo))

(let [compras (c.db/todas-as-compras-por-cartao (d/db conn) "1234567890123456")
      resumo (c.logic/busca-compra-por-filtro compras :compra/valor 29.90M)]
  (println "Compras pelo filtro de valor:")
  (println resumo))