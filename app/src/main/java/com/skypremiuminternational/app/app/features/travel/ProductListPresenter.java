package com.skypremiuminternational.app.app.features.travel;

import android.location.Location;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.internal.mvp.BaseFragmentPresenter;
import com.skypremiuminternational.app.app.internal.mvp.BaseSubscriber;
import com.skypremiuminternational.app.app.utils.Constants;
import com.skypremiuminternational.app.app.utils.Validator;
import com.skypremiuminternational.app.data.network.request.CaregoryDetailsRequest;
import com.skypremiuminternational.app.data.network.request.DetailsRequest;
import com.skypremiuminternational.app.data.network.request.FeatureProductRequest;
import com.skypremiuminternational.app.data.network.request.GetFavouriteRequest;
import com.skypremiuminternational.app.data.network.request.ProductListRequest;
import com.skypremiuminternational.app.domain.interactor.cart.GetCartCount;
import com.skypremiuminternational.app.domain.interactor.category.GetCategory;
import com.skypremiuminternational.app.domain.interactor.category.GetCategoryDetails;
import com.skypremiuminternational.app.domain.interactor.details.GetDetails;
import com.skypremiuminternational.app.domain.interactor.details.GetRecommendations;
import com.skypremiuminternational.app.domain.interactor.ean.SearchProperties;
import com.skypremiuminternational.app.domain.interactor.favourite.AddToFavourite;
import com.skypremiuminternational.app.domain.interactor.favourite.GetFavourites;
import com.skypremiuminternational.app.domain.interactor.favourite.RemoveFromFavourite;
import com.skypremiuminternational.app.domain.interactor.hunry_go_where.GetOutletResevation;
import com.skypremiuminternational.app.domain.interactor.shopping.GetFeatureShopping;
import com.skypremiuminternational.app.domain.interactor.shopping.GetShopping;
import com.skypremiuminternational.app.domain.interactor.travel.GetFeatureTravel;
import com.skypremiuminternational.app.domain.interactor.travel.GetTravel;
import com.skypremiuminternational.app.domain.interactor.wellness.GetFeatureWellness;
import com.skypremiuminternational.app.domain.interactor.wellness.GetWellness;
import com.skypremiuminternational.app.domain.interactor.wine.GetFeatureWineAndDine;
import com.skypremiuminternational.app.domain.interactor.wine.GetWineAndDine;
import com.skypremiuminternational.app.domain.models.category.CategoryDetailsResponse;
import com.skypremiuminternational.app.domain.models.category.CategoryResponse;
import com.skypremiuminternational.app.domain.models.category.ChildData;
import com.skypremiuminternational.app.domain.models.category.ChildData_;
import com.skypremiuminternational.app.domain.models.details.DetailsItem;
import com.skypremiuminternational.app.domain.models.ean.Child;
import com.skypremiuminternational.app.domain.models.ean.Property;
import com.skypremiuminternational.app.domain.models.favourite.FavouriteListResponse;
import com.skypremiuminternational.app.domain.models.favourite.FavouriteResponse;
import com.skypremiuminternational.app.domain.models.feature.FeatureProduct;
import com.skypremiuminternational.app.domain.models.hunry_go_where.OutletItem;
import com.skypremiuminternational.app.domain.models.wellness.CustomAttributesItem;
import com.skypremiuminternational.app.domain.models.wellness.ItemsItem;
import com.skypremiuminternational.app.domain.models.wellness.ProductListResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.inject.Inject;

import rx.Observable;
import rx.SingleSubscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

/**
 * Created by johnsonmaung on 9/22/17.
 */

public class ProductListPresenter extends BaseFragmentPresenter<ProductListView> {

  public final static int DEFAULT_ROOM_COUNT = 1;
  public final static int DEFAULT_ADULT_COUNT = 2;

  private int adultCount = DEFAULT_ADULT_COUNT;
  private int roomCount = DEFAULT_ROOM_COUNT;

