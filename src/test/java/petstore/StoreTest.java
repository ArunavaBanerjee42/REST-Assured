package petstore;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.lessThan;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hamcrest.Matchers;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import petConfig.Order;


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
	 
	 @Test
	 public void petInventoryByStatus() {
		 List<String>expectedProperty = new ArrayList<>(Arrays.asList("sold", "string", "alive", "invalid_status", "pending", "available", "Not Available", "Available"));
		 Response response = RestAssured
				 .given()
				 .when()
				 	.get("/inventory")
				 .then()
				 	//Test 1: Verify the response status is 200
				 	.statusCode(200)
				 	// Test 2: Verify the response is valid JSON
				 	.contentType(ContentType.JSON)
		 			// Test 4: Verify response time is reasonable (under 2.5 seconds)
		 			.time(lessThan(2500L))
		 			.log().body()
			        .extract()
			        .response();
				    // Test 3: Verify the response contains expected properties
				    for(String key: expectedProperty) {
					   response.then().body("$", hasKey(key));
				    }
					 			
		 
	 }
	 
	 @Test
	 public void placeAnOrderForPet() {
		 String shipDate = OffsetDateTime.now().toString();
//	     System.out.println(date);
		 Order newOrder = new Order(0, 0, shipDate, "placed", true);
		 Response response = RestAssured
				 .given()
				 	.contentType(ContentType.JSON)
		 			.body(newOrder)
		 		 .when()
		 		 	.post("/order")
		 		 .then()
		 		 	//Test 1: Verify the response status is 200
				 	.statusCode(200)
				 	// Test 2: Verify the response is valid JSON
				 	.contentType(ContentType.JSON)
				 	//Test 3 : Verify each field
				 	.body("$", hasKey("petId"))
				 	.body("$", hasKey("quantity"))
				 	.body("$", hasKey("shipdate"))
				 	.body("$", hasKey("status"))
				 	.body("$", hasKey("complete"))
				 	
				 	//Test 4 : Verify the response values match the request
				 	.body("petId", equalTo(newOrder.getPetId()))
				 	.body("quantity", equalTo(newOrder.getQuantity()))
				 	.body("status", equalTo(newOrder.getStatus()))
				 	.body("complete", equalTo(newOrder.getComplete()))
				    
				 	
				 	.log().body()
			        .extract()
			        .response();
				 	
				 	
		 		 	
				 
	 }
	 
	 
	 
}
