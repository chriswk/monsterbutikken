package no.borber.monsterShop.store;

public class MonsterEvent {
    private final String rootId;
    private final String type;

    public MonsterEvent(String rootId, String type) {
        this.rootId = rootId;
        this.type = type;
    }

    public String getRootId() {
        return rootId;
    }

    public String getType() {
        return type;
    }
}
