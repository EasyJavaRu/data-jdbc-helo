package ru.easyjava.data.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Simple example of JDBC usage.
 */
public final class App {
    /**
     * Query that create table.
     */
    private static final String CREATE_QUERY =
            "CREATE TABLE EXAMPLE (GREETING VARCHAR(6), TARGET VARCHAR(6))";
    /**
     * Quaery that populates table with data.
     */
    private static final String DATA_QUERY =
            "INSERT INTO EXAMPLE VALUES('Hello','World')";

    /**
     * Do not construct me.
     */
    private App() {
    }

    /**
     * Entry point.
     *
     * @param args Command line args. Not used.
     */
    public static void main(final String[] args) {
        try (Connection db = DriverManager.getConnection("jdbc:h2:mem:")) {
            try (Statement dataQuery = db.createStatement()) {
                dataQuery.execute(CREATE_QUERY);
                dataQuery.execute(DATA_QUERY);
            }

            try (PreparedStatement query =
                         db.prepareStatement("SELECT * FROM EXAMPLE")) {
                ResultSet rs = query.executeQuery();
                while (rs.next()) {
                    System.out.println(String.format("%s, %s!",
                            rs.getString(1),
                            rs.getString("TARGET")));
                }
                rs.close();
            }
        } catch (SQLException ex) {
            System.out.println("Database connection failure: "
                    + ex.getMessage());
        }
    }
}
