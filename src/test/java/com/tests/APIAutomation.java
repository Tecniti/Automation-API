package com.tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.utils.UtilsHelper;
import cucumber.api.CucumberOptions;
import io.restassured.response.Response;
import org.testng.annotations.Test;

@CucumberOptions(
		//plugin ="io.qameta.allure.cucumber6jvm.AllureCucumber6Jvm"
		plugin ="io.qameta.allure.cucumber5jvm.AllureCucumber5Jvm"
		)
public class APIAutomation extends UtilsHelper {

	private String BaseURI="/public/v2/users";
	private String Addedemail = "";
	Response getAPIResponse = null;

	@Test (description = "[POST + Authentication] Create a user based on different email ID with authentication")
  public void POST_Create_new_user_with_authentication() throws JsonProcessingException {
		Response postAPIresponse = postAPIGorest(BaseURI);
		//Get value based on the JSON KEY value
		Addedemail=readJSONBasedOnKeyValue(postAPIresponse,"email");
  }
	@Test (description = "[Get]Running Get API and validating the status code")
	public void GET_all_user_Details() throws JsonProcessingException {
		getAPIResponse = GetAPIGorest(BaseURI);
		String Get =getAPIResponse.asString();
	}

	@Test (description = "[Fetch data from Nested Structure] Verify Created user from the response of GEt API")
	public void ReadValuefromNESTED_JSON() throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		try {
			JsonNode jsonNode = mapper.readTree(getAPIResponse.asString());
			if (jsonNode.isArray()) {
				for (JsonNode node : jsonNode) {
					String email = node.get("email").asText();
					System.out.println(email);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}









