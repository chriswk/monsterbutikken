package no.borber.monsterShop.store;

public final class AddBasketItemEvent extends MonsterEvent {

    String monster;

    public AddBasketItemEvent(String id, String type, String monster) {
        super(id, type);
        this.monster = monster;
    }

    public String getMonster() {
        return monster;
    }
}
