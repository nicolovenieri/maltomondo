package com.maltomondo.maltomondo.model.mo;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.math.BigDecimal;

public class Prodotto {
    private Long id_amuleto;
    private String nome;
    private String sede;
    private String descrizione;
    private BigDecimal prezzo;
    private int quantità_disponibile;
    private String categoria;
    private boolean deleted;
    private boolean blocked;
    private boolean push;
    private Long Version;
    private String img_path;
    private Carrello[] carrello;

    public Prodotto() {
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getId_amuleto() {
        return this.id_amuleto;
    }

    public void setId_amuleto(Long id_amuleto) {
        this.id_amuleto = id_amuleto;
    }

    public String getSede() {
        return this.sede;
    }

    public void setSede(String sede) {
        this.sede = sede;
    }

    public String getDescrizione() {
        return this.descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public BigDecimal getPrezzo() {
        return this.prezzo;
    }

    public void setPrezzo(BigDecimal prezzo) {
        this.prezzo = prezzo;
    }

    public int getQuantità_disponibile() {
        return this.quantità_disponibile;
    }

    public void setQuantità_disponibile(int quantità_disponibile) {
        this.quantità_disponibile = quantità_disponibile;
    }

    public String getCategoria() {
        return this.categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public boolean isDeleted() {
        return this.deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public boolean isBlocked() {
        return this.blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public boolean isPush() {
        return this.push;
    }

    public void setPush(boolean push) {
        this.push = push;
    }

    public Carrello[] getCarrello() {
        return this.carrello;
    }

    public void setCarrello(Carrello[] carrello) {
        this.carrello = carrello;
    }

    public Carrello getCarrello(int index) {
        return this.carrello[index];
    }

    public void setCarrello(int index, Carrello carrello) {
        this.carrello[index] = carrello;
    }

    public Long getVersion() {
        return this.Version;
    }

    public void setVersion(Long version) {
        this.Version = version;
    }

    public String getImg_path() {
        return this.img_path;
    }

    public void setImg_path(String img_path) {
        this.img_path = img_path;
    }
}

