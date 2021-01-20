package com.skypremiuminternational.app.app.features.search.result_products;

import com.skypremiuminternational.app.app.internal.mvp.BaseFragmentPresenter;
import com.skypremiuminternational.app.app.internal.mvp.BaseSubscriber;
import com.skypremiuminternational.app.app.utils.Constants;
import com.skypremiuminternational.app.data.network.request.GetFavouriteRequest;
import com.skypremiuminternational.app.data.network.request.SearchProductRequest;
import com.skypremiuminternational.app.domain.exception.cart.CartLimitException;
import com.skypremiuminternational.app.domain.interactor.cart.AddToBuyNow;
import com.skypremiuminternational.app.domain.interactor.cart.AddToCart;
import com.skypremiuminternational.app.domain.interactor.category.GetCategory;
import com.skypremiuminternational.app.domain.interactor.favourite.AddToFavourite;
import com.skypremiuminternational.app.domain.interactor.favourite.GetFavourites;
import com.skypremiuminternational.app.domain.interactor.favourite.RemoveFromFavourite;
import com.skypremiuminternational.app.domain.interactor.search.GetSearchAll;
import com.skypremiuminternational.app.domain.interactor.search.GetSearchHome;
import com.skypremiuminternational.app.domain.interactor.search.GetSearchHomeProducts;
import com.skypremiuminternational.app.domain.interactor.search.GetSearchProduct;
import com.skypremiuminternational.app.domain.models.category.CategoryResponse;
import com.skypremiuminternational.app.domain.models.category.ChildData;
import com.skypremiuminternational.app.domain.models.category.ChildData_;
import com.skypremiuminternational.app.domain.models.favourite.FavouriteListResponse;
import com.skypremiuminternational.app.domain.models.favourite.FavouriteResponse;
import com.skypremiuminternational.app.domain.models.search.SearchHomeResponse;
import com.skypremiuminternational.app.domain.models.search.SearchProductResponse;
import com.skypremiuminternational.app.domain.models.wellness.CustomAttributesItem;
import com.skypremiuminternational.app.domain.models.wellness.ItemsItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

/**
 * Created by johnsonmaung on 9/19/17.
 */

public class SearchProductsPresenter extends BaseFragmentPresenter<SearchProductsView> {

  private final GetSearchProduct getSearchProduct;

  private final GetSearchAll getSearchAll;

  private final GetSearchHome getSearchHome;

  private final AddToBuyNow addToBuyNow;

  private final AddToCart addToCart;

  private final GetSearchHomeProducts getSearchHomeProducts;

  private final GetCategory getCategory;
  private final RemoveFromFavourite removeFromFavourite;
  private final AddToFavourite addToFavourite;
  private final GetFavourites getFavourites;
  private SearchProductsViewState viewState;
  private CompositeSubscription compositeSubscription = new CompositeSubscription();

  @Inject
  public SearchProductsPresenter(GetSearchProduct getSearchProduct,
                                 GetSearchAll getSearchAll, GetSearchHome getSearchHome,
                                 GetSearchHomeProducts getSearchHomeProducts, GetCategory getCategory,
                                 GetFavourites getFavourites, AddToFavourite addToFavourite,
                                 RemoveFromFavourite removeFromFavourite, AddToBuyNow addToBuyNow,
                                 AddToCart addToCart) {
    viewState = SearchProductsViewState.builder()
        .error(null)
        .isLoading(false)
        .isSuccess(false)
        .message(null)
        .dataList(null)
        .category(null)
        .build();
    this.getFavourites = getFavourites;
    this.addToFavourite = addToFavourite;
    this.removeFromFavourite = removeFromFavourite;
    this.getSearchProduct = getSearchProduct;
    this.getSearchAll = getSearchAll;
    this.getSearchHome = getSearchHome;
    this.getSearchHomeProducts = getSearchHomeProducts;
    this.getCategory = getCategory;
    this.addToCart = addToCart;
    this.addToBuyNow = addToBuyNow;
    attachLoading();
  }

  @Override
  public void onStop() {
    compositeSubscription.clear();
    super.onStop();
  }

