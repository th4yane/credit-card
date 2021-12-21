(ns credit-card.db
  (:require [datomic.api :as d]))

(def db-uri "datomic:dev://localhost:4334/card")

(defn abre-conexao []
  (d/create-database db-uri)
  (d/connect db-uri))

(defn apaga-banco []
  (d/delete-database db-uri))

(def schema [
                            {:db/ident       :cliente/id
                             :db/valueType   :db.type/uuid
                             :db/cardinality :db.cardinality/one
                             :db/unique      :db.unique/identity
                             :db/doc         "O id de um cliente"}
                            {:db/ident       :cliente/nome
                             :db/valueType   :db.type/string
                             :db/cardinality :db.cardinality/one
                             :db/doc         "O nome de um cliente"}
                            {:db/ident       :cliente/cpf
                             :db/valueType   :db.type/string
                             :db/cardinality :db.cardinality/one
                             :db/unique      :db.unique/identity
                             :db/doc         "O CPF de um cliente"}
                            {:db/ident       :cliente/cartao
                             :db/valueType   :db.type/ref
                             :db/cardinality :db.cardinality/one
                             :db/doc         "O cartao do cliente"}

                            {:db/ident       :cartao/numero
                             :db/valueType   :db.type/string
                             :db/cardinality :db.cardinality/one
                             :db/unique      :db.unique/identity
                             :db/doc         "O numero do cartao"}
                            {:db/ident       :cartao/cvv
                             :db/valueType   :db.type/string
                             :db/cardinality :db.cardinality/one
                             :db/doc         "O CVV do cartao"}
                            {:db/ident       :cartao/validade
                             :db/valueType   :db.type/string
                             :db/cardinality :db.cardinality/one
                             :db/doc         "A validade do cartao"}
                            {:db/ident       :cartao/limite
                             :db/valueType   :db.type/bigdec
                             :db/cardinality :db.cardinality/one
                             :db/doc         "O limite do cartao"}
                            {:db/ident       :cartao/compras
                             :db/valueType   :db.type/ref
                             :db/cardinality :db.cardinality/many
                             :db/doc         "Compras do cartao"}

                            {:db/ident :compra/id
                             :db/valueType :db.type/uuid
                             :db/cardinality :db.cardinality/one
                             :db/unique      :db.unique/identity
                             :db/doc         "O id da compra"}
                            {:db/ident :compra/data
                             :db/valueType :db.type/instant
                             :db/cardinality :db.cardinality/one
                             :db/doc         "A data da compra"}
                            {:db/ident :compra/valor
                             :db/valueType :db.type/bigdec
                             :db/cardinality :db.cardinality/one
                             :db/doc         "O valor da compra"}
                            {:db/ident :compra/estabelecimento
                             :db/valueType :db.type/string
                             :db/cardinality :db.cardinality/one
                             :db/doc         "O estabelecimento onde a compra foi realizada"}
                            {:db/ident :compra/categoria
                             :db/valueType :db.type/keyword
                             :db/cardinality :db.cardinality/one
                             :db/doc         "A categoria da compra"}])


(defn cria-schema [conn]
  (d/transact conn schema))

(defn salva-cliente [conn cliente]
  (d/transact conn [cliente]))

(defn salva-compra [conn cartao-numero compra]
  (d/transact conn [compra])
  (d/transact conn [[:db/add [:cartao/numero cartao-numero] :cartao/compras [:compra/id (:compra/id compra)]]]))

(defn todos-os-clientes [db]
  (d/q '[:find (pull ?entidade [*])
         :where [?entidade :cliente/id]] db))

(defn remove-db-id [entidade]
    (mapv #(dissoc % :db/id) entidade))

(defn todas-as-compras-por-cartao [db cartao-numero]
  (remove-db-id (d/q '[:find [(pull ?compras [*]) ...]
                 :in $ ?cartao-procurado
                 :where [?entidade :cartao/numero ?cartao-procurado]
                        [?entidade :cartao/compras ?compras]]
                 db cartao-numero)))