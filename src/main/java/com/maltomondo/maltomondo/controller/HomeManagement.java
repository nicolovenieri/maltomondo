package com.maltomondo.maltomondo.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class HomeManagement {

    private HomeManagement() {
    }

    public static String view(HttpServletRequest request, HttpServletResponse response) {
        // Non è necessario utilizzare DAO o altri servizi qui
        // Puoi semplicemente restituire il nome della vista desiderata
        request.setAttribute("viewUrl", "HomeManagement/HomePage");

        return "HomePage"; // Ritorna il nome della vista "HomePage.jsp"
    }



}
