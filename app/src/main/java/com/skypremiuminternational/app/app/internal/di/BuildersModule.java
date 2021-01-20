package com.skypremiuminternational.app.app.internal.di;

import android.app.Activity;
import androidx.fragment.app.Fragment;

import com.skypremiuminternational.app.app.features.booking.detail.BookingDetailActivity;
import com.skypremiuminternational.app.app.features.booking.history.BookingsHistoryActivity;
import com.skypremiuminternational.app.app.features.booking.summary.BookingSummaryActivity;
import com.skypremiuminternational.app.app.features.buy_now.BuyNowActivity;
import com.skypremiuminternational.app.app.features.cart.ShoppingCartActivity;
import com.skypremiuminternational.app.app.features.checkout.CheckoutActivity;
import com.skypremiuminternational.app.app.features.checkout.room.stepthree.OrderPlacedFragment;
import com.skypremiuminternational.app.app.features.checkout.room.steptwo.CheckoutPaymentReviewFragment;
import com.skypremiuminternational.app.app.features.checkout.stepone.CheckoutDeliveryFragment;
import com.skypremiuminternational.app.app.features.checkout.stepthree.CheckoutOrderPlacedFragment;
import com.skypremiuminternational.app.app.features.checkout.steptwo.CheckoutReviewFragment;
import com.skypremiuminternational.app.app.features.checkout.room.RoomCheckoutActivity;
import com.skypremiuminternational.app.app.features.checkout.room.stepone.CheckoutGuestDetailFragment;
import com.skypremiuminternational.app.app.features.demo3_activity.DemoThreeActivity;
import com.skypremiuminternational.app.app.features.demo_activity.DemoActvity;
import com.skypremiuminternational.app.app.features.demo_two_activity.DemoTwoActivity;
import com.skypremiuminternational.app.app.features.estore.EstoreActivity;
import com.skypremiuminternational.app.app.features.estore.detail.EstoreDetailActivity;
import com.skypremiuminternational.app.app.features.estore.detail.review.ImageReviewDialogFragment;
import com.skypremiuminternational.app.app.features.faq.FaqActivity;
import com.skypremiuminternational.app.app.features.forgot_password.ForgotPasswordActivity;
import com.skypremiuminternational.app.app.features.forgot_password.success.ForgotPasswordSuccessDialogFragment;
import com.skypremiuminternational.app.app.features.forgot_password.update.UpdatePasswordActivity;
import com.skypremiuminternational.app.app.features.home.HomeFragment;
import com.skypremiuminternational.app.app.features.hunry_go_where.confirm_create_reservation.ConfirmReservationDialogFragment;
import com.skypremiuminternational.app.app.features.hunry_go_where.datesinglepicker.DateSinglePickerDialogFragment;
import com.skypremiuminternational.app.app.features.hunry_go_where.detail.DetailsResevationActivity;
import com.skypremiuminternational.app.app.features.hunry_go_where.make_reservation.MakeAReservationDialogFragment;
import com.skypremiuminternational.app.app.features.hunry_go_where.my_reservation.MyResevationsActivity;
import com.skypremiuminternational.app.app.features.landing.LandingActivity;
import com.skypremiuminternational.app.app.features.membership_renewal.MembershipRenewalActivity;
import com.skypremiuminternational.app.app.features.memership_services.MembershipActivity;
import com.skypremiuminternational.app.app.features.navigation.NavigationDialogFragment;
import com.skypremiuminternational.app.app.features.notification_setting.NotificationSettingActivity;
import com.skypremiuminternational.app.app.features.notifications.NotificationActivity;
import com.skypremiuminternational.app.app.features.profile.ProfileActivity;
import com.skypremiuminternational.app.app.features.profile.billingaddress.ManageBillingAddressActivity;
import com.skypremiuminternational.app.app.features.profile.edit_profile.EditProfileActivity;
import com.skypremiuminternational.app.app.features.profile.edit_profile.MembershipRenewalDialogFragment;
import com.skypremiuminternational.app.app.features.profile.manage_credit_card.ManageCreditCardActivity;
import com.skypremiuminternational.app.app.features.profile.manage_delivery_address.ManageDeliveryAddressActivity;
import com.skypremiuminternational.app.app.features.profile.my_favourites.MyFavouritesActivity;
import com.skypremiuminternational.app.app.features.profile.my_orders.MyOrderActivity;
import com.skypremiuminternational.app.app.features.profile.my_sky_dollar.MySkyDollarActivity;
import com.skypremiuminternational.app.app.features.profile.order.detail.OrderDetailsActivity;
import com.skypremiuminternational.app.app.features.profile.order.detail.edit_review.EditReviewDialogFragment;
import com.skypremiuminternational.app.app.features.profile.order.detail.review.ReviewDialogFragment;
import com.skypremiuminternational.app.app.features.profile.order.detail.see_review.ReviewContentDialogFragment;
import com.skypremiuminternational.app.app.features.reset_password.ResetPasswordActivity;
import com.skypremiuminternational.app.app.features.search.SearchActivity;
import com.skypremiuminternational.app.app.features.search.result_keyword.SearchKeywordFragment;
import com.skypremiuminternational.app.app.features.search.result_products.SearchProductsFragment;
import com.skypremiuminternational.app.app.features.shopping.detail.ShoppingDetailActivity;
import com.skypremiuminternational.app.app.features.signin.SignInActivity;
import com.skypremiuminternational.app.app.features.splash.SplashActivity;
import com.skypremiuminternational.app.app.features.travel.ProductListFragment;
import com.skypremiuminternational.app.app.features.travel.booking.HotelBookingDialogFragment;
import com.skypremiuminternational.app.app.features.travel.detail.TravelDetailActivity;
import com.skypremiuminternational.app.app.features.travel.ean.cancellationpolicy.CancellationPolicyDialogFragment;
import com.skypremiuminternational.app.app.features.travel.ean.daterangepicker.DateRangePickerDialogFragment;
import com.skypremiuminternational.app.app.features.travel.ean.detail.property.PropertyDetailActivity;
import com.skypremiuminternational.app.app.features.travel.ean.detail.room.RoomDetailDialogFragment;
import com.skypremiuminternational.app.app.features.travel.ean.occupancy.OccupancyPickerDialogFragment;
import com.skypremiuminternational.app.app.features.travel.ean.search.SearchHotelDialogFragment;
import com.skypremiuminternational.app.app.features.travel.ean.suggestion.SuggestionActivity;
import com.skypremiuminternational.app.app.internal.di.demo.DemoSubcomponent;
import com.skypremiuminternational.app.app.internal.di.demo.DemoThreeSubcomponent;

