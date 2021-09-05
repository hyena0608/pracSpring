package prac.manboki.retrofitPrac;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GithubApi {

    @GET("users/hyena0608")
    Call<Object> githubInfo();
}
