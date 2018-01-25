package net.topan_xt.movielist.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/*************************************************
 * Author     : Topan E.                         *
 * Contact    : topan.xt@gmail.com               *
 * Created on : Jan 21, 2018.                    *
 *************************************************/

public class ApiClient {
    private static Retrofit retrofit;
    private static String BASE_URL="http://api.themoviedb.org/3/";
    public static Retrofit getClient() {
        if(retrofit==null){
            retrofit= new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }
    public static Service getService() {
        return getClient().create(Service.class);
    }
}
