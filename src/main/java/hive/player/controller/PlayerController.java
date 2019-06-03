package hive.player.controller;

import static hive.pandora.constant.HiveInternalHeaders.*;

import hive.player.entity.Player;
import hive.player.exception.PlayerIdShouldNotBeInJsonException;
import hive.player.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class PlayerController {
  private final PlayerRepository playerRepository;

  @Autowired
  public PlayerController(final PlayerRepository playerRepository) {
    this.playerRepository = playerRepository;
  }

  @GetMapping
  public Player retrieveProfileData(
      @RequestHeader(name = AUTHENTICATED_USER_ID) final String authenticatedUserId
  ) {
    return createAnPlayerInDatabaseIfNotExists(authenticatedUserId);
  }

  @PostMapping
  public void insertProfileData(
      @RequestHeader(name = AUTHENTICATED_USER_ID) final String authenticatedUserId,
      @RequestBody final Player player
  ) {
    if(player.getPlayerId() != null){
      throw new PlayerIdShouldNotBeInJsonException();
    }
    updatePlayerCorrelatedWithTheAuthenticatedId(player, authenticatedUserId);
    player.setAuthenticatedUserId(authenticatedUserId);
    playerRepository.save(player);
  }

  private Player createAnPlayerInDatabaseIfNotExists(final String authenticatedUserId) {
    var player = playerRepository.findByAuthenticatedUserId(authenticatedUserId);
    if (player == null) {
      player = new Player(authenticatedUserId);
      playerRepository.save(player);
    }
    return player;
  }

  private void updatePlayerCorrelatedWithTheAuthenticatedId(
      final Player player,
      final String authenticatedUserId
  ) {
    final var playerFound=playerRepository.findByAuthenticatedUserId(authenticatedUserId);
    if(playerFound != null) {
      final var autogeneratedPlayerId = playerFound.getPlayerId();
      if (autogeneratedPlayerId != null) {
        player.setPlayerId(autogeneratedPlayerId);
      }
    }
  }
}
