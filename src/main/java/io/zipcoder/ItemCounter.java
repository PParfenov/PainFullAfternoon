package io.zipcoder;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;

public class ItemCounter {
    
    ItemParser itemParser = new ItemParser();

    public void parseCountAndPrintAllDataIntoItems(String rawData){
        ArrayList<Item> allItems = new ArrayList<>();
        int errorCount = 0;
        String doubleSpacer = "=============\t\t =============";
        String singleSpacer = "-------------\t\t -------------";
        ArrayList<String> splitRawData = itemParser.parseRawDataIntoStringArray(rawData);
        for (String splitString :
                splitRawData) {
            try {
                allItems.add(itemParser.parseStringIntoItem(splitString));
            } catch (ItemParseException ipe){
                errorCount++;
            }
        }
        
        allItems.sort(Comparator.comparing(Item::getName));

        String activeName = "";
        int nameCount = 0;
        HashMap<Double, Integer> priceCount = new HashMap<>();
        
        for (int i = 0; i < allItems.size(); i++) {
            if(activeName.equals(allItems.get(i).getName())){
                nameCount++;
                Double activePrice = allItems.get(i).getPrice();
                if(!priceCount.containsKey(activePrice)){
                    priceCount.put(activePrice, 1);
                } else {
                    priceCount.put(activePrice, (priceCount.get(activePrice)+1));
                }
            } else {
                activeName = allItems.get(i).getName();
                nameCount = 1;
                priceCount = new HashMap<>();
                priceCount.put(allItems.get(i).getPrice(), 1);
            }

            if(i == allItems.size()-1 || !allItems.get(i+1).getName().equals(activeName)){
                System.out.printf("\n\nName:%8s\t\t seen: %d times\n%s", activeName, nameCount, doubleSpacer);
                for (Double priceOccurence :
                        priceCount.keySet()) {
                    System.out.printf("\nPrice:%7s\t\t seen: %d times\n%s", priceOccurence, priceCount.get(priceOccurence), singleSpacer);
                }
            }
        }
        System.out.printf("\n\nErrors         \t \t seen: %d times", errorCount);
    }
}
