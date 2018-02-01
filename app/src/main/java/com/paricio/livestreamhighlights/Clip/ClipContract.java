package com.paricio.livestreamhighlights.Clip;


import com.paricio.livestreamhighlights.BasePresenter;
import com.paricio.livestreamhighlights.BaseView;
import com.paricio.livestreamhighlights.Model.Clip;

public interface ClipContract  {

    interface View extends BaseView<Presenter> {
        void setLoading(boolean active);

        void loadClip(Clip clip);
    }

    interface Presenter extends BasePresenter {
        void loadClip(Clip clip);
    }
}
