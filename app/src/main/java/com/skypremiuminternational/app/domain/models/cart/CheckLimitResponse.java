package com.skypremiuminternational.app.domain.models.cart;

import java.util.List;

/**
 * Created by codigo on 26/2/18.
 */

public class CheckLimitResponse {

  public final Boolean status_limit;
  public final String message;
  public final List<LimitError> limit_errors;

  public CheckLimitResponse(Boolean status_limit, String message, List<LimitError> limit_errors) {
    this.status_limit = status_limit;
    this.message = message;
    this.limit_errors = limit_errors;
  }

  public static class LimitError {
    public final String type_limit;
    public final String message;
    public final List<AffectedId> item;

    public LimitError(String type_limit, String message, List<AffectedId> item) {
      this.type_limit = type_limit;
      this.message = message;
      this.item = item;
    }
  }

  public static class AffectedId {
    public final String item_id;
    public final String product_id;
    public final int stock_qty;

    public AffectedId(String item_id, String product_id, int stock_qty) {
      this.item_id = item_id;
      this.product_id = product_id;
      this.stock_qty = stock_qty;
    }
  }

  public static class IdMessageErrorPair {
    public final String id;
    public final String errorMessage;

    public IdMessageErrorPair(String id, String errorMessage) {
      this.id = id;
      this.errorMessage = errorMessage;
    }
  }
}
