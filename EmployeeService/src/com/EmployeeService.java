package com;

import jakarta.ws.rs.core.MediaType;
import model.Employee;

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

import java.text.Normalizer;
import java.text.Normalizer.Form;

//For XML
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

@Path("/employee")
public class EmployeeService {

	Employee employeeObj = new Employee();

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readDoctors() {

		return employeeObj.readEmployee();
	}

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertEmployee(@FormParam("empId") int empId, @FormParam("empName") String empName,
			@FormParam("address") String address, @FormParam("telepohneNo") int telepohneNo) {
		String output = employeeObj.insertEmployee(empId, empName, address, telepohneNo);
		return output;
	}

	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateEmployee(String employeeData) {
		// Convert the input string to a JSON object
		JsonObject docObject = new JsonParser().parse(employeeData).getAsJsonObject();

		// Read the values from the JSON object
		int empId = docObject.get("empId").getAsInt();
		String empName = docObject.get("empName").getAsString();
		String address = docObject.get("address").getAsString();
		int telepohneNo = docObject.get("telepohneNo").getAsInt();

		String output = employeeObj.updateEmployee(empId, empName, address, telepohneNo);
		return output;
	}

	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteEmployee(String employeeData) {
		// Convert the input string to an XML document
		Document doc = Jsoup.parse(employeeData, "", Parser.xmlParser());

		// Read the value from the element <itemID>
		String employeeIdStr = Normalizer.normalize(doc.select("empId").text(), Form.NFKC);
		int empId = Integer.parseInt(employeeIdStr);

		String output = employeeObj.deleteEmployee(empId);

		return output;
	}
}
