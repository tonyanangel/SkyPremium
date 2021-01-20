package com.skypremiuminternational.app.domain.models;

import java.util.List;

/**
 * Created by codigo on 12/3/18.
 */

public class Version {

  public final List<AppVersion> app_versions;

  public Version(List<AppVersion> app_versions) {
    this.app_versions = app_versions;
  }

  public static class AppVersion {
    public final String platform;
    public final String current_version;
    public final String force_update_version;
    public final String update_message;
    public final String force_update_message;
    public final String url;

    public AppVersion(String platform, String current_version, String force_update_version,
                      String update_message, String force_update_message, String url) {
      this.platform = platform;
      this.current_version = current_version;
      this.force_update_version = force_update_version;
      this.update_message = update_message;
      this.force_update_message = force_update_message;
      this.url = url;
    }
  }
}
