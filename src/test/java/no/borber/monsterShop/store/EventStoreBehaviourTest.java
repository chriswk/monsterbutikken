package no.borber.monsterShop.store;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class EventStoreBehaviourTest {
    EventStore monster;

    @Before
    public void setUp() {
        monster = new EventStore();
    }

    @Test
    public void should_keep_order() {
        String id = UUID.randomUUID().toString();
        MonsterEvent me1 = new MonsterEvent(id, "event1");
        MonsterEvent me2 = new MonsterEvent(id, "event2");
        MonsterEvent me3 = new MonsterEvent(id, "event3");
        monster.append(me1);
        monster.append(me2);
        monster.append(me3);
        Iterable<MonsterEvent> events = monster.getEvents(id);
        Iterator<MonsterEvent> eventsIterator = events.iterator();
        assertEquals(me1, eventsIterator.next());
        assertEquals(me2, eventsIterator.next());
        assertEquals(me3, eventsIterator.next());
        assertFalse(eventsIterator.hasNext());
    }
}
