package com.flowergarden.DAO.impl;

import com.flowergarden.DAO.BouqetDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;

/**
 * Created by OleksiiF on 13.03.2018.
 */
@Repository
public class BouquetDAOimpl implements BouqetDAO {

    private Connection conn;

    /*@Autowired
    public BouquetDAOimpl(DataSource dataSource) {
        try {
            this.conn = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/

    public BouquetDAOimpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public float getAssemblePriceForBouqet(int bouqetId) {
        float assemblePrice = 0.0f;

        try {
            PreparedStatement st = conn.prepareStatement("SELECT assemble_price FROM bouquet WHERE bouquet.id = ?");
            st.setInt(1, bouqetId);
            ResultSet rs = st.executeQuery();
            assemblePrice = rs.getFloat("assemble_price");


            st.close();
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return assemblePrice;
    }

    public String getBouquetName(int bouquetId) {
        String name = "";
        try {
            PreparedStatement st = conn.prepareStatement("SELECT name FROM bouquet WHERE bouquet.id = ?");
            st.setInt(1, bouquetId);
            ResultSet rs = st.executeQuery();
            name = rs.getString("name");


            st.close();
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return name;
    }


    @Override
    public void closeConnection() {
        try {
            this.conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}