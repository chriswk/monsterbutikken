package no.borber.monsterShop.store;

import java.util.Observer;

import no.borber.monsterShop.store.EventStore;
import no.borber.monsterShop.store.MonsterProjection;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ProjectionTest {
    EventStore store;
    @Mock
    MonsterProjection projection;

    @Before
    public void setUp() {
        this.store = new EventStore();
    }

    @Test
    public void should_be_able_to_subscribe_to_event_store() {
        store.subscribe(projection);
        verify(projection, never()).update(any(MonsterEvent.class));
    }

    @Test
    public void projection_should_receive_event() {
        MonsterEvent event = new MonsterEvent("Something", "Somethingelse");
        store.subscribe(projection);
        store.append(event);
        verify(projection, times(1)).update(event);
    }
}
