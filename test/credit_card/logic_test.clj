(ns credit-card.logic-test
  (:require [clojure.test :refer :all]
            [credit-card.logic :as c.logic]
            [schema.core :as s]))

(s/set-fn-validation! true)

(deftest adiciona-compra-test

  (testing "Adiciona nova compra"
    (is (= {:compras [{:data            "01/02/2021"
                       :valor           29.90
                       :estabelecimento "renner"
                       :categoria        :vestuario}] :resultado :sucesso}
           (c.logic/adiciona-compra nil {:data            "01/02/2021"
                                                   :valor           29.90
                                                   :estabelecimento "renner"
                                                   :categoria       :vestuario})))))

(deftest compras-do-cliente-test

  (testing "Listagem de compras"
    (is (= [{:data            "01/02/2021"
             :valor           29.90
             :estabelecimento "renner"
             :categoria        :vestuario}
            {:data            "01/02/2021"
             :valor           19.90
             :estabelecimento "bk"
             :categoria        :alimentacao}]
           (c.logic/compras-do-cliente {:id 1
                                        :nome "nome"
                                        :cpf "cpf"
                                        :cartao [{:numero "123456789012"
                                                  :cvv "012"
                                                  :validade "01/24"
                                                  :limite 2000
                                                  :compras [{:data            "01/02/2021"
                                                             :valor           29.90
                                                             :estabelecimento "renner"
                                                             :categoria       :vestuario}]}
                                                 {:numero "123456789012"
                                                  :cvv "012"
                                                  :validade "01/24"
                                                  :limite 2000
                                                  :compras [{:data            "01/02/2021"
                                                             :valor           19.90
                                                             :estabelecimento "bk"
                                                             :categoria        :alimentacao}]}]})))))

(deftest resumo-valor-por-categoria-test
  (testing "Retorno de valores por categoria"
    (is (= [{:categoria   :alimentacao
             :valor-total 60}
            {:categoria   :vestuario
             :valor-total 120.30}]
           (c.logic/resumo-valor-por-categoria [{:data            "01/02/2021"
                                                 :valor           20
                                                 :estabelecimento "bk"
                                                 :categoria       :alimentacao}
                                                {:data            "01/02/2021"
                                                 :valor           40
                                                 :estabelecimento "bk"
                                                 :categoria       :alimentacao}
                                                {:data            "01/02/2021"
                                                 :valor           120.30
                                                 :estabelecimento "bk"
                                                 :categoria       :vestuario}])))))