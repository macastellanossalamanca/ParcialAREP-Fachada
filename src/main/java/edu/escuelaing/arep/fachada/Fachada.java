  
package edu.escuelaing.arep.fachada;

import static spark.Spark.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;


import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.template.velocity.VelocityTemplateEngine;

/**
 * Class in charge of starting the service.
 *
 */
public class Fachada {
	

	/**
	 * Main class that starts the main thread of the service.
	 * 
	 * @param args String array
	 */
	public static void main(String[] args) {
		port(getPort());
		get("/fachada", (req, res) -> {
			res.type("application/json");
			String ls_res;
			ls_res = "";
			ls_res = resultData(req, res);
			return ls_res;

		});
	}
	
	/**
	 * Specifies the port on which the service will run.
	 * 
	 * @return The port where the service will be run.
	 */
	public static int getPort() {    
		if (System.getenv("PORT") != null)
		{            
			return Integer.parseInt(System.getenv("PORT"));      
		} 
		return 4567; 
	}
	
	/**
	 * Method in charge of generating the values of the mean and the standard deviation.
	 * 
	 * @param am_model Data that will be passed to the template.
	 * @param ar_req Request received by the server
	 * @param ar_res Server response.
	 * @return Returns if it was possible to calculate the data with the information entered.
	 */
	private static String resultData(Request ar_req, Response ar_res){
		String ls_values;
		String ls_ope;
		String ls_res;
		String ls_aux;
		boolean lb_resp;
		
		ls_values = ar_req.queryParams("valor");
		ls_ope = ar_req.queryParams("operacion");
		if (ls_values == null)
			return "";

		
			
		ls_res = readURL("http://api.openweathermap.org/data/2.5/weather?q="+ls_values+"&appid=7241c511099db33048201d1e4cc20198");
		ls_res = readURL("https://parcial1servicio.herokuapp.com/trig?valor="+ls_values+"&operacion="+ls_ope);
		
		
		
		return ls_res;
	}
	
	public static String readURL(String as_site) {
		String ls_resData;
		ls_resData = null;
		try {
			URL lu_siteURL;

			lu_siteURL = new URL(as_site);

			if (lu_siteURL != null) {
				URLConnection luc_urlConnection;

				luc_urlConnection = lu_siteURL.openConnection();

				if (luc_urlConnection != null) {
					InputStreamReader lis_input;

					lis_input = new InputStreamReader(luc_urlConnection.getInputStream());

					if (lis_input != null) {
						BufferedReader lbr_reader;

						lbr_reader = new BufferedReader(lis_input);

						if (lbr_reader != null) {
							String ls_line;

							ls_line = null;

							ls_resData = "";

							while ((ls_line = lbr_reader.readLine()) != null)
								ls_resData += ls_line;

						}
					}

				}
			}
			
		} catch (IOException x) {
			ls_resData = "";
			x.printStackTrace();
		}
		
		return ls_resData;
	}
	
	
	
}