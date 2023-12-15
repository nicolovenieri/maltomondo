package com.maltomondo.maltomondo.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class UserManagment {
    private UserManagment() {

    }
    public static String Loginview(HttpServletRequest request, HttpServletResponse response) {
        // Non è necessario utilizzare DAO o altri servizi qui
        // Puoi semplicemente restituire il nome della vista desiderata
        request.setAttribute("viewUrl", "UserManagment/LoginPage");

        return "LoginPage"; // Ritorna il nome della vista "HomePage.jsp"
    }
}
