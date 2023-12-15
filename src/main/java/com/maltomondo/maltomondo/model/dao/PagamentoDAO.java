package com.maltomondo.maltomondo.model.dao;

import com.maltomondo.maltomondo.model.dao.exception.DuplicatedObjectException;
import com.maltomondo.maltomondo.model.mo.Ordine;
import com.maltomondo.maltomondo.model.mo.Pagamento;

import java.math.BigDecimal;

public interface PagamentoDAO {
    Pagamento create(BigDecimal var1, Ordine var2, Double var3, int var4, String var5, String var6) throws DuplicatedObjectException;

    Pagamento findbyOrderID(Long var1);
}

