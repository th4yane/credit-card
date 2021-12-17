(ns credit-card.db)

(def cliente {:usuario 2
              :nome "Maria Antonia"
              :CPF "876543210"
              :cartao { :numero "123456789012"
                              :cvv "012"
                              :validade "01/24"
                              :limite 2000
                              :compras [{:data "01/02/2021"
                                         :valor 230.5
                                         :estabelecimento "loja abc"
                                         :categoria "vestuario"}
                                        {:data "02/02/2021"
                                         :valor 23
                                         :estabelecimento "loja def"
                                         :categoria "alimentacao"}
                                         {:data "03/02/2021"
                                          :valor 100
                                          :estabelecimento "loja def"
                                          :categoria "alimentacao"}]}})
