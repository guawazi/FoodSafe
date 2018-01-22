package cn.wangliang.foodsafe.data.network;

import com.orhanobut.logger.Logger;

import java.util.concurrent.TimeUnit;

import cn.wangliang.foodsafe.BuildConfig;
import cn.wangliang.foodsafe.data.network.okhttputils.HeaderInterceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiService {

    public static final String BASE_URL = BuildConfig.BASE_URL;

    private Api api;
    private volatile static ApiService instance;


    private ApiService(){
        if (api==null){
            OkHttpClient.Builder builder = new OkHttpClient.Builder()
                    .addInterceptor(new HeaderInterceptor())
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS);

            if (BuildConfig.DEBUG){
                //DEBUG模式下打印请求的json数据，如需打印所有数据，如请求信息和response，去掉if判断即可。
                builder.addInterceptor(new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log(String message) {
                        if (message.startsWith("{"))
                             Logger.t("HttpInfo").d(message);
                    }
                }).setLevel(HttpLoggingInterceptor.Level.BODY));
            }

            Retrofit retrofit = new Retrofit.Builder()
                    .client(builder.build())
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();

            api = retrofit.create(Api.class);
        }
    }

    public static Api getInstance(){
        if (instance==null){
            synchronized (ApiService.class){
                if (instance==null)
                    instance = new ApiService();
            }
        }

        return instance.api;
    }
}
