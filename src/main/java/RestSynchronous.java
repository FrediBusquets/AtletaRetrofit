import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by USER on 23/01/2017.
 */
public class RestSynchronous {
    private static Retrofit retrofit;
    public static void main(String[] args) throws IOException {
        retrofit = new Retrofit.Builder().baseUrl("http://localhost:8080")
                .addConverterFactory(GsonConverterFactory.create()).build();

        AtletaService atletaService = retrofit.create(AtletaService.class);
        //get all Atletas
        Response<List<Atleta>> responseAllAtleta = atletaService.getAllAtleta().execute();
        if (responseAllAtleta.isSuccessful()) {
            List<Atleta> atletaList = responseAllAtleta.body();
            System.out.println("Status code: " + responseAllAtleta.code() +
                     System.lineSeparator() + "GET all Athletes: " + atletaList);
        } else {
            System.out.println("Status code: " + responseAllAtleta.code() +
                    "Message error: " + responseAllAtleta.errorBody());
        }
        //Post Atleta
        Atleta atleta = new Atleta();
        atleta.setNombre("Javier");
        atleta.setApellido("Goméz");
        atleta.setNacionalidad("Spain");
        atleta.setFechanacimiento(LocalDate.of(1991, 06, 21));
        Response<Atleta> postAtleta = atletaService.createAtleta(atleta).execute();

        if (postAtleta.isSuccessful()) {
            Atleta atletaResp = postAtleta.body();
            System.out.println("Status code: " + postAtleta.code() + System.lineSeparator() +
                    "POST player: " + atletaResp);
            // Get un Jugador
            Response<Atleta> responseOneAtleta = atletaService.getAtleta(atletaResp.getId()).execute();
            if (responseOneAtleta.isSuccessful()) {
                System.out.println("GET one Athlete. Status code: " + responseOneAtleta.code() + " Athlete: " + responseOneAtleta.body());
            } else {
                System.out.println("Status code: " + responseOneAtleta.code() + "Message error: " + responseOneAtleta.errorBody());
            }
            // put atleta(actualizar/modificar atleta)
            atletaResp.setNacionalidad("Spain");
            Response<Atleta> putAtleta = atletaService.updateAtleta(atletaResp).execute();

            if (responseOneAtleta.isSuccessful()) {
                System.out.println("Status code: " + putAtleta.code() + System.lineSeparator() +
                        "PUT Athlete: " + putAtleta.body());
            } else {
                System.out.println("Status code: " + putAtleta.code() + "Message error: " + putAtleta.errorBody());
            }
            // Delete un Atleta
            Response<Void> atletaDelete = atletaService.deleteAtleta(atletaResp.getId()).execute();
            System.out.println("DELETE Athlete. Status code: " + atletaDelete.code());
            responseAllAtleta = atletaService.getAllAtleta().execute();
            System.out.println("Check delete " +
                    "Status code: " + responseAllAtleta.code() + System.lineSeparator() +
                    "GET Athletes: " + responseAllAtleta.body());

        }
    }

}
