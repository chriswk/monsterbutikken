package no.borber.monsterShop.service;

import no.borber.monsterShop.projection.BasketProjection;
import no.borber.monsterShop.store.EventStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectionService {
    @Autowired
    private EventStore eventStore;

    public BasketProjection getBasketProjection(String id) {
        BasketProjection proj = new BasketProjection(id);
        eventStore.subscribe(proj);
        return proj;
    }
}
