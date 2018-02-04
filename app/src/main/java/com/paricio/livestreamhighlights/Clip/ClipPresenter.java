package com.paricio.livestreamhighlights.Clip;


import android.support.annotation.NonNull;

import com.paricio.livestreamhighlights.Model.Clip;

public class ClipPresenter implements ClipContract.Presenter {

    private ClipContract.View clipView;


    public ClipPresenter(@NonNull ClipContract.View view) {
        this.clipView = view;
        clipView.setPresenter(this);
    }

    @Override
    public void start() {
        clipView.initializePlayer();
    }

    @Override
    public void stop() {
        clipView.releasePlayer();
    }
}
