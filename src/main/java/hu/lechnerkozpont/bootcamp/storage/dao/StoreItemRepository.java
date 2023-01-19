package hu.lechnerkozpont.bootcamp.storage.dao;

import hu.lechnerkozpont.bootcamp.storage.entity.StoreItem;

public interface StoreItemRepository {
	StoreItem loadItem(String productName);
	void saveItem(StoreItem item);
}