import com.skypremiuminternational.app.app.internal.di.demo.DemoTwoSubcomponent;
import com.skypremiuminternational.app.app.internal.di.detail_reservation.DetailReservationSubcomponent;
import com.skypremiuminternational.app.app.internal.di.my_reservations.ConfirmReservationSubcomponent;
import com.skypremiuminternational.app.app.internal.di.my_reservations.DateSinglePickerSubcomponent;
import com.skypremiuminternational.app.app.internal.di.my_reservations.MakeAReservationSubcomponent;
import com.skypremiuminternational.app.app.internal.di.my_reservations.MyReservationsSubcomponent;
import com.skypremiuminternational.app.app.internal.di.booking.BookingDetailSubcomponent;
import com.skypremiuminternational.app.app.internal.di.booking.BookingHistorySubcomponent;
import com.skypremiuminternational.app.app.internal.di.booking.BookingSummarySubcomponent;
import com.skypremiuminternational.app.app.internal.di.buy_now.BuyNowSubcomponent;
import com.skypremiuminternational.app.app.internal.di.cancellationpolicy.CancellationPolicySubcomponent;
import com.skypremiuminternational.app.app.internal.di.checkingout.CheckoutDeliverySubcomponent;
import com.skypremiuminternational.app.app.internal.di.checkingout.room.CheckoutGuestDetailSubcomponent;
import com.skypremiuminternational.app.app.internal.di.checkingout.CheckoutNoticeSubcomponent;
import com.skypremiuminternational.app.app.internal.di.checkingout.CheckoutOrderPlacedSubcomponent;
import com.skypremiuminternational.app.app.internal.di.checkingout.CheckoutReviewSubcomponent;
import com.skypremiuminternational.app.app.internal.di.checkingout.CheckoutSubcomponent;
import com.skypremiuminternational.app.app.internal.di.checkingout.RoomCheckoutSubcomponent;
import com.skypremiuminternational.app.app.internal.di.checkingout.ShoppingCartSubcomponent;
import com.skypremiuminternational.app.app.internal.di.checkingout.room.OrderPlacedSubcomponent;
import com.skypremiuminternational.app.app.internal.di.checkingout.room.PaymentReviewSubcomponent;
import com.skypremiuminternational.app.app.internal.di.estore.EstoreDetailSubcomponent;
import com.skypremiuminternational.app.app.internal.di.estore.EstoreSubcomponent;
import com.skypremiuminternational.app.app.internal.di.estore.ImageReviewSubcomponent;
import com.skypremiuminternational.app.app.internal.di.faq.FaqSubcomponent;
import com.skypremiuminternational.app.app.internal.di.forgot_password.ForgotPasswordSubcomponent;
import com.skypremiuminternational.app.app.internal.di.forgot_password.ForgotPasswordSuccessSubcomponent;
import com.skypremiuminternational.app.app.internal.di.forgot_password.UpdatePasswordSubcomponent;
import com.skypremiuminternational.app.app.internal.di.home.HomeSubcomponent;
import com.skypremiuminternational.app.app.internal.di.landing.LandingSubcomponent;
import com.skypremiuminternational.app.app.internal.di.membership.MembershipSubcomponent;
import com.skypremiuminternational.app.app.internal.di.membership_renewal.MembershipRenewalSubcomponent;
import com.skypremiuminternational.app.app.internal.di.navigation.NavigationSubcomponent;
import com.skypremiuminternational.app.app.internal.di.notification.NotificationSubcomponent;
import com.skypremiuminternational.app.app.internal.di.notification_setting.NotificationSettingSubcomponent;
import com.skypremiuminternational.app.app.internal.di.profile.BillingAddressSubcomponent;
import com.skypremiuminternational.app.app.internal.di.profile.EditProfileSubcomponent;
import com.skypremiuminternational.app.app.internal.di.profile.EditReviewDialogSubcomponent;
import com.skypremiuminternational.app.app.internal.di.profile.ManageCreditCardsSubcomponent;
import com.skypremiuminternational.app.app.internal.di.profile.ManageDeliveryAddressSubcomponent;
import com.skypremiuminternational.app.app.internal.di.profile.MyFavouritesSubcomponent;
import com.skypremiuminternational.app.app.internal.di.profile.MyOrdersSubcomponent;
import com.skypremiuminternational.app.app.internal.di.profile.MySkyDollarSubcomponent;
import com.skypremiuminternational.app.app.internal.di.profile.OrderDetailsSubcomponent;
import com.skypremiuminternational.app.app.internal.di.profile.ProfileSubcomponent;
import com.skypremiuminternational.app.app.internal.di.profile.ReviewContentSubcomponent;
import com.skypremiuminternational.app.app.internal.di.profile.ReviewSubcomponent;
import com.skypremiuminternational.app.app.internal.di.reset_password.ResetPasswordSubcomponent;
import com.skypremiuminternational.app.app.internal.di.search.SearchKeywordSubcomponent;
import com.skypremiuminternational.app.app.internal.di.search.SearchProductsSubcomponent;
import com.skypremiuminternational.app.app.internal.di.search.SearchSubcomponent;
import com.skypremiuminternational.app.app.internal.di.shopping.ShoppingDetailSubcomponent;
import com.skypremiuminternational.app.app.internal.di.signin.SignInSubcomponent;
import com.skypremiuminternational.app.app.internal.di.splash.SplashSubcomponent;
import com.skypremiuminternational.app.app.internal.di.travel.DateRangePickerSubcomponent;
import com.skypremiuminternational.app.app.internal.di.travel.HotelBookingSubcomponent;
import com.skypremiuminternational.app.app.internal.di.travel.OccupancyPickerSubcomponent;
import com.skypremiuminternational.app.app.internal.di.travel.PropertyDetailSubcomponent;
import com.skypremiuminternational.app.app.internal.di.travel.RoomDetailSubcomponent;
import com.skypremiuminternational.app.app.internal.di.travel.SearchHotelSubcomponent;
import com.skypremiuminternational.app.app.internal.di.travel.SuggestionSubcomponent;
import com.skypremiuminternational.app.app.internal.di.travel.TravelDetailSubcomponent;
import com.skypremiuminternational.app.app.internal.di.travel.TravelSubcomponent;

