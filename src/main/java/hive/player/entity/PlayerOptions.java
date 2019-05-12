package hive.player.entity;

import javax.persistence.Embeddable;

@Embeddable
public class PlayerOptions {
  public String laurel_wreath;
  public String honorific;
  public String darkmode;
  public String notify_hiveshare;
  public String notify_hivecentral;
  public String notify_disciplines;

  public PlayerOptions(
      final String laurel_wreath,
      final String honorific,
      final String darkmode,
      final String notify_hiveshare,
      final String notify_hivecentral,
      final String notify_disciplines
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
