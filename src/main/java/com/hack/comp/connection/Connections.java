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

            c = DriverManager.getConnection("jdbc:postgresql://comepost-org.postgres.database.azure.com:5432/comepost?user=anonymous@comepost-org&password=AllAreOneIfWeSay1");
            c.setAutoCommit( false );
        }
        catch (ClassNotFoundException | SQLException e)
        {
            e.printStackTrace();
        }
        return c;
    }

}
