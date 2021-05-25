package RestAPI_Activities;

import static io.restassured.RestAssured.given;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;


public class GitHub_RestAssured_Project {
	// Declare request specification
	RequestSpecification requestSpec;
	// Declare response specification
	ResponseSpecification responseSpec;
	// Global properties
	String sshKey = "ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABgQC0tdSPvjiXCMgu0tvXRn0uvg1JID/5WJcgglXa2sKuy7/oLqfZiF45Bq/Vmf0rQ7/2QqQERJTXEzf99WFjTPigA7WyVxqt3NB+LAATm+4ZF4JOQC9Bdffg6FTlkxukWYEYbaJEv2N1Ndci0Vh9oX7AdyxIkDMBsaAGbsjWFAdKqs9TXghkwsCRgrcoiPmbHsqzmJwwF5B1l022lCDfL2zlY8kQWYGfXOxiW1GCcvipNiJMjlBQfQWvGONAAd/Lai06AoS2Xr/0ORkWkZcm60fFQ29dEn0BxVQhYjfLZdp5/HSWU31ZGqJw0zdXAK7zHCvVb/CJrfFGpS2Mg3rWkdzoRw+3R0F61MpfozxG0X0rAgo8lHneEMsuFsHm+NgS+h8T7yZwkeHNm1oEyuziRB+uVEWF4Ph3t5nNARpihkav8nGVM5XNivw/XwuPKyuFygsrdsdZqfNZZwO2gfqxOWixoiP9hDKmoRAiWddTFtIvmcmuX7Uz2dXlR2oi9IaYwR8=";
	int sshKeyId;
	@BeforeClass
	public void setUp() {
		// Create request specification
		requestSpec = new RequestSpecBuilder()
				// Set content type
				.setContentType(ContentType.JSON)
				.addHeader("Authorization", "token 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")
				// Set base URL
				.setBaseUri("https://api.github.com")
				// Build request specification
				.build();
		
	}
	@Test(priority = 1)
	// Test case using a DataProvider
	public void addKeys() {
		String reqBody = "{\"title\": \"TestKey\", \"key\": \"" + sshKey + "\" }";
		Response response = given().spec(requestSpec) // Use requestSpec
				.body(reqBody) // Send request body
				.when().post("/user/keys"); // Send POST request
		String resBody = response.getBody().asPrettyString();
		System.out.println(resBody);
		sshKeyId = response.then().extract().path("id");
		// Assertions
		response.then().statusCode(201);
	}
	@Test(priority = 2)
	// Test case using a DataProvider
	public void getKeys() {
		Response response = given().spec(requestSpec) // Use requestSpec
				.when().get("/user/keys"); // Send GET Request
		String resBody = response.getBody().asPrettyString();
		System.out.println(resBody);
		// Assertions
		response.then().statusCode(200);
	}
	@Test(priority = 3)
	// Test case using a DataProvider
	public void deleteKeys() {
		Response response = given().spec(requestSpec) // Use requestSpec
				.pathParam("keyId", sshKeyId).when().delete("/user/keys/{keyId}"); // Send GET Request
		String resBody = response.getBody().asPrettyString();
		System.out.println(resBody);
		// Assertions
		response.then().statusCode(204);
	}
}

