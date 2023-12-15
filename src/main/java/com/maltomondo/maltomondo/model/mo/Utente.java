package com.maltomondo.maltomondo.model.mo;
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)


public class Utente {
    private Long id_utente;
    private String nome;
    private String password;
    private String cognome;
    private String email;
    private boolean deleted;
    private boolean admin;
    private boolean blocked;
    private String indirizzo;
    private String stato;
    private String città;
    private int CAP;
    private Carrello[] carrello;

    public Utente() {
    }

    public Long getId_utente() {
        return this.id_utente;
    }

    public void setId_utente(Long id_utente) {
        this.id_utente = id_utente;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCognome() {
        return this.cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public boolean isDeleted() {
        return this.deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public boolean isAdmin() {
        return this.admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public boolean isBlocked() {
        return this.blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public String getIndirizzo() {
        return this.indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getStato() {
        return this.stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public String getCittà() {
        return this.città;
    }

    public void setCittà(String città) {
        this.città = città;
    }

    public int getCAP() {
        return this.CAP;
    }

    public void setCAP(int CAP) {
        this.CAP = CAP;
    }

    public Carrello getCarrello(int index) {
        return this.carrello[index];
    }

    public void setCarrello(int index, Carrello carrello) {
        this.carrello[index] = carrello;
    }

    public Carrello[] getCarrello() {
        return this.carrello;
    }

    public void setCarrello(Carrello[] carrello) {
        this.carrello = carrello;
    }
}

