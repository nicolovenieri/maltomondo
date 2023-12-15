package com.maltomondo.maltomondo.services.config;

import com.maltomondo.maltomondo.model.dao.DAOFactory;

import java.util.Calendar;
import java.util.logging.Level;



public class Configuration {

    /* Database Configruation */
    public static final String DAO_IMPL= DAOFactory.MYSQLJDBCIMPL;
    public static final String DATABASE_DRIVER="com.mysql.cj.jdbc.Driver";
    public static final String SERVER_TIMEZONE=Calendar.getInstance().getTimeZone().getID();
    public static final String
            DATABASE_URL="jdbc:mysql://localhost/maltomondo?user=root&password=4994&allowPublicKeyRetrieval=true&useSSL=false&serverTimezone="+SERVER_TIMEZONE;

    /* Session Configuration */
    public static final String COOKIE_IMPL=DAOFactory.COOKIEIMPL;
    public static final String GLOBAL_LOGGER_NAME="maltomondo";
    public static final String GLOBAL_LOGGER_FILE= "C:\\Users\\vniko\\Documents\\MaltoMondo\\MaltoMondoLog.txt";
    public static final Level GLOBAL_LOGGER_LEVEL=Level.ALL;



}


