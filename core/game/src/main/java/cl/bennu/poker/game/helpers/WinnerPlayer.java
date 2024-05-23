package cl.bennu.poker.game.helpers;

import cl.bennu.poker.game.entities.Card;
import cl.bennu.poker.game.entities.Player;
import cl.bennu.poker.game.enums.CountTypeCard;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.IntStream;

@Component
public class WinnerPlayer {

    // Método para verificar si todas las cartas tienen el mismo tipo
    public boolean allSameType(List<Card> cards) {
        String typeName = cards.get(0).getCardType().getName();
        return cards.stream()
                .allMatch(card -> card.getCardType().getName().equals(typeName));
    }

    // Método para verificar si las cartas son consecutivas considerando As como 1 y 14
    private boolean areConsecutive(List<Card> cards) {
        List<Integer> numbers = cards.stream()
                .map(Card::getNumber)
                .sorted()
                .toList();

// Caso especial para manejar el As (1 y 14)
//        if (numbers.contains(1)) {
//            List<Integer> withAceHigh = new ArrayList<>(numbers);
//            withAceHigh.remove((Integer) 1);
//            withAceHigh.add(14);
//            Collections.sort(withAceHigh);
//            return isSequential(withAceHigh);
//        } else {
//            return isSequential(numbers);
//        }
        return IntStream.range(0, numbers.size() - 1)
                .allMatch(i -> numbers.get(i) + 1 == numbers.get(i + 1));
    }


    //Cuenta la cantidad de veces que se repite una carta, por cada tipo.
    public int countMatchingCards(List<Card> cards, int tipo) {
        Map<Integer, Integer> cardCountMap = new HashMap<>();
        int result = 0;
        // Contar la cantidad de cada número de carta
        for (Card card : cards) {
            int number = card.getNumber();
            cardCountMap.put(number, cardCountMap.getOrDefault(number, 0) + 1);
        }
        // Contar la cantidad de cartas con el numero de veces por el tipo
        for (int count : cardCountMap.values()) {
            if (count == tipo) {
                result++;
            }
        }
        return result;
    }

    //Metodos que asigna la puntucacion de jugada dependiendo de las cartas (teniendo todas del mismo palo)
    public void sameTypeMethod(Player player) {
        if (areConsecutive(player.getCards())) {
            List<Integer> numbers = player.getCards().stream()
                    .map(Card::getNumber)
                    .sorted()
                    .toList();
            System.out.println(numbers);
            if (numbers.equals(Arrays.asList(1,10, 11, 12, 13))) {
                System.out.println("Player " + player.getPlayerName() + " tiene una Escalera Real (Royal Flush)");
                player.setPuntuacion(10);
            } else {
                System.out.println("Player " + player.getPlayerName() + " tiene una Escalera de Color (Straight Flush)");
                player.setPuntuacion(9);
            }
        }else{
            System.out.println("Player " + player.getPlayerName() + " tiene un Color Fush (5 del mismo palo no consecutivas.)");
            player.setPuntuacion(6);
        }
    }

    //Metodos que asigna la puntuacion de jugada dependiendo de las cartas (distinto palo)
    public void noSameTypeMethod(List<Player> players, Player player) {
        int pairs = countMatchingCards(player.getCards(), CountTypeCard.PAIR.getValue());
        int threes = countMatchingCards(player.getCards(), CountTypeCard.TRIO.getValue());
        int quartet = countMatchingCards(player.getCards(), CountTypeCard.QUARTET.getValue());

        if (quartet==1){
            System.out.println("Player " + player.getPlayerName() + " tiene un Póker (Four of a Kind): Cuatro cartas del mismo valor.");
            player.setPuntuacion(8);
        }else if (pairs ==1 && threes==1){
            System.out.println("Player " + player.getPlayerName() + " tiene un Full House: Tres cartas del mismo valor y dos cartas de otro valor.");
            player.setPuntuacion(7);
        }else if (areConsecutive(player.getCards())){
            System.out.println("Player " + player.getPlayerName() + " tiene una Escalera (Straight): Cinco cartas en secuencia, de diferentes palos.");
            player.setPuntuacion(5);
        } else if (threes==1){
            System.out.println("Player " + player.getPlayerName() + " tiene un Trío (Three of a Kind): Tres cartas del mismo valor.");
            player.setPuntuacion(4);
        } else if (pairs==2){
            System.out.println("Player " + player.getPlayerName() + " tiene una Doble Pareja (Two Pair): Dos pares de cartas de diferentes valores.");
            player.setPuntuacion(3);
        } else if (pairs==1){
            System.out.println("Player " + player.getPlayerName() + " tiene una Pareja (One Pair): Dos cartas del mismo valor.");
            player.setPuntuacion(2);
        } else {
            assignToHighestCard(players);
        }
    }


    //Asignacion de puntuacion por carta mas alta
    private void assignToHighestCard(List<Player> players) {
        // Obtener la carta más alta de cada jugador
        Map<Player, Card> highestCards = new HashMap<>();
        for (Player player : players) {
            Card highestCard = getHighestCard(player.getCards());
            highestCards.put(player, highestCard);
        }

        // Encontrar la carta más alta y determinar los jugadores con empate
        Card highestCard = null;
        List<Player> tiedPlayers = new ArrayList<>();
        for (Map.Entry<Player, Card> entry : highestCards.entrySet()) {
            Card playerCard = entry.getValue();
            if (highestCard == null || playerCard.getNumber() > highestCard.getNumber()) {
                highestCard = playerCard;
                tiedPlayers.clear();
                tiedPlayers.add(entry.getKey());
            } else if (playerCard.getNumber().equals(highestCard.getNumber())) {
                tiedPlayers.add(entry.getKey());
            }
        }
        // Asignar 1 al jugador con la carta más alta y a los jugadores empatados
        for (Player player : tiedPlayers) {
            player.setPuntuacion(1);
        }
    }

    private Card getHighestCard(List<Card> cards) {
        return cards.stream()
                .max(Comparator.comparing(Card::getNumber))
                .orElse(null);
    }
}
