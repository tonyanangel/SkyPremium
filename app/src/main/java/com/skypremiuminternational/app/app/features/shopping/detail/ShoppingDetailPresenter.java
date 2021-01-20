package com.skypremiuminternational.app.app.features.shopping.detail;

import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.internal.mvp.BasePresenter;
import com.skypremiuminternational.app.app.internal.mvp.BaseSubscriber;
import com.skypremiuminternational.app.app.utils.Constants;
import com.skypremiuminternational.app.data.InternalStorageManager;
import com.skypremiuminternational.app.data.network.request.DetailsRequest;
import com.skypremiuminternational.app.data.network.request.GetFavouriteRequest;
import com.skypremiuminternational.app.domain.interactor.auth.GetAppVersion;
import com.skypremiuminternational.app.domain.interactor.cart.GetCartCount;
import com.skypremiuminternational.app.domain.interactor.category.GetCategory;
import com.skypremiuminternational.app.domain.interactor.details.GetDetails;
import com.skypremiuminternational.app.domain.interactor.details.GetRecommendations;
import com.skypremiuminternational.app.domain.interactor.favourite.AddToFavourite;
import com.skypremiuminternational.app.domain.interactor.favourite.GetFavourites;
import com.skypremiuminternational.app.domain.interactor.favourite.RemoveFromFavourite;
import com.skypremiuminternational.app.domain.interactor.hunry_go_where.GetOutletResevation;
import com.skypremiuminternational.app.domain.models.category.CategoryResponse;
import com.skypremiuminternational.app.domain.models.category.ChildData;
import com.skypremiuminternational.app.domain.models.category.ChildData_;
import com.skypremiuminternational.app.domain.models.details.CustomAttribute;
import com.skypremiuminternational.app.domain.models.details.DetailsItem;
import com.skypremiuminternational.app.domain.models.favourite.FavouriteListResponse;
import com.skypremiuminternational.app.domain.models.favourite.FavouriteResponse;

import com.skypremiuminternational.app.domain.models.hunry_go_where.OutletItem;
import com.skypremiuminternational.app.domain.models.wellness.CustomAttributesItem;
import com.skypremiuminternational.app.domain.models.wellness.ItemsItem;

import java.util.List;

import javax.inject.Inject;

import rx.SingleSubscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

/**
 * Created by johnsonmaung on 9/22/17.
 */

public class ShoppingDetailPresenter extends BasePresenter<ShoppingDetailView> {

  private final AddToFavourite addToFavourite;
  private final RemoveFromFavourite removeFromFavourite;
  private final GetFavourites getFavourites;
  private final GetCategory getCategory;
  private final GetCartCount getCartCount;
  private final GetRecommendations getRecommendations;
  private final GetOutletResevation getOutletResevation;
  private GetDetails getDetails;
  private ShoppingDetailViewState viewState;

  private CompositeSubscription compositeSubscription = new CompositeSubscription();

  @Inject
  public ShoppingDetailPresenter(GetAppVersion getAppVersion, InternalStorageManager internalStorageManager,
                                 GetDetails getDetails, GetFavourites getFavourites,
                                 RemoveFromFavourite removeFromFavourite, AddToFavourite addToFavourite,
                                 GetRecommendations getRecommendations, GetCategory getCategory, GetCartCount getCartCount,
                                 GetOutletResevation getOutletResevation) {
    super(getAppVersion, internalStorageManager);
    this.getCartCount = getCartCount;
    viewState = ShoppingDetailViewState.builder()
        .error(null)
        .isLoading(false)
        .isSuccess(false)
        .message(null)
        .build();
    this.getDetails = getDetails;
    this.getCategory = getCategory;
    this.getRecommendations = getRecommendations;
    this.getFavourites = getFavourites;
    this.removeFromFavourite = removeFromFavourite;
    this.addToFavourite = addToFavourite;
    this.getOutletResevation = getOutletResevation;
  }

  @Override
  public void onStop() {
    compositeSubscription.clear();
    super.onStop();
  }

  public void getDetails(final String sku) {
    onShowLoading();

    DetailsRequest detailsRequest = new DetailsRequest(sku,"");

    Subscription subscription = getDetails.execute(new BaseSubscriber<DetailsItem>() {
      @Override
      public void onSuccess(DetailsItem response) {

        viewState = ShoppingDetailViewState.builder()
            .error(null)
            .isLoading(false)
            .isSuccess(true)
            .message(response)
            .build();
        getView().render(viewState);
        getCategory(sku);
      }

      @Override
      public void onError(Throwable error) {
        viewState = ShoppingDetailViewState.builder()
            .error(new Exception("Failed to load product detail"))
            .isLoading(false)
            .isSuccess(false)
            .message(null)
            .build();
        getView().render(viewState);
      }
    }, detailsRequest);
    compositeSubscription.add(subscription);
  }

