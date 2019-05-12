package hive.player.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;

@Entity
@Table(name = "tb_user_profile")
public class Player {
  @Id
  @JsonIgnore
  private Integer id;

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

  @JsonProperty
  @Column(name = "cpf")
  private String cpf;
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

  public Integer getId() {//delete by no use?
    return id;
  }

  public void setId(final Integer id) {
    this.id = id;
  }

  public Player(
      final Integer id,
      final String loginAlias,
      final String email,
      final String telnumber,
      final String flavorText,
      final String birthday,
      final String cpf,
      final PlayerOptions options,
      final PlayerSocial social
  ) {
    this.id = id;
    this.loginAlias = loginAlias;
    this.email = email;
    this.telnumber = telnumber;
    this.flavorText = flavorText;
    this.birthday = birthday;
    this.cpf = cpf;
    this.options = options;
    this.social = social;
  }
}
