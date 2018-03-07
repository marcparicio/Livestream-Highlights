package com.paricio.livestreamhighlights;


import com.paricio.livestreamhighlights.ClipList.ClipListContract;
import com.paricio.livestreamhighlights.ClipList.ClipListPresenter;
import com.paricio.livestreamhighlights.Model.Clip;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 *  Unit tests of the implementatios of {@link ClipListPresenter}
 */

public class ClipListPresenterTest {

    private static List<Clip> CLIPS;

    @Mock
    private ClipListContract.View clipListView;

    private ClipListPresenter clipListPresenter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        clipListPresenter = new ClipListPresenter(clipListView);

        when(clipListView.isActive()).thenReturn(true);

        //We start with an empty list of clips
        CLIPS = new ArrayList<>();
    }

    @Test
    public void createPresenter_viewSetsPresenter() {
        clipListPresenter = new ClipListPresenter(clipListView);

        verify(clipListView).setPresenter(clipListPresenter);
    }

}
