package no.borber.monsterShop.store;

import no.borber.monsterShop.basket.BasketItem;

public class RemoveBasketItemEvent extends MonsterEvent {
    String monster;

    public RemoveBasketItemEvent(String rootId, String type, String monster) {
        super(rootId, type);
        this.monster = monster;
    }

    public String getMonster() {
        return monster;
    }
}
