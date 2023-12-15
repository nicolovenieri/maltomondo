package com.maltomondo.maltomondo.model.mo;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.math.BigDecimal;

public class Pagamento {
    private Long id_pagamento;
    private BigDecimal importo;
    private String stato_p;
    private String tipo;
    private boolean deleted;
    private double NumeroCarta;
    private int CVV;
    private String name;
    private String ScadenzaCarta;
    private Ordine ordine;

    public Pagamento() {
    }

    public String getScadenzaCarta() {
        return this.ScadenzaCarta;
    }

    public void setScadenzaCarta(String scadenzaCarta) {
        this.ScadenzaCarta = scadenzaCarta;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCVV() {
        return this.CVV;
    }

    public void setCVV(int CVV) {
        this.CVV = CVV;
    }

    public double getNumeroCarta() {
        return this.NumeroCarta;
    }

    public void setNumeroCarta(double numeroCarta) {
        this.NumeroCarta = numeroCarta;
    }

    public Long getId_pagamento() {
        return this.id_pagamento;
    }

    public void setId_pagamento(Long id_pagamento) {
        this.id_pagamento = id_pagamento;
    }

    public BigDecimal getImporto() {
        return this.importo;
    }

    public void setImporto(BigDecimal importo) {
        this.importo = importo;
    }

    public String getStato_p() {
        return this.stato_p;
    }

    public void setStato_p(String stato_p) {
        this.stato_p = stato_p;
    }

    public String getTipo() {
        return this.tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public boolean isDeleted() {
        return this.deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Ordine getOrdine() {
        return this.ordine;
    }

    public void setOrdine(Ordine ordine) {
        this.ordine = ordine;
    }
}


