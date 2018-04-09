package com.flowergarden.DAO.impl;

import com.flowergarden.DAO.FlowerDAO;
import com.flowergarden.flowers.*;
import com.flowergarden.properties.FreshnessInteger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;

/**
 * Created by OleksiiF on 13.03.2018.
 */
@Repository
public class FlowerDAOimpl implements FlowerDAO {

    private Connection conn;
    private ArrayList<Float> resultArrayListWithPricesForBouqet = new ArrayList<>();

    public FlowerDAOimpl(Connection conn) {
        this.conn = conn;
    }


    @Override
    public ArrayList<FlowerWrapper> getAllFlowers() {
        ArrayList<FlowerWrapper> allFlowers = new ArrayList<>();

        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM flower");

            fillFlowersFromResultSet(rs, allFlowers);

            st.close();
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allFlowers;
    }

    public ArrayList<FlowerWrapper> getFlowersInBouquet(int bouquetId) {
        ArrayList<FlowerWrapper> allFlowers = new ArrayList<>();

        try {
            PreparedStatement st = conn.prepareStatement("SELECT * FROM flower WHERE bouquet_id=?");
            st.setInt(1, bouquetId);
            ResultSet rs = st.executeQuery();

            fillFlowersFromResultSet(rs, allFlowers);

            st.close();
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        }
        return allFlowers;
    }

    private void fillFlowersFromResultSet(ResultSet rs,  ArrayList<FlowerWrapper> allFlowers) {

        try {
            while (rs.next()) {
                FlowerWrapper flower = new FlowerWrapper();

                flower.setId(rs.getInt("id"));
                flower.setName(rs.getString("name"));
                flower.setLenght(rs.getInt("lenght"));//as named in db
                flower.setFreshness(new FreshnessInteger(rs.getInt("freshness")));
                flower.setPrice(rs.getFloat("price"));
                flower.setPetals(rs.getInt("petals"));
                flower.setSpike(rs.getInt("spike"));
                flower.setBouquetId(rs.getInt("bouquet_id"));

                allFlowers.add(flower);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public FlowerWrapper getFlower(int id) {
        FlowerWrapper flowerWrapper = new FlowerWrapper();


        try {
            PreparedStatement st = conn.prepareStatement("SELECT * FROM flower WHERE id="+id);
            ResultSet rs = st.executeQuery();
            flowerWrapper.setId(rs.getInt("id"));
            flowerWrapper.setName(rs.getString("name"));
            flowerWrapper.setLenght(rs.getInt("lenght"));//as named in db
            flowerWrapper.setFreshness(new FreshnessInteger(rs.getInt("freshness")));
            flowerWrapper.setPrice(rs.getFloat("price"));
            flowerWrapper.setPetals(rs.getInt("petals"));
            flowerWrapper.setSpike(rs.getInt("spike"));
            flowerWrapper.setBouquetId(rs.getInt("bouquet_id"));

            st.close();
            rs.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return flowerWrapper;
    }


    @Override
    public void updateFlower(FlowerWrapper flower) {
        try {
            PreparedStatement st = conn.prepareStatement("UPDATE flower SET lenght = ?, " +
                    "freshness = ?, price = ?, petals = ?, spike = ?, bouquet_id = ?  WHERE id =?");
            st.setFloat(1, flower.getLenght());
            st.setInt(2, flower.getFreshness().getFreshness());
            st.setFloat(3, flower.getPrice());
            st.setInt(6, flower.getBouquetId());
            st.setInt(7, flower.getId());
            if(flower.getName().equals("rose")) {st.setInt(5, flower.getSpike());}
            if(flower.getName().equals("chamomile")) {st.setInt(4, flower.getPetals());}

            st.executeUpdate();
            st.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteFlower(int flowerID) {
        try {
            Statement st = conn.createStatement();
            st.execute("DELETE FROM flowers WHERE id = "+ flowerID);

            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void addFlower(Flower flower) {
        try {
            PreparedStatement st = conn.prepareStatement(
                    "INSERT INTO flower (name, lenght, price, freshness, petals, spike, bouquet_id) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?)");

            st.setFloat(2, flower.getLenght());
            st.setFloat(3, flower.getPrice());
            st.setFloat(4, (Integer)flower.getFreshness().getFreshness());
            st.setInt(7, ((GeneralFlower)flower).getBouquetId());
            if (flower instanceof Rose){
                st.setString(1, "rose");
                st.setBoolean(6, ((Rose) flower).getSpike());
                //st.setNull(5, Integer.parseInt(null));
            } else if (flower instanceof Chamomile) {
                st.setString(1, "chamomile");
                st.setInt(5, ((Chamomile) flower).getPetals());
                //st.setNull(6, Integer.parseInt(null));
            } else if (flower instanceof Tulip) {
                st.setString(1, "tulip");
            }

            st.executeUpdate();
            st.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Float> getFlowerPricesForBouqet(int bouqetId) {

        try {
            PreparedStatement st = conn.prepareStatement("SELECT * FROM flower WHERE flower.bouquet_id = ?");
            st.setInt(1, bouqetId);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                resultArrayListWithPricesForBouqet.add(rs.getFloat("price"));
            }

            st.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultArrayListWithPricesForBouqet;
    }

    @Override
    public void closeConnection(){
        try {
            this.conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}