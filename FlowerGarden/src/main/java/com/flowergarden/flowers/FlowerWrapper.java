package com.flowergarden.flowers;

import com.flowergarden.properties.FreshnessInteger;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;

/**
 * Created by OleksiiF on 16.03.2018.
 */
@XmlRootElement(name = "flower")
@XmlAccessorType(XmlAccessType.FIELD)
public class FlowerWrapper extends GeneralFlower {

    @XmlElement(name = "flowerId")
    private int id;
    private String name;
    private int lenght;
    private FreshnessInteger freshness;
    private float price;
    private int petals;
    private int spike;
    private int bouquetId;


    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLenght(int lenght) {
        this.lenght = lenght;
    }

    public void setFreshness(FreshnessInteger freshness) {
        this.freshness = freshness;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setPetals(int petals) {
        this.petals = petals;
    }

    public void setSpike(int spike) {
        this.spike = spike;
    }

    public void setBouquetId(int bouquetId) {
        this.bouquetId = bouquetId;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getLenght() {
        return lenght;
    }

    @Override
    public FreshnessInteger getFreshness() {
        return freshness;
    }

    @Override
    public float getPrice() {
        return price;
    }

    public int getPetals() {
        return petals;
    }

    public int getSpike() {
        return spike;
    }

    @Override
    public int getBouquetId() {
        return bouquetId;
    }
}
