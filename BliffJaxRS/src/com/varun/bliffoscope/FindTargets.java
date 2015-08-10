package com.varun.bliffoscope;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

@Path("/uploadfile")
public class FindTargets extends Application {

    @POST
    @Path("{samplename}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces("text/plain")
    public Response uploadFile(@FormDataParam("file") InputStream uploadedInputStream,
            @FormDataParam("file") FormDataContentDisposition fileDetail,
            @PathParam("samplename") String sample_name, @Context ServletContext ctx) {

        String filepath = "";
        String filename = "";
        List<MatchCoorindates> results = null;
        Gson gson = new Gson();

        String appPath = ctx.getRealPath("");

        switch (sample_name) {
            case "SlimeTorpedo": {
                filepath = appPath + File.separator + "samples\\SlimeTorpedo.blf";
                filename = "SlimeTorpedo";
                break;
            }

            case "Starship": {
                filepath = appPath + File.separator + "samples\\Starship.blf";
                filename = "Starship";
                break;
            }
        }

        try {
            results = compare(uploadedInputStream, filepath, filename);
        } catch (IOException e) {
            System.out.println("Error while reading the uploaded file or Stream.");
        } finally {
            try {
                uploadedInputStream.close();
            } catch (IOException e) {
                System.out.println("Error While closing the Upload Stream.");
                ;
            }
        }
        String jsonresults = gson.toJson(results);

        return Response.status(200).entity(jsonresults).header("Access-Control-Allow-Origin", "*").build();

    }

    private List<MatchCoorindates> compare(InputStream is, String samplepath, String sample_name)
            throws IOException {
        Convertor objConvertor = new Convertor();
        char[][] data = objConvertor.readData(is);
        char[][] sample = objConvertor.readSample(samplepath);
        return new Comparer().generateSimilarity(sample_name, data, sample);
    }

    // Can be used to validate Input Stream
    private void validateInputFile(InputStream is) {

    }
}
