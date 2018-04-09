package com.flowergarden.rest.services;

import com.flowergarden.DAO.impl.FlowerDAOimpl;
import com.flowergarden.flowers.FlowerWrapper;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by OleksiiF on 09.04.2018.
 */
@Path("/flower")
public class FlowerServiceRest {

    Connection conn;
    FlowerDAOimpl flowerDAOimpl;

    public FlowerServiceRest() throws IOException, SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");

        File file = new File("flowergarden.db");
        String url = "jdbc:sqlite:"+file.getCanonicalFile().toURI();
        conn = DriverManager.getConnection(url);

        flowerDAOimpl = new FlowerDAOimpl(conn);
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public FlowerWrapper getFlower(@PathParam("id") int flowerId){
        return flowerDAOimpl.getFlower(flowerId);
    }
}
