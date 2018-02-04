package com.paricio.livestreamhighlights.Clip;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.paricio.livestreamhighlights.R;
import com.paricio.livestreamhighlights.databinding.ActivityClipBinding;

public class ClipActivity extends AppCompatActivity {

    ClipPresenter clipPresenter;
    private ActivityClipBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_clip);

        setSupportActionBar(binding.activityToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        ClipFragment clipFragment =
                (ClipFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (clipFragment == null) {
            clipFragment = ClipFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentFrame,clipFragment).commit();
        }

        clipPresenter = new ClipPresenter(clipFragment);


    }

}
