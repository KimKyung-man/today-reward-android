package xyz.gracefulife.api;

import com.google.gson.GsonBuilder;

import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitFactory {
  private static final String TAG = RetrofitFactory.class.getSimpleName();
  private static final int MAX_CONNECTION_COUNT = 10;

  public static <T> T createService(Class<T> serviceClass) {
    return getRetrofitObject().create(serviceClass);
  }

  private static Retrofit getRetrofitObject() {
    Dispatcher dispatcher = new Dispatcher();
    dispatcher.setMaxRequests(MAX_CONNECTION_COUNT);

    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

    OkHttpClient client = new OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .dispatcher(dispatcher)
        .build();

    GsonBuilder builder = new GsonBuilder(); // Set up the custom GSON converters
    // builder.registerTypeAdapter(Date.class, new GsonDateFormatAdapter());

    return new Retrofit.Builder()
        .baseUrl(BuildConfig.DEBUG ? "" : "")
        .client(client)
        // .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(builder.create()))
        .build();
  }
}
