package hive.player.controller;

import static hive.pandora.constant.HiveInternalHeaders.*;

import com.netflix.client.http.HttpResponse;
import hive.player.entity.Player;
import hive.player.entity.PlayerOptions;
import hive.player.entity.PlayerSocial;
import hive.player.repository.PlayerRepository;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/")
public class PlayerController {

  @Autowired PlayerRepository playerRepository;
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
        1,
        "alias",
        "email",
        "11985054202",
        "String grande",
        "21/04/1998",
        "46358570855",
        options,
        social);
    playerRepository.save(player);
    player=new Player(
        2,
        "germano",
        "email",
        "11985055502",
        "String grande Lorem Ipsum dolor aquicumsitum amet",
        "21/04/1998",
        "46358570855",
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
  public Optional<Player> retrieveProfileData(@RequestHeader(name=AUTHENTICATED_USER_ID) final String userId){
    final var profileData = playerRepository.findById(Integer.parseInt(userId));
    return profileData;
  }
  @PostMapping
  public void insertProfileData(@RequestHeader(name=AUTHENTICATED_USER_ID) final String userId,
                                @RequestBody final Player player){
    player.setId(Integer.parseInt(userId));
    playerRepository.save(player);
  }
  @DeleteMapping
  public void deleteProfileData(@RequestHeader(name=AUTHENTICATED_USER_ID) final String userId){
    final var profileData = playerRepository.findById(Integer.parseInt(userId));
    profileData.get().setId(Integer.parseInt(userId));
    playerRepository.delete(profileData.get());
  }

}
