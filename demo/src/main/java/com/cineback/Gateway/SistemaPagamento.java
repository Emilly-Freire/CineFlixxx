package com.cineback.Gateway;

public interface SistemaPagamento {
    boolean autorizar(String metodoPagamento, double valor);
}