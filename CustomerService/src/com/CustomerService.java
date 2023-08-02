package com;

//For JSON
import com.google.gson.*;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import model.Customer;

import java.text.Normalizer;
import java.text.Normalizer.Form;

//For XML
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

@Path("/customer")
public class CustomerService {
	Customer customerObj = new Customer();

	//	this is a test method
	@GET
	@Path("/test")
	@Produces(MediaType.TEXT_PLAIN)
	public String readItemsinit() {
		return " This is a test method ";
	}
	
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readCustomer() {
		return customerObj.readCustomer();
	}

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertCustomer(@FormParam("customerId") int customerId, @FormParam("customerName") String customerName, @FormParam("address") String address,
			@FormParam("telepohneNo") int telepohneNo) {
		String output = customerObj.insertCustomer(customerId, customerName,address, telepohneNo);
		return output;
	}

	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateCustomer(String customerData) {
		//Convert the input string to a JSON object
		JsonObject customerObject = new JsonParser().parse(customerData).getAsJsonObject();
		//Read the values from the JSON object
		int customerId = customerObject.get("customerId").getAsInt();
		String customerName = customerObject.get("customerName").getAsString();
		String address = customerObject.get("address").getAsString();
		int telepohneNo = customerObject.get("telepohneNo").getAsInt();

		String output = customerObj.updateCustomer(customerId, customerName, address, telepohneNo);
		return output;
	}

	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteCustomer(String customerData) {
		//Convert the input string to an XML document
		Document doc = Jsoup.parse(customerData, "", Parser.xmlParser());

		//Read the value from the element <itemID>
		String customerIdStr = Normalizer.normalize(doc.select("customerId").text(), Form.NFKC);
		int customerId = Integer.parseInt(customerIdStr); 
		String output = customerObj.deleteCustomer(customerId);
		return output;
	}
}
