package cl.bennu.poker.game.controllers;


import cl.bennu.poker.game.entities.Player;
import cl.bennu.poker.game.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/v1")
public class GameController {

    @Autowired
    private GameService gameService;


    @PostMapping("/player/winner")
    public Optional<Player> getWinner(@RequestBody List<Player>players){
        return gameService.saveWinner(players);
    }

    @GetMapping("/player/winners")
    public List<Player> findPlayersDetails(){
        return gameService.getAllPlayersWithDetails();
    }
}
