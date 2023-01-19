package hu.lechnerkozpont.bootcamp.storage.dao;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import hu.lechnerkozpont.bootcamp.storage.entity.StoreItem;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONRepository implements StoreItemRepository {
    private String JSON_FILE_PATH = ".\\test.json";
    private String PARENT_JSON_ITEM_KEY = "products";
    private String KEY_NAME = "name";
    private String KEY_QUANTITY = "quantity";

    @Override
	public StoreItem loadItem(String productName) {
        List<StoreItem> allProducts = loadAllItems();
        StoreItem result =null;
        for (StoreItem oneProduct:allProducts) {
            if(oneProduct.getName().equals(productName))
            {
                result = oneProduct;
            }
        }
        return result;
	}

    private List<StoreItem> loadAllItems()
    {
        JSONParser parser = new JSONParser();
        List<StoreItem> result = new ArrayList<>();
        String actName;
        int actQuantity;
        try {
            File jsonFile = new File(JSON_FILE_PATH);
            if(!jsonFile.exists())
            {
                jsonFile.createNewFile();
            }
            Reader reader = new FileReader(JSON_FILE_PATH);
            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            JSONArray products = (JSONArray) jsonObject.get(PARENT_JSON_ITEM_KEY);
            for(int i=0; i < products.size();i++){
                JSONObject product = (JSONObject) products.get(i);
                actName = (String) product.get(KEY_NAME);
                actQuantity = ((Long)product.get(KEY_QUANTITY)).intValue();
                result.add(new StoreItem(actName,actQuantity));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }
	@Override
	public void saveItem(StoreItem item) {
        List<StoreItem> Products = loadAllItems();
        Integer selectedItemIndex = null;
        for (int i = 0; i < Products.size(); i++) {
            if (Products.get(i).getName().equals(item.getName()))
            {
                selectedItemIndex = i;
            }
        }
        if(selectedItemIndex == null)
        {
            Products.add(item);
        }
        else
        {
            Products.set(selectedItemIndex,item);
        }

        saveAllItems(Products);
	}

    private void saveAllItems(List<StoreItem> products) {
        JSONObject fullJsonObject = new JSONObject();
        JSONArray ProductsJson = new JSONArray();
        for (StoreItem product:products) {
            JSONObject oneProductJson = new JSONObject();
            oneProductJson.put(KEY_NAME, product.getName());
            oneProductJson.put(KEY_QUANTITY, product.getQuantity());
            ProductsJson.add(oneProductJson);
        }
        fullJsonObject.put(PARENT_JSON_ITEM_KEY, ProductsJson);
        try (FileWriter file = new FileWriter(JSON_FILE_PATH)) {
            file.write(fullJsonObject.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