import dagger.Binds;
import dagger.Module;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.android.support.FragmentKey;
import dagger.multibindings.IntoMap;

/**
 * Created by hein on 5/15/17.
 */
@Module
public abstract class BuildersModule {

  @Binds
  @IntoMap
  @ActivityKey(SplashActivity.class)
  abstract AndroidInjector.Factory<? extends Activity> bindSplashActivityInjectorFactory(
      SplashSubcomponent.Builder builder);

  @Binds
  @IntoMap
  @ActivityKey(ManageBillingAddressActivity.class)
  abstract AndroidInjector.Factory<? extends Activity> bindManageBillingAddressActivityInjectorFactory(
      BillingAddressSubcomponent.Builder builder);

  @Binds
  @IntoMap
  @ActivityKey(SuggestionActivity.class)
  abstract AndroidInjector.Factory<? extends Activity> bindSuggestionActivityInjectorFactory(
      SuggestionSubcomponent.Builder builder);

  @Binds
  @IntoMap
  @ActivityKey(BookingSummaryActivity.class)
  abstract AndroidInjector.Factory<? extends Activity> bindBookingSummaryActivityInjectorFactory(
      BookingSummarySubcomponent.Builder builder);

  @Binds
  @IntoMap
  @ActivityKey(SignInActivity.class)
  abstract AndroidInjector.Factory<? extends Activity> bindSignInActivityInjectorFactory(
      SignInSubcomponent.Builder builder);

