package hive.player.repository;

import hive.player.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<Player,Integer> {

   @Query("from Player where authenticatedUserId= ?1")
   Player findByAuthenticatedUserId(String authenticatedUserId);

   @Query("select playerId from Player where authenticatedUserId= ?1")
   Integer findPlayerIdWithAuthenticatedUserId(String authenticatedUserId);

}
