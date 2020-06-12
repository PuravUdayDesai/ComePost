package com.hack.comp.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connections
{

    public static Connection c;

    public static Connection setConnection()
    {

        try
        {
            Class.forName( "org.postgresql.Driver" );
            c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/compost", "postgres", "purav" );
            c.setAutoCommit( false );
        }
        catch (ClassNotFoundException | SQLException e)
        {
            e.printStackTrace();
        }
        return c;
    }

}
