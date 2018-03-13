package com.paricio.livestreamhighlights;



import com.paricio.livestreamhighlights.ClipList.ClipListContract;
import com.paricio.livestreamhighlights.ClipList.ClipListPresenter;
import com.paricio.livestreamhighlights.Model.Clip;
import com.paricio.livestreamhighlights.Network.ClipsService;

import org.junit.Before;
import org.junit.Test;

import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.schedulers.TestScheduler;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 *  Unit tests of the implementatios of {@link ClipListPresenter}
 */

public class ClipListPresenterTest {

    private static List<Clip> CLIPS;

    @Mock
    private ClipListContract.View clipListView;

    @Mock
    private ClipsService clipsService;

    private ClipListPresenter clipListPresenter;
    private TestScheduler testScheduler;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        testScheduler = new TestScheduler();

        clipListPresenter = new ClipListPresenter(clipListView,clipsService,testScheduler,testScheduler);

        //We define a list of clips for the networking
        CLIPS = TestUtils.getFakeClipsList();

        //The presenter won't update the view list if it is not available
        when(clipListView.isActive()).thenReturn(true);
        when(clipsService.getAllMeetings(anyInt())).thenReturn(Single.just(CLIPS));
    }

    @Test
    public void startPresenter_checksWifi() {
        clipListPresenter.start();

        verify(clipListView).checkWifi();
    }

    @Test
    public void createPresenter_viewSetsPresenter() {
        testScheduler = new TestScheduler();

        clipListPresenter = new ClipListPresenter(clipListView, clipsService,testScheduler,testScheduler);

        verify(clipListView).setPresenter(clipListPresenter);
    }

    @Test
    public void loadFirstPageOfClipsFromServer() {
        clipListPresenter.loadClips(true);

        //Check that the view shows a loading indicator
        InOrder inOrder = Mockito.inOrder(clipListView);
        inOrder.verify(clipListView).setLoadingIndicator(true);
        testScheduler.triggerActions();

        verify(clipListView).refreshClipList(anyList());

        //When the list is loaded the loading indicator is hidden
        inOrder.verify(clipListView).setLoadingIndicator(false);
    }

    @Test
    public void loadAnyOtherPageOfClipsFromServer() {
        //Load first page
        clipListPresenter.loadClips(true);
        testScheduler.triggerActions();


        //Load another page
        clipListPresenter.loadClips(false);

        //Check that the view shows a loading indicator
        InOrder inOrder = Mockito.inOrder(clipListView);
        inOrder.verify(clipListView).setLoadingIndicator(true);
        testScheduler.triggerActions();

        verify(clipListView).refreshClipList(anyList());

        //When the list is loaded the loading indicator is hidden
        inOrder.verify(clipListView).setLoadingIndicator(false);
    }


    @Test
    public void showClipFromList() {
        Clip clip = any(Clip.class);
        clipListPresenter.showClip(clip);

        verify(clipListView).showClip(clip);
    }

    @Test
    public void scrollDown_loadsMoreClips() {
        //Fill the list with some clips
        clipListPresenter.loadClips(true);
        testScheduler.triggerActions();

        //If we scroll down and get to the end of the list, call for more clips
        clipListPresenter.onScrolled(5,10,6);

        //Check that the view shows a loading indicator
        InOrder inOrder = Mockito.inOrder(clipListView);
        inOrder.verify(clipListView).setLoadingIndicator(true);
        testScheduler.triggerActions();

        verify(clipListView).refreshClipList(anyList());

        //When the list is loaded the loading indicator is hidden
        inOrder.verify(clipListView).setLoadingIndicator(false);    }

}
