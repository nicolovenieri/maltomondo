package com.maltomondo.maltomondo.model.dao.mySQLJDBCImpl;
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import com.maltomondo.maltomondo.services.config.Configuration;
import com.maltomondo.maltomondo.model.dao.CarrelloDAO;
import com.maltomondo.maltomondo.model.dao.CartItemDAO;
import com.maltomondo.maltomondo.model.dao.DAOFactory;
import com.maltomondo.maltomondo.model.dao.OrdineDAO;
import com.maltomondo.maltomondo.model.dao.PagamentoDAO;
import com.maltomondo.maltomondo.model.dao.ProdottoDAO;
import com.maltomondo.maltomondo.model.dao.UtenteDAO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

public class MySQLJDBCDAOFactory extends DAOFactory {
    private Map factoryParameters;
    private Connection connection;

    public MySQLJDBCDAOFactory(Map factoryParameters) {
        this.factoryParameters = factoryParameters;
    }

    public void beginTransaction() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(Configuration.DATABASE_URL);
            this.connection.setAutoCommit(false);
        } catch (ClassNotFoundException var2) {
            throw new RuntimeException(var2);
        } catch (SQLException var3) {
            throw new RuntimeException(var3);
        }
    }

    public void commitTransaction() {
        try {
            this.connection.commit();
        } catch (SQLException var2) {
            throw new RuntimeException(var2);
        }
    }

    public void rollbackTransaction() {
        try {
            this.connection.rollback();
        } catch (SQLException var2) {
            throw new RuntimeException(var2);
        }
    }

    public void closeTransaction() {
        try {
            this.connection.close();
        } catch (SQLException var2) {
            throw new RuntimeException(var2);
        }
    }


    public UtenteDAO getUtenteDAO() {
        return new UtenteDAOMySQLJDBCImpl(this.connection);
    }

    public ProdottoDAO getProdottoDAO() {
        return new ProdottoDAOMySQLJDBCImpl(this.connection);
    }

    public OrdineDAO getOrdineDAO() {
        return new OrdineDAOMySQLJDBCImpl(this.connection);
    }

    public CarrelloDAO getCarrelloDAO() {
        return new CarrelloDAOMySQLJDBCImpl(this.connection);
    }

    public CartItemDAO getCartItemDAO() {
        return new CartItemDAOMySQLJDBCImpl(this.connection);
    }

    public PagamentoDAO getPagamentoDAO() {
        return new PagamentoDAOMySQLJDBCImpl(this.connection);
    }

}


