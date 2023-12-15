package com.maltomondo.maltomondo.controller;

import com.maltomondo.maltomondo.model.dao.DAOFactory;
import com.maltomondo.maltomondo.model.dao.OrdineDAO;
import com.maltomondo.maltomondo.model.dao.UtenteDAO;
import com.maltomondo.maltomondo.model.mo.Ordine;
import com.maltomondo.maltomondo.model.mo.Utente;
import com.maltomondo.maltomondo.services.config.Configuration;
import com.maltomondo.maltomondo.services.logservice.LogService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HomeManager {
    public static void view(HttpServletRequest request, HttpServletResponse response) {
        DAOFactory sessionDAOFactory= null;
        Utente loggedUser;
        Logger logger = LogService.getApplicationLogger();

        try {
            Map sessionFactoryParameters=new HashMap<String,Object>();
            sessionFactoryParameters.put("request",request);
            sessionFactoryParameters.put("response",response);
            sessionDAOFactory = DAOFactory.getDAOFactory(Configuration.COOKIE_IMPL,sessionFactoryParameters);
            sessionDAOFactory.beginTransaction();

            UtenteDAO sessionUtenteDAO = sessionDAOFactory.getUtenteDAO();
            loggedUser = sessionUtenteDAO.findLoggedUser();

            sessionDAOFactory.commitTransaction();

            request.setAttribute("loggedOn",loggedUser!=null);
            request.setAttribute("loggedUser", loggedUser);
            request.setAttribute("viewUrl", "HomeManager/HomePage");
        }catch (Exception e) {

            try {logger.log(Level.SEVERE, "Controller Error", e);
                if (sessionDAOFactory != null) sessionDAOFactory.rollbackTransaction();
            } catch (Throwable t) {
            }
            throw new RuntimeException(e);

        } finally {
            try {
                if (sessionDAOFactory != null) sessionDAOFactory.closeTransaction();
            } catch (Throwable t) {
            }
        }
    }

    public static void loginView(HttpServletRequest request, HttpServletResponse response) {
        DAOFactory sessionDAOFactory=null;
        Utente loggedUser;

        Logger logger = LogService.getApplicationLogger();

        try{
            Map sessionFactoryParameters=new HashMap<String,Object>();
            sessionFactoryParameters.put("request",request);
            sessionFactoryParameters.put("response",response);
            sessionDAOFactory = DAOFactory.getDAOFactory(Configuration.COOKIE_IMPL,sessionFactoryParameters);
            sessionDAOFactory.beginTransaction();

            UtenteDAO sessionUserDAO = sessionDAOFactory.getUtenteDAO();
            loggedUser = sessionUserDAO.findLoggedUser();

            request.setAttribute("loggedOn",loggedUser!=null);
            request.setAttribute("loggedUser", loggedUser);
            request.setAttribute("viewUrl", "HomeManager/loginView");

        }catch (Exception e) {
            logger.log(Level.SEVERE, "Controller Error", e);
            try {
                if (sessionDAOFactory != null) sessionDAOFactory.rollbackTransaction();
            } catch (Throwable t) {
            }
            throw new RuntimeException(e);

        } finally {
            try {
                if (sessionDAOFactory != null) sessionDAOFactory.closeTransaction();
            } catch (Throwable t) {
            }

        }
    }

    public static void login(HttpServletRequest request, HttpServletResponse response){

        DAOFactory sessionDAOFactory= null;
        DAOFactory daoFactory = null;
        Utente loggedUser;
        String applicationMessage = null;
        Logger logger = LogService.getApplicationLogger();

        try{
            Map sessionFactoryParameters=new HashMap<String,Object>();
            sessionFactoryParameters.put("request",request);
            sessionFactoryParameters.put("response",response);
            sessionDAOFactory = DAOFactory.getDAOFactory(Configuration.COOKIE_IMPL,sessionFactoryParameters);
            sessionDAOFactory.beginTransaction();

            UtenteDAO sessionUserDAO = sessionDAOFactory.getUtenteDAO();
            loggedUser = sessionUserDAO.findLoggedUser();

            daoFactory = DAOFactory.getDAOFactory(Configuration.DAO_IMPL,null);
            daoFactory.beginTransaction();

            String email = request.getParameter("email");
            String password = request.getParameter("password");

            UtenteDAO utenteDAO = daoFactory.getUtenteDAO();
            Utente utente = utenteDAO.findByEmail(email);

            if (utente == null || !utente.getPassword().equals(password) || utente.isBlocked() == true || utente.isDeleted() == true) {
                sessionUserDAO.deleteUser(null);
                applicationMessage = "email e password errati!";
                loggedUser=null;
                request.setAttribute("viewUrl", "HomeManager/HomePage");
             } else {
                loggedUser = sessionUserDAO.create(utente.getId_utente(), utente.getNome(), utente.getCognome(),null, null, null, null, null, utente.isAdmin(), 0);

                if(utente != null && utente.isAdmin()) {
                    logger.log(Level.INFO, "Utente loggato come admin: " + utente.getEmail());
                    logger.log(Level.INFO, "Vista da visualizzare: " + request.getAttribute("viewUrl"));
                    request.setAttribute("viewUrl", "AdminManagment/AdminPage");
                } else {
                    logger.log(Level.INFO, "Vista da visualizzare: " + request.getAttribute("viewUrl"));
                    request.setAttribute("viewUrl", "HomeManager/HomePage");
                    /*
                    OrdineDAO ordineDAO = daoFactory.getOrdineDAO();
                    List<Ordine> ordini = new ArrayList<Ordine>(ordineDAO.showPersonalOrders(loggedUser.getId_utente()));

                    if(loggedUser != null && !loggedUser.isAdmin() && !ordini.isEmpty()) {
                      //qui dovrai mettere il redirect al listino prodotti in quanto quando uno logga deve vedere i propri ordini mentre se uno logga e non ha ordini non deve vederli
                    }
                    */

                }
            }



            daoFactory.commitTransaction();
            sessionDAOFactory.commitTransaction();

            request.setAttribute("loggedOn",loggedUser!=null);
            request.setAttribute("loggedUser", loggedUser);
            request.setAttribute("applicationMessage", applicationMessage);
            //  request.setAttribute("viewUrl", "HomeManager/HomePage");

        }catch (Exception e){
            logger.log(Level.SEVERE, "Controller Error", e);
            try {
                if (daoFactory != null) daoFactory.rollbackTransaction();
                if (sessionDAOFactory != null) sessionDAOFactory.rollbackTransaction();
            } catch (Throwable t) {
            }
            throw new RuntimeException(e);

        } finally{
            try {
                if (daoFactory != null) daoFactory.closeTransaction();
                if (sessionDAOFactory != null) sessionDAOFactory.closeTransaction();
            } catch (Throwable t) {
            }
        }


    }

    public static void logout(HttpServletRequest request, HttpServletResponse response){
        DAOFactory sessionDAOFactory= null;
        Logger logger = LogService.getApplicationLogger();

        try {

            Map sessionFactoryParameters=new HashMap<String,Object>();
            sessionFactoryParameters.put("request",request);
            sessionFactoryParameters.put("response",response);
            sessionDAOFactory = DAOFactory.getDAOFactory(Configuration.COOKIE_IMPL,sessionFactoryParameters);
            sessionDAOFactory.beginTransaction();

            UtenteDAO sessionUserDAO = sessionDAOFactory.getUtenteDAO();
            sessionUserDAO.deleteUser(null);

            sessionDAOFactory.commitTransaction();

            request.setAttribute("loggedOn",false);
            request.setAttribute("loggedUser", null);
            request.setAttribute("viewUrl", "HomeManager/HomePage");

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Controller Error", e);
            try {
                if (sessionDAOFactory != null) sessionDAOFactory.rollbackTransaction();
            } catch (Throwable t) {
            }
            throw new RuntimeException(e);

        } finally {
            try {
                if (sessionDAOFactory != null) sessionDAOFactory.closeTransaction();
            } catch (Throwable t) {
            }
        }
    }

    public static void registerView(HttpServletRequest request, HttpServletResponse response){
        DAOFactory sessionDAOFactory=null;
        Utente loggedUser;

        Logger logger = LogService.getApplicationLogger();

        try{
            Map sessionFactoryParameters=new HashMap<String,Object>();
            sessionFactoryParameters.put("request",request);
            sessionFactoryParameters.put("response",response);
            sessionDAOFactory = DAOFactory.getDAOFactory(Configuration.COOKIE_IMPL,sessionFactoryParameters);
            sessionDAOFactory.beginTransaction();

            UtenteDAO sessionUserDAO = sessionDAOFactory.getUtenteDAO();
            loggedUser = sessionUserDAO.findLoggedUser();


            request.setAttribute("loggedOn",loggedUser!=null);
            request.setAttribute("loggedUser", loggedUser);
            request.setAttribute("viewUrl", "HomeManager/registerView");
        }catch (Exception e) {
            logger.log(Level.SEVERE, "Controller Error", e);
            try {
                if (sessionDAOFactory != null) sessionDAOFactory.rollbackTransaction();
            } catch (Throwable t) {
            }
            throw new RuntimeException(e);

        } finally {
            try {
                if (sessionDAOFactory != null) sessionDAOFactory.closeTransaction();
            } catch (Throwable t) {
            }

        }
    }

    public static void register (HttpServletRequest request, HttpServletResponse response) {
        DAOFactory sessionDAOFactory= null;
        DAOFactory daoFactory = null;
        Utente loggedUser;
        String applicationMessage = null;

        Logger logger = LogService.getApplicationLogger();
        try{
            Map sessionFactoryParameters=new HashMap<String,Object>();
            sessionFactoryParameters.put("request",request);
            sessionFactoryParameters.put("response",response);
            sessionDAOFactory = DAOFactory.getDAOFactory(Configuration.COOKIE_IMPL,sessionFactoryParameters);
            sessionDAOFactory.beginTransaction();

            UtenteDAO sessionUserDAO = sessionDAOFactory.getUtenteDAO();
            loggedUser = sessionUserDAO.findLoggedUser();

            daoFactory = DAOFactory.getDAOFactory(Configuration.DAO_IMPL,null);
            daoFactory.beginTransaction();

            Long user_id;
            try {
                user_id = loggedUser.getId_utente();
            } catch (NullPointerException e) {
                user_id = null;
            }

            UtenteDAO userDAO = daoFactory.getUtenteDAO();
            Utente utente;

            try {

                utente = userDAO.create(
                        null,
                        request.getParameter("nome"),
                        request.getParameter("cognome"),
                        request.getParameter("email"),
                        null,
                        null,
                        null,
                        request.getParameter("password"),
                        false,
                        0);

                applicationMessage = "Registrazione avvenuta con successo. Clicca su Login per effettuare l'accesso";

            } catch (Exception e) {
                applicationMessage = "Errore di registrazione: " + e;
                logger.log(Level.INFO, "Errore di registrazione: " + e);
            }
            logger.log(Level.INFO, "Prima dell'inserimento nel database");

                daoFactory.commitTransaction();
                sessionDAOFactory.commitTransaction();

                request.setAttribute("loggedOn",loggedUser!=null);
                request.setAttribute("loggedUser", loggedUser);
                request.setAttribute("applicationMessage", applicationMessage);
                request.setAttribute("viewUrl", "HomeManager/HomePage");
            logger.log(Level.INFO, "Dopo l'inserimento nel database");



            } catch (Exception e) {
            logger.log(Level.SEVERE, "Controller Error", e);
            try {
                if (daoFactory != null) daoFactory.rollbackTransaction();
                if (sessionDAOFactory != null) sessionDAOFactory.rollbackTransaction();
            } catch (Throwable t) {
            }
            throw new RuntimeException(e);

        } finally {
            try {
                if (daoFactory != null) daoFactory.closeTransaction();
                if (sessionDAOFactory != null) sessionDAOFactory.closeTransaction();
            } catch (Throwable t) {
            }
        }
    }
}
