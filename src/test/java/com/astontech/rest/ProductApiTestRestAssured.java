package com.astontech.rest;

import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class ProductApiTestRestAssured {

    //Vehicle Make Tests

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
                .pathParam("id", 7)
                .when().put("/vehicle-makes/{id}")
                .then().statusCode(200);
    }

    @Test
    public void whenDeleteVehicleMake_thenOk() {
        given().pathParam("id", 7)
                .when().delete("/vehicle-makes/{id}")
                .then().statusCode(200);
    }

    //Vehicle Model Tests
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
                .pathParam("id", 7)
                .when().put("/vehicle-models/{id}")
                .then().statusCode(200);
    }

    @Test
    public void whenDeleteVehicleModel_thenOk() {
        given().pathParam("id", 8)
                .when().delete("/vehicle-models/{id}")
                .then().statusCode(200);
    }

    //Vehicle Test
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
        requestBody.put("licensePlate", "WVU-345");
        requestBody.put("year", 1995);
        requestBody.put("vin", "W349582028475");
        requestBody.put("color", "Maroon");

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
        requestBody.put("vin", "W349582028475");
        requestBody.put("color", "Red");

        given().header("Content-Type", "application/json")
                .body(requestBody.toJSONString())
                .pathParam("id", 1)
                .when().put("/vehicle/{id}")
                .then().statusCode(200);
    }

    @Test
    public void whenDeleteVehicle_thenOk() {
        given().pathParam("id", 3)
                .when().delete("/vehicle/{id}")
                .then().statusCode(200);
    }

    //Exception testing
    @Test
    public void whenVehicleNotFound_thenNotFound() {
        given().pathParam("id", 999)
                .when().get("/vehicle/{id}")
                .then().statusCode(404)
                .assertThat()
                .body(equalTo("Vehicle not found"));
    }

    @Test
    public void whenVehicleMakeNotFound_thenNotFound() {
        given().pathParam("id", 999)
                .when().get("/vehicle-makes/{id}")
                .then().statusCode(404)
                .assertThat()
                .body(equalTo("Vehicle make not found"));
    }

    @Test
    public void whenVehicleModelNotFound_thenNotFound() {
        given().pathParam("id", 999)
                .when().get("/vehicle-models/{id}")
                .then().statusCode(404)
                .assertThat()
                .body(equalTo("Vehicle model not found"));
    }

    @Test
    public void whenFieldNotFound_thenUnprocessableEntity() {
        given().pathParam("id", 999)
                .when().get("/vehicle-models/{id}")
                .then().statusCode(422)
                .assertThat()
                .body(equalTo("Field not found"));
    }

    @Test
    public void whenUnauthorized_thenUnauthorized() {
        given().pathParam("id", 999)
                .when().get("/vehicle/{id}")
                .then().statusCode(401)
                .assertThat()
                .body(equalTo("Unauthorized access"));
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
}
