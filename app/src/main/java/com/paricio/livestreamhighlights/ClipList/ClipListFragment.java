package com.paricio.livestreamhighlights.ClipList;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.paricio.livestreamhighlights.Clip.ClipActivity;
import com.paricio.livestreamhighlights.ClipList.ClipListRecyclerView.ClipItemListener;
import com.paricio.livestreamhighlights.ClipList.ClipListRecyclerView.ClipListAdapter;
import com.paricio.livestreamhighlights.Model.Broadcaster;
import com.paricio.livestreamhighlights.Model.Clip;
import com.paricio.livestreamhighlights.R;
import com.paricio.livestreamhighlights.databinding.FragmentClipListBinding;

import java.util.ArrayList;
import java.util.List;


public class ClipListFragment extends Fragment implements ClipListContract.View {

    private ClipListContract.Presenter presenter;
    private FragmentClipListBinding binding;
    private ClipListAdapter adapter;
    private LinearLayoutManager layoutManager;

    public ClipListFragment() {
        //Empty constructor
    }

    public static ClipListFragment newInstance() {
        return new ClipListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.start();
    }

    @Override
    public void onDestroy() {
        presenter.unsubscribe();
        super.onDestroy();
    }

    @Override
    public void setPresenter(@NonNull ClipListContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_clip_list, container, false);
        View view = binding.getRoot();
        layoutManager = new LinearLayoutManager(getActivity());
        binding.clipRecyclerview.setLayoutManager(layoutManager);
        adapter = new ClipListAdapter(new ArrayList<Clip>(), new ClipItemListener() {
            @Override
            public void onClipItemClicked(int position) {
                presenter.showClip(adapter.getClipAtPosition(position));
            }
        });
        binding.clipRecyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                presenter.onScrolled(visibleItemCount,totalItemCount,firstVisibleItemPosition);
            }
        });
        binding.clipsSwiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.loadClips(true);
                binding.clipsSwiperefresh.setRefreshing(false);
            }
        });

        binding.clipRecyclerview.setAdapter(adapter);
        return view;
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        if (active) binding.pbLoading.setVisibility(View.VISIBLE);
        else binding.pbLoading.setVisibility(View.INVISIBLE);
    }

    @Override
    public void refreshClipList(List<Clip> clipList) {
        adapter.refreshClipList(clipList);
    }


    @Override
    public void addClipsToList(List<Clip> clipList) {
        adapter.addClipsToList(clipList);
    }

    @Override
    public void showClip(Clip clip) {
        Intent intent = new Intent(getActivity(), ClipActivity.class);
        intent.putExtra("url",clip.getEmbed_url());
        startActivity(intent);
    }
}
