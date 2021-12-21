(ns credit-card.logic-test
  (:require [clojure.test :refer :all]
            [credit-card.logic :as c.logic]
            [schema.core :as s]))

(s/set-fn-validation! true)

(deftest adiciona-compra-test
  (testing "Adiciona nova compra"
    (is (= {:compras [{:compra/id              #uuid "a915a7ea-5645-4d9b-ae72-e971482104ed"
                       :compra/data            #inst "2021-12-21T21:39:20.387-00:00"
                       :compra/valor           29.90M
                       :compra/estabelecimento "renner"
                       :compra/categoria       :vestuario}] :resultado :sucesso}
           (c.logic/adiciona-compra nil {:compra/id              #uuid "a915a7ea-5645-4d9b-ae72-e971482104ed"
                                         :compra/data            #inst "2021-12-21T21:39:20.387-00:00"
                                         :compra/valor           29.90M
                                         :compra/estabelecimento "renner"
                                         :compra/categoria       :vestuario})))))

(deftest compras-do-cliente-test

  (testing "Listagem de compras"
    (is (= [{:compra/id              #uuid "a915a7ea-5645-4d9b-ae72-e971482104ed"
             :compra/data            #inst "2021-12-21T21:39:20.387-00:00"
             :compra/valor           29.90M
             :compra/estabelecimento "bk"
             :compra/categoria       :alimentacao}
            {:compra/id              #uuid "b915a7ea-5645-4d9b-ae72-e971412304ed"
             :compra/data            #inst "2021-12-21T21:39:20.387-00:00"
             :compra/valor           100M
             :compra/estabelecimento "renner"
             :compra/categoria       :vestuario}]
           (c.logic/compras-do-cliente {:cliente/id     #uuid "c115a7ea-5645-4d9b-ae72-e971412304ed"
                                        :cliente/nome   "nome"
                                        :cliente/cpf    "cpf"
                                        :cliente/cartao  {:cartao/numero   "123456789012"
                                                            :cartao/cvv      "012"
                                                            :cartao/validade "01/24"
                                                            :cartao/limite   2000
                                                            :cartao/compras  [{:compra/id              #uuid "a915a7ea-5645-4d9b-ae72-e971482104ed"
                                                                               :compra/data            #inst "2021-12-21T21:39:20.387-00:00"
                                                                               :compra/valor           29.90M
                                                                               :compra/estabelecimento "bk"
                                                                               :compra/categoria       :alimentacao}
                                                                              {:compra/id              #uuid "b915a7ea-5645-4d9b-ae72-e971412304ed"
                                                                               :compra/data            #inst "2021-12-21T21:39:20.387-00:00"
                                                                               :compra/valor           100M
                                                                               :compra/estabelecimento "renner"
                                                                               :compra/categoria       :vestuario}]}})))))

(deftest resumo-valor-por-categoria-test
  (testing "Retorno de valores por categoria"
    (is (= [{:categoria   :alimentacao
             :valor-total 60M}
            {:categoria   :vestuario
             :valor-total 120.30M}]
           (c.logic/resumo-valor-por-categoria [{:compra/id              #uuid "a915a7ea-5645-4d9b-ae72-e971482104ed"
                                                 :compra/data            #inst "2021-12-21T21:39:20.387-00:00"
                                                 :compra/valor           20M
                                                 :compra/estabelecimento "bk"
                                                 :compra/categoria       :alimentacao}
                                                {:compra/id              #uuid "b115a7ea-5645-4d9b-ae72-e971482104ed"
                                                 :compra/data            #inst "2021-12-21T21:39:20.387-00:00"
                                                 :compra/valor           40M
                                                 :compra/estabelecimento "bk"
                                                 :compra/categoria       :alimentacao}
                                                {:compra/id              #uuid "c315a7ea-5645-4d9b-ae72-e971482104ed"
                                                 :compra/data            #inst "2021-12-21T21:39:20.387-00:00"
                                                 :compra/valor           120.30M
                                                 :compra/estabelecimento "bk"
                                                 :compra/categoria       :vestuario}])))))