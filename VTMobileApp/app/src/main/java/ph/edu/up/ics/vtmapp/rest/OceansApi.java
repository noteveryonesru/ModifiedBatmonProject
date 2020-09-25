package ph.edu.up.ics.vtmapp.rest;

import ph.edu.up.ics.vtmapp.rest.request.BodyJson;
import ph.edu.up.ics.vtmapp.rest.response.UploadResponseJson;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface OceansApi {
    @POST("save")
    Call<UploadResponseJson> uploadSessions(@Body BodyJson body);
}
