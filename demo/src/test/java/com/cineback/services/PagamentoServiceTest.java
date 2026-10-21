package com.cineback.services;

import com.cineback.Gateway.SistemaPagamento;
import com.cineback.Models.Pagamento;
import com.cineback.repositories.PagamentoRepository;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class PagamentoServiceTest {
    @Test
    public void testProcessarPagamento_Aprovado() {
        Pagamento pagamento = new Pagamento();
        pagamento.setValor(50.0);
        pagamento.setMetodoPagamento("cartao");

        PagamentoRepository repositorio = new RepositorioDeTeste();
        SistemaPagamento gateway = new GatewayDeTeste(true);
        PagamentoService pagamentoService = new PagamentoService(repositorio, gateway);

        Pagamento resultado = pagamentoService.processarPagamento(pagamento);

        assertEquals("APROVADO", resultado.getStatus());
        assertEquals(1, ((RepositorioDeTeste) repositorio).quantidadeSalva);
        assertEquals(1, ((GatewayDeTeste) gateway).quantidadeChamadas);
    }

    @Test
    public void testProcessarPagamento_Recusado() {
        Pagamento pagamento = new Pagamento();
        pagamento.setValor(50.0);
        pagamento.setMetodoPagamento("cartao");

        RepositorioDeTeste repositorio = new RepositorioDeTeste();
        GatewayDeTeste gateway = new GatewayDeTeste(false);
        PagamentoService pagamentoService = new PagamentoService(repositorio, gateway);

        Pagamento resultado = pagamentoService.processarPagamento(pagamento);

        assertEquals("RECUSADO", resultado.getStatus());
        assertEquals(1, gateway.quantidadeChamadas);
        assertEquals(1, repositorio.quantidadeSalva);
    }

    @Test
    public void testProcessarPagamento_ValorInvalido() {
        Pagamento pagamento = new Pagamento();
        pagamento.setValor(0.0);
        pagamento.setMetodoPagamento("cartao");

        RepositorioDeTeste repositorio = new RepositorioDeTeste();
        GatewayDeTeste gateway = new GatewayDeTeste(true);
        PagamentoService pagamentoService = new PagamentoService(repositorio, gateway);

        try {
            pagamentoService.processarPagamento(pagamento);
            fail("Deveria rejeitar valor zero.");
        } catch (IllegalArgumentException exception) {
            assertEquals("Valor do pagamento deve ser maior que zero.", exception.getMessage());
        }
        assertEquals(0, gateway.quantidadeChamadas);
        assertEquals(0, repositorio.quantidadeSalva);
    }

    private static class GatewayDeTeste implements SistemaPagamento {
        private final boolean autorizado;
        private int quantidadeChamadas;

        private GatewayDeTeste(boolean autorizado) {
            this.autorizado = autorizado;
        }

        @Override
        public boolean autorizar(String metodoPagamento, double valor) {
            quantidadeChamadas++;
            return autorizado;
        }
    }

    private static class RepositorioDeTeste implements PagamentoRepository {
        private int quantidadeSalva;

        @Override
        public Pagamento save(Pagamento pagamento) {
            quantidadeSalva++;
            return pagamento;
        }
    }
}
