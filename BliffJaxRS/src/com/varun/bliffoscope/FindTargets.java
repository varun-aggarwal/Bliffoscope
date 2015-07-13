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
	//@Produces(MediaType.APPLICATION_JSON)
	@Produces("text/plain")
	public Response uploadFile(@FormDataParam("file") InputStream uploadedInputStream, @FormDataParam("file") FormDataContentDisposition fileDetail,@PathParam ("samplename") String sample_name, @Context ServletContext ctx)
	{
		//System.out.println("in Upload file");
		String filepath = "";
		String filename = "";
		 Gson gson = new Gson();
		InputStream testfilecontent = null;
		testfilecontent = uploadedInputStream;
	    String appPath= ctx.getRealPath("");
		//testfile = appPath + File.separator +"samples\\TestData.blf";
		//System.out.println(testfile);
		
	    if(sample_name !=null && sample_name.equals("SlimeTorpedo"))
		{
	    filepath = appPath + File.separator +"samples\\SlimeTorpedo.blf";
		filename = "SlimeTorpedo";
		}
	    else if(sample_name !=null && sample_name.equals("Starship"))
		{
	    filepath = appPath + File.separator +"samples\\Starship.blf";
		filename = "Starship";
		}
	    List<MatchCoorindates> results=null;
		try {
			results = compare(testfilecontent, filepath, filename);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    String jsonresults = gson.toJson(results);
	   // System.out.println("returning json" + jsonresults );
	    return Response.status(200).entity(jsonresults).header("Access-Control-Allow-Origin", "*").build();

	}
	
	private  List<MatchCoorindates> compare(InputStream is, String samplepath, String sample_name) throws IOException{
		Convertor objConvertor = new Convertor();
		char[][] data = objConvertor.readData(is);
		char[][] sample = objConvertor.readSample(samplepath);
		return new Comparer().generateSimilarity(sample_name,data, sample);
	}
	
	//Can be used to validate Input Stream
		private void validateInputFile (InputStream is)
		{
			
		}
}
