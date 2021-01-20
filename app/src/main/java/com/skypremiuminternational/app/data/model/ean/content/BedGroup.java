package com.skypremiuminternational.app.data.model.ean.content;

import java.util.List;

public class BedGroup {

  public final int id;

  public final String description;

  public final List<ConfigurationItem> configuration;

  public BedGroup(int id, String description, List<ConfigurationItem> configuration) {
    this.id = id;
    this.description = description;
    this.configuration = configuration;
  }
}
