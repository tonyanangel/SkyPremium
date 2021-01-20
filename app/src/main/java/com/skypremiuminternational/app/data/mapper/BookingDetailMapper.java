package com.skypremiuminternational.app.data.mapper;

import android.text.TextUtils;
import android.util.Log;

import com.skypremiuminternational.app.app.model.BookingHistory;
import com.skypremiuminternational.app.app.utils.Constants;
import com.skypremiuminternational.app.app.utils.StringUtil;
import com.skypremiuminternational.app.app.utils.Validator;
import com.skypremiuminternational.app.data.model.ean.availability.AmenityItem;
import com.skypremiuminternational.app.data.model.ean.availability.CancelPenaltiesItem;
import com.skypremiuminternational.app.data.model.ean.availability.FeesItem;
import com.skypremiuminternational.app.data.model.ean.availability.NightlyItem;
import com.skypremiuminternational.app.data.model.ean.availability.Occupancy;
import com.skypremiuminternational.app.data.model.ean.booking.book.BookResponse;
import com.skypremiuminternational.app.data.model.ean.booking.history.AdditionalData;
import com.skypremiuminternational.app.data.model.ean.booking.history.Booking;
import com.skypremiuminternational.app.data.model.ean.booking.history.BookingData;
import com.skypremiuminternational.app.data.model.ean.booking.itinerary.Fee;
import com.skypremiuminternational.app.data.model.ean.booking.itinerary.ItineraryResponse;
import com.skypremiuminternational.app.data.model.ean.booking.itinerary.RoomsItem;
import com.skypremiuminternational.app.data.model.ean.content.BedGroup;
import com.skypremiuminternational.app.data.model.ean.content.ImagesItem;
import com.skypremiuminternational.app.data.model.ean.content.PropertyContent;
import com.skypremiuminternational.app.data.model.ean.content.Rates;
import com.skypremiuminternational.app.data.model.ean.content.RoomContent;
import com.skypremiuminternational.app.data.model.ean.pricecheck.PriceCheckResponse;
import com.skypremiuminternational.app.data.utl.OccupancyArranger;
import com.skypremiuminternational.app.domain.models.booking.BookingDetail;
import com.skypremiuminternational.app.domain.models.booking.GuestDetail;
import com.skypremiuminternational.app.domain.models.booking.PriceCheckResult;
import com.skypremiuminternational.app.domain.models.booking.TourismFee;
import com.skypremiuminternational.app.domain.models.ean.Amenity;
import com.skypremiuminternational.app.domain.models.ean.CancelPenalty;
import com.skypremiuminternational.app.domain.models.ean.Child;
import com.skypremiuminternational.app.domain.models.ean.Room;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.observables.GroupedObservable;

public class BookingDetailMapper {
  private final TourismFeeAggregator tourismFeeAggregator;
  private List<String> euCountryCodes;
  private final OccupancyArranger occupancyArranger;

  @Inject
  public BookingDetailMapper(OccupancyArranger occupancyArranger,
                             TourismFeeAggregator tourismFeeAggregator) {
    this.occupancyArranger = occupancyArranger;
    this.tourismFeeAggregator = tourismFeeAggregator;
    euCountryCodes = Arrays.asList(Constants.EU_COUNTRY_CODE);
  }

  public BookingDetail map(Map<String, PropertyContent> content, ItineraryResponse itinerary,
                           BookResponse bookResponse, String priceResponse) {
    if (bookResponse.getEanResult().get(0).getErrors() != null
        && bookResponse.getEanResult().get(0).getErrors().size() > 0) {
      return mapFail(bookResponse.getEanResult().get(0).getErrors());
    } else {
      return mapSuccess(content, itinerary, bookResponse, priceResponse);
    }
  }