  private ArrayList<Child> children = new ArrayList<>();

  private final GetCategory getCategory;

  private final GetTravel getTravel;

  private final GetFeatureTravel getFeatureTravel;

  private final GetCategoryDetails getCategoryDetails;
  private final RemoveFromFavourite removeFromFavourite;
  private final AddToFavourite addToFavourite;
  private final GetFavourites getFavourites;
  private final GetFeatureWellness getFeatureWellness;
  private final GetFeatureShopping getFeatureShopping;
  private final GetFeatureWineAndDine getFeatureWineAndDine;
  private final GetWellness getWellness;
  private final GetShopping getShopping;
  private final GetWineAndDine getWineAndDine;
  private final GetCartCount getCartCount;
  private final SearchProperties searchProperties;
  private final GetRecommendations getRecommendations;
  private final GetOutletResevation getOutletResevation;
  private GetDetails getDetails;
  private String pillarID;

  private TravelViewState viewState;

  private CompositeSubscription compositeSubscription = new CompositeSubscription();
  private List<CalendarDay> dates;

  @Inject
  public ProductListPresenter(SearchProperties searchProperties, GetCategory getCategory,
                              GetCategoryDetails getCategoryDetails, GetFavourites getFavourites,
                              AddToFavourite addToFavourite, RemoveFromFavourite removeFromFavourite,
                              GetTravel getTravel, GetWineAndDine getWineAndDine, GetShopping getShopping,
                              GetWellness getWellness, GetFeatureTravel getFeatureTravel,
                              GetFeatureWineAndDine getFeatureWineAndDine,
                              GetFeatureShopping getFeatureShopping, GetFeatureWellness getFeatureWellness,
                              GetCartCount getCartCount, GetRecommendations getRecommendations, GetDetails getDetails,
                              GetOutletResevation getOutletResevation
  ) {
    this.getCartCount = getCartCount;

    viewState = TravelViewState.builder()
            .error(null)
            .isLoading(false)
            .isSuccess(false)
            .message(null)
            .dataList(null)
            .featureList(null)
            .category(null)
            .categoryDetails(null)
            .build();

    this.searchProperties = searchProperties;
    this.getWineAndDine = getWineAndDine;
    this.getShopping = getShopping;
    this.getWellness = getWellness;
    this.getFeatureWineAndDine = getFeatureWineAndDine;
    this.getFeatureShopping = getFeatureShopping;
    this.getFeatureWellness = getFeatureWellness;
    this.getFavourites = getFavourites;
    this.addToFavourite = addToFavourite;
    this.removeFromFavourite = removeFromFavourite;
    this.getCategory = getCategory;
    this.getTravel = getTravel;
    this.getFeatureTravel = getFeatureTravel;
    this.getCategoryDetails = getCategoryDetails;
    this.getRecommendations = getRecommendations;
    this.getOutletResevation = getOutletResevation;
    this.getDetails = getDetails;
  }

  @Override
  public void onStop() {
    compositeSubscription.clear();
    super.onStop();
  }

  void searchProperties(String query, String arrival, String departure, String sortField,
                        Location location) {

    if (!Validator.isTextValid(query)) {
      getView().render(new Exception("Invalid query"));
      return;
    }

    String checkIn = formatDate(arrival);
    if (checkIn == null) {
      getView().render(new Exception("Invalid Check-in date"));
      return;
    }

    String checkOut = formatDate(departure);
    if (checkOut == null) {
      getView().render(new Exception("Invalid Check-out date"));
      return;
    }

    String mQuery = query.replaceAll("[^a-zA-Z0-9 _]", "") + "&return=property_id&size=250";

    String sort = buildSort(sortField, location);
    if (sort != null) {
      mQuery += sort;
    }
    getView().showLoading();

    add(searchProperties.execute(new SingleSubscriber<List<Property>>() {
      @Override
      public void onSuccess(List<Property> properties) {
        getView().hideLoading();
        // TODO remove this filter method once EAN fixes duplicate values
        getView().render(removeDuplicateProperties(properties));
      }

      @Override
      public void onError(Throwable error) {
        error.printStackTrace();
        getView().hideLoading();
        getView().renderPropertyError(error);
      }
    }, new SearchProperties.Params(mQuery, checkIn, checkOut,
            "SG", roomCount, adultCount, children)));
  }

