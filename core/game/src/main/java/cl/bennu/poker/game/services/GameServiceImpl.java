package cl.bennu.poker.game.services;

import cl.bennu.poker.game.entities.Card;
import cl.bennu.poker.game.entities.Player;
import cl.bennu.poker.game.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class GameServiceImpl implements GameService {
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private WinnerService winnerService;

    @Transactional
    @Override
    public Optional<Player> saveWinner(List<Player> players) {
        Optional<Player> optionalWinner = Optional.ofNullable(winnerService.evaluatePlayersHands(players));
        optionalWinner.ifPresent(winner -> {
            for (Card card : winner.getCards()) {
                card.setPlayer(winner);
            }
            playerRepository.save(winner);
        });
        return optionalWinner;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Player> getAllPlayersWithDetails() {
        return playerRepository.findAllPlayersWithCardDetails();
    }


}
