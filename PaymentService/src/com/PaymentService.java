package com;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.text.Normalizer;
import java.text.Normalizer.Form;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import model.Payment;

@Path("/payments")
public class PaymentService {
	Payment paymentObj = new Payment();

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertpaymentdetails(@FormParam("paymentId") int paymentId, @FormParam("date") String date,
			@FormParam("amount") int amount, @FormParam("customerId") int customerId) {
		String output = paymentObj.insertpaymentdetails(paymentId, date, amount, customerId);
		return output;
	}

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readpaymentdetails() {
		return paymentObj.readpaymentdetails();
	}

	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updatepaymentdetails(String paymentdata) {
		// Convert the input string to a
		JsonObject paymentObject = new JsonParser().parse(paymentdata).getAsJsonObject();

		// Read the values from the JSON object
		int paymentId = paymentObject.get("paymentId").getAsInt();
		String date = paymentObject.get("date").getAsString();
		int amount = paymentObject.get("amount").getAsInt();
		int customerId = paymentObject.get("customerId").getAsInt();

		String output = paymentObj.updatepaymentdetails(paymentId, date, amount, customerId);

		return output;
	}

	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deletepaymentdetails(String paymentdata) {
		// Convert the input string to an XML document
		Document doc = Jsoup.parse(paymentdata, "", Parser.xmlParser());
		// Read the value from the element <paymentId>

		String paymentIdStr = Normalizer.normalize(doc.select("paymentId").text(), Form.NFKC);
		int paymentId = Integer.parseInt(paymentIdStr);

		String output = paymentObj.deletepaymentdetails(paymentId);

		return output;
	}

}
