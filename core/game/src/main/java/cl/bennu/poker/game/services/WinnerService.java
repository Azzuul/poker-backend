package cl.bennu.poker.game.services;

import cl.bennu.poker.game.entities.Card;
import cl.bennu.poker.game.entities.Player;
import cl.bennu.poker.game.helpers.TieBreaker;
import cl.bennu.poker.game.helpers.WinnerPlayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WinnerService {

    @Autowired
    private WinnerPlayer winnerPlayer;

    @Autowired
    private TieBreaker tieBreaker;


    //Revisar cartas de los jugadores
    public Player evaluatePlayersHands(List<Player> players) {
        for (Player player : players) {
            List<Card> playerCards = player.getCards();

            //Por cada jugador agarra sus cartas, y primero evaluo si son iguales.
            if (winnerPlayer.allSameType(playerCards) ) {
                winnerPlayer.sameTypeMethod(player);
            }else {
                winnerPlayer.noSameTypeMethod(players, player);
            }
        }
        return findPlayerWithHighestScore(players);
    }



    // encuentra al jugador con la puntuacion mas alta, si empatan usa el tiebreaker
    public Player findPlayerWithHighestScore(List<Player> players) {
        Player playerWithHighestScore = null;
        int highestScore = 0;
        List<Player> playersWithHighestScore = new ArrayList<>();

        // por cada jugador agarra la puntuacion mas alta
        for (Player player : players) {
            int score = player.getPuntuacion();
            if (score > highestScore) {
                highestScore = score;
                playerWithHighestScore = player;
                playersWithHighestScore.clear();
                playersWithHighestScore.add(player);
            } else if (score == highestScore) {
                // En caso de empate agrega el jugador a la lista de jugadores con la puntuación mas alta
                playersWithHighestScore.add(player);
            }
        }

        // Si solo hay un jugador con la puntuación mas alta devuelve su informacion
        if (playersWithHighestScore.size() == 1) {
            System.out.println("El jugador con la puntuación más alta es: " +
                    playerWithHighestScore.getPlayerName() +
                    ", Puntuación: " + playerWithHighestScore.getPuntuacion() +
                    ", Cartas: " + playerWithHighestScore.getCards());

            return playerWithHighestScore;

            } else {
                // Si hay empate utiliza TieBreaker para desempatar
                Optional<Player> opWinner = tieBreaker.desempatar(playersWithHighestScore, highestScore);
                if (opWinner.isPresent()) {
                    Player winner = opWinner.get();

                    System.out.println("El ganador del empate es: " +
                            winner.getPlayerName() +
                            ", Puntuación: " + winner.getPuntuacion() +
                            ", Cartas: " + winner.getCards());
                    return winner;
                } else {
                    System.out.println("No se pudo determinar un ganador claro.");
                    playerWithHighestScore =null;
                    return playerWithHighestScore;
                }
        }
    }

}
