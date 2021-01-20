package com.skypremiuminternational.app.domain.interactor.ean;

import com.skypremiuminternational.app.app.model.BookingHistory;
import com.skypremiuminternational.app.data.model.ean.booking.history.Booking;
import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;
import com.skypremiuminternational.app.domain.models.booking.BookingDetail;

import java.util.ArrayList;

import javax.inject.Inject;

import rx.Observable;

public class GetBookingDetailValue extends UseCase<Booking, GetBookingDetailValue.Params> {

    @Inject
    protected GetBookingDetailValue(DataManager dataManager,
                               ThreadExecutor subscriberThread,
                               PostExecutionThread observerThread) {
        super(dataManager, subscriberThread, observerThread);
    }

    @Override
    public Observable<Booking> provideObservable(GetBookingDetailValue.Params params) {
        return getDataManager().getBookingDetailvalue(params);
    }

    public static class Params {
        public final String bookingId;
        public final ArrayList<BookingHistory.Room> rooms;

        public Params(String bookingId,
                      ArrayList<BookingHistory.Room> rooms) {
            this.bookingId = bookingId;
            this.rooms = rooms;
        }
    }
}