  public void onShowLoading() {
    viewState = ShoppingDetailViewState.builder()
        .error(null)
        .isLoading(true)
        .isSuccess(false)
        .message(null)
        .build();
    getView().render(viewState);
  }

  void removeFromFavourite(final String id) {
    onShowLoading();
    getView().notifyFavStatusChanged(false, id);
    add(getFavourites.asObservable(GetFavouriteRequest.getAll())
        .flatMapIterable(favouriteListResponses -> favouriteListResponses)
        .filter(favouriteListResponse -> favouriteListResponse.getProductId().equals(id))
        .map(
            favouriteListResponse -> new RemoveFromFavourite.Params(favouriteListResponse.getWishlistItemId()))
        .flatMap(removeFromFavourite::asObservable)
        .subscribe(favouriteResponse -> {
          if (!favouriteResponse.status) {
            getView().notifyFavStatusChanged(true, id);
            viewState = ShoppingDetailViewState.builder()
                .error(new Exception(favouriteResponse.message))
                .isLoading(false)
                .isSuccess(false)
                .message(viewState.message())
                .build();
            getView().render(viewState);
          } else {
            getView().hideLoading();
          }
        }, throwable -> {
          getView().notifyFavStatusChanged(true, id);
          viewState = ShoppingDetailViewState.builder()
              .error(throwable)
              .isLoading(false)
              .isSuccess(false)
              .message(viewState.message())
              .build();
          getView().render(viewState);
        }));
  }

  void addToFavourite(final String id) {
    onShowLoading();
    getView().notifyFavStatusChanged(true, id);

    add(getFavourites.asObservable(GetFavouriteRequest.getAll())
        .filter(favouriteListResponses -> favouriteListResponses.size() < Constants.MAX_FAV_COUNT)
        .map(favouriteListResponses -> new AddToFavourite.Params(id))
        .flatMap(addToFavourite::asObservable)
        .defaultIfEmpty(FavouriteResponse.numExceeded())
        .subscribe(favouriteResponse -> {
          if (!favouriteResponse.status) {
            getView().notifyFavStatusChanged(false, id);
            viewState = ShoppingDetailViewState.builder()
                .error(new Exception(favouriteResponse.message))
                .isLoading(false)
                .isSuccess(false)
                .message(viewState.message())
                .build();
            getView().render(viewState);
          } else {
            getView().hideLoading();
          }
        }, throwable -> {
          getView().notifyFavStatusChanged(false, id);
          viewState = ShoppingDetailViewState.builder()
              .error(throwable)
              .isLoading(false)
              .isSuccess(false)
              .message(viewState.message())
              .build();
          getView().render(viewState);
        }));
  }

  public void getCategory(final String sku) {
    Subscription subscription = getCategory.execute(new SingleSubscriber<CategoryResponse>() {
      @Override
      public void onSuccess(CategoryResponse value) {
        viewState = ShoppingDetailViewState.builder()
            .error(null)
            .isLoading(false)
            .isSuccess(true)
            .category(value)
            .message(viewState.message())
            .build();
        DetailsItem detailsItem = viewState.message();
        String categoryRelation = ShoppingDetailActivity.category_id;
        for(CustomAttribute customAttribute : detailsItem.getCustomAttributes()){
          if(customAttribute!=null&&customAttribute.getAttributeCode().equalsIgnoreCase("category_ids")){

            List<String> listCategory = (List<String>) customAttribute.getValue();
            if(listCategory!=null&&listCategory.size()>0){
              categoryRelation = listCategory.get(listCategory.size()-1);
            }
          }
        }
        getRecommendationLsit(sku,categoryRelation);
      }

      @Override
      public void onError(Throwable error) {
        viewState = ShoppingDetailViewState.builder()
            .error(error)
            .isLoading(false)
            .isSuccess(false)
            .category(viewState.category())
            .message(viewState.message())
            .build();
        getView().render(viewState);
      }
    } );
    compositeSubscription.add(subscription);
  }

