package petstore;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.not;

import java.util.List;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import config.Category;
import config.Pet;
import config.Tag;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class PetApiTest {
	@BeforeMethod
	public void init() {
		  RestAssured.baseURI = "https://petstore.swagger.io/v2/pet";
	 }
	 @Test
	 public void addNewPetToTheStore() {
		 Category category = new Category();
		 category.setId(0);
		 category.setName("string");
		 Tag tag = new Tag();
		 tag.setId(0);
		 tag.setName("string");

	     Pet pet = new Pet();
	     pet.setId(922337203);
	     pet.setCategory(category);
	     pet.setName("doggie");
	     pet.setPhotoUrls(List.of("string"));
	     pet.setTags(List.of(tag));
	     pet.setStatus("available");
		 Response response = RestAssured
				  .given()
					  .contentType(ContentType.JSON)
					  .body(pet)
				  .when()
				  	  .post()
				   .then()
				        //Test 1 : Check status code is 200
				       .statusCode(200)
				       //Test 2 : Verify response is valid JSON
				       .contentType(ContentType.JSON)
				       //Test 3 : Verify response has all required fields
				       .body("$", hasKey("id"))
				       .body("$", hasKey("category"))
				       .body("$", hasKey("name"))
				       .body("$", hasKey("photoUrls"))
				       .body("$", hasKey("tags"))
				       .body("$", hasKey("status"))
				       //Test 4: Verify pet name matches request body
				       .body("name", equalTo(pet.getName()))
				       //Test 5 : Verify time 
				       .time(lessThan(2500l))
					   .log().body()
			           .extract()
			           .response();
				  
	 }
	 
	 @Test
	 public void findPetByStatus() {
		 Response response = RestAssured 
				 .given()
				 	.queryParam("status", "available")
				 .when()
				 	.get("/findByStatus")
				 .then()
				 // Test 1: Verify status code
                 .statusCode(200)

                 // Test 2: Verify response is JSON
                 .contentType(ContentType.JSON)

                 // Test 3: Verify response is a non-empty array
                 .body("$", not(empty()))

                 // Test 4: Verify each pet has required fields
                 .body("[0]", hasKey("id"))
                 .body("[0]", hasKey("name"))
                 .body("[0]", hasKey("status"))

                 // Test 5: Verify all pets have status = available
                 .body("status", everyItem(equalTo("available")))
                 
                 
                 .log().body()
                 .extract()
                 .response();
	 }
	 
	 @Test 
	 public void findPetByCorrectPetId() {
		 Response response = RestAssured
				 .given()
				 	.pathParam("id", 12)
				 .when()
				 	.get("/{id}")
				 .then()
				// Test 1: Verify status code
                 .statusCode(200)

                 // Test 2: Verify response is JSON
                 .contentType(ContentType.JSON)

                 // Test 3: Verify response is a non-empty array
                 .body("$", not(empty()))

                 // Test 4: Verify pet id is 12 or not
                 .body("id", equalTo(12))

                 .log().body()
                 .extract()
                 .response();
	 }
	 @Test
	 public void findPetByInValidPetId() {
		 Response response = RestAssured
				 .given()
				 	.pathParam("id", "12@122")
				 .when()
				 	.get("/{id}")
				 .then()
				 // Test 1: Verify status code
                 .statusCode(404)
                 .log().body()
                 .extract()
                 .response();
	 }
	 
	 
	 
	 
}
