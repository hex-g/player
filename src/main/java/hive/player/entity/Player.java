package hive.player.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
@Table(name = "tb_user_profile")
public class Player {
  @Id
  @JsonIgnore
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer playerId;
  @JsonIgnore
  private String autenticateUserId;

  @JsonProperty
  @Column(name = "login_alias", unique = true)
  private String loginAlias;
  @JsonProperty
  @Column(name = "email")
  private String email;
  @JsonProperty
  @Column(name = "telnumber")
  private String telnumber;
  @JsonProperty
  @Column(name = "flavor_text")
  private String flavorText;
  @JsonProperty
  @Column(name = "birthday")
  private String birthday;

  @Embedded
  @JsonProperty
  @Column(name = "options")
  private PlayerOptions options;
  @Embedded
  @JsonProperty
  @Column(name = "social")
  private PlayerSocial social;

  public Player() {
  }

  public Integer getPlayerId() {
    return playerId;
  }

  public void setPlayerId(Integer playerId) {
    this.playerId = playerId;
  }

  public String getAutenticateUserId() {
    return autenticateUserId;
  }

  public void setAutenticateUserId(String autenticateUserId) {
    this.autenticateUserId = autenticateUserId;
  }

  public Player(
      final String autenticateUserId,
      final String loginAlias,
      final String email,
      final String telNumber,
      final String flavorText,
      final String birthday,
      final PlayerOptions options,
      final PlayerSocial social
  ) {
    this.autenticateUserId = autenticateUserId;
    this.loginAlias = loginAlias;
    this.email = email;
    this.telnumber = telNumber;
    this.flavorText = flavorText;
    this.birthday = birthday;
    this.options = options;
    this.social = social;
  }
}
