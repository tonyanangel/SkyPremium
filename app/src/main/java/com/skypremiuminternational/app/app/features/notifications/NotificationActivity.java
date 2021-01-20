package com.skypremiuminternational.app.app.features.notifications;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.features.booking.history.BookingsHistoryActivity;
import com.skypremiuminternational.app.app.features.cart.ShoppingCartActivity;
import com.skypremiuminternational.app.app.features.estore.EstoreActivity;
import com.skypremiuminternational.app.app.features.estore.detail.EstoreDetailActivity;
import com.skypremiuminternational.app.app.features.faq.FaqActivity;
import com.skypremiuminternational.app.app.features.info.InfoActivity;
import com.skypremiuminternational.app.app.features.landing.LandingActivity;
import com.skypremiuminternational.app.app.features.membership_renewal.MembershipRenewalActivity;
import com.skypremiuminternational.app.app.features.memership_services.MembershipActivity;
import com.skypremiuminternational.app.app.features.notification_setting.NotificationSettingActivity;
import com.skypremiuminternational.app.app.features.notifications.dialog.DeleteNotificationDialog;
import com.skypremiuminternational.app.app.features.notifications.helper.MyButtonClickListener;
import com.skypremiuminternational.app.app.features.notifications.helper.MySwipeHelper;
import com.skypremiuminternational.app.app.features.profile.ProfileActivity;
import com.skypremiuminternational.app.app.features.profile.billingaddress.ManageBillingAddressActivity;
import com.skypremiuminternational.app.app.features.profile.edit_profile.EditProfileActivity;
import com.skypremiuminternational.app.app.features.profile.invite_friend.InviteFriendActivity;
import com.skypremiuminternational.app.app.features.profile.manage_credit_card.ManageCreditCardActivity;
import com.skypremiuminternational.app.app.features.profile.manage_delivery_address.ManageDeliveryAddressActivity;
import com.skypremiuminternational.app.app.features.profile.my_favourites.MyFavouritesActivity;
import com.skypremiuminternational.app.app.features.profile.my_orders.MyOrderActivity;
import com.skypremiuminternational.app.app.features.search.SearchActivity;
import com.skypremiuminternational.app.app.features.signin.SignInActivity;
import com.skypremiuminternational.app.app.features.splash.SplashActivity;
import com.skypremiuminternational.app.app.features.travel.detail.TravelDetailActivity;
import com.skypremiuminternational.app.app.internal.mvp.BaseActivity;
import com.skypremiuminternational.app.app.utils.Constants;
import com.skypremiuminternational.app.app.utils.listener.ItemClickListener;
import com.skypremiuminternational.app.app.utils.listener.MovePillarListener;
import com.skypremiuminternational.app.app.utils.listener.NotificationItemClickListener;
import com.skypremiuminternational.app.domain.models.notification.NotificationItem;
import com.skypremiuminternational.app.domain.models.notification.NotificationResponse;
import com.skypremiuminternational.app.domain.models.notification.NotificationItem;
import com.skypremiuminternational.app.domain.models.notification.SkyPayload;
import com.skypremiuminternational.app.domain.models.notification.UpdateDelItem;
import com.skypremiuminternational.app.domain.models.notification.UpdateReadItem;
import com.skypremiuminternational.app.push_notification.ChangeNotificationListener;
import com.skypremiuminternational.app.push_notification.SkyNotificationOpenHandler;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.AndroidInjection;

