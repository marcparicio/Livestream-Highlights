package com.paricio.livestreamhighlights.ClipList;


import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.paricio.livestreamhighlights.Model.Clip;
import com.paricio.livestreamhighlights.Network.ClipsService;
import com.paricio.livestreamhighlights.Network.RetrofitHelper;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class ClipListPresenter implements ClipListContract.Presenter {

    private int PAGE;
    private int pageSize;
    private final ClipListContract.View clipListView;
    private boolean firstLoad = true;
    private ClipsService clipsService;
    private Disposable clipsDisposable;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private boolean forceRefresh = false;

    public ClipListPresenter(@NonNull ClipListContract.View clipListView) {
        this.clipListView = clipListView;

        PAGE = 0;
        clipsService = new RetrofitHelper().getCityService();
        clipListView.setPresenter(this);
    }

    @Override
    public void start() {
        loadClips(false);
    }

    @Override
    public void loadClips(boolean forceReload) {
        if (forceReload) PAGE = 0;
        loadClips(forceReload || firstLoad,true);
        firstLoad = false;
    }

    public void loadClips(boolean forceReaload, boolean showLoadingUI) {
        if (showLoadingUI) {
            clipListView.setLoadingIndicator(true);
        }
        forceRefresh = forceReaload;
        isLoading = true;
        requestClips();
    }

    @Override
    public void showClip(@NonNull Clip clip) {
        clipListView.showClip(clip);
    }

    @Override
    public void unsubscribe() {
        clipsDisposable.dispose();
    }

    @Override
    public void onScrolled(int visibleItemCount, int totalItemCount, int firstVisibleItemPosition) {
        if (!isLoading && (visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                && firstVisibleItemPosition >= 0) {
            if (!isLastPage) {
                loadClips(false);
            }
        }
    }

    private void requestClips() {
        clipsDisposable = clipsService.getAllMeetings(PAGE).
                observeOn(AndroidSchedulers.mainThread()).
                subscribeOn(Schedulers.newThread()).
                subscribe(this::processClipList);
    }

    private void processClipList(List<Clip> clipsToBeProcessed) {
        if (clipsToBeProcessed != null && !clipsToBeProcessed.isEmpty()) {
            if (PAGE == 0 || forceRefresh) {
                clipListView.refreshClipList(clipsToBeProcessed);
                pageSize = clipsToBeProcessed.size();
            }
            else {
                clipListView.addClipsToList(clipsToBeProcessed);
            }

            if (PAGE != 0 && clipsToBeProcessed.size() < pageSize) {
                isLastPage = true;
            }
            else PAGE++;
        }
        forceRefresh = false;
        isLoading = false;
        clipListView.setLoadingIndicator(false);
    }


}
