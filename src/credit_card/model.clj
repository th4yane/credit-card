(ns credit-card.model
  (:require [schema.core :as s]))

(def Compra
  {:data s/Str
   :valor s/Num
   :estabelecimento s/Str
   :categoria s/Keyword})

(def Cartao
  {:numero s/Str
   :cvv s/Str
   :validade s/Str
   :limite s/Num
   :compras [Compra]})

(def Cliente
  {:id s/Int
   :nome s/Str
   :cpf s/Str
   :cartao [Cartao]})

(def ValoresCategoria
  {:categoria s/Keyword
   :valor-total s/Num})