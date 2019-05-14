package hive.player.controller;

import static hive.pandora.constant.HiveInternalHeaders.*;

import hive.player.entity.Player;
import hive.player.entity.PlayerOptions;
import hive.player.entity.PlayerSocial;
import hive.player.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class PlayerController {

  private final PlayerRepository playerRepository;
  @Autowired
  public PlayerController(PlayerRepository playerRepository){
    this.playerRepository=playerRepository;
  }
  public void createUser(){
    var social=new PlayerSocial("git","linkedin","@twitter");
    var options = new PlayerOptions(
        "ouro",
        "titan do café",
        "on",
        "on",
        "on",
        "on");
    var player=new Player(
        "1",
        "alias",
        "email",
        "11985054202",
        "String grande",
        "21/04/1998",
        options,
        social);
    playerRepository.save(player);
    player=new Player(
        "2",
        "germano",
        "email",
        "11985055502",
        "String grande Lorem Ipsum dolor aquicumsitum amet",
        "21/04/1998",
        options,
        social);
    playerRepository.save(player);
  }

  @GetMapping("create")
  public void setUp(){
    createUser();
  }

  @GetMapping("all")
  public List<Player> retrieveAllProfileData(){
    final var all = playerRepository.findAll();
    return all;
  }

  @GetMapping
  public Player retrieveProfileData(@RequestHeader(name=AUTHENTICATED_USER_ID) final String userId){
    var player = playerRepository.getByAutenticateUserId(userId);
    if(player==null){
      player=new Player();
      player.setAutenticateUserId(userId);
      playerRepository.save(player);
    }
    return player;
  }

  @PostMapping
  public void insertProfileData(@RequestHeader(name=AUTHENTICATED_USER_ID) final String userId,
                                @RequestBody final Player player){
    player.setPlayerId(playerRepository.getPlayerIdWithAutenticateUserId(userId));
    player.setAutenticateUserId(userId);
    playerRepository.save(player);
  }

}
