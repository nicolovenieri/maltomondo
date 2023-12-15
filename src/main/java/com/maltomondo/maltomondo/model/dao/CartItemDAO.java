package com.maltomondo.maltomondo.model.dao;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import com.maltomondo.maltomondo.model.dao.exception.DuplicatedObjectException;
import com.maltomondo.maltomondo.model.mo.Carrello;
import com.maltomondo.maltomondo.model.mo.CartItem;
import com.maltomondo.maltomondo.model.mo.Prodotto;
import java.util.List;
import java.util.Map;

public interface CartItemDAO {
    CartItem create(Carrello var1, Prodotto var2, int var3) throws DuplicatedObjectException;

    void create(Carrello var1, Long var2, int var3) throws DuplicatedObjectException;

    void modifyQuantity(Long var1, Long var2, int var3);

    void deleteItem(Long var1, Long var2);

    CartItem findCartItem(Long var1, Long var2);

    Map<CartItem, Prodotto> findCartItems(Long var1);

    List<CartItem> getCartItems(Carrello var1);

    Map<CartItem, Prodotto> findOrderItems(Long var1);

    void modifyQuantityBySum(CartItem var1, int var2);
}


