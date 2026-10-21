package com.cineback.Gateway;

import org.springframework.stereotype.Component;

@Component
public class SistemaPagamentoFake implements SistemaPagamento {
    @Override
    public boolean autorizar(String metodoPagamento, double valor) {
        return metodoPagamento != null && !metodoPagamento.trim().isEmpty() && valor > 0;
    }
}