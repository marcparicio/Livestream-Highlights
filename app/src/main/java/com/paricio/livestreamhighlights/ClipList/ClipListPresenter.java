package com.paricio.livestreamhighlights.ClipList;


import android.support.annotation.NonNull;

import com.paricio.livestreamhighlights.Model.Clip;
import com.paricio.livestreamhighlights.Network.ClipsService;
import com.paricio.livestreamhighlights.Network.RetrofitHelper;

import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class ClipListPresenter implements ClipListContract.Presenter {


    private Scheduler processScheduler;
    private Scheduler androidScheduler;
    private ClipsService clipsService;
    private Disposable clipsDisposable;
    private int PAGE;
    private int pageSize;
    private final ClipListContract.View clipListView;
    private boolean firstLoad = true;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private boolean forceRefresh = false;

    public ClipListPresenter(@NonNull ClipListContract.View clipListView, @NonNull ClipsService clipsService,
                                @NonNull Scheduler androidScheduler, @NonNull Scheduler processScheduler) {
        this.clipListView = clipListView;

        PAGE = 0;
        this.clipsService = clipsService;
        this.processScheduler = processScheduler;
        this.androidScheduler = androidScheduler;
        clipListView.setPresenter(this);
    }

    @Override
    public void start() {
        loadClips(false);
        clipListView.checkWifi();
    }

    @Override
    public void loadClips(boolean forceReload) {
        if (forceReload) PAGE = 0;
        loadClips(forceReload || firstLoad,true);
        firstLoad = false;
    }

    private void loadClips(boolean forceReaload, boolean showLoadingUI) {
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
                observeOn(androidScheduler).
                subscribeOn(processScheduler).
                subscribe(this::processClipList);
    }

    private void processClipList(List<Clip> clipsToBeProcessed) {
        if (clipsToBeProcessed != null && !clipsToBeProcessed.isEmpty() && clipListView.isActive()) {
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
