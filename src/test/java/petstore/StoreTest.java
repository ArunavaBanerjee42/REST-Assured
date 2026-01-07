package petstore;

import static org.hamcrest.Matchers.hasKey;

import org.hamcrest.Matchers;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;


public class StoreTest {
	@BeforeMethod
	public void init() {
		  RestAssured.baseURI = "https://petstore.swagger.io/v2/store";
	 }
	 @Test
	 public void findPurchaseOrderById() {
		  Response response = RestAssured
				  .given()
				  	.pathParam("orderId", 9)
				  .when()
				  	.get("/order/{orderId}")
				  .then()
				  	// Test 1: Verify status code
				    .statusCode(200)
				    // Test 2: Verify response is JSON
	                .contentType(ContentType.JSON)
	                //Test 3 : Verify each field
	                .body("$", hasKey("id"))
	                .body("$", hasKey("petId"))
	                .body("$", hasKey("quantity"))
	                
	                //Test 4 : Id is same as path parameter or not
	                .body("id", Matchers.equalTo(9))
	                .log().body()
			        .extract()
			        .response();
	              
	 }
	 
	 
	 
}
