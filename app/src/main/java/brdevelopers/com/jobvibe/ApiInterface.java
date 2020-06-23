package brdevelopers.com.jobvibe;
import brdevelopers.com.jobvibe.PushNotification;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
public interface ApiInterface {

    @GET("Pushvalue")
    Call<PushNotification> loginUser(@Query("cat") String cat);
}