  /**
   * This removes duplicates by
   * 1. Adding the elements (containing duplicates) into a LinkedHashSet
   * 2. LinkedHashSet can only hold unique elements. Duplicates are skipped
   * 3. Clear List
   * 4. Add unique elements from LinkedHashSet back into List. Return List
   */
  private List<Property> removeDuplicateProperties(List<Property> properties) {

    // LinkedHashSet keeps unique elements & maintains order of elements
    Set<Property> set = new LinkedHashSet<>();

    for (Property property : properties) {
      set.add(property);
    }

    properties.clear();

    for (Property property : set) {
      properties.add(property);
    }

    return properties;
  }

  private String buildSort(String sortField, Location location) {
    switch (sortField) {
      case Constants.EAN_SORT_A_Z:
        return "&sort=name asc";
      case Constants.EAN_SORT_Z_A:
        return "&sort=name desc";
      case Constants.EAN_SORT_LOCATION:

        return "&expr.distance=haversin("
                + location.getLatitude()
                + ","
                + location.getLongitude()
                + ",location.latitude,location.longitude)"
                + "&sort=distance asc";
      default: // no value for Constants.EAN_SORT_POPULAR
        return null;
    }
  }
  public void getDetailsItem(final String sku) {
    onShowLoading();

    DetailsRequest detailsRequest = new DetailsRequest(sku,"");

    Subscription subscription = getDetails.execute(new BaseSubscriber<DetailsItem>() {
      @Override
      public void onSuccess(DetailsItem response) {
        getView().renderGetDetailToGoEstore(response);
      }

      @Override
      public void onError(Throwable error) {
        super.onError(error);
        getView().render(viewState);
        getView().hideLoading();
        getView().showErrorMsg(R.string.text_err_get_details);
      }
    }, detailsRequest);
    compositeSubscription.add(subscription);
  }

  private String formatDate(String time) {

    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    SimpleDateFormat dateFormat =
            new SimpleDateFormat(Constants.BOOKING_DATE_FORMAT, Locale.getDefault());
    try {
      Date date = dateFormat.parse(time);
      return df.format(date);
    } catch (ParseException e) {
      return null;
    }
  }

  void getProductList(String category_id, String field, String direction,String isthetime,String pillarName,int pillarCurrentId) {
    ProductListRequest request = new ProductListRequest(category_id, field, direction,isthetime);
    onShowLoading();
    add(getProductObservable(request)
            .zipWith(getFavourites.asObservable(GetFavouriteRequest.getAll()),
                    (productListResponse, favouriteListResponses) -> {
                      updateViewState(favouriteListResponses);
                      for (ItemsItem item : productListResponse.getItems()) {
                        setAsFavIfNeeded(item, favouriteListResponses);
                      }
                      return productListResponse;
                    })
            .subscribe(productListResponse -> {

              addCategoryNames(productListResponse.getItems(), viewState.category(),pillarName,pillarCurrentId);

              viewState = TravelViewState.builder()
                      .error(null)
                      .isLoading(false)
                      .isSuccess(true)
                      .message("1")
                      .dataList(productListResponse)
                      .featureList(viewState.featureList())
                      .category(viewState.category())
                      .favouriteList(viewState.favouriteList())
                      .categoryDetails(viewState.categoryDetails())
                      .build();

              ProductListFragment.filter="";
              getView().render(viewState);
            }, throwable -> {
              viewState = TravelViewState.builder()
                      .error(new Exception("Failed to load product list"))
                      .isLoading(false)
                      .isSuccess(false)
                      .message("")
                      .dataList(viewState.dataList())
                      .featureList(viewState.featureList())
                      .category(viewState.category())
                      .favouriteList(viewState.favouriteList())
                      .categoryDetails(viewState.categoryDetails())
                      .build();

              ProductListFragment.filter="";
              getView().render(viewState);
            }));
  }

