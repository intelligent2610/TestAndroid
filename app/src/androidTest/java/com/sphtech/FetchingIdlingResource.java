package com.sphtech;

import androidx.test.espresso.IdlingResource;

import com.sphtech.utils.FetcherListener;

public class FetchingIdlingResource implements IdlingResource, FetcherListener {

    private boolean idle = true;
    private IdlingResource.ResourceCallback resourceCallback;

    @Override
    public String getName() {
        return FetchingIdlingResource.class.getName();
    }

    @Override
    public boolean isIdleNow() {
        return idle;
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        resourceCallback = callback;
    }

    @Override
    public void doneFetching() {
        idle = true;
        resourceCallback.onTransitionToIdle();
    }

    @Override
    public void beginFetching() {
        idle = false;
    }
}
