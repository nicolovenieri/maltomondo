package com.maltomondo.maltomondo.model.dao;


import com.maltomondo.maltomondo.model.dao.CookieImpl.CookieDAOFactory;
import com.maltomondo.maltomondo.model.dao.mySQLJDBCImpl.MySQLJDBCDAOFactory;

import java.util.Map;

public abstract class DAOFactory {
    public static final String MYSQLJDBCIMPL = "MySQLJDBCImpl";
    public static final String COOKIEIMPL = "CookieImpl";

    public DAOFactory() {
    }

    public abstract void beginTransaction();

    public abstract void commitTransaction();

    public abstract void rollbackTransaction();

    public abstract void closeTransaction();


    public abstract UtenteDAO getUtenteDAO();

    public abstract ProdottoDAO getProdottoDAO();

    public abstract OrdineDAO getOrdineDAO();

    public abstract CartItemDAO getCartItemDAO();

    public abstract CarrelloDAO getCarrelloDAO();

    public abstract PagamentoDAO getPagamentoDAO();


    public static DAOFactory getDAOFactory(String whichFactory, Map factoryParameters) {
        if (whichFactory.equals("MySQLJDBCImpl")) {
            return new MySQLJDBCDAOFactory(factoryParameters);
        } else {
            return whichFactory.equals("CookieImpl") ? new CookieDAOFactory(factoryParameters) : null;
        }
    }
}
