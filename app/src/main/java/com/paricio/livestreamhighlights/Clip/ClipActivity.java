package com.paricio.livestreamhighlights.Clip;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.paricio.livestreamhighlights.ClipList.ClipListFragment;
import com.paricio.livestreamhighlights.ClipList.ClipListPresenter;
import com.paricio.livestreamhighlights.R;

public class ClipActivity extends AppCompatActivity {

    ClipPresenter clipPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clip);

        //TODO setup toolbar

        ClipFragment clipFragment =
                (ClipFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (clipFragment == null) {
            clipFragment = ClipFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentFrame,clipFragment).commit();



        }

        clipPresenter = new ClipPresenter(clipFragment);

        //TODO setup menu buttons

    }

}
