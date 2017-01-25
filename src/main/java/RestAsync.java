import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.io.IOException;
import java.util.List;

/**
 * Created by USER on 18/01/2017.
 */
public class RestAsync {
    private static Retrofit retrofit;

    public static void main(String[] args) throws IOException {
        retrofit = new Retrofit.Builder().baseUrl("http://localhost:8080").addConverterFactory(GsonConverterFactory.create()).build();
        AtletaService atletaService = retrofit.create(AtletaService.class);
        Call<List<Atleta>> call = atletaService.getAllAtleta();
        call.enqueue(new Callback<List<Atleta>>() {
            @Override
            public void onResponse(Call<List<Atleta>> call, Response<List<Atleta>> response) {
                System.out.println("Status code: " + response.code() + System.lineSeparator() +
                        "GET all Athletes: " + response.body());
            }
            @Override
            public void onFailure(Call<List<Atleta>> call, Throwable throwable) {
                System.out.println("Error: " + throwable.getMessage());
            }
        });
    }
}