  private void updateViewState(List<FavouriteListResponse> favouriteListResponses) {
    viewState = TravelViewState.builder()
            .error(viewState.error())
            .isLoading(viewState.isLoading())
            .isSuccess(viewState.isSuccess())
            .message(viewState.message())
            .dataList(viewState.dataList())
            .featureList(viewState.featureList())
            .category(viewState.category())
            .favouriteList(favouriteListResponses)
            .categoryDetails(viewState.categoryDetails())
            .build();
  }

  private void setAsFavIfNeeded(ItemsItem item,
                                List<FavouriteListResponse> favouriteListResponses) {
    for (FavouriteListResponse fav : favouriteListResponses) {
      if (fav.getProductId().equals(item.getId() + "")) {
        item.setFavourite(true);
        return;
      }
    }
  }

  private void addCategoryNames(List<ItemsItem> itemsItems, CategoryResponse categories,String pillerName,int pillarCurrentId) {
    for (ItemsItem item : itemsItems) {
      String categoryName = getCategoryName(categories.getChildrenData(), item,pillerName,pillarCurrentId);
      item.setCategoryName(categoryName);
    }
  }

  private String getCategoryName(List<ChildData> categories, ItemsItem item,String pillerName,int pillarCurrentId) {
    for (CustomAttributesItem attributesItem : item.getCustomAttributes()) {
      if (attributesItem.getAttributeCode().equals("category_ids")) {
        Object value = attributesItem.getValue();
        try {
          StringBuilder subCatBuilder = new StringBuilder();
          List<String> ids = (List<String>) value;
          for (String id : ids) {
            for (ChildData pillar : categories) {
              if (pillar.getId()==pillarCurrentId) {
                for (ChildData_ subCat : pillar.getChildrenData()) {
                  if (id.equals("" + subCat.getId())) {
                    subCatBuilder.append(subCat.getName());
                    subCatBuilder.append(", ");
                  }
                }
              }
                item.setPillarName(pillerName);
                item.setPillarId(id);
            }
          }
          String subCategories = subCatBuilder.toString();
          if (subCategories.endsWith(", ")) {
            subCategories = subCategories.substring(0, subCategories.lastIndexOf(", "));
          }
          return subCategories;
        } catch (Exception e) {
          Timber.e(e);
          return "";
        }
      }
    }
    return "";
  }

  public void getCategoryDetails(final String category_id,String pillarName) {

    CaregoryDetailsRequest request = new CaregoryDetailsRequest(category_id);
    onShowLoading();

    Subscription subscription =
            getCategoryDetails.execute(new BaseSubscriber<CategoryDetailsResponse>() {
              @Override
              public void onSuccess(CategoryDetailsResponse response) {
                getCategories(category_id, response,pillarName);
              }

              @Override
              public void onError(Throwable error) {
                super.onError(error);
                error.printStackTrace();
                viewState = TravelViewState.builder()
                        .error(error)
                        .isLoading(false)
                        .isSuccess(false)
                        .message(null)
                        .dataList(viewState.dataList())
                        .featureList(viewState.featureList())
                        .category(viewState.category())
                        .categoryDetails(viewState.categoryDetails())
                        .favouriteList(viewState.favouriteList())
                        .build();
                getView().render(viewState);
              }
            }, request);

    compositeSubscription.add(subscription);
  }

