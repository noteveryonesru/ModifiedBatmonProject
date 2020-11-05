package ph.edu.up.ics.vtmapp.rest;

import ph.edu.up.ics.vtmapp.rest.requestjsons.BodyJson;
import ph.edu.up.ics.vtmapp.rest.responsejsons.UploadResponseJson;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface VTMappApi {
    @POST("save")
    Call<UploadResponseJson> uploadSessions(@Body BodyJson body);
}
