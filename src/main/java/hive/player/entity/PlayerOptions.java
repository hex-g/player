package hive.player.entity;

import javax.persistence.Embeddable;

@Embeddable
public class PlayerOptions {
  public String laurel_wreath;
  public String honorific;
  public Boolean darkmode;
  public Boolean notify_hiveshare;
  public Boolean notify_hivecentral;
  public Boolean notify_disciplines;

  public PlayerOptions(
      final String laurel_wreath,
      final String honorific,
      final Boolean darkmode,
      final Boolean notify_hiveshare,
      final Boolean notify_hivecentral,
      final Boolean notify_disciplines
  ) {
    this.laurel_wreath = laurel_wreath;
    this.honorific = honorific;
    this.darkmode = darkmode;
    this.notify_hiveshare = notify_hiveshare;
    this.notify_hivecentral = notify_hivecentral;
    this.notify_disciplines = notify_disciplines;
  }
  public PlayerOptions(){}
}
