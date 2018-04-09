package com.flowergarden.rest.services;

import com.flowergarden.DAO.impl.BouquetDAOimpl;
import com.flowergarden.DAO.impl.FlowerDAOimpl;
import com.flowergarden.flowers.FlowerWrapper;
import com.flowergarden.properties.FreshnessInteger;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by OleksiiF on 08.04.2018.
 */
@Path("/bouquet")
public class BouquetServiceRest {

    Connection conn;
    BouquetDAOimpl bouquetDAOimpl;
    FlowerDAOimpl flowerDAOimpl;

    public BouquetServiceRest() throws IOException, SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");

        File file = new File("flowergarden.db");
        String url = "jdbc:sqlite:"+file.getCanonicalFile().toURI();
        conn = DriverManager.getConnection(url);

        bouquetDAOimpl = new BouquetDAOimpl(conn);
        flowerDAOimpl = new FlowerDAOimpl(conn);
}


    @GET
    @Path("/{id}/price")
    @Produces(MediaType.TEXT_HTML)
    public Response getPrice(@PathParam("id") int bouquetId){


        float flowerPrices = 0.0f;

        Float assemblePrice = bouquetDAOimpl.getAssemblePriceForBouqet(bouquetId);
        for(Float f: flowerDAOimpl.getFlowerPricesForBouqet(bouquetId)) {flowerPrices += f;}


        return Response.status(200).entity("Total price of bouquet with id: "
                + bouquetId+ " is " + assemblePrice+flowerPrices).build();
    }


    @GET
    @Path("/{id}/flowers")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<FlowerWrapper> getBouquetFlowers(@PathParam("id") int bouquetId){
        return flowerDAOimpl.getFlowersInBouquet(bouquetId);
    }

    @GET
    @Path("/{id}/lower_freshness")
    public Response lowerBouquetFreshness(@PathParam("id") int bouquetId){

        boolean isFreshnessUnderZero = false;

        ArrayList<FlowerWrapper> flowers = flowerDAOimpl.getFlowersInBouquet(bouquetId);
        for (FlowerWrapper f: flowers) {
            if (f.getFreshness().getFreshness() == 0) {
                isFreshnessUnderZero = true;
            } else {
                f.setFreshness(new FreshnessInteger(f.getFreshness().getFreshness()-1));
                flowerDAOimpl.updateFlower(f);
            }
        }

        if (isFreshnessUnderZero == false) {
            return Response.status(200).entity("freshness lowered successfully").build();
        } else {
            return Response.status(500).entity("freshness could not be lower than 0").build();
        }
    }


}
