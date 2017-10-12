package com.spkj.supai.app;

import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by pc on 2017/1/2.
 */

public interface ApiService {


    @FormUrlEncoded
    @POST("/app/login_01.php")
    Observable<String> login(@FieldMap Map<String, String> map);

}
