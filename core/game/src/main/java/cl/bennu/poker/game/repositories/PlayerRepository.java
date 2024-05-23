package cl.bennu.poker.game.repositories;

import cl.bennu.poker.game.entities.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PlayerRepository extends JpaRepository<Player,Integer> {
//    @Query(value =
//            "SELECT p.id , p.name , p.puntuacion , p.mazo , c.number , c.symbol , c.card_type_id " +
//                    "FROM tbl_players p "+
//                    "left join fetch tbl_cards c "+
//                    "ON p.id=c.player_id",
//            nativeQuery = true)

    @Query("select p from Player p left join fetch p.cards")
    List<Player> findAllPlayersWithCardDetails();




}
