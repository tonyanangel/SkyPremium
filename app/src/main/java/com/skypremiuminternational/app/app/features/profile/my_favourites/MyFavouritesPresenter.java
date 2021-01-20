package com.skypremiuminternational.app.app.features.profile.my_favourites;

import com.skypremiuminternational.app.app.internal.mvp.BasePresenter;
import com.skypremiuminternational.app.data.InternalStorageManager;
import com.skypremiuminternational.app.data.network.request.DetailsRequest;
import com.skypremiuminternational.app.data.network.request.GetFavouriteRequest;
import com.skypremiuminternational.app.domain.interactor.auth.GetAppVersion;
import com.skypremiuminternational.app.domain.interactor.cart.GetCartCount;
import com.skypremiuminternational.app.domain.interactor.category.GetCategory;
import com.skypremiuminternational.app.domain.interactor.details.GetDetails;
import com.skypremiuminternational.app.domain.interactor.favourite.GetFavourites;
import com.skypremiuminternational.app.domain.interactor.favourite.RemoveFromFavourite;
import com.skypremiuminternational.app.domain.models.category.CategoryResponse;
import com.skypremiuminternational.app.domain.models.category.ChildData;
import com.skypremiuminternational.app.domain.models.category.ChildData_;
import com.skypremiuminternational.app.domain.models.details.DetailsItem;
import com.skypremiuminternational.app.domain.models.favourite.FavouriteListResponse;
import com.skypremiuminternational.app.domain.models.favourite.FavouriteResponse;

import java.util.List;

import javax.inject.Inject;

import rx.SingleSubscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

/**
 * Created by johnsonmaung on 9/22/17.
 */

public class MyFavouritesPresenter extends BasePresenter<MyFavouritesView> {

  private final GetFavourites getFavourites;
  private final RemoveFromFavourite removeFromFavourite;
  private final GetCategory getCategory;
  private final GetCartCount getCartCount;
  private final GetDetails getDetails;
  private MyFavouritesViewState viewState;
  private CompositeSubscription compositeSubscription = new CompositeSubscription();
  private GetFavouriteRequest filteredRequest;

  @Inject
  public MyFavouritesPresenter(GetAppVersion getAppVersion, InternalStorageManager internalStorageManager,
                               GetDetails getDetails, GetFavourites getFavourites,
                               RemoveFromFavourite removeFromFavourite, GetCategory getCategory, GetCartCount getCartCount) {
    super(getAppVersion, internalStorageManager);
    this.getFavourites = getFavourites;
    this.getCategory = getCategory;
    this.removeFromFavourite = removeFromFavourite;
    this.getCartCount = getCartCount;
    attachLoading(getFavourites);
    this.getDetails = getDetails;
    viewState =
        MyFavouritesViewState.builder().error(null).myFavourites(null).category(null).build();

  }

  @Override
  public void add(Subscription subscription) {
    compositeSubscription.add(subscription);
  }

  @Override
  public void onStop() {
    compositeSubscription.clear();
    super.onStop();
  }

  void getDetails(String sku, final FavouriteListResponse item) {
    DetailsRequest request = new DetailsRequest(sku,"");
    add(getDetails.execute(new SingleSubscriber<DetailsItem>() {
      @Override
      public void onSuccess(DetailsItem value) {
        getView().goToDetail(value, item);
      }

      @Override
      public void onError(Throwable error) {
        viewState = MyFavouritesViewState.builder()
            .error(error)
            .category(viewState.category())
            .myFavourites(viewState.myFavourites())
            .totalCount(viewState.totalCount())
            .build();
        getView().render(viewState);
      }
    }, request));
  }

  void removeFromFav(String wishlistItemId) {
    RemoveFromFavourite.Params params = new RemoveFromFavourite.Params(wishlistItemId);

    add(removeFromFavourite.execute(new SingleSubscriber<FavouriteResponse>() {
      @Override
      public void onSuccess(FavouriteResponse value) {
        if (value.status) {
          getFavourites();
        } else {
          viewState = MyFavouritesViewState.builder()
              .error(new Exception(value.message))
              .myFavourites(viewState.myFavourites())
              .totalCount(viewState.totalCount())
              .category(viewState.category())
              .build();
          getView().render(viewState);
        }
      }

      @Override
      public void onError(Throwable error) {
        viewState = MyFavouritesViewState.builder()
            .error(error)
            .category(viewState.category())
            .myFavourites(viewState.myFavourites())
            .totalCount(viewState.totalCount())
            .build();
        getView().render(viewState);
      }
    }, params));
  }

