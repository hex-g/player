package hive.player.repository;

import hive.player.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<Player,Integer> {

   @Query("from Player where autenticateUserId= ?1")
   Player getByAutenticateUserId(String autenticateUserId);

   @Query("select playerId from Player where autenticateUserId= ?1")
   Integer getPlayerIdWithAutenticateUserId(String autenticateUserId);

}
