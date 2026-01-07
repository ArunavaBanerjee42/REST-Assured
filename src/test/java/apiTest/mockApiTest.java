package apiTest;

import static org.testng.Assert.assertEquals;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import config.Config;
import config.Details;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import static org.hamcrest.Matchers.*;
public class mockApiTest {

  @BeforeMethod
  public void init() {
	  RestAssured.baseURI = Config.BASE_URI;
  }
  @Test (priority = 1)
  public void testGetRequest() {
	  Response response = RestAssured
			  .given()
			  .when()
			  	.get()
	          .then()
	          	// Test 1: Verify the response status code is 200
	          	 .statusCode(200)
	            // Test 2: Verify the response body is valid JSON
	          	 .contentType("application/json")
	            // Test 3: Verify required properties
	          	.body("[0]", hasKey("id"))
	            .body("[0]", hasKey("name"))
	            .body("[0]", hasKey("price"))
	            .log().body()
	            .extract()
	            .response();
  }
  
  @Test (priority = 2)
  public void testPostRequest() {
	  Details data = new Details("10", 3621674, "Adidas");
	  Response response = RestAssured 
			  .given()
			    .contentType(ContentType.JSON)
			  	.body(data)
			  .when()
			  	.post()
			  .then()
			    // Test 1: Verify the response status code is 201
			  	.statusCode(201)
			  	// Test 2: Verify response type
	            .contentType("application/json")
	            // Test 3: Verify response fields exist
	            .body("id", notNullValue())
	            .body("name", notNullValue())
	            .body("price", notNullValue())
	            // Test 4: Verify response values
	            .body("id", equalTo("10"))
	            .body("name", equalTo("Adidas"))
	            .body("price", equalTo(3621674))
	            .log().body()
	            .extract()
	            .response();
  }
  
  @Test (priority = 3)
  public void testPatchRequest() {
	  Details data = new Details(3572175, "Accenture");
	  Response response = RestAssured
			   .given()
			   		.contentType(ContentType.JSON)
			   		.body(data)
			   	.when()
			   		.patch("/10")
			   	.then()
			        // Test 1: Verify the response status code is 200
			   		.statusCode(200)
			   	    // Test 2: Verify returned values match request body
			   		.body("name", equalTo("Accenture"))
			   		.body("price", equalTo(3572175))
			   	    // Test 3: Verify id is "10"
			   		.body("id", equalTo("10"))
			   		.log().body()
		            .extract()
		            .response();
  }
  
  @Test (priority = 4)
  public void testDeleteRequest() {
	  Response response = RestAssured
			  .given()
			  .when()
			  	.delete("/10")
	          .then()
	          	// Test 1: Verify the response status code is 200
		   		.statusCode(200)
		   		.log().body()
		   		.extract()
		   		.response();
		 
  }
  
}