  public void getCategoryDetailsfortravel(final String category_id) {

    CaregoryDetailsRequest request = new CaregoryDetailsRequest(category_id);
    onShowLoading();

    Subscription subscription =
            getCategoryDetails.execute(new BaseSubscriber<CategoryDetailsResponse>() {
              @Override
              public void onSuccess(CategoryDetailsResponse response) {
                getView().render(response);
              }

              @Override
              public void onError(Throwable error) {
                super.onError(error);
                error.printStackTrace();
              }
            }, request);

    compositeSubscription.add(subscription);
  }


  public void getCategories(final String category_id,
                            final CategoryDetailsResponse categoryDetailsResponse,String pillarName) {

    Subscription subscription = getCategory.execute(new BaseSubscriber<CategoryResponse>() {
      @Override
      public void onSuccess(CategoryResponse response) {
        viewState = TravelViewState.builder()
                .error(null)
                .isLoading(false)
                .isSuccess(true)
                .message("2")
                .dataList(null).featureList(viewState.featureList())
                .category(response).favouriteList(viewState.favouriteList())
                .categoryDetails(categoryDetailsResponse)
                .build();
        getView().render(viewState);
        if(category_id.equalsIgnoreCase("25")){
          getFeatureProducts("25",pillarName,24);
        }else {

          getFeatureProducts(category_id,pillarName,Integer.parseInt(category_id));
        }
      }

      @Override
      public void onError(Throwable error) {
        super.onError(error);
        error.printStackTrace();
        viewState = TravelViewState.builder()
                .error(error)
                .isLoading(false)
                .isSuccess(false)
                .message(null)
                .dataList(viewState.dataList())
                .featureList(viewState.featureList())
                .category(viewState.category()).favouriteList(viewState.favouriteList())
                .categoryDetails(viewState.categoryDetails())
                .build();
        getView().render(viewState);
      }
    } );

    compositeSubscription.add(subscription);
  }

  public void getFeatureProducts(String category_id,String pillarName,int pillarCurrentId) {

    FeatureProductRequest request = new FeatureProductRequest(category_id);
    add(getFeatureProductObservable(request)
            .zipWith(getFavourites.asObservable(GetFavouriteRequest.getAll()),
                    (featureProduct, favouriteListResponses) -> {
                      updateViewState(favouriteListResponses);
                      for (ItemsItem item : featureProduct.getFeatureItems()) {
                        setAsFavIfNeeded(item, favouriteListResponses);
                      }
                      return featureProduct;
                    })
            .subscribe(response -> {
              addCategoryNames(response.getFeatureItems(), viewState.category(),pillarName,pillarCurrentId);

              viewState = TravelViewState.builder()
                      .error(null)
                      .isLoading(false)
                      .isSuccess(true)
                      .message("3")
                      .dataList(viewState.dataList())
                      .featureList(response)
                      .category(viewState.category())
                      .favouriteList(viewState.favouriteList())
                      .categoryDetails(viewState.categoryDetails())
                      .build();
              Timber.e(response.toString());
              getView().render(viewState);
            }, throwable -> {
              viewState = TravelViewState.builder()
                      .error(new Exception("Failed to load featured product list"))
                      .isLoading(false)
                      .isSuccess(false)
                      .message(null)
                      .dataList(viewState.dataList())
                      .category(viewState.category())
                      .featureList(viewState.featureList())
                      .favouriteList(viewState.favouriteList())
                      .categoryDetails(viewState.categoryDetails())
                      .build();
              getView().render(viewState);
            }));
  }

  private Observable<FeatureProduct> getFeatureProductObservable(FeatureProductRequest request) {
    if (pillarID.equalsIgnoreCase(Constants.WELLNESS)) {
      return getFeatureWellness.asObservable(request);
    } else if (pillarID.equalsIgnoreCase(Constants.TRAVEL)) {
      return getFeatureTravel.asObservable(request);
    } else if (pillarID.equalsIgnoreCase(Constants.WINE_AND_DINE)) {
      return getFeatureWineAndDine.asObservable(request);
    } else if (pillarID.equalsIgnoreCase(Constants.SHOPPING)) {
      return getFeatureShopping.asObservable(request);
    } else {
      return getFeatureTravel.asObservable(request);
    }
  }

