package testExecutor;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utils.CompactUtils;
import utils.PropertyUtil;
import utils.Reports;

public class testOne {

	String id;
	PropertyUtil prop = new PropertyUtil();
	RequestSpecification spec;
	Response resp;
	Reports report = new Reports();
	String pathToReponse;

	@BeforeClass
	public void initializeRequestSpec() {
		RestAssured.baseURI = "https://api.twitter.com/1.1/statuses";
		spec = RestAssured.given().auth().oauth(prop.getAPI_key(), prop.getAPI_secret_key(), prop.getAccessToken(),
				prop.getAccessTokenSecret());
		pathToReponse = report.responseFilePath;
	}

	@BeforeMethod
	public void startTest(Method m) {
		System.out.println(m.getName());
		report.startTest(m.getName());
	}

	@AfterMethod
	public void endTest(Method m) {
		System.out.println(m.getName());
		report.stopTest(m.getName());
	}

	@Test
	public void creatTweet() {

		String tweetText = "Automation_" + new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());
		String currentResponseFilePath = pathToReponse + "CreateTweetResponse.txt";
		resp = given().spec(spec).queryParam("status", tweetText).log().all().when().post("/update.json").then().log()
				.all().extract().response();
		CompactUtils.writeContentToFile(currentResponseFilePath, resp.asString());
		if (resp.getStatusCode() == 200) {
			id = new JsonPath(resp.asString()).getString("id");
			System.out.println("Created Tweet by id " + id);
			report.pass("Status Code Verification", "Expected Status 200 </br> Actual Status " + resp.getStatusCode());
			report.info("Tweet Created", "Created Tweet by ID " + id);
			report.ReportSuccessResponse(currentResponseFilePath.substring(currentResponseFilePath.indexOf("Response"),
					currentResponseFilePath.length()));
		} else {
			report.fail("Status Code Verification", "Expected Status 200 </br> Actual Status " + resp.getStatusCode());
			report.ReportFailedResponse(currentResponseFilePath.substring(currentResponseFilePath.indexOf("Response"),
					currentResponseFilePath.length()));
			System.out.println("Unable to Created Tweet");
		}

	}

	@Test
	public void deleteTweet() {
		String currentResponseFilePath = pathToReponse + "DeleteTweetResponse.txt";
		resp = given().spec(spec).log().all().when().post("/destroy/" + id + ".json").then().log().all().extract()
				.response();
		CompactUtils.writeContentToFile(currentResponseFilePath, resp.asString());

		if (resp.getStatusCode() == 200) {
			System.out.println("Deleted Tweet by id " + id);
			report.pass("Status Code Verification", "Expected Status 200 </br> Actual Status " + resp.getStatusCode());
			report.info("Tweet Deleted", "Deleted Tweet by ID " + id);
			report.ReportSuccessResponse(currentResponseFilePath.substring(currentResponseFilePath.indexOf("Response"),
					currentResponseFilePath.length()));
		} else {
			report.fail("Status Code Verification", "Expected Status 200 </br> Actual Status " + resp.getStatusCode());
			report.ReportFailedResponse(currentResponseFilePath.substring(currentResponseFilePath.indexOf("Response"),
					currentResponseFilePath.length()));

			System.out.println("Unable to delete Tweet by id " + id);
		}
	}

}
