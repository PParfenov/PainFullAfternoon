package io.zipcoder;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ItemParserTest {

    private String rawSingleItem =    "naMe:Milk;price:3.23;type:Food;expiration:1/25/2016##";

    private String rawSingleItemIrregularSeperatorSample = "naMeMiLK;price:3.23;type:Food^expiration:1/11/2016##";

    private String rawBrokenSingleItem =    "naMe:Milk;price:3.23;type:Food;expiration:1/25/2016##";

    private String rawMultipleItems = "naMe:Milk;price:3.23;type:Food;expiration:1/25/2016##"
                                      +"naME:BreaD;price:1.23;type:Food;expiration:1/02/2016##"
                                      +"NAMe:BrEAD;price:1.23;type:Food;expiration:2/25/2016##";
    private ItemParser itemParser;

    @Before
    public void setUp(){
        itemParser = new ItemParser();
    }

    @Test
    public void parseRawDataIntoStringArrayTest(){
        Integer expectedArraySize = 3;
        ArrayList<String> items = itemParser.parseRawDataIntoStringArray(rawMultipleItems);
        Integer actualArraySize = items.size();
        assertEquals(expectedArraySize, actualArraySize);
    }

    @Test
    public void parseStringIntoItemTest() throws ItemParseException{
        Item expected = new Item("Milk", 3.23, "Food","1/25/2016");
        Item actual = itemParser.parseStringIntoItem(rawSingleItem);
        assertEquals(expected.toString(), actual.toString());
    }

    @Test(expected = ItemParseException.class)
    public void parseBrokenStringIntoItemTest() throws ItemParseException{
        itemParser.parseStringIntoItem(rawBrokenSingleItem);
    }

    @Test
    public void findKeyValuePairsInRawItemDataTest(){
        Integer expected = 4;
        Integer actual = itemParser.findKeyValuePairsInRawItemData(rawSingleItem).size();
        assertEquals(expected, actual);
    }

    @Test
    public void findKeyValuePairsInRawItemDataTestIrregular(){
        Integer expected = 4;
        Integer actual = itemParser.findKeyValuePairsInRawItemData(rawSingleItemIrregularSeperatorSample).size();
        assertEquals(expected, actual);
    }

    @Test
    public void nameParseTest(){
        ItemParser itemParser = new ItemParser();
        String goodItem = "nAmE:C00kies";
        String badItem1 = "notName:C00kies";
        String badItem2 = "nAmE:Cake";

        Assert.assertEquals("Cookies", itemParser.parseName(goodItem));
        Assert.assertNull(itemParser.parseName(badItem1));
        Assert.assertNull(itemParser.parseName(badItem2));
    }

    @Test
    public void priceParseTest(){
        ItemParser itemParser = new ItemParser();
        String goodItem = "pRiCe:2.50";
        String badItem1 = "notPrice:2.50";
        String badItem2 = "pRiCE:Cake";

        Assert.assertEquals(2.50d, (double)itemParser.parsePrice(goodItem), 0.001);
        Assert.assertNull(itemParser.parsePrice(badItem1));
        Assert.assertNull(itemParser.parsePrice(badItem2));
    }

    @Test
    public void typeParseTest(){
        ItemParser itemParser = new ItemParser();
        String goodItem = "tYpE:fOoD";
        String badItem1 = "nottYpE:fOoD";
        String badItem2 = "tYpE:CaKe";

        Assert.assertEquals("Food", itemParser.parseType(goodItem));
        Assert.assertNull(itemParser.parseType(badItem1));
        Assert.assertNull(itemParser.parseType(badItem2));
    }

    @Test
    public void expirationParseTest(){
        ItemParser itemParser = new ItemParser();
        String goodItem = "expiration:1/12/2013";
        String badItem1 = "notExpiration:1/12/2013";
        String badItem2 = "expiration:cAkE";

        Assert.assertEquals("1/12/2013", itemParser.parseExpiration(goodItem));
        Assert.assertNull(itemParser.parseExpiration(badItem1));
        Assert.assertNull(itemParser.parseExpiration(badItem2));
    }
}
