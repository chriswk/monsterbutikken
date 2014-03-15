package no.borber.monsterShop.command;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import no.borber.monsterShop.basket.BasketItem;
import no.borber.monsterShop.monsterTypes.MonsterTypeJson;
import no.borber.monsterShop.monsterTypes.MonsterTypesRepo;
import no.borber.monsterShop.store.AddBasketItemEvent;
import no.borber.monsterShop.store.MonsterEvent;
import no.borber.monsterShop.store.RemoveBasketItemEvent;

public class BasketAggregate {
    private Map<String, Integer> items;
    private final String id;
    private final String type = "basket";

    public BasketAggregate(String id) {
        this.id = id;
        this.items = new HashMap<>();
    }


    public MonsterEvent handle(BasketAddItemCommand basketAddItemCommand) {
        return new AddBasketItemEvent(id, type, basketAddItemCommand.getMonster());
    }

    public MonsterEvent handle(BasketRemoveItemCommand basketRemoveItemCommand) {
        return new RemoveBasketItemEvent(id, type, basketRemoveItemCommand.getMonster());
    }

    public void handle(MonsterEvent event) {
        if (event instanceof AddBasketItemEvent) {
            handleAddEvent((AddBasketItemEvent) event);
        } else if (event instanceof RemoveBasketItemEvent) {
            handleRemoveEvent((RemoveBasketItemEvent) event);
        }
    }

    private void handleRemoveEvent(RemoveBasketItemEvent event) {
        String monster = event.getMonster();
        if (items.containsKey(monster)) {
            Integer amount = items.get(monster);
            if (amount > 1) {
                items.put(monster, --amount);
            } else {
                items.remove(monster);
            }
        }
    }

    private void handleAddEvent(AddBasketItemEvent event) {
        String monster = event.getMonster();
        Integer amount = items.containsKey(monster) ? items.get(monster) : 0;
        items.put(monster, ++amount);
    }

    public List<BasketItem> getItems() {
        return FluentIterable
                .from(items.entrySet())
                .transform(new Function<Map.Entry<String, Integer>, BasketItem>() {
                    @Override
                    public BasketItem apply(Map.Entry<String, Integer> input) {
                        MonsterTypeJson monster = MonsterTypesRepo.getMonsterType(input.getKey());
                        return new BasketItem(monster.getName(), monster.getPrice(), input.getValue());
                    }
                }).toList();
    }
}
