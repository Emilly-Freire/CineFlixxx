package com.cineback.repositories;

import com.cineback.Models.Pagamento;
import org.springframework.stereotype.Repository;

@Repository
public class PagamentoRepositoryFake implements PagamentoRepository {
    @Override
    public Pagamento save(Pagamento pagamento) {
        return pagamento;
    }
}