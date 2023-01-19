package hu.lechnerkozpont.bootcamp.storage;

import hu.lechnerkozpont.bootcamp.storage.enumeration.StorePesristenceType;
import hu.lechnerkozpont.bootcamp.storage.exceptions.ItemNotAvailableException;

public interface StoreRegister {

    void setPersistanceType(StorePesristenceType Mode);

    int sellProductItem(String productName, int quantity) throws ItemNotAvailableException;

    void buyProductItem(String productName, int quantity) throws ItemNotAvailableException;

    void createProduct(String productName);
}