  public void getRecommendationLsit(String sku,String category_id) {
    DetailsRequest detailsRequest = new DetailsRequest(sku,category_id);
    Subscription subscription = getFavourites.asObservable(GetFavouriteRequest.getAll())
        .zipWith(getRecommendations.asObservable(detailsRequest),
            (favouriteListResponses, recommendItems) -> {
              for (ItemsItem item : recommendItems.getItems()) {
                setAsFavIfNeeded(item, favouriteListResponses);
              }
              addCategoryNames(recommendItems.getItems(), viewState.category());
              return recommendItems;
            })
        .subscribe(recommendItems -> getView().showRecommendationList(recommendItems.getItems()),
            throwable -> {
              viewState = ShoppingDetailViewState.builder()
                  .error(new Exception("Failed to load recommended products"))
                  .isLoading(false)
                  .isSuccess(false)
                  .category(viewState.category())
                  .message(viewState.message())
                  .build();
              getView().render(viewState);
            });
    compositeSubscription.add(subscription);
  }

  private void addCategoryNames(List<ItemsItem> itemsItems, CategoryResponse categories) {
    for (ItemsItem item : itemsItems) {
      String categoryName = getCategoryName(categories.getChildrenData(), item);
      item.setCategoryName(categoryName);
    }
  }

  private String getCategoryName(List<ChildData> categories, ItemsItem item) {
    for (CustomAttributesItem attributesItem : item.getCustomAttributes()) {
      if (attributesItem.getAttributeCode().equals("category_ids")) {
        Object value = attributesItem.getValue();
        try {
          StringBuilder subCatBuilder = new StringBuilder();
          List<String> ids = (List<String>) value;
          for (String id : ids) {
            for (ChildData pillar : categories) {
              if (!id.equals("" + pillar.getId())) {
                for (ChildData_ subCat : pillar.getChildrenData()) {
                  if (id.equals("" + subCat.getId())) {
                    subCatBuilder.append(subCat.getName());
                    subCatBuilder.append(",");
                  }
                }
              } else {
                item.setPillarName(pillar.getName());
                item.setPillarId(id);
              }
            }
          }
          String subCategories = subCatBuilder.toString();
          if (subCategories.endsWith(",")) {
            subCategories = subCategories.substring(0, subCategories.lastIndexOf(","));
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

  private void setAsFavIfNeeded(ItemsItem item,
                                List<FavouriteListResponse> favouriteListResponses) {
    for (FavouriteListResponse fav : favouriteListResponses) {
      if (fav.getProductId().equals(item.getId() + "")) {
        item.setFavourite(true);
        return;
      }
    }
  }

  public void getCartCount() {
    add(getCartCount.asObservable()
        .subscribe(s -> getView().updateCartCount(s), Timber::e));
  }

  @Override
  public void add(Subscription subscription) {
    compositeSubscription.add(subscription);
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

  public void getCategoryDefault(DetailsItem detailsItem) {
    add(getCategory.execute(new SingleSubscriber<CategoryResponse>() {
      @Override
      public void onSuccess(CategoryResponse value) {
        addCategoryNames(detailsItem,value);
        getView().renderGotoEstore(detailsItem);
      }

      @Override
      public void onError(Throwable error) {

      }
    } ));
  }

  public void addCategoryNames(DetailsItem id, CategoryResponse categories) {
    String categoryName = getCategoryName(categories.getChildrenData(), id);
    id.setCategoryName(categoryName);
  }

  private String getCategoryName(List<ChildData> categories, DetailsItem item) {
    for (CustomAttribute attributesItem : item.getCustomAttributes()) {
      if (attributesItem.getAttributeCode().equals("category_ids")) {
        Object value = attributesItem.getValue();
        try {
          StringBuilder subCatBuilder = new StringBuilder();
          List<String> ids = (List<String>) value;
          for (String id : ids) {
            for (ChildData pillar : categories) {
              if (!id.equals("" + pillar.getId())) {
                for (ChildData_ subCat : pillar.getChildrenData()) {
                  if (id.equals("" + subCat.getId())) {
                    subCatBuilder.append(subCat.getName());
                    subCatBuilder.append(",");
                  }
                }
              }
            }
          }
          String subCategories = subCatBuilder.toString();
          if (subCategories.endsWith(",")) {
            subCategories = subCategories.substring(0, subCategories.lastIndexOf(","));
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


  public void getOutletResevation(String productId){
    getView().showLoading();
    add(getOutletResevation.execute(new SingleSubscriber<List<OutletItem>>() {
      @Override
      public void onSuccess(List<OutletItem> value) {
        getView().hideLoading();
        getView().renderGetOutletSuccess(value);
      }

      @Override
      public void onError(Throwable error) {
        getView().hideLoading();
        getView().renderGetOutletFailed();
      }
    },productId));
  }
}