  @Binds
  @IntoMap
  @ActivityKey(ForgotPasswordActivity.class)
  abstract AndroidInjector.Factory<? extends Activity> bindForgotPasswordActivityInjectorFactory(
      ForgotPasswordSubcomponent.Builder builder);

  @Binds
  @IntoMap
  @ActivityKey(PropertyDetailActivity.class)
  abstract AndroidInjector.Factory<? extends Activity> bindPropertyDetailActivityInjectorFactory(
      PropertyDetailSubcomponent.Builder builder);

  @Binds
  @IntoMap
  @FragmentKey(ForgotPasswordSuccessDialogFragment.class)
  abstract AndroidInjector.Factory<? extends Fragment> bindForgotPasswordSuccessDialogFragmentInjectorFactory(
      ForgotPasswordSuccessSubcomponent.Builder builder);

  @Binds
  @IntoMap
  @FragmentKey(CheckoutGuestDetailFragment.class)
  abstract AndroidInjector.Factory<? extends Fragment> bindGuestDetailFragmentInjectorFactory(
      CheckoutGuestDetailSubcomponent.Builder builder);

  @Binds
  @IntoMap
  @FragmentKey(SearchHotelDialogFragment.class)
  abstract AndroidInjector.Factory<? extends Fragment> bindSearchHotelDialogFragmentInjectorFactory(
      SearchHotelSubcomponent.Builder builder);

  @Binds
  @IntoMap
  @FragmentKey(RoomDetailDialogFragment.class)
  abstract AndroidInjector.Factory<? extends Fragment> bindRoomDetailDialogFragmentInjectorFactory(
      RoomDetailSubcomponent.Builder builder);

  @Binds
  @IntoMap
  @ActivityKey(ResetPasswordActivity.class)
  abstract AndroidInjector.Factory<? extends Activity> bindResetPasswordActivityInjectorFactory(
      ResetPasswordSubcomponent.Builder builder);

  @Binds
  @IntoMap
  @ActivityKey(LandingActivity.class)
  abstract AndroidInjector.Factory<? extends Activity> bindLandingActivityInjectorFactory(
      LandingSubcomponent.Builder builder);

  @Binds
  @IntoMap
  @FragmentKey(HomeFragment.class)
  abstract AndroidInjector.Factory<? extends Fragment> bindHomeFragmentInjectorFactory(
      HomeSubcomponent.Builder builder);

  @Binds
  @IntoMap
  @FragmentKey(NavigationDialogFragment.class)
  abstract AndroidInjector.Factory<? extends Fragment> bindNavigationDialogFragmentInjectorFactory(
      NavigationSubcomponent.Builder builder);

  @Binds
  @IntoMap
  @FragmentKey(ConfirmReservationDialogFragment.class)
  abstract AndroidInjector.Factory<? extends Fragment> bindConfirmReservationDialogFragmentInjectorFactory(
      ConfirmReservationSubcomponent.Builder builder);

