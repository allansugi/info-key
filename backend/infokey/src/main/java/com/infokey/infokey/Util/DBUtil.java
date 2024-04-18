package com.infokey.infokey.Util;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;

@Component
public class DBUtil {

    /**
     * connecting to MySQL database
     */
    private static final String URL = "jdbc:mysql://localhost:3305/application";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "secret";

    private static final BasicDataSource dataSource = new BasicDataSource();

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
