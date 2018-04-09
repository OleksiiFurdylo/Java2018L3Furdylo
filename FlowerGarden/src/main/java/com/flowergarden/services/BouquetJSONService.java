package com.flowergarden.services;

import com.flowergarden.DAO.impl.BouquetDAOimpl;
import com.flowergarden.DAO.impl.BouquetJSONDAOimpl;
import com.flowergarden.DAO.impl.FlowerDAOimpl;
import com.flowergarden.flowers.BouquetWrapper;
import com.flowergarden.flowers.FlowerWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by OleksiiF on 27.03.2018.
 */
@Service
public class BouquetJSONService {

    @Autowired
    private FlowerDAOimpl flowerDAOimpl;
    @Autowired
    private BouquetDAOimpl bouquetDAOimpl;
    @Autowired
    private BouquetWrapper bouquetWrapper;
    @Autowired
    private BouquetJSONDAOimpl bouquetJSONDAOimpl;

    public void saveBouquetService(int bouquetId){
        bouquetWrapper.setName(bouquetDAOimpl.getBouquetName(bouquetId));
        bouquetWrapper.setAssemblyPrice(bouquetDAOimpl.getAssemblePriceForBouqet(bouquetId));
        bouquetWrapper.setFlowerWrappers(flowerDAOimpl.getFlowersInBouquet(bouquetId));
        bouquetWrapper.setId(bouquetId);
        //bouquetDAOimpl.closeConnection();

        bouquetJSONDAOimpl.saveBouquetToFile(bouquetWrapper);
    }

    public BouquetWrapper readBouquetService(int bouquetId){
        return bouquetJSONDAOimpl.readBouquetFromFile(bouquetId);
    }

}