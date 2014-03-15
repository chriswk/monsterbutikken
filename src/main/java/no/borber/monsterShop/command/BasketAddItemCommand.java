package no.borber.monsterShop.command;

import no.borber.monsterShop.basket.BasketItem;

public class BasketAddItemCommand {
    private String monster;

    public BasketAddItemCommand(String monster) {
        this.monster = monster;
    }

    public String getMonster() {
        return monster;
    }
}
