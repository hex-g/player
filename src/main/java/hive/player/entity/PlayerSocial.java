package hive.player.entity;

import javax.persistence.Embeddable;

@Embeddable
public class PlayerSocial {
  public String github;
  public String linkedIn;
  public String twitter;

  public PlayerSocial(final String github, final String linkedIn, final String twitter) {
    this.github = github;
    this.linkedIn = linkedIn;
    this.twitter = twitter;
  }
  public PlayerSocial() {}
}
