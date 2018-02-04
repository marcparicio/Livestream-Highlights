package com.paricio.livestreamhighlights.Clip;


import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlaybackControlView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.paricio.livestreamhighlights.R;
import com.paricio.livestreamhighlights.databinding.FragmentClipBinding;

import java.io.FileDescriptor;
import java.io.PrintWriter;


public class ClipFragment extends Fragment implements ClipContract.View {

    private final String STATE_RESUME_WINDOW = "resumeWindow";
    private final String STATE_RESUME_POSITION = "resumePosition";
    private final String STATE_PLAYER_FULLSCREEN = "playerFullscreen";
    private int resumeWindow;
    private long resumePosition;
    private boolean exoPlayerFullscreen = false;
    private Dialog fullScreenDialog;
    private ImageView fullScreenIcon;
    private FrameLayout fullScreenButton;
    private boolean haveResumePosition;

    private ClipContract.Presenter clipPresenter;
    private FragmentClipBinding binding;
    private SimpleExoPlayerView playerView;
    private SimpleExoPlayer player;
    private Uri uri;


    public ClipFragment() {
        //Empty constructor
    }

    public static ClipFragment newInstance() {
        return new ClipFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            resumeWindow = savedInstanceState.getInt(STATE_RESUME_WINDOW);
            resumePosition = savedInstanceState.getLong(STATE_RESUME_POSITION);
            exoPlayerFullscreen = savedInstanceState.getBoolean(STATE_PLAYER_FULLSCREEN);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        outState.putInt(STATE_RESUME_WINDOW, resumeWindow);
        outState.putLong(STATE_RESUME_POSITION, resumePosition);
        outState.putBoolean(STATE_PLAYER_FULLSCREEN, exoPlayerFullscreen);

        super.onSaveInstanceState(outState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_clip, container, false);
        View view = binding.getRoot();
        String broadcaster = getActivity().getIntent().getStringExtra("broadcaster");
        getActivity().setTitle(broadcaster);
        String url = getActivity().getIntent().getStringExtra("url");
        uri = Uri.parse(url);
        Log.i("ClipFragment",url);
        setHasOptionsMenu(true);
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        clipPresenter.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        clipPresenter.stop();
    }

    @Override
    public void dump(String prefix, FileDescriptor fd, PrintWriter writer, String[] args) {
        super.dump(prefix, fd, writer, args);
    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory("exoplayer-codelab")).
                createMediaSource(uri);
    }

    @Override
    public void initializePlayer() {
        Log.d("ClipFragment","initializePlayer");
        if (playerView == null) {
            playerView = binding.exoplayer;
            initFullscreenDialog();
            initFullscreenButton();
        }

        haveResumePosition = resumeWindow != C.INDEX_UNSET;

        initExoPlayer();

        if (exoPlayerFullscreen) {
            ((ViewGroup) playerView.getParent()).removeView(playerView);
            fullScreenDialog.addContentView(playerView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            fullScreenIcon.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_fullscreen_skrink));
            fullScreenDialog.show();
        }
    }

    @Override
    public void releasePlayer() {
        Log.d("ClipFragment","releasePlayer");
        if (player != null) {
            resumeWindow = playerView.getPlayer().getCurrentWindowIndex();
            resumePosition = Math.max(0, playerView.getPlayer().getContentPosition());
            player.release();
            player = null;
        }

        if (fullScreenDialog != null)
            fullScreenDialog.dismiss();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void setPresenter(@NonNull ClipContract.Presenter presenter) {
        this.clipPresenter = presenter;
    }

    @Override
    public void setLoading(boolean active) {
        //TODO set loading
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().finish();
        }
        return true;
    }

    private void initExoPlayer() {
        Log.d("ClipFragment","initExoPlayer");
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
        LoadControl loadControl = new DefaultLoadControl();
        player = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(getActivity()), trackSelector, loadControl);
        playerView.setPlayer(player);
        player.setPlayWhenReady(true);
        if (haveResumePosition) {
            playerView.getPlayer().seekTo(resumeWindow, resumePosition);
        }
        MediaSource mediaSource = buildMediaSource(uri);
        player.addListener(getErrorListener());
        player.prepare(mediaSource, true, false);
    }


    private void initFullscreenDialog() {
        Log.d("ClipFragment","initFullScreenDialog");
        fullScreenDialog = new Dialog(getActivity(), android.R.style.Theme_Black_NoTitleBar_Fullscreen) {
            public void onBackPressed() {
                if (exoPlayerFullscreen) {
                    closeFullscreenDialog();
                    Log.d("ClipFragment","onBackPressed!!");
                }
                super.onBackPressed();
            }
        };
    }

    private void openFullscreenDialog() {
        Log.d("ClipFragment","openFullScreenDialog");
        ((ViewGroup) playerView.getParent()).removeView(playerView);
        fullScreenDialog.addContentView(playerView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        fullScreenIcon.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_fullscreen_skrink));
        exoPlayerFullscreen = true;
        fullScreenDialog.show();
    }

    private void closeFullscreenDialog() {
        Log.d("ClipFragment","closeFullScreenDialog");
        ((ViewGroup) playerView.getParent()).removeView(playerView);
        ((FrameLayout) getActivity().findViewById(R.id.main_media_frame)).addView(playerView);
        exoPlayerFullscreen = false;
        fullScreenDialog.dismiss();
        fullScreenIcon.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_fullscreen_expand));
    }


    private void initFullscreenButton() {
        Log.d("ClipFragment","initFullScreenButton");
        PlaybackControlView controlView = playerView.findViewById(R.id.exo_controller);
        fullScreenIcon = controlView.findViewById(R.id.exo_fullscreen_icon);
        fullScreenButton = controlView.findViewById(R.id.exo_fullscreen_button);
        fullScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!exoPlayerFullscreen)
                    openFullscreenDialog();
                else
                    closeFullscreenDialog();
            }
        });
    }

    private Player.EventListener getErrorListener() {
        return new Player.EventListener() {
            @Override
            public void onTimelineChanged(Timeline timeline, Object manifest) {

            }

            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

            }

            @Override
            public void onLoadingChanged(boolean isLoading) {

            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

            }

            @Override
            public void onRepeatModeChanged(int repeatMode) {

            }

            @Override
            public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {
                Toast.makeText(getContext(),R.string.clip_quality_not_available,Toast.LENGTH_LONG).show();
                getActivity().finish();
            }

            @Override
            public void onPositionDiscontinuity(int reason) {

            }

            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

            }

            @Override
            public void onSeekProcessed() {

            }
        };
    }


}
