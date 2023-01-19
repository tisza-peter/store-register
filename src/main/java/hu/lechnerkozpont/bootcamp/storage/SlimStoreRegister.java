package hu.lechnerkozpont.bootcamp.storage;


import hu.lechnerkozpont.bootcamp.storage.dao.SQLiteRepository;
import hu.lechnerkozpont.bootcamp.storage.dao.JSONRepository;
import hu.lechnerkozpont.bootcamp.storage.dao.StoreItemRepository;
import hu.lechnerkozpont.bootcamp.storage.entity.StoreItem;
import hu.lechnerkozpont.bootcamp.storage.enumeration.StorePesristenceType;
import hu.lechnerkozpont.bootcamp.storage.exceptions.ItemNotAvailableException;
import lombok.SneakyThrows;

public class SlimStoreRegister implements StoreRegister {
    StoreItemRepository storeItemRepository;

    @Override
    public void setPersistanceType(StorePesristenceType mode)
    {
        if(mode.equals(StorePesristenceType.InMemory)) {
            storeItemRepository = new SQLiteRepository();
        }

        if(mode.equals(StorePesristenceType.File)) {
            storeItemRepository = new JSONRepository();
        }
    }

    @Override
    public int sellProductItem(String productName, int quantity){
        StoreItem storeItem = storeItemRepository.loadItem(productName);
        if (storeItem == null)
        {
            throw new ItemNotAvailableException("Non-existent product name : " + productName );
        }
        int salableQuantity = Math.min(storeItem.getQuantity(),quantity);
        storeItem.setQuantity(storeItem.getQuantity()-salableQuantity);
        storeItemRepository.saveItem(storeItem);
        return salableQuantity;
    }

    @Override
    public void buyProductItem(String productName, int quantity) {
        StoreItem storeItem = storeItemRepository.loadItem(productName);
        if (storeItem == null)
        {
            throw new ItemNotAvailableException("Non-existent product name : " + productName );
        }
        storeItem.setQuantity(storeItem.getQuantity()+quantity);
        storeItemRepository.saveItem(storeItem);
    }

    @Override
    public void createProduct(String productName) {
        StoreItem storeItem = new StoreItem(productName, 0);
        storeItemRepository.saveItem(storeItem);
    }


}
