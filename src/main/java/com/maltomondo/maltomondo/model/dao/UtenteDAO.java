package com.maltomondo.maltomondo.model.dao;

import com.maltomondo.maltomondo.model.dao.exception.DuplicatedObjectException;
import com.maltomondo.maltomondo.model.mo.Utente;

import java.util.List;

public interface UtenteDAO {
    Utente create(Long var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, Boolean var9, int var10) throws DuplicatedObjectException;

    void update(Utente var1);

    void deleteUser(Utente var1);

    void block(Utente var1);

    void unblock(Utente var1);

    void setAdmin(Utente var1);

    void unsetAdmin(Utente var1);

    Utente findLoggedUser();

    Utente findByUserId(Long var1);

    Utente findByEmail(String var1);

    List<Utente> findAllAndSearchString(String var1);
}
