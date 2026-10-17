package com.cineback.Gateway;

public class SistemaPagamentoFake implements SistemaPagamento {
    public boolean processarPagamento(Pagamento p) { return true; }
    public StatusPagamento consultarStatus(Long id) { return StatusPagamento.PAGO; }
}