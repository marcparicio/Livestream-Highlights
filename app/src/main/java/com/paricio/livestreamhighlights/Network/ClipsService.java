package com.paricio.livestreamhighlights.Network;


import com.paricio.livestreamhighlights.Model.Clip;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ClipsService {

    @GET("/clips")
    Single< List<Clip> > getAllMeetings(@Query("page") int page);
}