  public BookingDetail mapFail(List<BookResponse.Error> errors) {
    String creditNumber = "";
    String creditType = "";
    String paymentStatus = "";
    String hotelName = "";
    String roomType = "";
    String bedType = "";
    String roomImage = "";
    int adultCount = 0;
    int childCount = 0;
    List<GuestDetail> guestDetails = new ArrayList<>();
    List<Amenity> amenities = new ArrayList<>();
    List<TourismFee> tourismFees = new ArrayList<>();
    double subTotal = 0;
    double tourismFee = 0;
    double grandTotal = 0;
    String bookingPeriod = "";
    String city = "Singapore";
    int nightCount = 0;
    String bookingStatus = "";

    List<String> errorMessages = new ArrayList<>();
    for (BookResponse.Error error : errors) {
      errorMessages.add(error.message);
    }
    return BookingDetail.builder()
        .itineraryId("")
        .creditNumber(creditNumber)
        .creditType(creditType)
        .paymentStatus(paymentStatus)
        .bookingStatus(bookingStatus)
        .hotelName(hotelName)
        .city(city)
        .roomType(roomType)
        .bedType(bedType)
        .roomImage(roomImage)
        .bookingPeriod(bookingPeriod)
        .nightCount(nightCount)
        .adultCount(adultCount)
        .childCount(childCount)
        .guestDetails(guestDetails)
        .amenities(amenities)
        .subTotal(subTotal)
        .tourismFee(tourismFee)
        .fees(tourismFees)
        .grandTotal(grandTotal)
        .errorMessages(errorMessages)
        .success(false)
        .totalGuest("")
        .tourismFeeCurrency("")
        .build();
  }

