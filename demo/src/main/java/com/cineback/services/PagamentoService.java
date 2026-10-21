package com.cineback.services;

import com.cineback.Gateway.SistemaPagamento;
import com.cineback.Models.Pagamento;
import com.cineback.repositories.PagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PagamentoService {
    private final PagamentoRepository pagamentoRepository;
    private final SistemaPagamento sistemaPagamento;

    @Autowired
    public PagamentoService(PagamentoRepository pagamentoRepository, SistemaPagamento sistemaPagamento) {
        this.pagamentoRepository = pagamentoRepository;
        this.sistemaPagamento = sistemaPagamento;
    }

    public Pagamento processarPagamento(Pagamento pagamento) {
        if(pagamento.getValor() <= 0) {
            throw new IllegalArgumentException("Valor do pagamento deve ser maior que zero.");
        }

        boolean autorizado = sistemaPagamento.autorizar(pagamento.getMetodoPagamento(), pagamento.getValor());
        if(autorizado) {
            pagamento.setStatus("APROVADO");
        } else {
            pagamento.setStatus("RECUSADO");
        }
        return pagamentoRepository.save(pagamento);
    }
}