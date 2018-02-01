package com.paricio.livestreamhighlights.ClipList;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.paricio.livestreamhighlights.R;

public class ClipListActivity extends AppCompatActivity {

    private ClipListPresenter clipListPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clip_list);

        //TODO setup toolbar

        ClipListFragment clipListFragment =
                (ClipListFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (clipListFragment == null) {
            clipListFragment = ClipListFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentFrame,clipListFragment).commit();

        }

        clipListPresenter = new ClipListPresenter(clipListFragment);
    }

    //TODO setup menu buttons
}
