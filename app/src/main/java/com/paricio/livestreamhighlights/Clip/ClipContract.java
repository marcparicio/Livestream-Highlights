package com.paricio.livestreamhighlights.Clip;


import com.paricio.livestreamhighlights.BasePresenter;
import com.paricio.livestreamhighlights.BaseView;
import com.paricio.livestreamhighlights.Model.Clip;

public interface ClipContract  {

    interface View extends BaseView<Presenter> {

        void initializePlayer();

        void releasePlayer();

        boolean isActive();
    }

    interface Presenter extends BasePresenter {

        void stop();
    }
}
