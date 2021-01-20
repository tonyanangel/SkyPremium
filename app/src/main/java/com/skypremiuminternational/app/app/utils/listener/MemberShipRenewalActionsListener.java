package com.skypremiuminternational.app.app.utils.listener;

/**
 * Created by codigo on 2/4/18.
 */

public interface MemberShipRenewalActionsListener {

  void onClickedRenewLater(boolean showPromptAgain);

  void onClickedRenewMemberShip(boolean showPromptAgain);

  void onClickedRenew(boolean usePoints);
}
