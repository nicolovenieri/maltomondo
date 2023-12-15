package com.maltomondo.maltomondo.model.dao;

import com.maltomondo.maltomondo.model.dao.exception.DuplicatedObjectException;
import com.maltomondo.maltomondo.model.mo.Carrello;
import com.maltomondo.maltomondo.model.mo.Ordine;
import com.maltomondo.maltomondo.model.mo.Utente;

import java.math.BigDecimal;
import java.util.Date;

public interface CarrelloDAO {
    Carrello create(Date var1, Utente var2) throws DuplicatedObjectException;

    void delete(Carrello var1);

    void setStatoChiuso(Utente var1);

    void restoreCart(Carrello var1);

    Carrello findOpenCartByUserId(Long var1);

    Carrello findCartByOrder(Ordine var1);

    void UpdateBySumTotal(Carrello var1, BigDecimal var2);

    void UpdateTotalBySubtract(Carrello var1, BigDecimal var2);
}

