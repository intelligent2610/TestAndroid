package com.sphtech.ui.main;

import android.util.Log;

import androidx.annotation.NonNull;

import com.rx2androidnetworking.Rx2AndroidNetworking;
import com.sphtech.data.networking.ApiEndPoint;
import com.sphtech.model.APIResponse;
import com.sphtech.model.MobileData;
import com.sphtech.utils.FetcherListener;

import java.util.List;
import java.util.Objects;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;

public class MainPresenter implements MainContract.Presenter {

    public static final int TOP = 2018;
    public static final int BOTTOM = 2008;

    private List<MobileData.Record> listRecords = null;
    private MainContract.View view;
    private Disposable disposable;
    private FetcherListener fetcherListener;
    private static OkHttpClient okHttpClient;

    public MainPresenter() {
        OkHttpClient.Builder okBuilder = new OkHttpClient().newBuilder();
        okHttpClient = okBuilder.build();
    }


    @Override
    public void fetchMobileData() {
        if (disposable != null) {
            disposable.dispose();
        }
        disposable = getMobileData().observeOn(AndroidSchedulers.mainThread()).
                doOnSubscribe((s)-> {if(fetcherListener!= null )fetcherListener.beginFetching();}).
                doFinally(()->{if(fetcherListener!= null )fetcherListener.doneFetching();}).
                subscribeOn(Schedulers.io()).
                subscribe(this::handleGetLessonsResponse, error -> view.showErrorMessage(error.toString()));
    }

    @Override
    public void attachView(MainContract.View v) {
        view = v;
    }

    @Override
    public void registerIdling(FetcherListener fetcherListener) {
        this.fetcherListener = fetcherListener;
    }


    private void handleGetLessonsResponse(APIResponse apiResponse) {
        view.hideRefreshing();
        if (apiResponse.isSuccess()) {
            try {
                MobileData mobileData = (MobileData) apiResponse.getData(MobileData.class);
                assert mobileData != null;
                mobileData.filterRange(BOTTOM, TOP);
                mobileData.filterDecrease();
                listRecords = (Objects.requireNonNull(mobileData.getRecords()));
                view.refreshAdapter(listRecords);
            } catch (NullPointerException ex) {
                ex.printStackTrace();
            }
        } else {
            handleApiError(apiResponse);
        }
    }


    void handleApiError(@NonNull APIResponse apiResponse) {
        view.showErrorMessage(apiResponse.getHelp());
    }

    private Observable<APIResponse> getMobileData() {
        return Rx2AndroidNetworking.get(ApiEndPoint.END_POINT_GET_MOBILE_DATA)
                .setOkHttpClient(okHttpClient)
                .build()
                .getObjectObservable(APIResponse.class);
    }

}
