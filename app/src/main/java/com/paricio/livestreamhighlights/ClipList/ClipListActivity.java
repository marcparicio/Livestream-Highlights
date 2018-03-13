package com.paricio.livestreamhighlights.ClipList;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.paricio.livestreamhighlights.Network.ClipsService;
import com.paricio.livestreamhighlights.Network.RetrofitHelper;
import com.paricio.livestreamhighlights.R;
import com.paricio.livestreamhighlights.databinding.ActivityClipListBinding;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ClipListActivity extends AppCompatActivity {

    private ClipListPresenter clipListPresenter;
    private ActivityClipListBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_clip_list);

        setSupportActionBar(binding.activityToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        ClipListFragment clipListFragment =
                (ClipListFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (clipListFragment == null) {
            clipListFragment = ClipListFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentFrame,clipListFragment).commit();

        }
        ClipsService clipsService = new RetrofitHelper().getCityService();
        clipListPresenter = new ClipListPresenter(clipListFragment,clipsService, AndroidSchedulers.mainThread(), Schedulers.newThread());
    }

}
