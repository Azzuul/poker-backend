package cl.bennu.poker.game.helpers;

import cl.bennu.poker.game.entities.Card;
import cl.bennu.poker.game.entities.Player;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class TieBreaker {

    public Optional<Player> desempatar(List<Player> players, int score) {
        switch (score) {
            case 10: // Escalera Real (Royal Flush)
                return Optional.of(players.get(0)); // Solo un ganador en un empate de escalera real
            case 9: // Escalera de Color (Straight Flush)
            case 6: // Color (Flush)
            case 5: // Escalera (Straight)
            case 1: // Carta Alta (High Card)
                return desempatePorCartaAlta(players);
            case 8: // Póker (Four of a Kind)
            case 7: // Full House
            case 4: // Trío (Three of a Kind)
            case 3: // Doble Pareja (Two Pair)
            case 2: // Pareja (One Pair)
                return desempatePorGrupoDeCartas(players);
            default:
                return desempatePorCartaAlta(players);
        }
    }

    //desempata entre el group de carta mas alto,si empatan devuelve la carta mas alta.
    public Optional<Player> desempatePorGrupoDeCartas(List<Player> players) {
        Optional<Player> highestPlayer = Optional.empty();
        int maxGroupSize = 0;
        int maxKickerValue = 0;

        for (Player player : players) {
            int maxPlayerGroupCard = getMaxGroupValue(player.getCards());
            int maxPlayerKicker = getMaxCardPlayer(player.getCards());

            if (maxPlayerGroupCard > maxGroupSize) {
                highestPlayer = Optional.of(player);
                maxGroupSize = maxPlayerGroupCard;
                maxKickerValue = maxPlayerKicker;
            } else if (maxPlayerGroupCard == maxGroupSize) {
                if (maxPlayerKicker > maxKickerValue) {
                    highestPlayer = Optional.of(player);
                    maxKickerValue = maxPlayerKicker;
                } else if (maxPlayerKicker == maxKickerValue) {
                    highestPlayer = Optional.empty(); // En caso de empate no hay un ganador claro y lo dejo vacio
                }
            }
        }
        return highestPlayer;
    }


    //Encuentra el valor numerico mas alto para cada carta si empatan devuelve el segundo mas alto.
    private int getMaxGroupValue(List<Card> cards) {
        Map<Integer, Integer> cardCountMap = new HashMap<>();
        for (Card card : cards){
            int number = card.getNumber();
            cardCountMap.put(number, cardCountMap.getOrDefault(number, 0) + 1);
        }

        int maxCount = 0;
        List<Integer> maxCardCounts = new ArrayList<>();

        for (Map.Entry<Integer, Integer> entry : cardCountMap.entrySet()) {
            int count = entry.getValue();
            if (count > maxCount) {
                maxCardCounts.clear();
                maxCardCounts.add(entry.getKey());
                maxCount = count;
            } else if (count == maxCount) {
                maxCardCounts.add(entry.getKey());
            }
        }

        if (maxCardCounts.size() > 1) {
            maxCardCounts.sort(Collections.reverseOrder());
            return maxCardCounts.get(1);
        } else {
            return maxCardCounts.isEmpty() ? 0 : maxCardCounts.get(0);
        }
    }




    // Devuelve al jugador con la carta mas alta  si empatan se devuelve vacio
    public Optional<Player> desempatePorCartaAlta(List<Player> players) {
        Optional<Player> highestPlayer = Optional.empty();
        int maxKickerValue = 0;
        for (Player player : players) {
            int maxPlayerKicker = getMaxCardPlayer(player.getCards());
            if (maxPlayerKicker > maxKickerValue) {
                highestPlayer = Optional.of(player);
                maxKickerValue = maxPlayerKicker;
            } else if (maxPlayerKicker == maxKickerValue) {
                highestPlayer = Optional.empty();
            }
        }
        return highestPlayer;
    }

    // Encuenta el valor numerico mas altro entre las cartas  si son iguales devuelve el segundo mas alto
    private int getMaxCardPlayer(List<Card> cards){
        int maxCard = cards.get(0).getNumber();
        List<Integer> maxCards = new ArrayList<>();

        for (Card card : cards) {
            int number = card.getNumber();
            if (number > maxCard) {
                maxCards.clear();
                maxCards.add(number);
                maxCard = number;
            } else if (number == maxCard) {
                maxCards.add(number);
            }
        }
        if (maxCards.size() > 1) {
            maxCards.sort(Collections.reverseOrder());
            return maxCards.get(1);
        } else {
            return maxCard;
        }
    }

}
