package com.skypremiuminternational.app.data.model.ean.booking.history;

public class RoomsItem {

  public final Links links;

  public RoomsItem(
      Links links) {
    this.links = links;
  }

  public static class Links {
    public final Cancel cancel;

    public Links(Cancel cancel) {
      this.cancel = cancel;
    }
  }
}
