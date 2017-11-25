package io.zipcoder;

import java.util.ArrayList;
import java.util.Arrays;

public class ItemParser {

    public ArrayList<String> parseRawDataIntoStringArray(String rawData){
        String stringPattern = "##";
        ArrayList<String> response = splitStringWithRegexPattern(stringPattern , rawData);
        return response;
    }

    public Item parseStringIntoItem(String rawItem) throws ItemParseException{
        String name = null;
        Double price = null;
        String type = null;
        String expiration = null;

        rawItem = rawItem.replaceAll("#", "");
        ArrayList<String> kVPairs = findKeyValuePairsInRawItemData(rawItem);
        for (String splitItem :
                kVPairs) {
            if (splitItem.matches("(?i)(^name:.+)")) {
                name = this.parseName(splitItem);
            }
            if (splitItem.matches("(?i)(^price:.+)")) {
                price = this.parsePrice(splitItem);
            }
            if (splitItem.matches("(?i)(^type:.+)")) {
                type = this.parseType(splitItem);
            }
            if (splitItem.matches("(?i)(^expiration:.+)")) {
                expiration = this.parseExpiration(splitItem);
            }
        }

        if (name == null || price == null || type == null || expiration == null) {
            throw new ItemParseException();
        } else {
            return new Item(name, price, type, expiration);
        }
    }

    public ArrayList<String> findKeyValuePairsInRawItemData(String rawItem){
        String stringPattern = "[;^%*!@]";
        ArrayList<String> response = splitStringWithRegexPattern(stringPattern , rawItem);
        return response;
    }

    private ArrayList<String> splitStringWithRegexPattern(String stringPattern, String inputString){
        return new ArrayList<String>(Arrays.asList(inputString.split(stringPattern)));
    }

    String parseName(String input){
        String name = null;
        input = input.replaceAll("(?i)(^name:)", "");
        if(input.matches("(?i)(c..kies)")){
            name = "Cookies";
        }
        if(input.matches("(?i)(milk)")){
            name = "Milk";
        }
        if(input.matches("(?i)(bread)")){
            name = "Bread";
        }
        if(input.matches("(?i)(apples)")){
            name = "Apples";
        }
        return name;
    }

    Double parsePrice(String input){
        Double price = null;
        input = input.replaceAll("(?i)(^price:)", "");
        try{
            price = Double.parseDouble(input);
        } catch (Exception e){}
        return price;
    }

    String parseType(String input){
        String type = null;
        input = input.replaceAll("(?i)(^type:)", "");
        if(input.matches("(?i)(food)")){
            type = "Food";
        }
        return type;
    }

    String parseExpiration(String input){
        String expiration = null;
        input = input.replaceAll("(?i)(^expiration:)", "");
        if(input.matches("\\d+/\\d{2}/\\d{4}")){
            expiration = input;
        }
        return expiration;
    }

}
