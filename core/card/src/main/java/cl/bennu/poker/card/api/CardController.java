package cl.bennu.poker.card.api;

import cl.bennu.poker.card.api.base.BaseController;
import cl.bennu.poker.card.constants.Constants;
import cl.bennu.poker.card.enums.CardTypeEnum;
import cl.bennu.poker.card.exception.CardException;
import cl.bennu.poker.card.model.Card;
import cl.bennu.poker.card.model.Play;
import cl.bennu.poker.card.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = Constants.BASE_API, produces = MediaType.APPLICATION_JSON_VALUE)
public class CardController extends BaseController {

    private @Autowired CardService cardService;


    @GetMapping
    public ResponseEntity<?> deal() {
        String uuid = cardService.deal();
        return ok(uuid);
    }

    @GetMapping("/{uuid}/player")
    public ResponseEntity<?> get(@PathVariable("uuid") String uuid) throws CardException {
        List<Card> cards = cardService.getCards(uuid, CardTypeEnum.PLAYER);
        return ok(cards);
    }

    @GetMapping("/{uuid}/flop")
    public ResponseEntity<?> getFlop(@PathVariable("uuid") String uuid) throws CardException {
        List<Card> cards = cardService.getCards(uuid, CardTypeEnum.FLOP);
        return ok(cards);
    }

    @GetMapping("/{uuid}/turn")
    public ResponseEntity<?> getTurn(@PathVariable("uuid") String uuid) throws CardException {
        List<Card> cards = cardService.getCards(uuid, CardTypeEnum.TURN);
        return ok(cards);
    }

    @GetMapping("/{uuid}/river")
    public ResponseEntity<?> getRiver(@PathVariable("uuid") String uuid) throws CardException {
        List<Card> cards = cardService.getCards(uuid, CardTypeEnum.RIVER);
        return ok(cards);
    }

    @GetMapping("/{uuid}/type/{cardType}")
    public ResponseEntity<?> get2(@PathVariable("uuid") String uuid, @PathVariable("cardType") CardTypeEnum cardTypeEnum) throws CardException {
        List<Card> cards = cardService.getCards(uuid, cardTypeEnum);
        return ok(cards);
    }

        @GetMapping("/{uuid}")
    public ResponseEntity<?> getPlay(@PathVariable("uuid") String uuid) throws CardException {
        Play play = cardService.getPlay(uuid);
        return ok(play);
    }

}