  @Binds
  @IntoMap
  @FragmentKey(ReviewDialogFragment.class)
  abstract AndroidInjector.Factory<? extends Fragment> bindReviewDialogFragmentInjectorFactory(
      ReviewSubcomponent.Builder builder); @Binds
  @IntoMap
  @FragmentKey(ReviewContentDialogFragment.class)
  abstract AndroidInjector.Factory<? extends Fragment> bindReviewContentDialogFragmentInjectorFactory(
      ReviewContentSubcomponent.Builder builder);

  @Binds
  @IntoMap
  @FragmentKey(CancellationPolicyDialogFragment.class)
  abstract AndroidInjector.Factory<? extends Fragment> bindCancellationDialogFragmentInjectorFactory(
      CancellationPolicySubcomponent.Builder builder);

  @Binds
  @IntoMap
  @FragmentKey(ProductListFragment.class)
  abstract AndroidInjector.Factory<? extends Fragment> bindTravelFragmentInjectorFactory(
      TravelSubcomponent.Builder builder);

  @Binds
  @IntoMap
  @FragmentKey(CheckoutDeliveryFragment.class)
  abstract AndroidInjector.Factory<? extends Fragment> bindCheckoutDeliveryFragmentInjectorFactory(
      CheckoutDeliverySubcomponent.Builder builder);

  @Binds
  @IntoMap
  @FragmentKey(CheckoutReviewFragment.class)
  abstract AndroidInjector.Factory<? extends Fragment> bindCheckoutReviewFragmentInjectorFactory(
      CheckoutReviewSubcomponent.Builder builder);

  @Binds
  @IntoMap
  @FragmentKey(CheckoutOrderPlacedFragment.class)
  abstract AndroidInjector.Factory<? extends Fragment> bindCheckoutOrderPlacedFragmentInjectorFactory(
      CheckoutOrderPlacedSubcomponent.Builder builder);

  @Binds
  @IntoMap
  @FragmentKey(MakeAReservationDialogFragment.class)
  abstract AndroidInjector.Factory<? extends Fragment> bindMakeAReservationDialogFragmentInjectorFactory(
      MakeAReservationSubcomponent.Builder builder);

  @Binds
  @IntoMap
  @FragmentKey(DateSinglePickerDialogFragment.class)
  abstract AndroidInjector.Factory<? extends Fragment> bindDateSinglePickerDialogFragmentInjectorFactory(
      DateSinglePickerSubcomponent.Builder builder);

  @Binds
  @IntoMap
  @ActivityKey(SearchActivity.class)
  abstract AndroidInjector.Factory<? extends Activity> bindSearchActivityInjectorFactory(
      SearchSubcomponent.Builder builder);

  @Binds
  @IntoMap
  @FragmentKey(SearchKeywordFragment.class)
  abstract AndroidInjector.Factory<? extends Fragment> bindSearchKeywordFragmentInjectorFactory(
      SearchKeywordSubcomponent.Builder builder);

  @Binds
  @IntoMap
  @FragmentKey(SearchProductsFragment.class)
  abstract AndroidInjector.Factory<? extends Fragment> bindSearchProductsFragmentInjectorFactory(
      SearchProductsSubcomponent.Builder builder);

  @Binds
  @IntoMap
  @ActivityKey(ShoppingDetailActivity.class)
  abstract AndroidInjector.Factory<? extends Activity> bindShoppingDetailActivityInjectorFactory(
      ShoppingDetailSubcomponent.Builder builder);

  @Binds
  @IntoMap
  @ActivityKey(ProfileActivity.class)
  abstract AndroidInjector.Factory<? extends Activity> bindProfileActivityInjectorFactory(
      ProfileSubcomponent.Builder builder);

  @Binds
  @IntoMap
  @ActivityKey(EditProfileActivity.class)
  abstract AndroidInjector.Factory<? extends Activity> bindEditProfileActivityInjectorFactory(
      EditProfileSubcomponent.Builder builder);

  @Binds
  @IntoMap
  @ActivityKey(FaqActivity.class)
  abstract AndroidInjector.Factory<? extends Activity> bindFaqActivityInjectorFactory(
      FaqSubcomponent.Builder builder);

