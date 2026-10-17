public interface SistemaPagamento {
    boolean processarPagamento(Pagamento pagamento);
    StatusPagamento consultarStatus(Long idPagamento);
}