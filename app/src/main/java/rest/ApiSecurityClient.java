package rest;

import java.util.concurrent.TimeUnit;

import firstmob.firstbank.com.firstagent.ApplicationConstants;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class ApiSecurityClient {
    public static final String BASE_URL = ApplicationConstants.NET_URL;
    private static Retrofit retrofit = null;


    public static Retrofit getClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.readTimeout(60, TimeUnit.SECONDS).connectTimeout(60, TimeUnit.SECONDS).writeTimeout(60, TimeUnit.SECONDS);


        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();

// Can be Level.BASIC, Level.HEADERS, or Level.BODY
// See http://square.github.io/okhttp/3.x/logging-interceptor/ to see the options.

        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.networkInterceptors().add(httpLoggingInterceptor);
        OkHttpClient okHttpClient = builder.build();

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }

        return retrofit;
    }
}