  @Binds
  @IntoMap
  @ActivityKey(MembershipActivity.class)
  abstract AndroidInjector.Factory<? extends Activity> bindMembershipActivityInjectorFactory(
      MembershipSubcomponent.Builder builder);

  @Binds
  @IntoMap
  @ActivityKey(TravelDetailActivity.class)
  abstract AndroidInjector.Factory<? extends Activity> bindTravelDetailActivityInjectorFactory(
      TravelDetailSubcomponent.Builder builder);

  @Binds
  @IntoMap
  @ActivityKey(ManageCreditCardActivity.class)
  abstract AndroidInjector.Factory<? extends Activity> bindManageCreditCardActivityInjectorFactory(
      ManageCreditCardsSubcomponent.Builder builder);

  @Binds
  @IntoMap
  @ActivityKey(ManageDeliveryAddressActivity.class)
  abstract AndroidInjector.Factory<? extends Activity> bindManageDeliveryAddressActivityInjectorFactory(
      ManageDeliveryAddressSubcomponent.Builder builder);

  @Binds
  @IntoMap
  @FragmentKey(HotelBookingDialogFragment.class)
  abstract AndroidInjector.Factory<? extends Fragment> bindHotelBookingDialogFragmentInjectorFactory(
      HotelBookingSubcomponent.Builder builder);

  @Binds
  @IntoMap
  @ActivityKey(MyOrderActivity.class)
  abstract AndroidInjector.Factory<? extends Activity> bindMyOrderActivityInjectorFactory(
      MyOrdersSubcomponent.Builder builder);

  @Binds
  @IntoMap
  @ActivityKey(MyFavouritesActivity.class)
  abstract AndroidInjector.Factory<? extends Activity> bindMyFavouritesActivityInjectorFactory(
      MyFavouritesSubcomponent.Builder builder);

  @Binds
  @IntoMap
  @FragmentKey(DateRangePickerDialogFragment.class)
  abstract AndroidInjector.Factory<? extends Fragment> bindDateRangePickerDialogFragmentInjectorFactory(
      DateRangePickerSubcomponent.Builder builder);

  @Binds
  @IntoMap
  @FragmentKey(CheckoutPaymentReviewFragment.class)
  abstract AndroidInjector.Factory<? extends Fragment> bindPaymentReviewFragmentInjectorFactory(
      PaymentReviewSubcomponent.Builder builder);

  @Binds
  @IntoMap
  @FragmentKey(OrderPlacedFragment.class)
  abstract AndroidInjector.Factory<? extends Fragment> bindOrderPlacedFragmentInjectorFactory(
      OrderPlacedSubcomponent.Builder builder);

  @Binds
  @IntoMap
  @ActivityKey(UpdatePasswordActivity.class)
  abstract AndroidInjector.Factory<? extends Activity> bindUpdatePasswordActivityInjectorFactory(
      UpdatePasswordSubcomponent.Builder builder);

  @Binds
  @IntoMap
  @ActivityKey(RoomCheckoutActivity.class)
  abstract AndroidInjector.Factory<? extends Activity> bindRoomCheckoutActivityInjectorFactory(
      RoomCheckoutSubcomponent.Builder builder);

  @Binds
  @IntoMap
  @ActivityKey(ShoppingCartActivity.class)
  abstract AndroidInjector.Factory<? extends Activity> bindShoppingCartActivityInjectorFactory(
      ShoppingCartSubcomponent.Builder builder);

  @Binds
  @IntoMap
  @ActivityKey(CheckoutActivity.class)
  abstract AndroidInjector.Factory<? extends Activity> bindCheckoutActivityInjectorFactory(
      CheckoutSubcomponent.Builder builder);

  @Binds
  @IntoMap
  @ActivityKey(EstoreActivity.class)
  abstract AndroidInjector.Factory<? extends Activity> bindEstoreActivityInjectorFactory(
      EstoreSubcomponent.Builder builder);

  @Binds
  @IntoMap
  @ActivityKey(EstoreDetailActivity.class)
  abstract AndroidInjector.Factory<? extends Activity> bindEstoreDetailActivityInjectorFactory(
      EstoreDetailSubcomponent.Builder builder);