  public void onShowLoading() {
    viewState = TravelViewState.builder()
            .error(null)
            .isLoading(true)
            .isSuccess(false)
            .message(viewState.message())
            .dataList(viewState.dataList())
            .featureList(viewState.featureList())
            .category(viewState.category())
            .categoryDetails(viewState.categoryDetails())
            .favouriteList(viewState.favouriteList())
            .build();
    getView().render(viewState);
  }

  void removeFromFavourite(final ItemsItem item, final boolean featuredProduct) {

    item.setFavourite(false);
    getView().notifyFavStatusChanged(featuredProduct);
    onShowLoading();
    add(getFavourites.asObservable(GetFavouriteRequest.getAll())
            .flatMapIterable(favouriteListResponses -> favouriteListResponses)
            .filter(
                    favouriteListResponse -> favouriteListResponse.getProductId().equals(item.getId() + ""))
            .map(
                    favouriteListResponse -> new RemoveFromFavourite.Params(
                            favouriteListResponse.getWishlistItemId()))
            .flatMap(removeFromFavourite::asObservable)
            .subscribe(favouriteResponse -> {
              if (favouriteResponse.status) {
                viewState = TravelViewState.builder()
                        .error(null)
                        .isLoading(false)
                        .isSuccess(true)
                        .message("")
                        .dataList(viewState.dataList())
                        .featureList(viewState.featureList())
                        .category(viewState.category())
                        .categoryDetails(viewState.categoryDetails())
                        .favouriteList(viewState.favouriteList())
                        .build();
                getView().render(viewState);
                getView().hideLoading();
              } else {
                item.setFavourite(true);
                getView().notifyFavStatusChanged(featuredProduct);
                viewState = TravelViewState.builder()
                        .error(null)
                        .isLoading(false)
                        .isSuccess(false)
                        .message(favouriteResponse.message)
                        .dataList(viewState.dataList())
                        .featureList(viewState.featureList())
                        .category(viewState.category())
                        .categoryDetails(viewState.categoryDetails())
                        .favouriteList(viewState.favouriteList())
                        .build();
                getView().render(viewState);
                getView().hideLoading();
              }
            }, throwable -> {
              item.setFavourite(true);
              getView().notifyFavStatusChanged(featuredProduct);
              viewState = TravelViewState.builder()
                      .error(new Exception("Failed to remove from favourites"))
                      .isLoading(false)
                      .isSuccess(false)
                      .message(viewState.message())
                      .dataList(viewState.dataList())
                      .featureList(viewState.featureList())
                      .category(viewState.category())
                      .categoryDetails(viewState.categoryDetails())
                      .favouriteList(viewState.favouriteList())
                      .build();
              getView().render(viewState);
              getView().hideLoading();
            }));
  }

