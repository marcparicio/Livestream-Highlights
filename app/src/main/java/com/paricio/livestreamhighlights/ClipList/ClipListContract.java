package com.paricio.livestreamhighlights.ClipList;


import android.support.annotation.NonNull;

import com.paricio.livestreamhighlights.BasePresenter;
import com.paricio.livestreamhighlights.BaseView;
import com.paricio.livestreamhighlights.Model.Clip;

import java.util.List;

public interface ClipListContract {

    interface View extends BaseView<Presenter> {
        void setLoadingIndicator(boolean active);

        void refreshClipList(List<Clip> clipList);

        void showClip(Clip clip);

        void addClipsToList(List<Clip> clipsToBeProcessed);
    }

    interface Presenter extends BasePresenter {
        void loadClips(boolean forceReload);

        void showClip(@NonNull Clip clip);

        void unsubscribe();

        void onScrolled(int visibleItemCount, int totalItemCount, int firstVisibleItemPosition);
    }
}
