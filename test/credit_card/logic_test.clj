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