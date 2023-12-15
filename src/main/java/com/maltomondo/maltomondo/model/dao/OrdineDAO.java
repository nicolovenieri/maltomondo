package com.maltomondo.maltomondo.model.dao;

import com.maltomondo.maltomondo.model.dao.exception.DuplicatedObjectException;
import com.maltomondo.maltomondo.model.mo.Carrello;
import com.maltomondo.maltomondo.model.mo.Ordine;
import com.maltomondo.maltomondo.model.mo.Utente;

import java.util.Date;
import java.util.List;

public interface OrdineDAO {
    Ordine create(Date var1, Carrello var2, Utente var3, String var4, String var5, int var6, String var7) throws DuplicatedObjectException;

    void update(Ordine var1);

    void delete(Ordine var1);

    void permanentlydelete(Ordine var1);

    void setConsegnato(Ordine var1);

    void setStatoCompletato(Ordine var1);

    Ordine setStatoAnnullato(Ordine var1);

    Ordine findByOrderId(Long var1);

    Ordine findByStatoInSospeso(Utente var1);

    List<Ordine> findAllAndSearchString(String var1);

    List<Ordine> showPersonalOrders(Long var1);

    Long getLinkedID_Cart(Long var1);
}


