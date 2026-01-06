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

public class mockApiTest {

  @BeforeMethod
  public void init() {
	  RestAssured.baseURI = Config.BASE_URI;
  }
  @Test (priority = 1)
  public void testGetRequest() {
	  RequestSpecification httpRequest = RestAssured.given();
	  Response response = httpRequest.get();
	  int passed = 0, failed = 0;
	  // Test 1: Verify the response status code is 200
	  try {
	     assertEquals(200, response.statusCode());
	     System.out.println("Test 1 PASSED : The response status code is 200");
	     passed++;
	  } catch (AssertionError e) {
	     System.out.println("Test 1 FAILED : The response status code is not 200");
	     failed++;
	  }
	  // Test 2: Verify the response body is valid JSON
	  try {
        String type = response.header("Content-Type");
        assertEquals(type, "application/json");
        System.out.println("Test 2 PASSED : Response body is valid JSON");
        passed++;
	  } catch (AssertionError e) {
        System.out.println("Test 2 FAILED : Response body is not valid JSON");
        failed++;
	  }
	  // Test 3: Verify response has required properties (id, name, price)
	  try {
	      String responseBody = response.getBody().asString();
	      assertTrue(responseBody.contains("\"id\""));
	      assertTrue(responseBody.contains("\"name\""));
	      assertTrue(responseBody.contains("\"price\""));

	      System.out.println("Test 2 PASSED : Response has required properties (id, name, price)");
	      passed++;
	  } catch (AssertionError e) {
	      System.out.println("Test 2 FAILED : Missing required properties");
	      failed++;
	  }
	  System.out.println("Total Tests : " + (passed + failed));
	  System.out.println("Passed      : " + passed);
	  System.out.println("Failed      : " + failed);

	  System.out.println(response.asPrettyString());
	  
  }
  
  @Test (priority = 2)
  public void testPostRequest() {
	  Details data = new Details("6", 35535757, "Nike");
	  RequestSpecification httpRequest = RestAssured.given();
	  httpRequest.contentType(ContentType.JSON);
	  httpRequest.body(data);
	  Response response = httpRequest.post();
	  int passed = 0, failed = 0;
	  // Test 1: Verify the response status code is 201
	  try {
		  assertEquals(201, response.statusCode());
		  System.out.println("Test 1 PASSED : The response status code is 201");
		  passed++;
	  }catch(AssertionError e) {
		  System.out.println("Test 1 FAILED : The response status code is not 201");
		  failed++;
	  }
	  // Test 2: Verify the response body is valid JSON
	  try {
	        String type = response.header("Content-Type");
	        assertEquals(type, "application/json");
	        System.out.println("Test 2 PASSED : Response body is valid JSON");
	        passed++;
	    } catch (AssertionError e) {
	        System.out.println("Test 2 FAILED : Response body is not valid JSON");
	        failed++;
	    }
	  // Test 3: Verify the response contains the expected fields (id, name, price)
	  try {
	        String name = response.jsonPath().getString("name");
	        String id = response.jsonPath().getString("id");
	        int price = response.jsonPath().get("price");
	        assertEquals("Nike", name);
	        assertEquals("6", id);
	        assertEquals(35535757, price);
	        System.out.println("Test 3 PASSED : Response contains expected fields");
	        passed++;
	    } catch (Exception e) {
	        System.out.println("Test 3 FAILED : Missing or incorrect response fields");
	        failed++;
	    }
	  	System.out.println("Total Tests : " + (passed + failed));
	    System.out.println("Passed      : " + passed);
	    System.out.println("Failed      : " + failed);
  }
  
  @Test (priority = 3)
  public void testDeleteRequest() {
	  RequestSpecification httpRequest = RestAssured.given();
	  Response response = httpRequest.delete("/6");
	  int passed = 0, failed = 0;
	  // Test 1: Verify the response status code is 201
	  try {
		  assertEquals(201, response.statusCode());
		  System.out.println("Test 1 PASSED : The response status code is 201");
		  passed++;
	  }catch(AssertionError e) {
		  System.out.println("Test 1 FAILED : The response status code is not 201");
		  failed++;
	  } 
	  System.out.println("Total Tests : " + (passed + failed));
	  System.out.println("Passed      : " + passed);
	  System.out.println("Failed      : " + failed);
  }
  
  @Test (priority = 4)
  public void testPatchRequest() {
	  Details data = new Details(3572175, "Accenture");
	  RequestSpecification httpRequest = RestAssured.given();
	  httpRequest.contentType(ContentType.JSON);  
	  httpRequest.body(data);
	  Response response = httpRequest.patch("/9");
	  int passed = 0, failed = 0;
	  // Test 1: Verify the response status code is 201
	  try {
		  assertEquals(200, response.statusCode());
		  System.out.println("Test 1 PASSED : The response status code is 201");
		  passed++;
	  }catch(AssertionError e) {
		  System.out.println("Test 1 FAILED : The response status code is not 201");
		  failed++;
	  }
	  // Test 2: Verify returned values match request body
	  try {
		  System.out.println(response.asPrettyString());
		  String name = response.jsonPath().getString("name");
	      int price = response.jsonPath().get("price");
	      assertEquals(name, data.getName());
	      assertEquals(price, data.getPrice());
	      System.out.println("Test 2 PASSED : Returned values match request body");
	      passed++;
	  }catch(AssertionError e) {
		  System.out.println("Test 2 FAILED : Returned values do not match request body");
		  failed++;
	  }
	  // Test 3: Verify id is "9"
	  try {
		  String id = response.jsonPath().getString("id");
		  assertEquals(id, "9");
		  System.out.println("Test 3 PASSED : Id is 9");
	      passed++;
	  }catch(AssertionError e) {
		  System.out.println("Test 3 FAILED : Id is not 9");
	      failed++;
	  }
	  System.out.println("Total Tests : " + (passed + failed));
	  System.out.println("Passed      : " + passed);
	  System.out.println("Failed      : " + failed);
  }
  
}
