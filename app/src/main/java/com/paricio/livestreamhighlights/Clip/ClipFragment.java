package com.paricio.livestreamhighlights.Clip;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.paricio.livestreamhighlights.Model.Clip;
import com.paricio.livestreamhighlights.R;
import com.paricio.livestreamhighlights.databinding.FragmentClipBinding;


public class ClipFragment extends Fragment implements ClipContract.View {

    private ClipContract.Presenter clipPresenter;
    private FragmentClipBinding binding;
    private String slug;
    private String html_url;

    public ClipFragment() {
        //Empty constructor
    }

    public static ClipFragment newInstance() {
        return new ClipFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_clip, container, false);
        View view = binding.getRoot();
        int length = view.getWidth();
        /*slug = getActivity().getIntent().getExtras().getString("slug");
        String embed_url = "http://clips.twitch.tv/embed?clip=" + slug + "&autoplay=false&muted=false&preload=metadata";
        html_url = "<iframe src='"+ embed_url +"' width='"+ length + "' height='"+ length +"' frameborder='0' scrolling='no' allowfullscreen='true'><\\/iframe>";
*/
        //html_url = "<iframe src='https://clips.twitch.tv/embed?clip=VictoriousDependableLarkPanicVis&tt_medium=clips_api&tt_content=embed' width='"+ length + "' height='" + length +"' frameborder='0' scrolling='no' allowfullscreen='true'><\\/iframe>";
        html_url = "<iframe src='https://clips.twitch.tv/embed?clip=VictoriousDependableLarkPanicVis&tt_medium=clips_api&tt_content=embed' width='100%' height='100%' scrolling='no'><\\/iframe>";
        WebView webView = binding.fragmentClipWebview;
        webView.loadData(html_url,"text/html",null);
        webView.setWebChromeClient(new WebChromeClient());
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
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
    public void loadClip(Clip clip) {
        //TODO load clip ui
    }
}
