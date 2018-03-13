package com.paricio.livestreamhighlights;


import com.paricio.livestreamhighlights.Clip.ClipContract;
import com.paricio.livestreamhighlights.Clip.ClipPresenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


/**
 *  Unit tests of the implementatios of {@link ClipPresenter}
 */

public class ClipPresenterTest {

    @Mock
    private ClipContract.View clipView;

    private ClipPresenter clipPresenter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        clipPresenter = new ClipPresenter(clipView);

        //The presenter won't load the view if it is not available
        when(clipView.isActive()).thenReturn(true);
    }

    @Test
    public void startPresenter_initializedPlayer() {
        clipPresenter.start();

        verify(clipView).initializePlayer();
    }

    @Test
    public void stopPresenter_releasesPlayer() {
        clipPresenter.stop();

        verify(clipView).releasePlayer();
    }
}
