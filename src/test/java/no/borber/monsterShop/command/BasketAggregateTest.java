package no.borber.monsterShop.command;

import java.util.UUID;

import no.borber.monsterShop.basket.BasketItem;
import no.borber.monsterShop.store.AddBasketItemEvent;
import no.borber.monsterShop.store.MonsterEvent;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BasketAggregateTest {

    BasketAggregate aggregate;

    @Before
    public void setUp() {
        aggregate = new BasketAggregate("testId");
    }

    @Test
    public void handles_add_item_events() {
        BasketItem testItem = new BasketItem("Hydra", 24000d);
        MonsterEvent event = new AddBasketItemEvent("testid", "basket", testItem.getMonsterType());
        aggregate.handle(event);
        assertEquals(1, aggregate.getItems().size());
        assertEquals(1, aggregate.getItems().get(0).getNumber());
    }

    @Test
    public void handles_two_adds_by_incrementing() {
        BasketItem testItem = new BasketItem("Hydra", 24000d);
        MonsterEvent event = new AddBasketItemEvent("testid", "basket", testItem.getMonsterType());
        aggregate.handle(event);
        aggregate.handle(event);
        assertEquals(1, aggregate.getItems().size());
        assertEquals(2, aggregate.getItems().get(0).getNumber());
    }

    @Test
    public void handles_remove_when_empty() {

    }
}
