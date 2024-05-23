package cl.bennu.poker.card.builder;

import cl.bennu.poker.card.enums.CharacterCardTypeEnum;
import cl.bennu.poker.card.model.Card;

import java.util.ArrayList;
import java.util.List;

public class CardBuilder {

    public static List<Card> buildList() {
        List<Card> cards = new ArrayList<>();

        CharacterCardTypeEnum[] characterCardTypeEnums = CharacterCardTypeEnum.values();
        for (CharacterCardTypeEnum characterCardTypeEnum : characterCardTypeEnums) {
            for (int i = 0; i < 13; i++) {
                int cardNumber = i + 1;
                Card card = new Card();
                card.setCharacterCardTypeEnum(characterCardTypeEnum);
                card.setNumber(cardNumber);
                card.setSymbol(getSymbol(cardNumber));
                card.setUse(false);
                cards.add(card);
            }
        }

        return cards;
    }

    private static String getSymbol(Integer number) {
        if (number == 1) return "A";
        else if (number == 11) return "J";
        else if (number == 12) return "Q";
        else if (number == 13) return "K";
        else return number.toString();
    }
}
