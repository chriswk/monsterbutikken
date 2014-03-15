package no.borber.monsterShop.store;

import com.google.common.eventbus.Subscribe;

public abstract class MonsterProjection {
    private String rootId;

    protected MonsterProjection(String rootId) {
        this.rootId = rootId;
    }

    public void catchUp(Iterable<MonsterEvent> events) {
        if (events != null) {
            for (MonsterEvent event : events) {
                update(event);
            }
        }
    }

    @Subscribe
    public abstract void update(MonsterEvent event);

    public String getRootId() {
        return rootId;
    }
}
