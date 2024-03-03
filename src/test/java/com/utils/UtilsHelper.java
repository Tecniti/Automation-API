package com.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.UUID;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class UtilsHelper {
	private String AccessToken="f0768cc39e74e2c6d2dafbf39abbe42ba8136475f99a59a15a644d54f9623af9";
	private String BaseURL="https://gorest.co.in";
	private static final Logger logger = LogManager.getLogger(UtilsHelper.class);

	@BeforeMethod
	public void beforeMethod() {

	}

	public Response postAPIGorest(String uri) {
		return RestAssured.given().
			header("Authorization","Bearer "+AccessToken)
			.contentType(ContentType.JSON)
			.body(payloadpost())
			.baseUri(BaseURL+uri)
		.when()
			.post()
		.then()
			.assertThat()
			.statusCode(201)
			.extract()
			.response();

	}
	public Response GetAPIGorest(String uri) {
		return RestAssured.given()
				.header("Authorization","Bearer "+AccessToken)
				.contentType(ContentType.JSON)
				.body(payloadpost())
				.baseUri(BaseURL+uri)
		.when()
				.get()
		.then()
				.assertThat()
				.statusCode(200)
				.extract()
				.response();
	}
	/**
	 * Payload for Adding the new user (POST API)
	 */
	public String payloadpost(){
		String email= "nitiemail"+Random_String_for_different_Email()+"@hsbcTest.com";
		//id=id+1;
		String json="{\"id\":6752838," +
				"\"name\":\"niti01\"," +
				"\"email\":\""+email+"\"" +
				",\"gender\":\"female\"," +
				"\"status\":\"active\"}";
		return json;
	}

	/**
	POST API was taking different EMAIL ID on every Hits.
	 that's why I've created a random function sothat every time new email will trigger.
	 */
	private	String Random_String_for_different_Email()
	{
		String randomString = UUID.randomUUID().toString();
		System.out.println(randomString);
		String[] splitArray = randomString.split("-");
		String random = splitArray[0];
		return random;
	}

	/**
	 Method for Getting the Json value based on the JSON key.
	 */
	public String readJSONBasedOnKeyValue(Response response, String Key)
	{
		String resp= response.asString();
		JsonPath js = new JsonPath(resp);
		return js.get(Key).toString();
	}
	/**
	 * This method will run after the failure method, and will provide the logs.
	 */
	@AfterMethod
	public void afterMethod(ITestResult result) {
		if(result.getStatus() == ITestResult.FAILURE) {
			Throwable t = result.getThrowable();
			StringWriter error = new StringWriter();
			t.printStackTrace(new PrintWriter(error));
			logger.info(error.toString());
		}
	}
}





























