package cl.bennu.poker.card.service;

import cl.bennu.poker.card.builder.CardBuilder;
import cl.bennu.poker.card.enums.CardTypeEnum;
import cl.bennu.poker.card.exception.CardException;
import cl.bennu.poker.card.model.Card;
import cl.bennu.poker.card.model.Play;
import cl.bennu.poker.card.repository.PlayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class CardService {

    //private HashMap<String, Play> CARD = new HashMap<>();

    public List<Card> getAllCards() {
        return CardBuilder.buildList();
    }

    public @Autowired PlayRepository playRepository;

    public String deal() {
        UUID uuid = UUID.randomUUID();
        List<Card> cards = CardBuilder.buildList();
        Play play = new Play();
        play.setUuid(uuid.toString());
        play.setCards(cards);
        play.setPlayers(0);
        play.setCardTypeEnum(CardTypeEnum.PLAYER);

        //CARD.put(uuid.toString(), play);

        playRepository.save(play);

        return uuid.toString();
    }

    public Play getPlay(String uuid) throws CardException {
        //Play play = CARD.get(uuid);
        Play play = playRepository.findById(uuid).orElse(null);

        if (play == null) throw new CardException("No se han repartido cartas para " + uuid);

        return play;
    }

    public List<Card> getCards(String uuid, CardTypeEnum cardTypeEnum) throws CardException {
        //Play play = CARD.get(uuid);
        Play play = playRepository.findById(uuid).orElse(null);

        if (play == null) throw new CardException("No se han repartido cartas para " + uuid);

        if (play.getCardTypeEnum().equals(CardTypeEnum.RIVER)) {
            throw new CardException("Baraja finalizada, no se pueden entregar mas cartas");
        }

        if (CardTypeEnum.PLAYER.equals(cardTypeEnum)) {
            if (play.getPlayers() > 7) {
                throw new CardException("Maximo de jugadores excedido");
            }
            if (!cardTypeEnum.equals(play.getCardTypeEnum())) {
                throw new CardException("Error de Etapa, no se puede jugar");
            }
        } else {
            if (play.getPlayers() < 2) {
                throw new CardException("Cantidad de jugadores mínimo no permitido");
            }

            if (CardTypeEnum.FLOP.equals(cardTypeEnum) && !play.getCardTypeEnum().equals(CardTypeEnum.PLAYER)) {
                throw new CardException("Error de Etapa, no se puede jugar");
            }
            if (CardTypeEnum.TURN.equals(cardTypeEnum) && !play.getCardTypeEnum().equals(CardTypeEnum.FLOP)) {
                throw new CardException("Error de Etapa, no se puede jugar");
            }
            if (CardTypeEnum.RIVER.equals(cardTypeEnum) && !play.getCardTypeEnum().equals(CardTypeEnum.TURN)) {
                throw new CardException("Error de Etapa, no se puede jugar");
            }
        }

        List<Card> cards = play.getCards();
        List<Card> result = new ArrayList<>();
        for (int i = 0; i < cardTypeEnum.getCardNumbers(); i++) {
            boolean forward = true;
            do {
                int index = new Random(System.currentTimeMillis()).nextInt(52);
                Card card = cards.get(index);
                if (!card.isUse()) {
                    card.setUse(true);
                    result.add(card);
                    forward = false;
                }

            } while (forward);
        }


        if (CardTypeEnum.PLAYER.equals(cardTypeEnum)) {
            play.setPlayers(play.getPlayers() + 1);
        } else {
            play.setCardTypeEnum(cardTypeEnum);
        }

        // store play
        playRepository.save(play);

        return result;
    }
}
