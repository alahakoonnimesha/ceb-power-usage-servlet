package com;

import jakarta.ws.rs.core.MediaType;

import com.google.gson.*;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import model.PowerUsage;

import java.text.Normalizer;
import java.text.Normalizer.Form;

import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

@Path("/PowerUsage")
public class PowerUsageService {
	PowerUsage powerUsageObj = new PowerUsage();

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readPowerUsage() {
		return powerUsageObj.readPowerUsage();
	}

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertPowerUsage(@FormParam("puId") int puId, @FormParam("units") int units,
			@FormParam("amount") int amount, @FormParam("month") int month, @FormParam("cusId") int cusId,
			@FormParam("empId") int empId) {
		String output = powerUsageObj.insertPowerUsage(puId, units, amount, month, cusId, empId);
		return output;
	}

	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updatePowerUsage(String powerUsageData) {
		// Convert the input string to a JSON object
		JsonObject hospitalObject = new JsonParser().parse(powerUsageData).getAsJsonObject();
		// Read the values from the JSON object
		int puId = hospitalObject.get("puId").getAsInt();
		int units = hospitalObject.get("units").getAsInt();
		int amount = hospitalObject.get("amount").getAsInt();
		int month = hospitalObject.get("month").getAsInt();
		int cusId = hospitalObject.get("cusId").getAsInt();
		int empId = hospitalObject.get("empId").getAsInt();

		String output = powerUsageObj.updatePowerUsage(puId, units, amount, month, cusId, empId);

		return output;
	}
	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteHospital(String powerUsageData) {
		// Convert the input string to an XML document
		Document doc = Jsoup.parse(powerUsageData, "", Parser.xmlParser());

		// Read the value from the element <itemID>
		String pdIdStr = Normalizer.normalize(doc.select("puId").text(), Form.NFKC);
		int puId = Integer.parseInt(pdIdStr); 
		String output = powerUsageObj.deleteHospital(puId);
		
		return output;
	}

}