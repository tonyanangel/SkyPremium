package com.skypremiuminternational.app.app.internal.di;

import android.content.Context;

import com.skypremiuminternational.app.app.App;
import com.skypremiuminternational.app.app.UIThread;
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
import com.skypremiuminternational.app.data.DBManager;
import com.skypremiuminternational.app.data.DbHelper;
import com.skypremiuminternational.app.data.InternalStorageManager;
import com.skypremiuminternational.app.data.JobExecutor;
import com.skypremiuminternational.app.data.NetworkManager;
import com.skypremiuminternational.app.data.impl.DBManagerImpl;
import com.skypremiuminternational.app.data.impl.InternalStorageManagerImpl;
import com.skypremiuminternational.app.data.impl.NetworkManagerImpl;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

/**
 * Created by hein on 5/9/17.
 */
@Module(subcomponents = {
    SuggestionSubcomponent.class, BillingAddressSubcomponent.class,
    PaymentReviewSubcomponent.class, CancellationPolicySubcomponent.class,
    CheckoutGuestDetailSubcomponent.class, SplashSubcomponent.class, SignInSubcomponent.class,
    ForgotPasswordSubcomponent.class,
    RoomDetailSubcomponent.class, RoomCheckoutSubcomponent.class,
    ForgotPasswordSuccessSubcomponent.class, ResetPasswordSubcomponent.class,
    LandingSubcomponent.class, HomeSubcomponent.class, NavigationSubcomponent.class, ReviewSubcomponent.class,
    SearchSubcomponent.class, SearchKeywordSubcomponent.class, SearchProductsSubcomponent.class,
    TravelSubcomponent.class, CheckoutDeliverySubcomponent.class, CheckoutReviewSubcomponent.class,
    CheckoutOrderPlacedSubcomponent.class, ShoppingDetailSubcomponent.class,
    ProfileSubcomponent.class, EditProfileSubcomponent.class, FaqSubcomponent.class,
    MembershipSubcomponent.class, TravelDetailSubcomponent.class,
    ManageDeliveryAddressSubcomponent.class, CheckoutNoticeSubcomponent.class,
    ManageCreditCardsSubcomponent.class, HotelBookingSubcomponent.class, MyOrdersSubcomponent.class,
    MyFavouritesSubcomponent.class, DateRangePickerSubcomponent.class,
    UpdatePasswordSubcomponent.class, ShoppingCartSubcomponent.class, CheckoutSubcomponent.class,
    EstoreSubcomponent.class, OccupancyPickerSubcomponent.class, EstoreDetailSubcomponent.class,
    OrderDetailsSubcomponent.class, BookingSummarySubcomponent.class,
    PropertyDetailSubcomponent.class, SearchHotelSubcomponent.class, OrderPlacedSubcomponent.class,
    BookingDetailSubcomponent.class, MembershipRenewalSubcomponent.class, EditReviewDialogSubcomponent.class,
    ImageReviewSubcomponent.class, ReviewContentSubcomponent.class,
    BookingHistorySubcomponent.class, BuyNowSubcomponent.class, MySkyDollarSubcomponent.class,
    BookingHistorySubcomponent.class, BuyNowSubcomponent.class, NotificationSettingSubcomponent.class,
    NotificationSubcomponent.class, MyReservationsSubcomponent.class, DetailReservationSubcomponent.class,
    MakeAReservationSubcomponent.class, DateSinglePickerSubcomponent.class , ConfirmReservationSubcomponent.class,
        DemoSubcomponent.class, DemoTwoSubcomponent.class, DemoThreeSubcomponent.class,
})
public abstract class ApplicationModule {

  static @Provides
  Context provideContext(App application) {
    return application.getApplicationContext();
  }

  static @Provides
  @Singleton
  ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
    return jobExecutor;
  }

  static @Provides
  @Singleton
  PostExecutionThread providePostExecutionThread(UIThread uiThread) {
    return uiThread;
  }

  static @Provides
  @Singleton
  DBManager provideDBManager(DbHelper dbHelper) {
    return new DBManagerImpl(dbHelper);
  }

  abstract @Binds
  NetworkManager bindNetworkManager(
      NetworkManagerImpl networkManager);

  @Binds
  abstract InternalStorageManager bindInternalStorageManager(
      InternalStorageManagerImpl internalStorageManager);
}
