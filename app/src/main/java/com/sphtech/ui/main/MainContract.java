package com.sphtech.ui.main;



import com.sphtech.model.MobileData;
import com.sphtech.utils.FetcherListener;

import java.util.List;

public interface MainContract {
    interface Presenter {
        void fetchMobileData();
        void attachView(MainContract.View v);
        void registerIdling(FetcherListener fetcherListener);
    }

    interface View   {
        void hideRefreshing();
        void showErrorMessage(String message);
        void refreshAdapter(List<MobileData.Record> list);
    }

}