import static com.skypremiuminternational.app.app.App.getAppContext;
import static com.skypremiuminternational.app.app.App.isSendingNotification;
import static com.skypremiuminternational.app.app.features.navigation.NavigationDialogFragment.LINK_ABOUT;
import static com.skypremiuminternational.app.app.features.navigation.NavigationDialogFragment.LINK_BUZZ;
import static com.skypremiuminternational.app.app.features.navigation.NavigationDialogFragment.LINK_HOW_IT_WORKS;
import static com.skypremiuminternational.app.app.features.navigation.NavigationDialogFragment.LINK_MEMBER_BENEFITS;
import static com.skypremiuminternational.app.app.features.notifications.NotificationAdapter.READ;
import static com.skypremiuminternational.app.app.features.notifications.NotificationAdapter.UNREAD;
import static com.skypremiuminternational.app.app.utils.CommonUtils.YYYY_MM_DD_HH_MM;
import static com.skypremiuminternational.app.app.utils.CommonUtils.getGMTZero;


public class NotificationActivity extends BaseActivity<NotificationPresenter>
    implements NotificationView<NotificationPresenter> , ChangeNotificationListener {


  @BindView(R.id.rv_notification)
  RecyclerView rvNotification;
  @BindView(R.id.tvTitle_toolbar)
  TextView tvTitleToolbar;
  @BindView(R.id.tv_num_notifi)
  TextView tvNumNotifi;
  @BindView(R.id.tv_edit_notification)
  TextView tvEditNotification;
  @BindView(R.id.tvCategory_filter)
  TextView tvCategoryFilter;
  @BindView(R.id.tvSort_filter)
  TextView tvSortFilter;
  @BindView(R.id.tv_make_as_read)
  TextView tvMakeAsRead;
  @BindView(R.id.tv_delete_checked)
  TextView tvDeleteChecked;
  @BindView(R.id.ll_action_bar)
  LinearLayout llActionBar;
  @BindView(R.id.view_shadow)
  View viewShadow;
  @BindView(R.id.ck_all)
  CheckBox checkBoxAll;
  @BindView(R.id.ll_no_notification)
  LinearLayout llNoNotification;


  NotificationAdapter notifyAdapter;

  ProgressDialog progressDialog;

  public static final String FROM_KEY = "FROM";
  public static final String FROM_NOTIFICATION = "notification";
  public static final String SCHEME_KEY = "skypremium";

  String from = "";
  int countSelected = 0;
  int colorEnable;
  int colorDisable;

  public static void startMe(Context context) {
    Intent intent = new Intent(context, NotificationActivity.class);
    context.startActivity(intent);
  }
  public static void startMe(Context context,String from) {
      Intent intent = new Intent(context, NotificationActivity.class);
      intent.putExtra(FROM_KEY,from);
      intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
      context.startActivity(intent);
  }

  public static Intent startMePush(Context context,String from) {
      Intent intent = new Intent(context, NotificationActivity.class);
      intent.putExtra(FROM_KEY,from);
      intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
      return intent;
  }


  @Inject
  @Override
  public void injectPresenter(NotificationPresenter presenter) {
    super.injectPresenter(presenter);
  }

  @SuppressLint("ResourceType")
  @Override
  public void onCreate(Bundle savedInstanceState) {
    AndroidInjection.inject(this);
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_notification);
    ButterKnife.bind(this);

    presenter.checkLogin();

    NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    manager.cancelAll();
    presenter.initNotification(getAppContext(),this);
    // get from where
    try{
      from = getIntent().getData().getScheme();
    }catch (NullPointerException ex){}

    if(!TextUtils.isEmpty(from)){
      from = getIntent().getStringExtra(FROM_KEY);
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      colorEnable = getColor(R.color.colorAccent);
      colorDisable = getColor(R.color.brightGrey);
    } else {
      colorEnable = Color.parseColor(getString(R.color.colorAccent));
      colorDisable = Color.parseColor(getString(R.color.brightGrey));
    }

    progressDialog = new ProgressDialog(this);
    progressDialog.setCanceledOnTouchOutside(false);

    setTitleToolbar(R.string.notifications);


    setRecyclerView();

    initData();

    logicLayoutNotification();

    setNumberUnread();

    setupFilterBar();


  }

  private void setupFilterBar() {

    tvCategoryFilter.setText(String.format("REFINE: %s", Constants.refineArrNotifi[0]));
    tvSortFilter.setText(String.format("Sort By: %s", Constants.sortingArrNotifi[0]));
  }

  private void setNumberUnread() {
    if (hasNotification(notifyAdapter.getData())) {
      int numberUnread = 0;
      for (NotificationItem item : notifyAdapter.getData()) {
        if (!item.getIsRead()) {
          numberUnread++;
        }
      }
      tvNumNotifi.setText(String.format("(%d)", numberUnread));

    } else
      tvNumNotifi.setText("");

  }


  /**
   * WIKI Toan Tran - setup title on toolbar
   *
   * @param notifications
   */
  private void setTitleToolbar(int notifications) {
    tvTitleToolbar.setText(notifications);
  }


  /**
   * WIKI Toan Tran - prepare data to show and handle showing logic.
   */
  private void initData() {
    //initList Data
    /*if (!isSendingNotification) {
      presenter.saveNotificarion();
    }*/

    presenter.initNotificationLocal();
  }

  private boolean hasNotification(List<NotificationItem> listData) {
    if (listData != null) {
      if (listData.size() > 0) {
        return true;
      } else {
        return false;
      }

    } else {
      return false;
    }
  }

  /**
   * WIKI Toan Tran -  handle logic layout
   */
  private void logicLayoutNotification() {
    if (hasNotification(notifyAdapter.getData())) {

      rvNotification.setVisibility(View.VISIBLE);
      llNoNotification.setVisibility(View.GONE);
    } else {
      rvNotification.setVisibility(View.GONE);
      llNoNotification.setVisibility(View.VISIBLE);
    }


  }


  private void setRecyclerView() {
    //setup recyclerView notification
    notifyAdapter = new NotificationAdapter(this);
    rvNotification.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    MySwipeHelper swipeHelper = new MySwipeHelper(this, rvNotification, 300) {
      @SuppressLint("ResourceType")
      @Override
      public void instantiateMyButton(RecyclerView.ViewHolder viewHolder, List<MyButton> buffer) {
        int color;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
          color = getColor(R.color.colorAccent);
        } else {
          color = Color.parseColor(getString(R.color.colorAccent));
        }
        buffer.add(new MyButton(NotificationActivity.this,
            "DELETE",
            45,
            0,
            color,
            new MyButtonClickListener() {
              @Override
              public void onClick(int pos) {
                /*new android.app.AlertDialog.Builder(NotificationActivity.this).setMessage("Are you sure you wish to delete this notification?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                      @Override
                      public void onClick(DialogInterface dialog, int which) {
                        List<UpdateDelItem> listDel = new ArrayList<>();
                        UpdateDelItem delItem = new UpdateDelItem();
                        delItem.setId((notifyAdapter.getData().get(pos).getId()));
                        listDel.add(delItem);
                        //presenter.makeDeleteNotifications(listDel);

                        notifyAdapter.removeItem(pos);

                        presenter.saveNotificationLocal(notifyAdapter.getData());


                        setNumberUnread();
                        logicLayoutNotification();
                      }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                  }
                }).show();*/
                new DeleteNotificationDialog(
                    NotificationActivity.this,
                    getResources().getString(R.string.are_you_sure_you_wish_to_delete_this_notification), new DeleteNotificationDialog.OnClickConfirmDeleteListener() {
                  @Override
                  public void onClickNo(DeleteNotificationDialog dialog) {
                  }

                  @Override
                  public void onClickYes(DeleteNotificationDialog dialog) {
                    List<UpdateDelItem> listDel = new ArrayList<>();
                    UpdateDelItem delItem = new UpdateDelItem();
                    delItem.setId((notifyAdapter.getData().get(pos).getId()));
                    listDel.add(delItem);
                    //presenter.makeDeleteNotifications(listDel);

                    notifyAdapter.removeItem(pos);

                    presenter.saveNotificationLocal(notifyAdapter.getData());


                    setNumberUnread();
                    logicLayoutNotification();
                  }
                }).show();
              }
            }));
      }
    };
    notifyAdapter.setItemClickListener(new NotificationItemClickListener<NotificationItem>() {
      @SuppressLint("ResourceAsColor")
      @Override
      public void onItemClicked(NotificationItem item, int position) {
        if (!notifyAdapter.isActionMode && !item.getIsRead()) {
         /* String stringDate = getGMTZero(YYYY_MM_DD_HH_MM);
          List<UpdateReadItem> listRead = new ArrayList<>();
          UpdateReadItem readItem = new UpdateReadItem();
          readItem.setId(item.getId());
          readItem.setReadTime(stringDate);
          listRead.add(readItem);
          //presenter.makeReadSingle(listRead, item);

          if (notifyAdapter.getData().get(position).getNotificationId().equals(item.getNotificationId())) {
            notifyAdapter.getData().get(position).setIsRead(READ);
            notifyAdapter.notifyItemChanged(position);
          }*/
          presenter.makeAsReadOneLocal(item);
          if (notifyAdapter.getData().get(position).getNotificationId().equals(item.getNotificationId())) {
            notifyAdapter.getData().get(position).setIsRead(READ);
            notifyAdapter.notifyItemChanged(position);
          }
          notifyAdapter.notifyItemChanged(position);

          setNumberUnread();
          if(!TextUtils.isEmpty(item.getLaunchUrl())){
            renderGoToDetails(item);
          }
        } else if (!notifyAdapter.isActionMode) {
          /*String stringDate = getGMTZero(YYYY_MM_DD_HH_MM);
          List<UpdateReadItem> listRead = new ArrayList<>();
          UpdateReadItem readItem = new UpdateReadItem();
          readItem.setId(item.getId());
          readItem.setReadTime(stringDate);
          listRead.add(readItem);
          */
          setNumberUnread();
          if(!TextUtils.isEmpty(item.getLaunchUrl())){
            renderGoToDetails(item);
          }
          //presenter.makeReadSingle(listRead, item);

        } else {
          if (item.isChecked()) {
            item.setChecked(false);
            countSelected--;
          } else {
            item.setChecked(true);
            countSelected++;
          }
          notifyAdapter.notifyItemChanged(position);
          if(countSelected<=0){

            tvMakeAsRead.setBackgroundColor(colorDisable);
            tvDeleteChecked.setBackgroundColor(colorDisable);
            tvMakeAsRead.setEnabled(false);
            tvDeleteChecked.setEnabled(false);
          }else {
            tvMakeAsRead.setBackgroundColor(colorEnable);
            tvDeleteChecked.setBackgroundColor(colorEnable);
            tvMakeAsRead.setEnabled(true);
            tvDeleteChecked.setEnabled(true);
          }
        }
      }

    });

    rvNotification.setAdapter(notifyAdapter);
  }

  @OnClick(R.id.tv_edit_notification)
  void onClickEdit() {
    if (!hasNotification(notifyAdapter.getData())) {
      llActionBar.setVisibility(View.GONE);
      viewShadow.setVisibility(View.GONE);
      return;
    }

    //==== show action bar
    if (notifyAdapter.isActionMode) {
      exitActionMode();
    } else {
      enterActionMode();
    }
  }

  @SuppressLint("ResourceAsColor")
  void enterActionMode() {
    //==== set uncheck all list
    notifyAdapter.setCheckAllFlag(false);

    notifyAdapter.setActionMode(true);
    llActionBar.setVisibility(View.VISIBLE);
    viewShadow.setVisibility(View.VISIBLE);

    tvEditNotification.setText(R.string.done);

    checkBoxAll.setChecked(false);
    tvMakeAsRead.setBackgroundColor(colorDisable);
    tvDeleteChecked.setBackgroundColor(colorDisable);
    tvMakeAsRead.setEnabled(false);
    tvDeleteChecked.setEnabled(false);
    countSelected = 0;
  }

  void exitActionMode() {
    //==== set uncheck all list
    notifyAdapter.setCheckAllFlag(false);

    notifyAdapter.setActionMode(false);
    llActionBar.setVisibility(View.GONE);
    viewShadow.setVisibility(View.GONE);

    tvEditNotification.setText(R.string.edit);

    checkBoxAll.setChecked(false);
    countSelected = 0;

  }

  @OnClick(R.id.cl_select_all)
  void onClickSelectAll() {
    checkBoxAll.setChecked(!checkBoxAll.isChecked());

    notifyAdapter.toggleSelectAll();

    if(notifyAdapter.isCheckAll()){
      countSelected = notifyAdapter.getItemCount();

      tvMakeAsRead.setBackgroundColor(colorEnable);
      tvDeleteChecked.setBackgroundColor(colorEnable);
      tvMakeAsRead.setEnabled(true);
      tvDeleteChecked.setEnabled(true);
    }else {
      countSelected = 0;

      tvMakeAsRead.setBackgroundColor(colorDisable);
      tvDeleteChecked.setBackgroundColor(colorDisable);
      tvMakeAsRead.setEnabled(false);
      tvDeleteChecked.setEnabled(false);
    }
  }

  @OnClick(R.id.tv_delete_checked)
  void onClickDeleteChecked() {


    /*new android.app.AlertDialog.Builder(this).setMessage("Are you sure you wish to delete these notifications?")
        .setCancelable(false)
        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            List<NotificationItem> listDelItem = new ArrayList<>();
            for (NotificationItem item : notifyAdapter.getData()) {
              if (item.isChecked()) {

                NotificationItem deltem = new NotificationItem();
                deltem.setId(item.getId());
                listDelItem.add(deltem);

              }

            }
            //presenter.makeDeleteNotifications(listDelItem);


            notifyAdapter.removeListChecked();
            presenter.saveNotificationLocal(notifyAdapter.getData());
            logicLayoutNotification();
            llActionBar.setVisibility(View.GONE);
            exitActionMode();
            setNumberUnread();
            rvNotification.smoothScrollToPosition(0);
          }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialogInterface, int i) {
        dialogInterface.dismiss();
      }
    }).show();*/
    new DeleteNotificationDialog(
        NotificationActivity.this,
        getResources().getString(R.string.are_you_sure_you_wish_to_delete_these_notification), new DeleteNotificationDialog.OnClickConfirmDeleteListener() {
      @Override
      public void onClickNo(DeleteNotificationDialog dialog) {
      }

      @Override
      public void onClickYes(DeleteNotificationDialog dialog) {
        List<NotificationItem> listDelItem = new ArrayList<>();
        for (NotificationItem item : notifyAdapter.getData()) {
          if (item.isChecked()) {

            NotificationItem deltem = new NotificationItem();
            deltem.setId(item.getId());
            listDelItem.add(deltem);

          }

        }
        //presenter.makeDeleteNotifications(listDelItem);


        notifyAdapter.removeListChecked();
        presenter.saveNotificationLocal(notifyAdapter.getData());
        logicLayoutNotification();
        llActionBar.setVisibility(View.GONE);
        exitActionMode();
        setNumberUnread();
        rvNotification.smoothScrollToPosition(0);
      }
    }).show();

  }

  @OnClick(R.id.ivNavigation_toolbar)
  void onClickBack() {
    LandingActivity.startMe(this);
    finish();
  }

  @Override
  public void onBackPressed() {
    if (notifyAdapter.isActionMode) {
      exitActionMode();
      return;
    }


    LandingActivity.startMe(this);
    finish();

  }

  @OnClick(R.id.tvCategory_filter)
  public void onClickCategoryFilter() {
    new AlertDialog.Builder(this).setTitle("REFINE :")
        .setItems(Constants.refineArrNotifi, (dialog, item) -> {
          tvCategoryFilter.setText(String.format("REFINE: %s", Constants.refineArrNotifi[item]));
        })
        .setNegativeButton("Cancel", null)
        .show();
  }

  @OnClick(R.id.tvSort_filter)
  public void onClickSortFilter() {
    new AlertDialog.Builder(this).setTitle("SORT BY:")
        .setItems(Constants.sortingArrNotifi, (dialog, item) -> {
          tvSortFilter.setText(String.format("Sort By: %s", Constants.sortingArrNotifi[item]));
        })
        .setNegativeButton("Cancel", null)
        .show();
  }

  @OnClick(R.id.tv_make_as_read)
  public void onClickMakeAsRead() {

    llActionBar.setVisibility(View.GONE);
/*
    String stringDate = getGMTZero(YYYY_MM_DD_HH_MM);

    List<UpdateReadItem> listReadItem = new ArrayList<>();

    for (NotificationItem item : notifyAdapter.getData()) {
      if (item.isChecked()) {

        UpdateReadItem readItem = new UpdateReadItem();
        readItem.setId(item.getId());
        readItem.setReadTime(stringDate);
        listReadItem.add(readItem);
        item.setIsRead(READ);

      }
    }*/
    //presenter.makeReadNotifications(listReadItem);

    presenter.makeAsReadMutilLocal(notifyAdapter.getData());

    notifyAdapter.notifyDataSetChanged();
    exitActionMode();

    setNumberUnread();
  }

  /**
   * render notification after call CRM API
   */

  @Override
  public void renderListNotification(List<NotificationItem> list) {
    notifyAdapter.setData(list);
    logicLayoutNotification();
    setNumberUnread();
    hideDialogLoading();
  }

  @Override
  public void showDialogLoading() {
    progressDialog.setMessage(getString(R.string.loading));
    progressDialog.show();
  }

  @Override
  public void hideDialogLoading() {
    if (progressDialog != null && progressDialog.isShowing()) {
      progressDialog.dismiss();
    }

  }

  @Override
  protected void onRestart() {
    super.onRestart();
    /*if (!isSendingNotification) {
      presenter.saveNotificarion();
    }*/
    presenter.initNotificationLocal();
  }


  @Override
  public void renderGoToDetails(NotificationItem notificationItem) {
    /*if (!TextUtils.isEmpty(item.getLaunchUrl())) {
      Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(item.getLaunchUrl()));
      NotificationActivity.this.startActivity(browserIntent);
    } else if (!TextUtils.isEmpty(item.getAdditionalData().getRedirect())) {
      if (item.getAdditionalData().getRedirect().equalsIgnoreCase("e-store")) {
        if (!TextUtils.isEmpty(item.getAdditionalData().getSku())) {
          EstoreDetailActivity.startMe(this, FROM_NOTIFICATION, item.getAdditionalData().getSku());
        } else if (!TextUtils.isEmpty(item.getAdditionalData().getFilter())) {
          EstoreActivity.startMe(this, item.getAdditionalData().getFilter());
        } else {
          EstoreActivity.startMe(this);
        }
      } else {
        if (!TextUtils.isEmpty(item.getAdditionalData().getSku())) {
          TravelDetailActivity.startMe(this, FROM_NOTIFICATION, item.getAdditionalData().getSku());
        } else if (!TextUtils.isEmpty(item.getAdditionalData().getFilter())) {
          LandingActivity.startMe(this, FROM_NOTIFICATION, item.getAdditionalData().getRedirect(), item.getAdditionalData().getFilter());
        } else {
          LandingActivity.startMe(this, FROM_NOTIFICATION, item.getAdditionalData().getRedirect());
        }
      }
    }else if(!TextUtils.isEmpty(item.getAdditionalData().getKeyword())){
      SearchActivity.startMeWithKey(this, item.getAdditionalData().getKeyword());
    }*/

    String launchUrl = notificationItem.getLaunchUrl().trim();

    String scheme = "";
    String host = "";
    String query = "";
    String page = "";
    Map<String,String> mapQuery = new HashMap<>();




    if(launchUrl!=null){
      URI uri = null;
      try {
        uri =  new URI(launchUrl);
      } catch (URISyntaxException e) {
        e.printStackTrace();
      }

      try{
        scheme = uri.getScheme();
      }catch (NullPointerException ex){

      }
      try{
        host = uri.getHost();
      }catch (NullPointerException ex){

      }

      try{
        query = uri.getQuery();
        String[] arrayQuery = query.split("&");
        for(String item :  arrayQuery){
          String[] arrayMap = item.split("=");
          if(arrayMap.length>=2){
            mapQuery.put(arrayMap[0],arrayMap[1]);
          }
        }
      }catch (NullPointerException ex){

      }

      page = mapQuery.get("page") == null ? "" : mapQuery.get("page");

      if(!scheme.equalsIgnoreCase(SCHEME_KEY)){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(launchUrl));
        NotificationActivity.this.startActivity(browserIntent);
        return;
      }

      if (!TextUtils.isEmpty(host)) {
        if (host.equalsIgnoreCase("estore")) {
          if (mapQuery.get("sku")!=null&&!TextUtils.isEmpty(mapQuery.get("sku"))) {
            presenter.checkDetailItemSimple(host,mapQuery.get("sku"),mapQuery.get("filter"),mapQuery.get("sortby"),page);
          } else if (mapQuery.get("filter")!=null&&!TextUtils.isEmpty(mapQuery.get("filter"))
              ||(mapQuery.get("sortby")!=null && !TextUtils.isEmpty(mapQuery.get("sortby")))) {
            EstoreActivity.startMe(this,FROM_NOTIFICATION, mapQuery.get("filter"),mapQuery.get("sortby"));
          } else {
            EstoreActivity.startMe(this);
          }
        }else if(host.equalsIgnoreCase("search")){
          if (mapQuery.get("keyword")!=null&&!TextUtils.isEmpty(mapQuery.get("keyword"))
              ||(mapQuery.get("sortby")!=null && !TextUtils.isEmpty(mapQuery.get("sortby")))) {
            SearchActivity.startMeWithKey(this, mapQuery.get("keyword"),mapQuery.get("sortby"));
          }else {
            SearchActivity.startMe(this);
          }
        } else if(isPillarValid(host)) {
          if (mapQuery.get("sku")!=null && !TextUtils.isEmpty(mapQuery.get("sku"))) {
            presenter.checkDetailItemSimple(host,mapQuery.get("sku"),mapQuery.get("filter"),mapQuery.get("sortby"),page);
          } else if ((mapQuery.get("filter")!=null && !TextUtils.isEmpty(mapQuery.get("filter")))
              ||(mapQuery.get("sortby")!=null && !TextUtils.isEmpty(mapQuery.get("sortby")))) {
            LandingActivity.startMe(this, FROM_NOTIFICATION, host, mapQuery.get("filter"),mapQuery.get(("sortby")));

          }else {
            LandingActivity.startMe(this, FROM_NOTIFICATION, host,"","");

          }
        }else if(host.equalsIgnoreCase("home")){
          LandingActivity.startMe(this);
          finish();
        }else if(host.equalsIgnoreCase("myprofile")){
          if (page != null) {
            switch (page){
              case "myfavorites" :{
                MyFavouritesActivity.startMe(this);
                break;
              }
              case "editprofile" :{
                EditProfileActivity.startMe(this);
                break;

              }
              case "mybookings" :{
                BookingsHistoryActivity.start(this);
                break;

              }
              case "myorders" :{
                MyOrderActivity.startMe(this);
                break;

              }
              case "managedeliveryaddresses" :{
                ManageDeliveryAddressActivity.startMe(this);
                break;

              }
              case "managebillingaddresses" :{
                ManageBillingAddressActivity.start(this);
                break;

              }
              case "managecreditcards" :{
                ManageCreditCardActivity.startMe(this);
                break;

              }
              case "inviteafriend" :{
                presenter.goToInviteFriend();
                break;

              }
              case "notificationsettings" :{
                NotificationSettingActivity.startMe(this);
                break;

              }
              default: {
                ProfileActivity.startMe(this);
              }
            }
          }else {
            ProfileActivity.startMe(this);
          }
        }else if(host.equalsIgnoreCase("footer")){
          if (page != null) {
            switch (page){
              case "about" :{
                InfoActivity.start(this,LINK_ABOUT , "About");
                break;
              }
              case "howitworks" :{
                InfoActivity.start(this,LINK_HOW_IT_WORKS , "How it works");
                break;

              }
              case "memberbenefits" :{
                InfoActivity.start(this,LINK_MEMBER_BENEFITS , "Member Benefits");
                break;

              }
              case "buzz" :{
                InfoActivity.start(this,LINK_BUZZ , "Buzz");
                break;

              }
              case "faq" :{
                FaqActivity.startMe(this);
                break;

              }
            }
          }else {
            LandingActivity.startMe(this);
            finish();
          }
        }else if(host.equals("membershipservices")){
          MembershipActivity.startMe(this);
        }else if(host.equalsIgnoreCase("cart")){
          ShoppingCartActivity.start(this);
          finish();
        }else {

        }
      }else{
        LandingActivity.startMe(this);
        finish();
      }
    }
  }

  @Override
  public void onExistEstoreProduct(String redirect,String sku) {
    if(redirect.equalsIgnoreCase("estore")){
      EstoreDetailActivity.startMe(this, FROM_NOTIFICATION, sku);
    }else {
      TravelDetailActivity.startMe(this, FROM_NOTIFICATION, sku);
    }
  }

  @Override
  public void onNonExistEstoreProduct(String redirect,String filter,String sortBy,String page) {
    if(redirect.equalsIgnoreCase("estore")){
      if(!TextUtils.isEmpty(filter)||!TextUtils.isEmpty(sortBy)){
        EstoreActivity.startMe(this,FROM_NOTIFICATION, filter,sortBy);
      }else {
        EstoreActivity.startMe(this);
      }
    }else if(isPillarValid(redirect)) {
      if(!TextUtils.isEmpty(filter)
          ||!TextUtils.isEmpty(sortBy)){
        LandingActivity.startMe(this, FROM_NOTIFICATION, redirect, filter,sortBy);
      }else {
        LandingActivity.startMe(this, FROM_NOTIFICATION, redirect, "","");
      }
    }
  }

  @Override
  public void goToInviteFriend(String code, String salutation, String firstname, String lastName, String description, String imgBannerUrl) {
    InviteFriendActivity.startMe(this, code, salutation, firstname, lastName, description,imgBannerUrl);
  }

  @Override
  public void renderChangeListNotification(List<NotificationItem> items) {
    runOnUiThread(new Runnable() {
      @Override
      public void run() {
        notifyAdapter.setData(items);
        int numberUnread = 0;
        if (hasNotification(notifyAdapter.getData())) {
          for (NotificationItem item : notifyAdapter.getData()) {
            if (!item.getIsRead()) {
              numberUnread++;
            }
          }
        }
        tvNumNotifi.setText(String.format("(%d)", numberUnread));
        logicLayoutNotification();
      }
    });
  }

  boolean isPillarValid(String redirect){
    String[] arrayredirect = {"estore","wine-dine","wellness","shopping","travel"};
    for(String item : arrayredirect){
      if(item.equalsIgnoreCase(redirect)){
        return true;
      }
    }
    return false;
  }

  @Override
  public void renderNumberNotification() {

  }

  @Override
  public void renderListNotification() {
    if(presenter!=null){
      presenter.changeNotificationLocal();
    }
  }

  @Override
  public void forceLogin() {
    finish();
    SignInActivity.startMe(this);
  }
}
