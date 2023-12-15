package com.maltomondo.maltomondo.model.mo;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

public class CartItem {
    private Carrello cart;
    private int quantità;
    private Prodotto prodotto;
    private boolean deleted;

    public CartItem() {
    }

    public Carrello getCart() {
        return this.cart;
    }

    public void setCart(Carrello cart) {
        this.cart = cart;
    }

    public int getQuantità() {
        return this.quantità;
    }

    public void setQuantità(int quantità) {
        this.quantità = quantità;
    }

    public Prodotto getProdotto() {
        return this.prodotto;
    }

    public void setProdotto(Prodotto prodotto) {
        this.prodotto = prodotto;
    }

    public boolean isDeleted() {
        return this.deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}

