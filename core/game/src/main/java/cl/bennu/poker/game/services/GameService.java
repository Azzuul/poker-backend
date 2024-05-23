package cl.bennu.poker.game.services;

import cl.bennu.poker.game.entities.Card;
import cl.bennu.poker.game.entities.Player;

import java.util.List;
import java.util.Optional;

public interface GameService {
    Optional<Player> saveWinner(List<Player> players);
    List<Player> getAllPlayersWithDetails();
}
