package com.skypremiuminternational.app.data.mapper;

import com.skypremiuminternational.app.app.model.BookerInfo;
import com.skypremiuminternational.app.data.network.request.BookRoomRequest;
import com.skypremiuminternational.app.data.utl.IpAddressFetcher;
import com.skypremiuminternational.app.domain.interactor.ean.BookRoom;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class BookRequestMapper {

  @Inject
  public BookRequestMapper() {
  }

  public BookRoomRequest map(BookRoom.Params params, String sessionId, String ipAddress) {

    BookRoomRequest.Address address = new BookRoomRequest.Address();
    address.line_1 = params.paymentDetail.streetAddress;
    address.line_2 = "";
    address.line_3 = params.paymentDetail.unitNumber;
    address.city = params.paymentDetail.city;
    address.state_province_code = params.paymentDetail.state;
    address.postal_code = params.paymentDetail.postalCode;
    address.country_code = params.paymentDetail.countryCode;

    BookRoomRequest.BillingContact billingContact = new BookRoomRequest.BillingContact();
    billingContact.given_name = params.paymentDetail.firstName;
    billingContact.family_name = params.paymentDetail.lastName;
    billingContact.email = params.paymentDetail.emailAddress;
    billingContact.phone = params.paymentDetail.phoneNumber;
    billingContact.address = address;

    BookRoomRequest.Payment payment = new BookRoomRequest.Payment();
    payment.type = "customer_card";
    payment.number = params.paymentDetail.cardNumber;
    payment.card_type = params.paymentDetail.cardTypeAbbr;
    payment.security_code = params.paymentDetail.securityCode;
    payment.expiration_month = params.paymentDetail.expiryMonth;
    payment.expiration_year = params.paymentDetail.expiryYear;
    payment.billing_contact = billingContact;

    List<BookRoomRequest.Payment> payments = new ArrayList<>();
    payments.add(payment);

    List<BookRoomRequest.Room> rooms = new ArrayList<>();
    for (BookerInfo bookerInfo : params.bookerInfos) {
      BookRoomRequest.Room room = new BookRoomRequest.Room();
      room.title = bookerInfo.salutation;
      room.given_name = bookerInfo.firstName;
      room.family_name = bookerInfo.lastName;
      room.email = params.email;
      room.phone = bookerInfo.phoneCode + bookerInfo.phoneNumber;
      room.smoking = bookerInfo.isSmoking;
      room.special_request = bookerInfo.specialRequest;
      room.number_of_adults = bookerInfo.numberOfAdult;
      room.child_ages = bookerInfo.childAges;
      rooms.add(room);
    }

    BookRoomRequest.CustomerData customerData = new BookRoomRequest.CustomerData();
    customerData.customer_session_id = sessionId;
    customerData.customer_ip = ipAddress;
    customerData.email_address = params.email;
    customerData.book_for_so = params.isBookForSomeone;
    customerData.check_in = params.checkIn;
    customerData.check_out = params.checkOut;
    customerData.property_id = params.propertyId;
    customerData.bed_group_selected = params.bedGroup;
    customerData.bed_groups_of_room = params.bedGroups;
    customerData.cancellation_policy = params.paymentDetail.cancellationPolicy;
    customerData.country_payment_will_be_processed = params.paymentDetail.processingCountry;
    customerData.sky_dollar = params.skyDollar;

    BookRoomRequest.Phone phone = new BookRoomRequest.Phone();
    phone.country_code = params.paymentDetail.phoneCode.replaceAll("[^0-9]", "");
    phone.number = params.paymentDetail.phoneNumber;

    BookRoomRequest.RequestData requestData = new BookRoomRequest.RequestData();
    payments.add(payment);
    requestData.affiliate_reference_id = 0;
    requestData.rooms = rooms;
    requestData.email = params.paymentDetail.emailAddress;
    requestData.payments = payments;
    requestData.affiliate_metadata = "";
    requestData.phone = phone;
    requestData.customer_data = customerData;



    BookRoomRequest request = new BookRoomRequest();
    request.token = params.bookingLink;
    request.request_data = requestData;
    return request;
  }
}
