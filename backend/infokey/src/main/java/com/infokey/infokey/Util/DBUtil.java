package com.infokey.infokey.Util;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp2.BasicDataSource;

public class DBUtil {

    /**
     * For storing user account and password account
     * config according to docker compose file at the parent folder
     */
    private static final String URL = "jdbc:mysql://db:3306/app";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "password";

    private static BasicDataSource dataSource = new BasicDataSource();

    static {
        dataSource.setUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);
        dataSource.setMaxOpenPreparedStatements(100);
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    private DBUtil() {}
}
