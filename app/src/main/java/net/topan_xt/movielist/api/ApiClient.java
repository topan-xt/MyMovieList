package net.topan_xt.movielist.api;

import net.topan_xt.movielist.util.Constant;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/*************************************************
 * Author     : Topan E.                         *
 * Contact    : topan.xt@gmail.com               *
 * Created on : Jan 21, 2018.                    *
 *************************************************/

public class ApiClient {
    private static Retrofit retrofit;
    public static Retrofit getClient() {
        if(retrofit==null){
            retrofit= new Retrofit.Builder().baseUrl(Constant.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }
    public static Service getService() {
        return getClient().create(Service.class);
    }
}