  @Binds
  @IntoMap
  @ActivityKey(OrderDetailsActivity.class)
  abstract AndroidInjector.Factory<? extends Activity> bindOrderDetailsActivityInjectorFactory(
      OrderDetailsSubcomponent.Builder builder
  );

  @Binds
  @IntoMap
  @FragmentKey(MembershipRenewalDialogFragment.class)
  abstract AndroidInjector.Factory<? extends Fragment> bindCheckoutNoticeDialogFragmentInjectorFactory(
      CheckoutNoticeSubcomponent.Builder builder);

  @Binds
  @IntoMap
  @FragmentKey(OccupancyPickerDialogFragment.class)
  abstract AndroidInjector.Factory<? extends Fragment> bindOccupancyPickerDialogFragmentInjectorFactory(
      OccupancyPickerSubcomponent.Builder builder);

  @Binds
  @IntoMap
  @ActivityKey(BookingsHistoryActivity.class)
  abstract AndroidInjector.Factory<? extends Activity> bindBookingHistoryActivityInjectorFactory(
      BookingHistorySubcomponent.Builder builder);

  @Binds
  @IntoMap
  @ActivityKey(BookingDetailActivity.class)
  abstract AndroidInjector.Factory<? extends Activity> bindBookingDetailActivityInjectorFactory(
      BookingDetailSubcomponent.Builder builder);

  @Binds
  @IntoMap
  @ActivityKey(MembershipRenewalActivity.class)
  abstract AndroidInjector.Factory<? extends Activity> bindMembershipRenewalActivityInjectorFactory(
          MembershipRenewalSubcomponent.Builder builder);
  @Binds
  @IntoMap
  @FragmentKey(EditReviewDialogFragment.class)
  abstract AndroidInjector.Factory<? extends Fragment> bindEditEditReviewDialogFragmentInjectorFactory(
      EditReviewDialogSubcomponent.Builder builder);
  @Binds
  @IntoMap
  @FragmentKey(ImageReviewDialogFragment.class)
  abstract AndroidInjector.Factory<? extends Fragment> bindEditImageReviewDialogFragmentInjectorFactory(
      ImageReviewSubcomponent.Builder builder);
  @Binds
  @IntoMap
  @ActivityKey(BuyNowActivity.class)
  abstract AndroidInjector.Factory<? extends Activity> bindBuyNowActivityInjectorFactory(
          BuyNowSubcomponent.Builder builder);

  @Binds
  @IntoMap
  @ActivityKey(NotificationSettingActivity.class)
  abstract AndroidInjector.Factory<? extends Activity> bindNotificationSettingActivityInjectorFactory(
          NotificationSettingSubcomponent.Builder builder);
  @Binds
  @IntoMap
  @ActivityKey(NotificationActivity.class)
  abstract AndroidInjector.Factory<? extends Activity> bindNotificationActivityInjectorFactory(
      NotificationSubcomponent.Builder builder);

  @Binds
  @IntoMap
  @ActivityKey(MySkyDollarActivity.class)
  abstract AndroidInjector.Factory<? extends Activity> bindMySkyDollarActivityInjectorFactory(
      MySkyDollarSubcomponent.Builder builder);
  @Binds
  @IntoMap
  @ActivityKey(MyResevationsActivity.class)
  abstract AndroidInjector.Factory<? extends Activity> bindMyResevationsActivityInjectorFactory(
      MyReservationsSubcomponent.Builder builder);

  @Binds
  @IntoMap
  @ActivityKey(DetailsResevationActivity.class)
  abstract AndroidInjector.Factory<? extends Activity> bindDetailsResevationActivityInjectorFactory(
      DetailReservationSubcomponent.Builder builder);

  @Binds
  @IntoMap
  @ActivityKey(DemoActvity.class)
  abstract AndroidInjector.Factory<? extends Activity> bindDemoActvityInjectorFactory(
          DemoSubcomponent.Builder builder);

  @Binds
  @IntoMap
  @ActivityKey(DemoTwoActivity.class)
  abstract AndroidInjector.Factory<? extends Activity> bindDemoTwoActvityInjectorFactory(
          DemoTwoSubcomponent.Builder builder);@Binds
  @IntoMap
  @ActivityKey(DemoThreeActivity.class)
  abstract AndroidInjector.Factory<? extends Activity> bindDemoThreeActivityInjectorFactory(
          DemoThreeSubcomponent.Builder builder);
}

