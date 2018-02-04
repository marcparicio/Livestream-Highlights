package com.paricio.livestreamhighlights.ClipList;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.paricio.livestreamhighlights.Clip.ClipActivity;
import com.paricio.livestreamhighlights.ClipList.ClipListRecyclerView.ClipItemListener;
import com.paricio.livestreamhighlights.ClipList.ClipListRecyclerView.ClipListAdapter;
import com.paricio.livestreamhighlights.Model.Clip;
import com.paricio.livestreamhighlights.R;
import com.paricio.livestreamhighlights.databinding.FragmentClipListBinding;
import com.paricio.livestreamhighlights.utils.UrlUtils;

import java.util.ArrayList;
import java.util.List;

import static com.paricio.livestreamhighlights.utils.ClipUtils.MEDIUM_QUALITY;


public class ClipListFragment extends Fragment implements ClipListContract.View {

    private ClipListContract.Presenter presenter;
    private FragmentClipListBinding binding;
    private ClipListAdapter adapter;
    private LinearLayoutManager layoutManager;
    private String quality;
    private Spinner quality_spinner;

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


        quality = MEDIUM_QUALITY;
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
        setHasOptionsMenu(true);
        getActivity().setTitle(getResources().getString(R.string.highlights));
        presenter.start();
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
    public void checkWifi() {
        ConnectivityManager connectionManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiCheck = connectionManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (!wifiCheck.isConnected()) {
            Toast.makeText(getContext(),R.string.connect_wifi_suggestion,Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void showClip(Clip clip) {
        quality = quality_spinner.getSelectedItem().toString();
        Intent intent = new Intent(getActivity(), ClipActivity.class);
        intent.putExtra("url", UrlUtils.getClipUrl(clip,quality));
        intent.putExtra("broadcaster",clip.getBroadcaster().getDisplay_name());
        startActivity(intent);
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.main_menu, menu);

        MenuItem item = menu.findItem(R.id.spinner);
        quality_spinner = (Spinner) item.getActionView();

        ArrayAdapter<CharSequence> qualityAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.quality_spinner_items, android.R.layout.simple_spinner_item);
        qualityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        quality_spinner.setAdapter(qualityAdapter);
        quality_spinner.setSelection(1);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().finish();
                break;
            case R.id.about:
                Toast.makeText(getContext(),R.string.version,Toast.LENGTH_LONG).show();
        }
        return true;
    }

}
