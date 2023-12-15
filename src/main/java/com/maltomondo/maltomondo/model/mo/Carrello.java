package com.maltomondo.maltomondo.model.mo;
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.math.BigDecimal;
import java.util.Date;

public class Carrello {
    private Long id_carrello;
    private String stato_c;
    private Date data_creazione;
    private BigDecimal totale_carrello;
    private Boolean deleted;
    private Utente utente;
    private Prodotto[] prodotto;
    private Ordine ordine;

    public Carrello() {
    }

    public Long getId_carrello() {
        return this.id_carrello;
    }

    public void setId_carrello(Long id_carrello) {
        this.id_carrello = id_carrello;
    }

    public String getStato_c() {
        return this.stato_c;
    }

    public void setStato_c(String stato_c) {
        this.stato_c = stato_c;
    }

    public Date getData_creazione() {
        return this.data_creazione;
    }

    public void setData_creazione(Date data_creazione) {
        this.data_creazione = data_creazione;
    }

    public BigDecimal getTotale_carrello() {
        return this.totale_carrello;
    }

    public void setTotale_carrello(BigDecimal totale_carrello) {
        this.totale_carrello = totale_carrello;
    }

    public Boolean getDeleted() {
        return this.deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Utente getUtente() {
        return this.utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public Prodotto[] getProdotto() {
        return this.prodotto;
    }

    public void setProdotto(Prodotto[] prodotto) {
        this.prodotto = prodotto;
    }

    public Prodotto getProdotto(int index) {
        return this.prodotto[index];
    }

    public void setProdotto(int index, Prodotto prodotto) {
        this.prodotto[index] = prodotto;
    }

    public Ordine getOrdine() {
        return this.ordine;
    }

    public void setOrdine(Ordine ordine) {
        this.ordine = ordine;
    }
}
