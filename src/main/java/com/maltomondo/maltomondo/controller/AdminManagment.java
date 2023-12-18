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

public class AdminManagment {
    public static void adminView(HttpServletRequest request, HttpServletResponse response) {
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
            request.setAttribute("viewUrl", "AdminManagment/AdminPage");
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

    public static void blockusertmpold(HttpServletRequest request, HttpServletResponse response){
        DAOFactory sessionDAOFactory = null;
        DAOFactory daoFactory = null;
        Utente loggedUser;
        String applicationMessage = null;
        Logger logger = LogService.getApplicationLogger();

        try{
            Map sessionFactoryParameters = new HashMap<String, Object>();
            sessionFactoryParameters.put("request",request);
            sessionFactoryParameters.put("response",response);
            sessionDAOFactory = DAOFactory.getDAOFactory(Configuration.COOKIE_IMPL, sessionFactoryParameters);
            sessionDAOFactory.beginTransaction();

            UtenteDAO sessionUserDAO = sessionDAOFactory.getUtenteDAO();
            loggedUser = sessionUserDAO.findLoggedUser();

            daoFactory = DAOFactory.getDAOFactory(Configuration.DAO_IMPL, null);
            daoFactory.beginTransaction();

            UtenteDAO userDAO = daoFactory.getUtenteDAO();
            loggedUser = userDAO.findByEmail(loggedUser.getEmail());

            Utente toBan = userDAO.findByEmail(request.getParameter("email"));

            if (toBan != null && toBan.getId_utente() != null){
                try{
                    userDAO.block(toBan);
                }catch (Exception e){
                    logger.log(Level.SEVERE, "Errore cancellazione utente e relativi dati collegati. " + e);
                    throw new RuntimeException(e);
                }

                applicationMessage = "Utente bloccato correttamente";
            }else {
                applicationMessage = "email inserita non trovata!";
            }

            daoFactory.commitTransaction();
            sessionDAOFactory.commitTransaction();

            request.setAttribute("loggedOn",loggedUser!=null);
            request.setAttribute("loggedUser",loggedUser);
            request.setAttribute("applicationMessage",applicationMessage);
            request.setAttribute("viewUrl","HomeManager/HomePage");


        }catch (Exception e){
            logger.log(Level.SEVERE, "User Controller Error / ban", e);
            try {
                if(daoFactory != null) daoFactory.rollbackTransaction();
                if(sessionDAOFactory != null) sessionDAOFactory.rollbackTransaction();
            }
            catch (Throwable t){}
            throw new RuntimeException(e);
        }
        finally {
            try {
                if(daoFactory != null) daoFactory.closeTransaction();
                if(sessionDAOFactory != null) sessionDAOFactory.closeTransaction();
            }
            catch (Throwable t){}
        }
    }

    public static void deleteuser(HttpServletRequest request, HttpServletResponse response){
        DAOFactory daoFactory = null;
        String applicationMessage = null;
        Logger logger = LogService.getApplicationLogger();

        try {
            daoFactory = DAOFactory.getDAOFactory(Configuration.DAO_IMPL, null);
            daoFactory.beginTransaction();

            UtenteDAO userDAO = daoFactory.getUtenteDAO();
            Utente toDelete = userDAO.findByEmail(request.getParameter("emaildelete"));

            if (toDelete != null && toDelete.getId_utente() != null) {
                try {
                    userDAO.deleteUser(toDelete);
                    applicationMessage = "Utente bloccato correttamente";
                } catch (Exception e) {
                    logger.log(Level.SEVERE, "Errore durante la cancellazione dell'utente: " + e.getMessage());
                    applicationMessage = "Errore durante la cancellazione dell'utente";
                }
            } else {
                applicationMessage = "Utente non trovato";
            }

            daoFactory.commitTransaction();

            request.setAttribute("applicationMessage", applicationMessage);
            request.setAttribute("viewUrl", "AdminManagment/AdminPage");

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Errore durante la cancellazione dell'utente", e);
            try {
                if (daoFactory != null) daoFactory.rollbackTransaction();
            } catch (Throwable t) {
            }
            throw new RuntimeException(e);
        } finally {
            try {
                if (daoFactory != null) daoFactory.closeTransaction();
            } catch (Throwable t) {
            }
        }
    }


    public static void blockuser(HttpServletRequest request, HttpServletResponse response){

            DAOFactory daoFactory = null;
            String applicationMessage = null;
            Logger logger = LogService.getApplicationLogger();

            try {
                daoFactory = DAOFactory.getDAOFactory(Configuration.DAO_IMPL, null);
                daoFactory.beginTransaction();

                UtenteDAO userDAO = daoFactory.getUtenteDAO();
                Utente toBan = userDAO.findByEmail(request.getParameter("emailblock"));

                if (toBan != null && toBan.getId_utente() != null) {
                    try {
                        userDAO.block(toBan);
                        applicationMessage = "Utente bloccato correttamente";
                    } catch (Exception e) {
                        logger.log(Level.SEVERE, "Errore durante il blocco dell'utente: " + e.getMessage());
                        applicationMessage = "Errore durante il blocco dell'utente";
                    }
                } else {
                    applicationMessage = "Utente non trovato";
                }

                daoFactory.commitTransaction();

                request.setAttribute("applicationMessage", applicationMessage);
                request.setAttribute("viewUrl", "AdminManagment/AdminPage");

            } catch (Exception e) {
                logger.log(Level.SEVERE, "Errore durante il blocco dell'utente", e);
                try {
                    if (daoFactory != null) daoFactory.rollbackTransaction();
                } catch (Throwable t) {
                }
                throw new RuntimeException(e);
            } finally {
                try {
                    if (daoFactory != null) daoFactory.closeTransaction();
                } catch (Throwable t) {
                }
            }
        }

    public static void setadmin(HttpServletRequest request, HttpServletResponse response){
        DAOFactory daoFactory = null;
        String applicationMessage = null;
        Logger logger = LogService.getApplicationLogger();

        try {
            daoFactory = DAOFactory.getDAOFactory(Configuration.DAO_IMPL, null);
            daoFactory.beginTransaction();

            UtenteDAO userDAO = daoFactory.getUtenteDAO();
            Utente toBan = userDAO.findByEmail(request.getParameter("emailadmin"));

            if (toBan != null && toBan.getId_utente() != null) {
                try {
                    userDAO.setAdmin(toBan);
                    applicationMessage = "Utente bloccato correttamente";
                } catch (Exception e) {
                    logger.log(Level.SEVERE, "Errore durante il blocco dell'utente: " + e.getMessage());
                    applicationMessage = "Errore durante il blocco dell'utente";
                }
            } else {
                applicationMessage = "Utente non trovato";
            }

            daoFactory.commitTransaction();

            request.setAttribute("applicationMessage", applicationMessage);
            request.setAttribute("viewUrl", "AdminManagment/AdminPage");

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Errore durante il blocco dell'utente", e);
            try {
                if (daoFactory != null) daoFactory.rollbackTransaction();
            } catch (Throwable t) {
            }
            throw new RuntimeException(e);
        } finally {
            try {
                if (daoFactory != null) daoFactory.closeTransaction();
            } catch (Throwable t) {
            }
        }
    }

    public static void unsetadmin(HttpServletRequest request, HttpServletResponse response){
        DAOFactory daoFactory = null;
        String applicationMessage = null;
        Logger logger = LogService.getApplicationLogger();

        try {
            daoFactory = DAOFactory.getDAOFactory(Configuration.DAO_IMPL, null);
            daoFactory.beginTransaction();

            UtenteDAO userDAO = daoFactory.getUtenteDAO();
            Utente tonotAdmin = userDAO.findByEmail(request.getParameter("emaildowngrade"));

            if (tonotAdmin != null && tonotAdmin.getId_utente() != null) {
                try {
                    userDAO.unsetAdmin(tonotAdmin);
                    applicationMessage = "Utente tolto da admin correttamente";
                } catch (Exception e) {
                    logger.log(Level.SEVERE, "Errore durante il settaggio a non admin dell'utente: " + e.getMessage());
                    applicationMessage = "Errore durante il settaggio a non admin dell'utente";
                }
            } else {
                applicationMessage = "Utente non trovato";
            }

            daoFactory.commitTransaction();

            request.setAttribute("applicationMessage", applicationMessage);
            request.setAttribute("viewUrl", "AdminManagment/AdminPage");

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Errore durante il settaggio a non admin dell'utente", e);
            try {
                if (daoFactory != null) daoFactory.rollbackTransaction();
            } catch (Throwable t) {
            }
            throw new RuntimeException(e);
        } finally {
            try {
                if (daoFactory != null) daoFactory.closeTransaction();
            } catch (Throwable t) {
            }
        }
    }

    public static void unblock (HttpServletRequest request, HttpServletResponse response){
        DAOFactory daoFactory = null;
        String applicationMessage = null;
        Logger logger = LogService.getApplicationLogger();

        try {
            daoFactory = DAOFactory.getDAOFactory(Configuration.DAO_IMPL, null);
            daoFactory.beginTransaction();

            UtenteDAO userDAO = daoFactory.getUtenteDAO();
            Utente toUnblock = userDAO.findByEmail(request.getParameter("emailunblock"));

            if (toUnblock != null && toUnblock.getId_utente() != null) {
                try {
                    userDAO.unblock(toUnblock);
                    applicationMessage = "Utente sbloccato correttamente";
                } catch (Exception e) {
                    logger.log(Level.SEVERE, "Errore durante lo sbloccaggio dell'utente: " + e.getMessage());
                    applicationMessage = "Errore durante lo sbloccaggio dell'utente";
                }
            } else {
                applicationMessage = "Utente non trovato";
            }

            daoFactory.commitTransaction();

            request.setAttribute("applicationMessage", applicationMessage);
            request.setAttribute("viewUrl", "AdminManagment/AdminPage");

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Errore durante lo sbloccaggio dell'utente", e);
            try {
                if (daoFactory != null) daoFactory.rollbackTransaction();
            } catch (Throwable t) {
            }
            throw new RuntimeException(e);
        } finally {
            try {
                if (daoFactory != null) daoFactory.closeTransaction();
            } catch (Throwable t) {
            }
        }
    }

    }