  void addToFavourite(final ItemsItem item, final boolean featuredItem) {
    item.setFavourite(true);
    getView().notifyFavStatusChanged(featuredItem);
    onShowLoading();

    add(getFavourites.asObservable(GetFavouriteRequest.getAll())
            .filter(favouriteListResponses -> favouriteListResponses.size() < Constants.MAX_FAV_COUNT)
            .map(favouriteListResponses -> new AddToFavourite.Params("" + item.getId()))
            .flatMap(addToFavourite::asObservable)
            .defaultIfEmpty(FavouriteResponse.numExceeded())
            .subscribe(favouriteResponse -> {
              if (favouriteResponse.status) {
                viewState = TravelViewState.builder()
                        .error(null)
                        .isLoading(false)
                        .isSuccess(true)
                        .message("")
                        .dataList(viewState.dataList())
                        .featureList(viewState.featureList())
                        .category(viewState.category())
                        .categoryDetails(viewState.categoryDetails())
                        .favouriteList(viewState.favouriteList())
                        .build();
                getView().render(viewState);
                getView().hideLoading();
              } else {
                item.setFavourite(false);
                getView().notifyFavStatusChanged(featuredItem);
                viewState = TravelViewState.builder()
                        .error(null)
                        .isLoading(false)
                        .isSuccess(false)
                        .message(favouriteResponse.message)
                        .dataList(viewState.dataList())
                        .featureList(viewState.featureList())
                        .category(viewState.category())
                        .categoryDetails(viewState.categoryDetails())
                        .favouriteList(viewState.favouriteList())
                        .build();
                getView().render(viewState);
                getView().hideLoading();
              }
            }, throwable -> {
              item.setFavourite(false);
              getView().notifyFavStatusChanged(featuredItem);
              viewState = TravelViewState.builder().error(throwable).isLoading(false).isSuccess(false)
                      .message(viewState.message())
                      .dataList(viewState.dataList())
                      .featureList(viewState.featureList())
                      .category(viewState.category())
                      .categoryDetails(viewState.categoryDetails())
                      .favouriteList(viewState.favouriteList())
                      .build();
              getView().render(viewState);
              getView().hideLoading();
            }));
  }

  private Observable<ProductListResponse> getProductObservable(ProductListRequest request) {
    if (pillarID.equalsIgnoreCase(Constants.WELLNESS)) {
      return getWellness.asObservable(request);
    } else if (pillarID.equalsIgnoreCase(Constants.TRAVEL)) {
      return getTravel.asObservable(request);
    } else if (pillarID.equalsIgnoreCase(Constants.WINE_AND_DINE)) {
      return getWineAndDine.asObservable(request);
    } else if (pillarID.equalsIgnoreCase(Constants.SHOPPING)) {
      return getShopping.asObservable(request);
    } else {
      return getTravel.asObservable(request);
    }
  }

  public void getCartCount() {
    add(getCartCount.asObservable()
            .subscribe(s -> getView().updateCartCount(s), Timber::e));
  }

  private void add(Subscription subscription) {
    compositeSubscription.add(subscription);
  }

  void setPillarID(String pillarID) {
    this.pillarID = pillarID;
  }

  public void changeDateRange(List<CalendarDay> list) {
    this.dates = list;
    if (list != null && !list.isEmpty()) {
      SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.BOOKING_DATE_FORMAT,
              Locale.getDefault());
      if (list.size() > 1) {
        CalendarDay startDay = list.get(0);
        String startDate = dateFormat.format(startDay.getDate());
        CalendarDay endDay = list.get(list.size() - 1);
        String endDate = dateFormat.format(endDay.getDate());
        getView().render(startDate, endDate);
      } else {
        String startDate =
                dateFormat.format(list.get(0).getDate());
        getView().render(startDate, "");
      }
    } else {
      getView().clearDateRange();
    }
  }

  public void collectOccupancyValues() {
    getView().showOccupancyDialog(roomCount, adultCount, children);
  }

  public void setOccupancyValues(int roomCount, int adultCount, ArrayList<Child> children) {
    this.roomCount = roomCount;
    this.adultCount = adultCount;
    this.children = children;

    getView().render(roomCount, adultCount, children);
  }

  public void collectBookingValues(Property property) {

    getView().goToPropertyDetail(property, roomCount, adultCount, children, dates);
  }

  public void collectSearchValues() {
    getView().showBottomEanLayout(dates, roomCount, adultCount, children);
  }

  public void getOutletResevation(String productId,int action,ItemsItem itemsItem,String linkHGW){
    getView().showLoading();
    add(getOutletResevation.execute(new SingleSubscriber<List<OutletItem>>() {
      @Override
      public void onSuccess(List<OutletItem> value) {
        getView().hideLoading();
        getView().renderOpenReservation(value,action,itemsItem);
      }

      @Override
      public void onError(Throwable error) {
        getView().hideLoading();
        getView().renderGetOutletFailed(itemsItem,linkHGW);
      }
    },productId));
  }
}
