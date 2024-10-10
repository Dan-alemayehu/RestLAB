package com.astontech.rest;

import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class ProductApiTestRestAssured {

    //Vehicle Make Tests
    @Test
    public void whenFindAllVehicleMakes_thenOk() {
        when().get("/vehicle-makes/")
                .then().statusCode(200)
                .assertThat()
                .body("$", hasSize(greaterThan(0)));
    }

    @Test
    public void whenUseVehicleMakePathParamValidId_thenOk() {
        given().pathParam("id", 1)
                .when().get("/vehicle-makes/{id}")
                .then().statusCode(200);
    }

    @Test
    public void whenUseVehicleMakePathParamInvalidId_thenNotFound() {
        given().pathParam("id", 999)
                .when().get("/vehicle-makes/{id}")
                .then().statusCode(404);
    }

    @Test
    public void whenPostValidVehicleMake_thenCreated() {
        JSONObject requestBody = new JSONObject();
        requestBody.put("vehicleMakeName", "PoleStar");

        given().header("Content-Type", "application/json")
                .body(requestBody.toJSONString())
                .when().post("/vehicle-makes/")
                .then().statusCode(201);
    }

    @Test
    public void whenUpdateValidVehicleMake_thenOk() {
        JSONObject requestBody = new JSONObject();
        requestBody.put("vehicleMakeName","Rivian");

        given().header("Content-Type", "application/json")
                .body(requestBody.toJSONString())
                .pathParam("id", 3)
                .when().put("/vehicle-makes/{id}")
                .then().statusCode(200);
    }

    @Test
    public void whenPatchVehicleMake_thenOk() {
        // Creating a JSON object to represent the updates we want to apply
        JSONObject requestBody = new JSONObject();
        requestBody.put("vehicleMakeName", "Fisker");

        // Sending the PATCH request and verifying the response
        given().header("Content-Type", "application/json")
                .body(requestBody.toJSONString())
                .pathParam("id", 3)
                .when().patch("/vehicle-makes/{id}")
                .then().statusCode(200)
                .assertThat()
                .body("vehicleMakeName", equalTo("Fisker"));  // Verifying the color is updated

    }

    @Test
    public void whenDeleteVehicleMake_thenOk() {
        given().pathParam("id", 3)
                .when().delete("/vehicle-makes/{id}")
                .then().statusCode(204);
    }

    //Vehicle Model Tests
    @Test
    public void whenFindAllVehicleModels_thenOk() {
        when().get("/vehicle-models/")
                .then().statusCode(200)
                .assertThat()
                .body("$", hasSize(greaterThan(0))); // Check that the result is not empty
    }

    @Test
    public void whenUseVehicleModelPathParamValidId_thenOk() {
        given().pathParam("id", 2)
                .when().get("/vehicle-models/{id}")
                .then().statusCode(200);
    }

    @Test
    public void whenUseVehicleModelPathParamInvalidId_thenNotFound() {
        given().pathParam("id", 999)
                .when().get("/vehicle-models/{id}")
                .then().statusCode(404);
    }

    @Test
    public void whenPostValidVehicleModel_thenCreated() {
        JSONObject requestBody = new JSONObject();
        requestBody.put("modelName", "Model 3");

        given().header("Content-Type", "application/json")
                .body(requestBody.toJSONString())
                .when().post("/vehicle-models/")
                .then().statusCode(201);
    }

    @Test
    public void whenUpdateValidVehicleModel_thenOk() {
        JSONObject requestBody = new JSONObject();
        requestBody.put("modelName", "Roadster");

        given().header("Content-Type", "application/json")
                .body(requestBody.toJSONString())
                .pathParam("id", 3)
                .when().put("/vehicle-models/{id}")
                .then().statusCode(200);
    }

    @Test
    public void whenPatchVehicleModel_thenOk() {
        // Creating a JSON object to represent the updates we want to apply
        JSONObject requestBody = new JSONObject();
        requestBody.put("modelName", "CyberTruck");  // Update the name

        // Sending the PATCH request and verifying the response
        given().header("Content-Type", "application/json")
                .body(requestBody.toJSONString())
                .pathParam("id", 3) // Assuming vehicle with ID 2 exists
                .when().patch("/vehicle-models/{id}")
                .then().statusCode(200)  // Expecting HTTP 200 OK status
                .assertThat()
                .body("modelName", equalTo("CyberTruck"));  // Verifying the color is updated

    }

    @Test
    public void whenDeleteVehicleModel_thenOk() {
        given().pathParam("id", 3)
                .when().delete("/vehicle-models/{id}")
                .then().statusCode(204);
    }

    //Vehicle Test
    @Test
    public void whenFindAllVehicles_thenOk() {
        when().get("/vehicle/")
                .then().statusCode(200)
                .assertThat()
                .body("$", hasSize(greaterThan(0))); // Check that the result is not empty
    }

    @Test
    public void whenUseVehiclePathParamValidId_thenOk() {
        given().pathParam("id", 3)
                .when().get("/vehicle/{id}")
                .then().statusCode(200);
    }

    @Test
    public void whenUseVehiclePathParamInvalidId_thenNotFound() {
        given().pathParam("id", 999)
                .when().get("/vehicle/{id}")
                .then().statusCode(404);
    }

    @Test
    public void whenPostValidVehicle_thenCreated() {
        JSONObject requestBody = new JSONObject();
        requestBody.put("licensePlate", "KTS-694");
        requestBody.put("year", 2020);
        requestBody.put("vin", "M284673028772");
        requestBody.put("color", "White");

        given().header("Content-Type", "application/json")
                .body(requestBody.toJSONString())
                .when().post("/vehicle/")
                .then().statusCode(201);
    }

    @Test
    public void whenUpdateValidVehicle_thenOk() {
        JSONObject requestBody = new JSONObject();
        requestBody.put("licensePlate", "ABC-123");
        requestBody.put("year", 2020);
        requestBody.put("vin", "X457399340216");
        requestBody.put("color", "Red");

        given().header("Content-Type", "application/json")
                .body(requestBody.toJSONString())
                .pathParam("id", 4)
                .when().put("/vehicle/{id}")
                .then().statusCode(200);
    }

    @Test
    public void whenPatchVehicle_thenOk() {
        // Creating a JSON object to represent the updates we want to apply
        JSONObject requestBody = new JSONObject();
        requestBody.put("color", "Blue");  // Update the color
        requestBody.put("year", 2022);     // Update the year

        // Sending the PATCH request and verifying the response
        given().header("Content-Type", "application/json")
                .body(requestBody.toJSONString())
                .pathParam("id", 4) // Assuming vehicle with ID 3 exists
                .when().patch("/vehicle/{id}")
                .then().statusCode(200)  // Expecting HTTP 200 OK status
                .assertThat()
                .body("color", equalTo("Blue"))  // Verifying the color is updated
                .body("year", equalTo(2022));    // Verifying the year is updated
    }

    @Test
    public void whenDeleteVehicle_thenOk() {
        given().pathParam("id", 4)
                .when().delete("/vehicle/{id}")
                .then().statusCode(204);
    }

    //Exception testing
    @Test
    public void whenVehicleNotFound_thenNotFound() {
        given().pathParam("id", 999)
                .when().get("/vehicle/{id}")
                .then().statusCode(404)
                .assertThat()
                .body(equalTo("Vehicle with ID 999 not found"));
    }

    @Test
    public void whenVehicleMakeNotFound_thenNotFound() {
        given().pathParam("id", 999)
                .when().get("/vehicle-makes/{id}")
                .then().statusCode(404)
                .assertThat()
                .body(equalTo("Vehicle Make with ID 999 not found"));
    }

    @Test
    public void whenVehicleModelNotFound_thenNotFound() {
        given().pathParam("id", 999)
                .when().get("/vehicle-models/{id}")
                .then().statusCode(404)
                .assertThat()
                .body(equalTo("Vehicle Model with ID 999 not found"));
    }

    @Test
    public void whenFieldNotFound_thenUnprocessableEntity() {
        Map<String, Object> updates = new HashMap<>();
        updates.put("invalidField", "testValue"); // Field that does not exist in the Vehicle class

        given().contentType("application/json")
                .body(updates)
                .pathParam("id", 1)  // Assuming vehicle with ID 1 exists
                .when().patch("/vehicle/{id}")
                .then().statusCode(422)
                .assertThat()
                .body(equalTo("Object does not contain field: invalidField"));
    }


    @Test
    public void whenPostVehicleWithDuplicateVin_thenConflict() {
        JSONObject requestBody = new JSONObject();
        requestBody.put("vin", "W349582028475");
        requestBody.put("licensePlate", "XYZ-123");
        requestBody.put("year", 2020);
        requestBody.put("color", "Black");

        given().header("Content-Type", "application/json")
                .body(requestBody.toJSONString())
                .when().post("/vehicle/")
                .then().statusCode(409);
    }

    @Test
    public void whenPostVehicleMakeWithDuplicateMakeName_thenConflict() {
        JSONObject requestBody = new JSONObject();
        requestBody.put("vehicleMakeName", "Tesla");

        given().header("Content-Type", "application/json")
                .body(requestBody.toJSONString())
                .when().post("/vehicle-makes/")
                .then().statusCode(409);
    }

    @Test
    public void whenPostVehicleModelWithDuplicateModelName_thenConflict() {
        JSONObject requestBody = new JSONObject();
        requestBody.put("modelName", "Model X");

        given().header("Content-Type", "application/json")
                .body(requestBody.toJSONString())
                .when().post("/vehicle-models/")
                .then().statusCode(409);
    }

    @Test
    public void whenUpdateVehicleWithDuplicateVin_thenConflict() {
        JSONObject requestBody = new JSONObject();
        requestBody.put("vin", "W349582028475"); // This VIN already exists
        requestBody.put("licensePlate", "NEW-456");
        requestBody.put("year", 2021);
        requestBody.put("color", "Silver");

        given().header("Content-Type", "application/json")
                .body(requestBody.toJSONString())
                .pathParam("id", 3) // Assuming vehicle with ID 3 is being updated
                .when().put("/vehicle/{id}")
                .then().statusCode(409); // Expecting conflict status code
    }

    @Test
    public void whenUpdateVehicleMakeWithDuplicateMake_thenConflict() {
        JSONObject requestBody = new JSONObject();
        requestBody.put("vehicleMakeName", "Tesla"); // Assuming "Tesla" already exists

        given().header("Content-Type", "application/json")
                .body(requestBody.toJSONString())
                .pathParam("id", 2) // Assuming vehicle make with ID 2 is being updated
                .when().put("/vehicle-makes/{id}")
                .then().statusCode(409); // Expecting conflict status code
    }

    @Test
    public void whenUpdateVehicleModelWithDuplicateModel_thenConflict() {
        JSONObject requestBody = new JSONObject();
        requestBody.put("modelName", "Model X"); // Assuming "Model X" already exists

        given().header("Content-Type", "application/json")
                .body(requestBody.toJSONString())
                .pathParam("id", 2) // Assuming vehicle model with ID 5 is being updated
                .when().put("/vehicle-models/{id}")
                .then().statusCode(409); // Expecting conflict status code
    }
}
