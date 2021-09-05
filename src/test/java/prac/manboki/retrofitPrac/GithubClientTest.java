package prac.manboki.retrofitPrac;

import retrofit2.Call;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class GithubClientTest {
    public static void main(String[] args) {
        Call<Object> getTest = GithubClient.getApiService().githubInfo();
        try{
            System.out.println(getTest.execute().body());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}