  void getCategory() {
    add(getCategory.execute(new SingleSubscriber<CategoryResponse>() {
      @Override
      public void onSuccess(CategoryResponse value) {
        viewState = MyFavouritesViewState.builder()
            .error(null)
            .myFavourites(viewState.myFavourites()).category(value)
            .build();
        getFavourites();
      }

      @Override
      public void onError(Throwable error) {
        viewState = MyFavouritesViewState.builder()
            .error(error)
            .category(viewState.category())
            .myFavourites(viewState.myFavourites())
            .build();
        getView().render(viewState);
      }
    } ));
  }

  void setRequest(String selectedCategoryID, String partnerType, String field, String direction) {

    if (selectedCategoryID == null) {
      filteredRequest = GetFavouriteRequest.getWithoutCategory(partnerType, field, direction);
    } else {
      filteredRequest = GetFavouriteRequest.getWithCategory(selectedCategoryID, partnerType, field, direction);
    }
  }

  void getCartCount() {
    add(getCartCount.asObservable().subscribe(s -> getView().updateViewCount(s), Timber::e));
  }

 /* void getFavourites() {
    add(getFavourites.asObservable(GetFavouriteRequest.getAll())
        .zipWith(getFavourites.asObservable(filteredRequest),
            (totalList, filteredList) -> new FavouritesWrapper(filteredList, totalList.size()))
        .subscribe(favouritesWrapper -> {
          addCategoryNames(favouritesWrapper.responseList, viewState.category());
          viewState = MyFavouritesViewState.builder()
              .error(null)
              .totalCount(favouritesWrapper.totalCount)
              .myFavourites(favouritesWrapper.responseList)
              .category(viewState.category())
              .build();
          getView().render(viewState);
        }, throwable -> {
          viewState = MyFavouritesViewState.builder()
              .error(throwable)
              .totalCount(viewState.totalCount())
              .category(viewState.category())
              .myFavourites(viewState.myFavourites())
              .build();
          getView().render(viewState);
        }));
  } */

  void getNumberFav() {
    add(getFavourites.asObservable(GetFavouriteRequest.getAll())
        .subscribe(favouritesWrapper -> {
          getView().renderCountItem(favouritesWrapper.size());
        }, throwable -> {

        }));
  }
  void getFavourites() {
    add(getFavourites.asObservable(filteredRequest)
        .subscribe(favouritesWrapper -> {
          addCategoryNames(favouritesWrapper, viewState.category());
          viewState = MyFavouritesViewState.builder()
              .error(null)
              .totalCount(favouritesWrapper.size())
              .myFavourites(favouritesWrapper)
              .category(viewState.category())
              .build();
          getView().render(viewState);
        }, throwable -> {
          viewState = MyFavouritesViewState.builder()
              .error(throwable)
              .totalCount(viewState.totalCount())
              .category(viewState.category())
              .myFavourites(viewState.myFavourites())
              .build();
          getView().render(viewState);
        }));
  }

  private void addCategoryNames(List<FavouriteListResponse> itemsItems,
                                CategoryResponse categories) {
    for (FavouriteListResponse item : itemsItems) {
      String categoryName = getCategoryName(categories.getChildrenData(), item);
      item.setCategoryName(categoryName);
    }
  }

  private String getCategoryName(List<ChildData> categories, FavouriteListResponse item) {
    StringBuilder subCatBuilder = new StringBuilder();
    for (String id : item.getProduct().getCategoryIds()) {
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
  }

  static class FavouritesWrapper {
    final List<FavouriteListResponse> responseList;
    final int totalCount;

    FavouritesWrapper(List<FavouriteListResponse> responseList, int totalCount) {
      this.responseList = responseList;
      this.totalCount = totalCount;
    }
  }
}
