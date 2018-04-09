package com.flowergarden.DAO.impl;

import com.flowergarden.flowers.*;
import com.flowergarden.properties.FreshnessInteger;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * Created by OleksiiF on 16.03.2018.
 */
@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration("classpath:application-context.xml")
public class FlowerDAOimplTest {

    @Mock
    private DataSource dataSource;

    /*@InjectMocks
    private FlowerDAOimpl flowerDAOimpl = new FlowerDAOimpl(dataSource);*/

    private FlowerDAOimpl flowerDAOimpl;

    @Mock
    private Connection conn;

    @Mock
    private PreparedStatement prst;

    @Mock
    private Statement st;

    @Mock
    private ResultSet rs;

    @Mock
    private FlowerWrapper flowerWrapper;

    private FlowerWrapper flowerRoseWrapped;
    private Chamomile flowerChamomile;
    private int testID = 7;


    @Before

    public void initFlowerDao() throws SQLException {
        when(dataSource.getConnection()).thenReturn(conn);

        when(conn.prepareStatement(any(String.class))).thenReturn(prst);
        when(conn.createStatement()).thenReturn(prst);
        when(st.executeQuery(any(String.class))).thenReturn(rs);

        //flowerDAOimpl = new FlowerDAOimpl(dataSource);
        flowerDAOimpl = new FlowerDAOimpl(conn);

        flowerRoseWrapped = new FlowerWrapper();
        flowerRoseWrapped.setBouquetId(1);
        flowerRoseWrapped.setId(testID);
        flowerRoseWrapped.setName("rose");
        flowerRoseWrapped.setLenght(123);
        flowerRoseWrapped.setFreshness(new FreshnessInteger(1));
        flowerRoseWrapped.setPrice(15.6f);
        flowerRoseWrapped.setSpike(1);

        flowerChamomile = new Chamomile(8, 22, 15f, new FreshnessInteger(9));
        flowerChamomile.setBouquetId(2);

        when(rs.getInt("bouquet_id")).thenReturn(flowerRoseWrapped.getBouquetId());
        when(rs.getInt("id")).thenReturn(flowerRoseWrapped.getId());
        when(rs.getString("name")).thenReturn(flowerRoseWrapped.getName());
        when(rs.getInt("lenght")).thenReturn(flowerRoseWrapped.getLenght());
        when(rs.getInt("freshness")).thenReturn(flowerRoseWrapped.getFreshness().getFreshness());
        when(rs.getFloat("price")).thenReturn(flowerRoseWrapped.getPrice());
        when(rs.getInt("petals")).thenReturn(flowerRoseWrapped.getPetals());
        when(rs.getInt("spike")).thenReturn(flowerRoseWrapped.getSpike());

        when(rs.next()).thenReturn(true).thenReturn(false);

        when(prst.executeQuery()).thenReturn(rs);
        when(prst.executeUpdate()).thenReturn(1);
    }

    @Test
    public void addFlower() throws Exception {
        flowerDAOimpl.addFlower(flowerChamomile);

    }

    @Test
    public void updateFlower() throws Exception {
        flowerDAOimpl.addFlower(flowerRoseWrapped);
        flowerRoseWrapped.setPrice(23);
        flowerDAOimpl.updateFlower(flowerRoseWrapped);
    }

    @Test
    public void getFlowerPricesForBouqet() throws Exception {

        ArrayList<Float> flowerPrices = flowerDAOimpl.getFlowerPricesForBouqet(1);
        float price = 0;

        for (Float f: flowerPrices) {price += f;}
        Assert.assertEquals(15.6f, price, 0.1); // one flower in bouquet and assemble price added in service

    }

}