  public void searchProduct(String keyword, String field, String direction,
                            final String categoryId) {

    final SearchProductRequest request =
        new SearchProductRequest(keyword, field, direction, categoryId);

    onShowLoading();

    add(getSearchProduct.asObservable(request)
        .zipWith(getFavourites.asObservable(GetFavouriteRequest.getAll()),
            (searchProductResponse, favouriteListResponses) -> {
              updateViewState(favouriteListResponses);
              for (ItemsItem item : searchProductResponse.getItems()) {
                setAsFavIfNeeded(item, favouriteListResponses);
              }
              return searchProductResponse;
            })
        .subscribe(response -> {
          addCategoryNames(response.getItems(), viewState.category());

          viewState = SearchProductsViewState.builder()
              .error(null)
              .isLoading(false)
              .isSuccess(true)
              .message("1")
              .dataList(response)
              .category(viewState.category())
              .favouriteList(viewState.favouriteList())
              .build();

          getView().render(viewState);
        }, throwable -> {
          viewState = SearchProductsViewState.builder()
              .error(new Exception("Failed to load searched products"))
              .isLoading(false)
              .isSuccess(false)
              .message("")
              .dataList(viewState.dataList())
              .category(viewState.category())
              .favouriteList(viewState.favouriteList())
              .build();
          getView().render(viewState);
        }));
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

  public void searchAll(String keyword, String field, String direction) {

    SearchProductRequest request = new SearchProductRequest(keyword, field, direction, null);

    viewState = SearchProductsViewState.builder()
        .error(null)
        .isLoading(true)
        .isSuccess(false)
        .message(null)
        .dataList(null)
        .category(viewState.category())
        .build();

    getView().render(viewState);

    Subscription subscription = getSearchAll.execute(new BaseSubscriber<SearchProductResponse>() {
      @Override
      public void onSuccess(SearchProductResponse response) {
        viewState = SearchProductsViewState.builder()
            .error(null)
            .isLoading(false)
            .isSuccess(true)
            .message("1")
            .dataList(response)
            .category(viewState.category())
            .build();

        getView().render(viewState);
      }

      @Override
      public void onError(Throwable error) {
        super.onError(error);
        error.printStackTrace();
        viewState = SearchProductsViewState.builder()
            .error(error)
            .isLoading(false)
            .isSuccess(false)
            .message(null)
            .dataList(null)
            .category(viewState.category())
            .build();
        getView().render(viewState);
      }
    }, request);

    compositeSubscription.add(subscription);
  }

  public void getCategories(String categoryId) {

    Subscription subscription = getCategory.execute(new BaseSubscriber<CategoryResponse>() {
      @Override
      public void onSuccess(CategoryResponse response) {
        viewState = SearchProductsViewState.builder()
            .error(null)
            .isLoading(false)
            .isSuccess(true)
            .message("1")
            .dataList(null)
            .category(response)
            .build();

        getView().showFilter(viewState);
      }

      @Override
      public void onError(Throwable error) {
        super.onError(error);
        error.printStackTrace();
        viewState = SearchProductsViewState.builder()
            .error(error)
            .isLoading(false)
            .isSuccess(false)
            .message(null)
            .dataList(null)
            .category(viewState.category())
            .build();
        getView().render(viewState);
      }
    } );

    compositeSubscription.add(subscription);
  }

  public void searchHome(String keyword, final String field, final String direction) {

    SearchProductRequest request = new SearchProductRequest(keyword, field, direction, null);

    viewState = SearchProductsViewState.builder()
        .error(null)
        .isLoading(true)
        .isSuccess(false)
        .message(null)
        .dataList(null)
        .category(viewState.category())
        .build();

    getView().render(viewState);

    Subscription subscription = getSearchHome.execute(new BaseSubscriber<SearchHomeResponse>() {
      @Override
      public void onSuccess(SearchHomeResponse response) {
        if (response.getItems() == null) {
          viewState = SearchProductsViewState.builder()
              .error(null)
              .isLoading(false)
              .isSuccess(true)
              .message("1")
              .dataList(new SearchProductResponse(new ArrayList<>()))
              .category(viewState.category())
              .build();
          getView().render(viewState);
        } else {
          String IDList = "";
          for (SearchHomeResponse.Item item : response.getItems()) {
            IDList += String.valueOf(item.getId()) + ",";
          }
          searchHomeProducts(field, direction, IDList.substring(0, IDList.length() - 1));
        }
      }

      @Override
      public void onError(Throwable error) {
        viewState = SearchProductsViewState.builder()
            .error(new Exception("Failed to search"))
            .isLoading(false)
            .isSuccess(false)
            .message(null)
            .dataList(null)
            .category(viewState.category())
            .build();
        getView().render(viewState);
      }
    }, request);

    compositeSubscription.add(subscription);
  }

  public void searchHomeProducts(String field, String direction, String IDList) {

    onShowLoading();

    final SearchProductRequest request = new SearchProductRequest(null, field, direction, IDList);
    add(getSearchHomeProducts.asObservable(request)
        .zipWith(getFavourites.asObservable(GetFavouriteRequest.getAll()),
            (searchProductResponse, favouriteListResponses) -> {
              updateViewState(favouriteListResponses);
              for (ItemsItem item : searchProductResponse.getItems()) {
                item.setSearchHomeProduct(true);
                setAsFavIfNeeded(item, favouriteListResponses);
              }
              return searchProductResponse;
            })
        .subscribe(searchProductResponse -> {

          addCategoryNames(searchProductResponse.getItems(), viewState.category());

          viewState = SearchProductsViewState.builder()
              .error(null)
              .isLoading(false)
              .isSuccess(true)
              .message("1")
              .dataList(searchProductResponse)
              .category(viewState.category())
              .favouriteList(viewState.favouriteList())
              .build();
          getView().render(viewState);
        }, throwable -> {
          viewState = SearchProductsViewState.builder()
              .error(new Exception("Failed to search"))
              .isLoading(false)
              .isSuccess(false)
              .message("")
              .dataList(viewState.dataList())
              .category(viewState.category())
              .favouriteList(viewState.favouriteList())
              .build();
          getView().render(viewState);
        }));
  }

  private void updateViewState(List<FavouriteListResponse> favouriteListResponses) {
    viewState = SearchProductsViewState.builder()
        .error(viewState.error())
        .isLoading(viewState.isLoading())
        .isSuccess(viewState.isSuccess())
        .message(viewState.message())
        .dataList(viewState.dataList())
        .category(viewState.category())
        .favouriteList(favouriteListResponses)
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

  void removeFromFavourite(final ItemsItem item) {
    item.setFavourite(false);
    getView().notifyFavStatusChanged(false);
    onShowLoading();
    add(getFavourites.asObservable(GetFavouriteRequest.getAll())
        .flatMapIterable(favouriteListResponses -> favouriteListResponses)
        .filter(
            favouriteListResponse -> favouriteListResponse.getProductId().equals(item.getId() + ""))
        .map(
            favouriteListResponse -> new RemoveFromFavourite.Params(favouriteListResponse.getWishlistItemId()))
        .flatMap(removeFromFavourite::asObservable)
        .subscribe(favouriteResponse -> {
          getView().hideLoading();
          if (!favouriteResponse.status) {
            item.setFavourite(true);
            getView().notifyFavStatusChanged(true);
          }
        }, throwable -> {
          getView().hideLoading();
          item.setFavourite(true);
          getView().notifyFavStatusChanged(true);
        }));
  }

  void addToFavourite(final ItemsItem item) {
    item.setFavourite(true);
    getView().notifyFavStatusChanged(true);
    onShowLoading();

    add(getFavourites.asObservable(GetFavouriteRequest.getAll())
        .filter(favouriteListResponses -> favouriteListResponses.size() < Constants.MAX_FAV_COUNT)
        .map(favouriteListResponses -> new AddToFavourite.Params("" + item.getId()))
        .flatMap(addToFavourite::asObservable)
        .defaultIfEmpty(FavouriteResponse.numExceeded())
        .subscribe(favouriteResponse -> {
          getView().hideLoading();
          if (!favouriteResponse.status) {
            item.setFavourite(false);
            getView().notifyFavStatusChanged(false);
            viewState = SearchProductsViewState.builder()
                .error(new Exception(favouriteResponse.message))
                .isLoading(false)
                .isSuccess(false)
                .message(favouriteResponse.message)
                .dataList(viewState.dataList())
                .category(viewState.category())
                .favouriteList(viewState.favouriteList())
                .build();
            getView().render(viewState);
          }
        }, throwable -> {
          throwable.printStackTrace();
          getView().hideLoading();
          item.setFavourite(false);
          getView().notifyFavStatusChanged(false);
        }));
  }

  public void onShowLoading() {
    viewState = SearchProductsViewState.builder()
        .error(null)
        .isLoading(true)
        .isSuccess(false)
        .message(viewState.message())
        .dataList(viewState.dataList())
        .category(viewState.category())
        .favouriteList(viewState.favouriteList())
        .build();
    getView().render(viewState);
  }
  public void addToCart(final AddToCart.Params params) {
    onShowLoading();
    add(addToCart.asObservable(params).subscribe(cart -> {
      getView().hideLoading();
      getView().render("You added  \""+params.name+"\" to  your shopping cart.");
    }, throwable -> {
      getView().hideLoading();
      if (throwable instanceof IOException) {
        //getView().showAddToCartFailedDialog(params, throwable);
      } else if (throwable instanceof CartLimitException) {
        CartLimitException cartLimitException = (CartLimitException) throwable;
        if (cartLimitException.getLimitErrors() != null
            && cartLimitException.getLimitErrors().size() > 0) {
          //getView().showAddToCartFailedDialog(null,new Exception(cartLimitException.getLimitErrors().get(0).message));
        }
      } else {
        getView().render(throwable.getMessage());
        // getView().render(new Exception("Failed to add product to cart"));

      }
    }));
  }

  public void addToBuyNow(final AddToBuyNow.Params params) {
    onShowLoading();
    add(addToBuyNow.asObservable(params).subscribe(cart -> {
      getView().hideLoading();
      getView().render("You added  \""+params.name+"\" to  your shopping cart.");
      getView().renderShoppingCart();
    }, throwable -> {
      getView().hideLoading();
      if (throwable instanceof IOException) {
        //getView().showAddToCartFailedDialog(params, throwable);
      } else if (throwable instanceof CartLimitException) {
        CartLimitException cartLimitException = (CartLimitException) throwable;
        if (cartLimitException.getLimitErrors() != null
            && cartLimitException.getLimitErrors().size() > 0) {
          //getView().showAddToCartFailedDialog(null,new Exception(cartLimitException.getLimitErrors().get(0).message));
        }
      } else {
        getView().render(throwable.getMessage());
        // getView().render(new Exception("Failed to add product to cart"));

      }
    }));
  }

  private void add(Subscription subscription) {
    compositeSubscription.add(subscription);
  }
}
