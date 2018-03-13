package com.paricio.livestreamhighlights;


import com.paricio.livestreamhighlights.Model.Clip;

import java.util.ArrayList;
import java.util.List;

public class TestUtils {

    public static List<Clip> getFakeClipsList() {
        Clip fakeClip = new Clip();
        List<Clip> fakeClips = new ArrayList<>();
        fakeClips.add(fakeClip);
        fakeClips.add(fakeClip);
        fakeClips.add(fakeClip);
        fakeClips.add(fakeClip);
        return fakeClips;
    }
}



