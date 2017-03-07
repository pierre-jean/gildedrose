package com.gildedrose;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.experimental.theories.suppliers.TestedOn;

public class GildedRoseTest {

    private final static String GENERIC_ITEM =  "Generic Item";
    private static final String BRIE = "Aged Brie";
    private static final String SULFURAS = "Sulfuras, Hand of Ragnaros";
    private static final String BACKSTAGE = "Backstage passes to a TAFKAL80ETC concert";

    private Item[] createItems(String name, int longestSellIn, int shortestSellIn, int quality) {
        Item[] items = new Item[longestSellIn-shortestSellIn+1];
        for (int sellIn = longestSellIn; sellIn >= shortestSellIn; sellIn --){
            items[sellIn-shortestSellIn] = new Item(name, sellIn, quality);
        }
        return items;
    }

    @Test
    public void should_decrease_quality_of_normal_item_by_1_before_expiration() {
        Item[] items = createItems(GENERIC_ITEM, 100, 1, 10);
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        for (Item updatedItem : items) {
            assertThat(updatedItem.quality, is(equalTo(10 - 1)));
        }
    }

    @Test
    public void should_decrease_quality_of_normal_item_by_2_after_expiration(){
        Item[] items = createItems(GENERIC_ITEM, 0, -100, 10);
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        for (Item updatedItem : items){
            assertThat(updatedItem.quality, is(equalTo(10 - 2)));
        }
    }

    @Test
    public void should_not_decrease_quality_of_normal_item_less_than_zero_quality(){
        Item[] items = createItems(GENERIC_ITEM, 0, -100, 0);
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        for (Item updatedItem : items){
            assertThat(updatedItem.quality, is(equalTo(0)));
        }
    }

    @Test
    public void should_decrease_sell_in_of_normal_item_by_one_after_update(){
        Item[] items = createItems(GENERIC_ITEM, 100, -100, 0);
        Item[] items_with_one_day_less = createItems(GENERIC_ITEM, 99, -101, 0);
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        for (int i = 0; i < items.length; i++){
            assertThat(items[i].quality, is(equalTo(items_with_one_day_less[i].quality)));
        }
    }

    @Test
    public void should_increase_quality_of_Brie_by_1_before_expiration(){
        Item[] items = createItems(BRIE, 100, 1, 10);
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        for (Item updatedItem : items){
            assertThat(updatedItem.quality, is(equalTo(10 + 1)));
        }
    }

    @Test
    public void should_increase_quality_of_Brie_by_2_after_expiration(){
        Item[] items = createItems(BRIE, 0, -100, 10);
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        for (Item updatedItem : items){
            assertThat(updatedItem.quality, is(equalTo(10 + 2)));
        }
    }

    @Test
    public void should_not_increase_quality_of_Brie_over_50(){
        Item[] items = createItems(BRIE, 100, -100, 50);
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        for (Item updatedItem : items){
            assertThat(updatedItem.quality, is(equalTo(50)));
        }
    }

    @Test
    public void should_not_decrease_quality_of_sulfuras(){
        Item[] items = createItems(SULFURAS, 100, -100, 10);
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        for (Item updatedItem : items){
            assertThat(updatedItem.quality, is(equalTo(10)));
        }
    }

    @Test
    public void should_not_decrease_sell_in_of_sulfuras(){
        Item[] items = createItems(SULFURAS, 100, -100, 10);
        Item[] itemsNotUpdated = createItems(SULFURAS, 100, -100, 10);
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        for (int i=0; i< items.length; i++){
            assertThat(items[i].quality, is(equalTo(itemsNotUpdated[i].quality)));
        }
    }

    @Test
    public void should_increase_quality_by_1_backstage_item_when_more_than_10_days_before_concert(){
        Item[] items = createItems(BACKSTAGE, 100, 11, 10);
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        for (Item updatedItem : items){
            assertThat(updatedItem.quality, is(equalTo(11)));
        }
    }

    @Test
    public void should_increase_quality_by_2_backstage_item_when_less_than_10_days_before_concert(){
        Item[] items = createItems(BACKSTAGE, 10, 6, 10);
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        for (Item updatedItem : items){
            assertThat(updatedItem.quality, is(equalTo(12)));
        }
    }

    @Test
    public void should_increase_quality_by_3_backstage_item_when_less_than_10_days_before_concert(){
        Item[] items = createItems(BACKSTAGE, 5, 1, 10);
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        for (Item updatedItem : items){
            assertThat(updatedItem.quality, is(equalTo(13)));
        }
    }

    @Test
    public void should_set_quality_to_0_backstage_item_when_less_than_10_days_before_concert(){
        Item[] items = createItems(BACKSTAGE, 0, -100, 10);
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        for (Item updatedItem : items){
            assertThat(updatedItem.quality, is(equalTo(0)));
        }
    }

    @Test
    public void should_not_increase_quality_of_backstage_over_50(){
        Item[] items = createItems(BACKSTAGE, 100, 1, 50);
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        for (Item updatedItem : items){
            assertThat(updatedItem.quality, is(equalTo(50)));
        }
    }

}