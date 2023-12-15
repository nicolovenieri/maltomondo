package com.maltomondo.maltomondo.model.mo;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.util.Date;

public class Ordine {
    private Long ID_ordine;
    private Date Data_ordine;
    private String Stato_o;
    private Boolean Deleted;
    private Boolean Consegnato;
    private String Indirizzo_consegna;
    private String Città_consegna;
    private int CAP_consegna;
    private String Nazione_consegna;
    private Carrello carrello;
    private Utente utente;

    public Ordine() {
    }

    public Long getID_ordine() {
        return this.ID_ordine;
    }

    public void setID_ordine(Long ID_ordine) {
        this.ID_ordine = ID_ordine;
    }

    public Date getData_ordine() {
        return this.Data_ordine;
    }

    public void setData_ordine(Date data_ordine) {
        this.Data_ordine = data_ordine;
    }

    public String getStato_o() {
        return this.Stato_o;
    }

    public void setStato_o(String stato_o) {
        this.Stato_o = stato_o;
    }

    public Boolean isDeleted() {
        return this.Deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.Deleted = deleted;
    }

    public Boolean isConsegnato() {
        return this.Consegnato;
    }

    public void setConsegnato(Boolean consegnato) {
        this.Consegnato = consegnato;
    }

    public String getIndirizzo_consegna() {
        return this.Indirizzo_consegna;
    }

    public void setIndirizzo_consegna(String indirizzo_consegna) {
        this.Indirizzo_consegna = indirizzo_consegna;
    }

    public String getCittà_consegna() {
        return this.Città_consegna;
    }

    public void setCittà_consegna(String città_consegna) {
        this.Città_consegna = città_consegna;
    }

    public int getCAP_consegna() {
        return this.CAP_consegna;
    }

    public void setCAP_consegna(int CAP_consegna) {
        this.CAP_consegna = CAP_consegna;
    }

    public String getNazione_consegna() {
        return this.Nazione_consegna;
    }

    public void setNazione_consegna(String nazione_consegna) {
        this.Nazione_consegna = nazione_consegna;
    }

    public Carrello getCarrello() {
        return this.carrello;
    }

    public void setCarrello(Carrello carrello) {
        this.carrello = carrello;
    }

    public Utente getUtente() {
        return this.utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }
}