  public BookingDetail mapSuccess(Map<String, PropertyContent> contentMap,
                                  ItineraryResponse itinerary,
                                  BookResponse bookResponse, String skyDollar) {
    String creditNumber = "";
    String creditType = "";
    String paymentStatus = "";
    String hotelName = "";
    String roomType = "";
    String bedType = "";
    String roomImage = "";
    int adultCount = 0;
    int childCount = 0;
    int roomCount = 0;
    List<GuestDetail> guestDetails = new ArrayList<>();
    List<Amenity> amenities = new ArrayList<>();
    List<TourismFee> tourismFees = new ArrayList<>();
    List<RoomsItem> roomsItems = new ArrayList<>();
    double subTotal = 0;
    double tourismFee = 0;
    double grandTotal = 0;
    String totalGuest = "";
    String tourismCurrency = "";
    String bookingPeriod = "";
    String city = "Singapore";
    int nightCount = 0;
    String bookingStatus = "";

    if (itinerary.getRooms() != null && itinerary.getRooms().size() > 0) {
      roomCount = itinerary.getRooms().size();
      RoomsItem room = itinerary.getRooms().get(0);
      bookingStatus = room.getStatus();
      String checkIn = room.getArrival();
      String checkOut = room.getDeparture();
      if (!TextUtils.isEmpty(checkIn) && !TextUtils.isEmpty(checkOut)) {
        try {
          SimpleDateFormat simpleDateFormat =
              new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
          Calendar checkInDate = Calendar.getInstance();
          Calendar checkOutDate = Calendar.getInstance();
          checkInDate.setTime(simpleDateFormat.parse(checkIn));
          checkOutDate.setTime(simpleDateFormat.parse(checkOut));
          bookingPeriod = formatDateRange(checkInDate, checkOutDate);
          nightCount =
              (int) ((checkOutDate.getTimeInMillis() - checkInDate.getTimeInMillis()) / (1000
                  * 60
                  * 60
                  * 24));
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }

    List<AdditionalData.Room> rooms = null;
    if (bookResponse.getAdditionalData() != null
        && bookResponse.getAdditionalData().size() > 0
        && bookResponse.getAdditionalData().get(0).rooms != null) {
      creditNumber = bookResponse.getAdditionalData().get(0).payments.get(0).number;
      creditType = bookResponse.getAdditionalData().get(0).payments.get(0).card_type;
      rooms = bookResponse.getAdditionalData().get(0).rooms;
    }

    PropertyContent propertyContent = contentMap.get(itinerary.getPropertyId());
    if (propertyContent != null) {

      Map<String, Rates> rateMap = propertyContent.getRates();
      Map<String, RoomContent> roomContentMap = propertyContent.getRooms();

      boolean isEurope = false;

      if (propertyContent.getAddress() != null) {
        isEurope = isEurope(propertyContent.getAddress().getCountryCode());
      }

      hotelName = propertyContent.getName();

      roomsItems.add(itinerary.getRooms().get(0));

      List<Child> children = new ArrayList<>();

      if (itinerary.getRooms() != null) {
        roomCount = itinerary.getRooms().size();
        for (RoomsItem roomItem : itinerary.getRooms()) {
          adultCount += roomItem.getNumberOfAdults();
          if (roomItem.getChildAges() != null) {
            for (int i = 0; i < roomItem.getChildAges().size(); i++) {
              childCount++;
              Integer age = roomItem.getChildAges().get(i);
              children.add(new Child(age, Constants.CHILDREN_AGES[age], "Child #" + (i + 1)));
            }
          }
        }
      }

      totalGuest = occupancyArranger.arrangeTotalGuest(adultCount, children);

      List<String> occupancies =
          occupancyArranger.arrangeChildAges(roomCount, adultCount, children);

      String totalNight = nightCount > 1 ? nightCount + " Nights" : nightCount + " Night";
      for (int i = 0; i < itinerary.getRooms().size(); i++) {
        RoomsItem roomItem = itinerary.getRooms().get(i);

        if (roomItem.getRate() != null && roomItem.getRate().getFees() != null) {
          for (Fee fee : roomItem.getRate().getFees()) {
            String currency = "";
            if (fee.currency != null) {
              currency = fee.currency;
            }
            TourismFee tf = TourismFee.builder()
                .price(fee.value)
                .currency(currency)
                .feesType(fee.type.replace("_", " "))
                .build();
            tourismFees.add(tf);
            tourismFee += Double.parseDouble(fee.value);
            if (fee.currency != null && tourismCurrency.isEmpty()) {
              tourismCurrency = fee.currency;
            }
          }
        }

        double[] prices = calculatePrices(roomItem.getRate().getPricing().getNightly());
        subTotal += prices[0] + prices[1];
        grandTotal += prices[0] + prices[1];

        String salutation = "";
        boolean isSmoking = false;
        String contactNumber = "";
        String specialRequest = "";

        if (rooms != null && rooms.size() == itinerary.getRooms().size()) {
          AdditionalData.Room room = rooms.get(i);
          salutation = room.title;
          isSmoking = room.smoking;
          contactNumber = room.phone;
          specialRequest = room.special_request;
        }

        GuestDetail guestDetail =
            new GuestDetail(salutation, isSmoking, contactNumber, specialRequest,
                roomItem.getGivenName() + " " + roomItem.getFamilyName(),
                "Room #" + (i + 1) + ": " + occupancies.get(i), totalNight, prices[0], prices[1],
                isEurope);
        guestDetails.add(guestDetail);

        Rates rates = rateMap.get(roomItem.getRate().getId() + "");
        if (rates != null) {
          Map<String, AmenityItem> amenityItemMap = rates.amenities;

          if (amenityItemMap != null) {
            if (roomItem.getRate() != null) {

              if (roomItem.getRate().getAmenities() != null) {
                for (Integer amenityId : roomItem.getRate().getAmenities()) {
                  AmenityItem amenityItem = amenityItemMap.get("" + amenityId);
                  if (amenityItem != null) {
                    Amenity amenity = Amenity.builder()
                        .id(amenityItem.id)
                        .description(amenityItem.name)
                        .isCancellationPolicy(false)
                        .build();
                    addToAmenities(amenities, amenity);
                  }
                }
              }
            }
          }
        }


        if (roomItem.getRate().getCancelPenalties() != null
            && roomItem.getRate().getCancelPenalties().size() > 0) {
          CancelPenaltiesItem cancelPenaltiesItem =
              roomItem.getRate().getCancelPenalties().get(0);

          List<CancelPenalty> cancelsPenaltiesList = new ArrayList<>();
          for(CancelPenaltiesItem cancelPenaltiesItem1 : roomItem.getRate().getCancelPenalties()) {
            CancelPenalty cancelPenalty = CancelPenalty.builder()
                    .start(cancelPenaltiesItem1.getStart())
                    .end(cancelPenaltiesItem1.getEnd())
                    .percent(cancelPenaltiesItem1.getPercent())
                    .amount(cancelPenaltiesItem1.getAmount())
                    .nights(cancelPenaltiesItem1.getNights())
                    .isRefundable(roomItem.getRate().isRefundable())
                    .currency(cancelPenaltiesItem1.getCurrency())
                    .build();
            cancelsPenaltiesList.add(cancelPenalty);

          }

          CancelPenalty cancelPenalty = CancelPenalty.builder()
              .start(cancelPenaltiesItem.getStart())
              .end(cancelPenaltiesItem.getEnd())
              .percent(cancelPenaltiesItem.getPercent())
              .amount(cancelPenaltiesItem.getAmount())
              .nights(cancelPenaltiesItem.getNights())
              .isRefundable(roomItem.getRate().isRefundable())
              .currency(cancelPenaltiesItem.getCurrency())
              .cancelPenaltyList(cancelsPenaltiesList)
              .build();


          Amenity cancellationPolicy = Amenity.builder()
              .id(0)
              .description("")
              .isCancellationPolicy(true)
              .cancelPenalty(cancelPenalty)
              .build();
          addToAmenities(amenities, cancellationPolicy);
        }

        RoomContent roomContent = null;
        if (roomContentMap != null) {
          roomContent = roomContentMap.get(roomItem.getId());
        }
        if (roomContent != null) {

          if (TextUtils.isEmpty(roomType)) {
            roomType = roomContent.getName();
          }
          if (TextUtils.isEmpty(bedType)) {
            Map<String, BedGroup> bedGroupsItemMap = roomContent.getBedGroups();
            if (bedGroupsItemMap != null) {
              BedGroup bedGroup = bedGroupsItemMap.get(roomItem.getBedGroupId());
              if (bedGroup != null) {
                bedType = bedGroup.description;
                String count = roomCount > 1 ? roomCount + " Rooms" : roomCount + " Room";
                bedType = bedType + " (" + count + ")";
              }
            }
          }

          if (TextUtils.isEmpty(roomImage) && roomContent.getImages() != null) {
            for (ImagesItem image : roomContent.getImages()) {
              if (image.getLinks() != null && image.getLinks().getDimension350Px() != null) {
                roomImage = image.getLinks().getDimension350Px().getHref();
                break;
              }
            }
          }
        }
      }
      city = propertyContent.getAddress().getLine1();
      city += ", " + propertyContent.getAddress().getCity();
      if (propertyContent.getAddress().getPostalCode() != null) {
        city += ", " + propertyContent.getAddress().getPostalCode();
      }
    }
    tourismFees = tourismFeeAggregator.aggregate(tourismFees);

    return BookingDetail.builder()
        .itineraryId(itinerary.getItineraryId())
        .creditNumber(creditNumber)
        .creditType(creditType)
        .paymentStatus(paymentStatus)
        .bookingStatus(bookingStatus)
        .hotelName(hotelName)
        .city(city)
        .roomType(roomType)
        .bedType(bedType)
        .roomImage(roomImage)
        .bookingPeriod(bookingPeriod)
        .nightCount(nightCount)
        .adultCount(adultCount)
        .childCount(childCount)
        .guestDetails(guestDetails)
        .amenities(amenities)
        .subTotal(subTotal)
        .tourismFee(tourismFee)
        .fees(tourismFees)
        .grandTotal(grandTotal)
        .tourismFeeCurrency(tourismCurrency)
        .success(true)
        .totalGuest(totalGuest)
        .skyDollar(skyDollar)
        .roomsItems(roomsItems)
        .build();
  }

  private void addToAmenities(List<Amenity> amenities, Amenity amenity) {
    boolean alreadyInTheList = false;
    for (Amenity amenity1 : amenities) {

      if (amenity.isCancellationPolicy()) {
        if (amenity1.isCancellationPolicy()) {
          alreadyInTheList = true;
          break;
        }
      } else {
        if (!amenity1.isCancellationPolicy() && amenity.description()
            .equalsIgnoreCase(amenity1.description())) {
          alreadyInTheList = true;
          break;
        }
      }
    }
    if (!alreadyInTheList) {
      amenities.add(amenity);
    }
  }

  private double[] calculatePrices(List<List<NightlyItem>> nightly) {
    double[] prices = new double[2];
    if (nightly != null) {
      for (List<NightlyItem> nightlyItems : nightly) {
        for (NightlyItem nightlyItem : nightlyItems) {
          if (nightlyItem.getType().equalsIgnoreCase("tax_and_service_fee")) {
            prices[1] += Double.parseDouble(nightlyItem.getValue());
          }

          if (!nightlyItem.getType().contains("tax")) {
            prices[0] += Double.parseDouble(nightlyItem.getValue());
          }
        }
      }
    }
    return prices;
  }

  /*This one is used for history booking*/
  public BookingDetail map(Booking booking,
                           ItineraryResponse itinerary,
                           Map<String, PropertyContent> contentMap,
                           BookingData bookingData,
                           ArrayList<BookingHistory.Room> rooms) {

    String tourismCurrency = "";
    String creditNumber = "";
    String creditType = "";
    String paymentStatus = "";
    String hotelName = "";
    String roomType = "";
    String bedType = "";
    String roomImage = "";
    int adultCount = 0;
    int childCount = 0;
    int roomCount = 0;
    List<GuestDetail> guestDetails = new ArrayList<>();
    List<RoomsItem> roomsItems = new ArrayList<>();
    List<Amenity> amenities = new ArrayList<>();
    List<TourismFee> tourismFees = new ArrayList<>();
    double subTotal = 0;
    double tourismFee = 0;
    double grandTotal = 0;
    String totalGuest = "";
    String skyDollar = "";
    String bookingPeriod = "";
    String city = "Singapore";
    int nightCount = 0;

    skyDollar = booking.getSkyDollar();

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    if (bookingData.getAdditionalData().payments != null
        && bookingData.getAdditionalData().payments.size() > 0) {
      AdditionalData.Payment payment = bookingData.getAdditionalData().payments.get(0);
      creditNumber = payment.number;
      creditType = payment.card_type;
    }

    try {
      Calendar checkInDate = Calendar.getInstance();
      Calendar checkOutDate = Calendar.getInstance();
      checkInDate.setTime(simpleDateFormat.parse(booking.getCheckIn()));
      checkOutDate.setTime(simpleDateFormat.parse(booking.getCheckOut()));
      bookingPeriod = formatDateRange(checkInDate, checkOutDate);
      nightCount = (int) ((checkOutDate.getTimeInMillis() - checkInDate.getTimeInMillis()) / (1000
          * 60
          * 60
          * 24));
    } catch (Exception e) {
      e.printStackTrace();
    }

    PropertyContent propertyContent = contentMap.get(itinerary.getPropertyId());

    if (propertyContent != null) {
      Map<String, Rates> rateMap = propertyContent.getRates();
      Map<String, RoomContent> roomContentMap = propertyContent.getRooms();

      boolean isEurope = false;

      if (propertyContent.getAddress() != null) {
        isEurope = isEurope(propertyContent.getAddress().getCountryCode());
      }

      hotelName = propertyContent.getName();

      List<Child> children = new ArrayList<>();

      if (itinerary.getRooms() != null) {
        roomCount = itinerary.getRooms().size();
        for (RoomsItem roomItem : itinerary.getRooms()) {
          adultCount += roomItem.getNumberOfAdults();
          if (roomItem.getChildAges() != null) {
            for (int i = 0; i < roomItem.getChildAges().size(); i++) {
              childCount++;
              Integer age = roomItem.getChildAges().get(i);
              children.add(new Child(age, Constants.CHILDREN_AGES[age], "Child #" + (i + 1)));
            }
          }
        }
      }

      roomsItems.add(itinerary.getRooms().get(0));

      totalGuest = occupancyArranger.arrangeTotalGuest(adultCount, children);


      List<String> occupancies =
          occupancyArranger.arrangeChildAges(roomCount, adultCount, children);


      String totalNight = nightCount > 1 ? nightCount + " Nights" : nightCount + " Night";

      for (int i = 0; i < itinerary.getRooms().size(); i++) {
        RoomsItem roomItem = itinerary.getRooms().get(i);
        if (roomItem.getRate() != null && roomItem.getRate().getPricing().getFees() != null) {
          roomItem.getRate().getPricing().getFees() ;
            TourismFee tf = TourismFee.builder()
                .feesType("mandatory_tax")
                .price(String.valueOf(roomItem.getRate().getPricing().getFees().getMandatory_tax().getBillable_currency().getValue()))
                .currency(roomItem.getRate().getPricing().getTotals().getInclusive().getBillableCurrency().getCurrency())
                .build();
            tourismFees.add(tf);

            tourismFee += Double.parseDouble(roomItem.getRate().getPricing().getFees().getMandatory_tax().getBillable_currency().getValue());
            if (roomItem.getRate().getPricing().getFees().getMandatory_tax().getBillable_currency().getCurrency() == null && tourismCurrency.isEmpty()) {
              tourismCurrency = roomItem.getRate().getPricing().getTotals().getInclusive().getBillableCurrency().getCurrency();
            }

        }
        double[] prices = calculatePrices(roomItem.getRate().getPricing().getNightly());

        subTotal += prices[0] + prices[1];
        grandTotal += prices[0] + prices[1];

        String salutation = "";
        boolean isSmoking = false;
        String contactNumber = "";
        String specialRequest = "";

        if (rooms != null && rooms.size() == itinerary.getRooms().size()) {
          BookingHistory.Room room = rooms.get(i);
          salutation = room.title();
          isSmoking = room.smoking();
          contactNumber = room.phone();
          specialRequest = room.specialRequest();
        }

        GuestDetail guestDetail =
            new GuestDetail(salutation, isSmoking, contactNumber, specialRequest,
                roomItem.getGivenName() + " " + roomItem.getFamilyName(),
                "Room #" + (i + 1) + ": " + occupancies.get(i), totalNight, prices[0], prices[1],
                isEurope);
        guestDetails.add(guestDetail);

        Rates rates = rateMap.get(roomItem.getRate().getId() + "");
        if (rates != null) {
          Map<String, AmenityItem> amenityItemMap = rates.amenities;

          if (amenityItemMap != null) {
            if (roomItem.getRate() != null) {

              if (roomItem.getRate().getAmenities() != null) {
                for (Integer amenityId : roomItem.getRate().getAmenities()) {
                  AmenityItem amenityItem = amenityItemMap.get("" + amenityId);
                  if (amenityItem != null) {
                    Amenity amenity = Amenity.builder()
                        .id(amenityItem.id)
                        .description(amenityItem.name)
                        .isCancellationPolicy(false)
                        .build();
                    addToAmenities(amenities, amenity);
                  }
                }
              }
            }
          }
        }

        if (roomItem.getRate().getCancelPenalties() != null
            && roomItem.getRate().getCancelPenalties().size() > 0) {
          CancelPenaltiesItem cancelPenaltiesItem =
              roomItem.getRate().getCancelPenalties().get(0);
          CancelPenalty cancelPenalty = CancelPenalty.builder()
              .start(cancelPenaltiesItem.getStart())
              .end(cancelPenaltiesItem.getEnd())
              .percent(cancelPenaltiesItem.getPercent())
              .amount(cancelPenaltiesItem.getAmount())
              .nights(cancelPenaltiesItem.getNights())
              .isRefundable(roomItem.getRate().isRefundable())
              .currency(cancelPenaltiesItem.getCurrency())
              .build();

          Amenity cancellationPolicy = Amenity.builder()
              .id(0)
              .description("")
              .isCancellationPolicy(true)
              .cancelPenalty(cancelPenalty)
              .build();
          addToAmenities(amenities, cancellationPolicy);
        }

        RoomContent roomContent = null;
        if (roomContentMap != null) {
          roomContent = roomContentMap.get(roomItem.getId());
        }

        if (roomContent != null) {
          if (TextUtils.isEmpty(roomType)) {
            roomType = roomContent.getName();
          }
          if (TextUtils.isEmpty(bedType)) {
            Map<String, BedGroup> bedGroupsItemMap = roomContent.getBedGroups();
            if (bedGroupsItemMap != null) {
              BedGroup bedGroup = bedGroupsItemMap.get(roomItem.getBedGroupId());
              if (bedGroup != null) {
                bedType = bedGroup.description;
                String count = roomCount > 1 ? roomCount + " Rooms" : roomCount + " Room";
                bedType = bedType + "(" + count + ")";
              }
            }
          }

          if (TextUtils.isEmpty(roomImage) && roomContent.getImages() != null) {
            for (ImagesItem image : roomContent.getImages()) {
              if (image.getLinks() != null && image.getLinks().getDimension350Px() != null) {
                roomImage = image.getLinks().getDimension350Px().getHref();
                break;
              }
            }
          }
        }
      }
      city = propertyContent.getAddress().getLine1();
      city += ", " + propertyContent.getAddress().getCity();
      if (propertyContent.getAddress().getPostalCode() != null) {
        city += ", " + propertyContent.getAddress().getPostalCode();
      }



    }
    tourismFees = tourismFeeAggregator.aggregate(tourismFees);

    return BookingDetail.builder()
        .itineraryId(itinerary.getItineraryId())
        .creditNumber(creditNumber)
        .creditType(creditType)
        .paymentStatus(paymentStatus)
        .bookingStatus(booking.getBookingStatus())
        .hotelName(hotelName)
        .city(city)
        .roomType(roomType)
        .bedType(bedType)
        .roomImage(roomImage)
        .bookingPeriod(bookingPeriod)
        .nightCount(nightCount)
        .adultCount(adultCount)
        .childCount(childCount)
        .guestDetails(guestDetails)
        .amenities(amenities)
        .subTotal(subTotal)
        .tourismFee(tourismFee)
        .tourismFeeCurrency(tourismCurrency)
        .fees(tourismFees)
        .grandTotal(grandTotal)
        .success(true)
        .totalGuest(totalGuest)
        .skyDollar(skyDollar)
        .roomsItems(roomsItems)
        .build();
  }

  private String formatDateRange(Calendar checkInDate, Calendar checkOutDate) {
    SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DATE_RANGE_FORMAT,
        Locale.getDefault());
    SimpleDateFormat dayFormat = new SimpleDateFormat(Constants.DAY_RANGE_FORMAT,
        Locale.getDefault());
    final String formattedCheckIn = dateFormat.format(checkInDate.getTime());
    final String formattedCheckOut = dateFormat.format(checkOutDate.getTime());

    if (checkInDate.get(Calendar.YEAR) != checkOutDate.get(Calendar.YEAR)) {
      return formattedCheckIn + " - " + formattedCheckOut;
    } else if (checkInDate.get(Calendar.MONTH) != checkOutDate.get(Calendar.MONTH)) {
      return dayFormat.format(checkInDate.get(Calendar.DAY_OF_MONTH))
          + " "
          + formattedCheckIn.split(" ")[1]
          + " - "
          + formattedCheckOut;
    } else {
      if(String.valueOf(checkInDate.get(Calendar.DAY_OF_MONTH)).length() > 1){
        return String.valueOf(checkInDate.get(Calendar.DAY_OF_MONTH)) + " - " + formattedCheckOut;
      }else {
        return "0"+String.valueOf(checkInDate.get(Calendar.DAY_OF_MONTH)) + " - " + formattedCheckOut;
      }
    }
  }

  private boolean isEurope(String countryCode) {
    return euCountryCodes.contains(countryCode);
  }
}
