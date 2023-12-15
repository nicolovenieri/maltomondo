package com.maltomondo.maltomondo.model.dao.CookieImpl;

import com.maltomondo.maltomondo.model.dao.UtenteDAO;
import com.maltomondo.maltomondo.model.mo.Utente;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

public class UtenteDAOCookieImpl implements UtenteDAO {
    HttpServletRequest request;
    HttpServletResponse response;

    public UtenteDAOCookieImpl(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    public Utente create(Long Id_utente, String Nome, String Cognome, String Email, String Indirizzo, String Città, String Stato, String Password, Boolean Admin, int CAP) {
        Utente loggedUtente = new Utente();
        loggedUtente.setId_utente(Id_utente);
        loggedUtente.setNome(Nome);
        loggedUtente.setCognome(Cognome);
        loggedUtente.setAdmin(Admin);
        Cookie cookie = new Cookie("loggedUtente", this.encode(loggedUtente));
        cookie.setPath("/");
        this.response.addCookie(cookie);
        return loggedUtente;
    }

    public void update(Utente loggedUtente) {
        Cookie cookie = new Cookie("loggedUtente", this.encode(loggedUtente));
        cookie.setPath("/");
        this.response.addCookie(cookie);
    }

    public void deleteUser(Utente loggedUtente) {
        Cookie cookie = new Cookie("loggedUtente", "");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        this.response.addCookie(cookie);
    }

    public Utente findLoggedUser() {
        Cookie[] cookies = this.request.getCookies();
        Utente loggedUtente = null;
        if (cookies != null) {
            for(int i = 0; i < cookies.length && loggedUtente == null; ++i) {
                if (cookies[i].getName().equals("loggedUtente")) {
                    loggedUtente = this.decode(cookies[i].getValue());
                }
            }
        }

        return loggedUtente;
    }

    public Utente findByUserId(Long id_utente) {
        return null;
    }

    public void block(Utente utente) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void unblock(Utente utente) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setAdmin(Utente utente) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void unsetAdmin(Utente utente) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Utente findByEmail(String email) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private String encode(Utente loggedUtente) {
        String encodedLoggedUtente = loggedUtente.getId_utente() + "#" + loggedUtente.getNome() + "#" + loggedUtente.getCognome() + "#" + loggedUtente.isAdmin();
        return encodedLoggedUtente;
    }

    private Utente decode(String encodedLoggedUtente) {
        Utente loggedUtente = new Utente();
        String[] values = encodedLoggedUtente.split("#");
        loggedUtente.setId_utente(Long.parseLong(values[0]));
        loggedUtente.setNome(values[1]);
        loggedUtente.setCognome(values[2]);
        loggedUtente.setAdmin(Boolean.parseBoolean(values[3]));
        return loggedUtente;
    }

    public List<Utente> findAllAndSearchString(String searchString) {
        throw new UnsupportedOperationException("Not supported.");
    }
}


