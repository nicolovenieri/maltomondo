package com.maltomondo.maltomondo.model.dao.CookieImpl;

import com.maltomondo.maltomondo.model.dao.CarrelloDAO;
import com.maltomondo.maltomondo.model.dao.CartItemDAO;
import com.maltomondo.maltomondo.model.dao.DAOFactory;
import com.maltomondo.maltomondo.model.dao.OrdineDAO;
import com.maltomondo.maltomondo.model.dao.PagamentoDAO;
import com.maltomondo.maltomondo.model.dao.ProdottoDAO;
import com.maltomondo.maltomondo.model.dao.UtenteDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Map;

public class CookieDAOFactory extends DAOFactory {
    private Map factoryParameters;
    private HttpServletRequest request;
    private HttpServletResponse response;

    public CookieDAOFactory(Map factoryParameters) {
        this.factoryParameters = factoryParameters;
    }

    public void beginTransaction() {
        try {
            this.request = (HttpServletRequest)this.factoryParameters.get("request");
            this.response = (HttpServletResponse)this.factoryParameters.get("response");
        } catch (Exception var2) {
            throw new RuntimeException(var2);
        }
    }

    public void commitTransaction() {
    }

    public void rollbackTransaction() {
    }

    public void closeTransaction() {
    }


    public UtenteDAO getUtenteDAO() {
        return new UtenteDAOCookieImpl(this.request, this.response);
    }

    public ProdottoDAO getProdottoDAO() {
        return null;
    }

    public OrdineDAO getOrdineDAO() {
        return null;
    }

    public CarrelloDAO getCarrelloDAO() {
        return null;
    }

    public CartItemDAO getCartItemDAO() {
        return null;
    }

    public PagamentoDAO getPagamentoDAO() {
        return null;
    }

}

