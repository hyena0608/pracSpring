package prac.manboki.retrofitPrac;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GithubClient {

    public static GithubApi getApiService() {
        return getInstance().create(GithubApi.class);
    }

    private static Retrofit getInstance() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        return new Retrofit.Builder()
                .baseUrl("http://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

}
