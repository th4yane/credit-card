(ns credit-card.model
  (:require [schema.core :as s]))

(def Compra
  {:compra/id              s/Uuid
   :compra/data            s/Inst
   :compra/valor           s/Num
   :compra/estabelecimento s/Str
   :compra/categoria       s/Keyword})

(def Cartao
  {:cartao/numero   s/Str
   :cartao/cvv      s/Str
   :cartao/validade s/Str
   :cartao/limite   s/Num
   :cartao/compras  [Compra]})

(def Cliente
  {:cliente/id     s/Uuid
   :cliente/nome   s/Str
   :cliente/cpf    s/Str
   :cliente/cartao Cartao})

(defn nova-compra [id data valor estabelecimento categoria] :- Compra
  {:compra/id              id
   :compra/data            data
   :compra/valor           valor
   :compra/estabelecimento estabelecimento
   :compra/categoria       categoria})

(defn novo-cartao [numero cvv validade limite compras] :- Cartao
  {:cartao/numero   numero
   :cartao/cvv      cvv
   :cartao/validade validade
   :cartao/limite   limite
   :cartao/compras  compras})

(defn novo-cliente [id nome cpf cartao] :- Cliente
  {:cliente/id     id
   :cliente/nome   nome
   :cliente/cpf    cpf
   :cliente/cartao cartao})

(def ValoresCategoria
  {:categoria   s/Keyword
   :valor-total s/Num})