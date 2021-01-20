package com.skypremiuminternational.app.domain.models.hunry_go_where;

import java.util.List;

public class OutletResponse {

  List<OutletItem> outletItems;

  public List<OutletItem> getOutletItems() {
    return outletItems;
  }

  public void setOutletItems(List<OutletItem> outletItems) {
    this.outletItems = outletItems;
  }
}
