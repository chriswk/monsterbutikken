package no.borber.monsterShop.projection;

import java.util.HashMap;
import java.util.Map;

import no.borber.monsterShop.basket.BasketItem;
import no.borber.monsterShop.monsterTypes.MonsterTypeJson;
import no.borber.monsterShop.monsterTypes.MonsterTypesRepo;
import no.borber.monsterShop.store.AddBasketItemEvent;
import no.borber.monsterShop.store.MonsterEvent;
import no.borber.monsterShop.store.MonsterProjection;
import no.borber.monsterShop.store.RemoveBasketItemEvent;

public class BasketProjection extends MonsterProjection {
    Map<String, BasketItem> basket;

    public BasketProjection(String rootId) {
        super(rootId);
        basket = new HashMap<>();
    }

    @Override
    public void update(MonsterEvent event) {
        if (event instanceof AddBasketItemEvent) {
            handleAddEvent((AddBasketItemEvent) event);
        } else if (event instanceof RemoveBasketItemEvent) {
            handleRemoveEvent((RemoveBasketItemEvent) event);
        }
    }

    private void handleRemoveEvent(RemoveBasketItemEvent event) {
        String monster = event.getMonster();
        if (basket.containsKey(monster)) {
            basket.get(monster).removeMonster();
            if (basket.get(monster).getNumber() == 0) {
                basket.remove(monster);
            }
        }
    }

    private void handleAddEvent(AddBasketItemEvent event) {
        String monster = event.getMonster();
        MonsterTypeJson monsterJson = MonsterTypesRepo.getMonsterType(monster);
        if (!basket.containsKey(monster) && monsterJson != null) {
            basket.put(monster, new BasketItem(monsterJson.getName(), monsterJson.getPrice()));
        }
        basket.get(monster).addMonster();
    }

    public Map<String, BasketItem> getBasket() {
        return basket;
    }
}
