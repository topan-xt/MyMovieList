package net.topan_xt.movielist.api;

import net.topan_xt.movielist.model.cast.CastResponse;
import net.topan_xt.movielist.model.general.MoviesResponse;
import net.topan_xt.movielist.model.moviedetail.MovieDetailResponse;
import net.topan_xt.movielist.model.review.ReviewsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/*************************************************
 * Author     : Topan E.                         *
 * Contact    : topan.xt@gmail.com               *
 * Created on : Jan 21, 2018.                    *
 *************************************************/

public interface Service {

    @GET("movie/top_rated")
    Call<MoviesResponse> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("movie/popular")
    Call<MoviesResponse> getPopular(@Query("api_key") String apiKey);

    @GET("movie/now_playing")
    Call<MoviesResponse> getNowPlaying(@Query("api_key") String apiKey);

    @GET("movie/upcoming")
    Call<MoviesResponse> getComingSoon(@Query("api_key") String apiKey);

    @GET("movie/{id}")
    Call<MovieDetailResponse> getMovieDetails (@Path("id")int id, @Query("api_key") String apiKey);

    @GET("movie/{movie_id}/credits")
    Call<CastResponse>getCasts(@Path("movie_id")int id, @Query("api_key") String apiKey);

    @GET("movie/{movie_id}/reviews")
    Call<ReviewsResponse>getReviews(@Path("movie_id")int id, @Query("api_key") String apiKey);

    @GET("movie/{movie_id}/videos")
    Call<ReviewsResponse>getVideos(@Path("movie_id")int id, @Query("api_key") String apiKey);
}
