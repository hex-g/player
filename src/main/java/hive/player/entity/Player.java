package hive.player.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import hive.player.exception.BlankIdException;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tb_user_profile")
public class Player {
  @Id
  @JsonIgnore
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  //is some DBMS like Oracle you should change the strategy
  private Integer playerId;
  @JsonIgnore
  private String authenticatedUserId;

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

  public Integer getPlayerId() {
    return playerId;
  }

  public void setPlayerId(final Integer playerId) {
    this.playerId = playerId;
  }

  public String getAuthenticatedUserId() {
    return authenticatedUserId;
  }

  public void setAuthenticatedUserId(@NotNull final String authenticatedUserId) {
    if(authenticatedUserId.isBlank()){
      throw new BlankIdException();
    }
    this.authenticatedUserId = authenticatedUserId;
  }

  private Player() {
  }

  public Player(
      @NotNull final String authenticatedUserId,
      final String loginAlias,
      final String email,
      final String telNumber,
      final String flavorText,
      final String birthday,
      final PlayerOptions options,
      final PlayerSocial social
  ) {
    setAuthenticatedUserId(authenticatedUserId);
    this.loginAlias = loginAlias;
    this.email = email;
    this.telnumber = telNumber;
    this.flavorText = flavorText;
    this.birthday = birthday;
    this.options = options;
    this.social = social;
  }
  public Player(@NotNull final String authenticatedUserId){
    setAuthenticatedUserId(authenticatedUserId);
  }
}
