package com.sphtech.ui.main;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.sphtech.R;
import com.sphtech.model.MobileData;
import com.sphtech.ui.main.adapter.AdapterMobileData;
import com.sphtech.utils.FetcherListener;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterMobileData.ClickListener,
        SwipeRefreshLayout.OnRefreshListener, MainContract.View {
    private AdapterMobileData mAdapterMobileData;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private MainContract.Presenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPresenter = new MainPresenter();
        mPresenter.attachView(this);
        initLayout();
    }

    public void registerIdling(FetcherListener fetcherListener) {
        mPresenter.registerIdling(fetcherListener);
    }

    private void initLayout() {
        mSwipeRefreshLayout = findViewById(R.id.swipe_refresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mAdapterMobileData = new AdapterMobileData(this, null, this);
        RecyclerView rc = findViewById(R.id.rc_list);
        rc.setAdapter(mAdapterMobileData);
        rc.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        onRefresh();
    }

    @Override
    public void onItemClicked(@NotNull View v, int position) {

    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        mPresenter.fetchMobileData();
    }

    @Override
    public void hideRefreshing() {
        if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void showErrorMessage(String message) {
        new AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage(message)

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                    // Continue with delete operation
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    @Override
    public void refreshAdapter(List<MobileData.Record> list) {
        mAdapterMobileData.set(list);
        mAdapterMobileData.notifyDataSetChanged();
    }
}
