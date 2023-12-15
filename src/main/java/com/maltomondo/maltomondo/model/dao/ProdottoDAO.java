package com.maltomondo.maltomondo.model.dao;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import com.maltomondo.maltomondo.model.dao.exception.DuplicatedObjectException;
import com.maltomondo.maltomondo.model.dao.exception.NotAvaibleProductException;
import com.maltomondo.maltomondo.model.dao.exception.OptimisticLockException;
import com.maltomondo.maltomondo.model.mo.Prodotto;

import java.math.BigDecimal;
import java.util.List;

public interface ProdottoDAO {
    Prodotto create(String var1, String var2, String var3, BigDecimal var4, int var5, String var6, Boolean var7) throws DuplicatedObjectException;

    void update(Prodotto var1) throws DuplicatedObjectException;

    void delete(Prodotto var1);

    Prodotto findByProdottoId(Long var1);

    List<Prodotto> findByCategoryANDSearchString(String var1, String var2);

    int checkAndDecreaseQuantity(Long var1, int var2) throws NotAvaibleProductException, OptimisticLockException;

    void checkAvaibleQuantity(Long var1, int var2) throws NotAvaibleProductException;

    Boolean isAvaible(Prodotto var1, int var2);

    void modify(Long var1, String var2, String var3, String var4, BigDecimal var5, int var6, String var7, Boolean var8, Boolean var9, Boolean var10);

    List<Prodotto> findPushProducts();
}
