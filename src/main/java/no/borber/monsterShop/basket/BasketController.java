package no.borber.monsterShop.basket;

import com.google.common.base.Optional;
import no.borber.monsterShop.MonsterShopController;
import no.borber.monsterShop.monsterTypes.MonsterTypesRepo;
import no.borber.monsterShop.projection.BasketProjection;
import no.borber.monsterShop.service.BasketService;
import no.borber.monsterShop.service.ProjectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import javax.servlet.http.HttpSession;


@Controller
public class BasketController extends MonsterShopController{
    @Autowired
    BasketService basketService;

    @Autowired
    ProjectionService projectionService;
    /**
     * Gets the current state of a customers basket
     *
     * @return Map of String monsterType og basketItem for the applicable monster type.
     */
    @RequestMapping(value = "/basket/",  method=RequestMethod.GET)
    @ResponseBody()
    public Map<String, BasketItem> getBasket(HttpSession session){
        return projectionService.getBasketProjection(getBasketId(session)).getBasket();
    }

    private String getBasketId(HttpSession session) {
        String sessionId = Optional.fromNullable((String) session.getAttribute("basketId")).or(basketService.create());
        session.setAttribute("basketId", sessionId);
        return sessionId;
    }
    /**
     * Adds a new monster of a specified type to the customers basket. If there is an existing basket item the number
     * of monsters is incremented, otherwise a new order baslet item is created.
     *
     * @param monstertype name of the monstertype to be added
     */
    @RequestMapping(value = "/basket/{monstertype}",  method=RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void add(@PathVariable String monstertype, HttpSession session){
        basketService.add(getBasketId(session), monstertype);

    }

    /**
     * Removes a monster from the customers basket. If the resulting number of monsters reaches 0, the basket item is
     * removed.
     *
     * @param monstertype name of the monstertype to be removed
     */
    @RequestMapping(value = "/basket/{monstertype}",  method=RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void remove(@PathVariable String monstertype, HttpSession session) {
        basketService.remove(getBasketId(session), monstertype);
    }

    /**
     * Calculates the sum of (price * number) for all items in the basket.
     */
    @RequestMapping(value = "/basket/sum",  method=RequestMethod.GET)
    @ResponseBody
    public BasketSum sum(){
        return null;
    }

}
