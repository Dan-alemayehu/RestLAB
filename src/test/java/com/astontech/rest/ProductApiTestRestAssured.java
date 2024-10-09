package com.astontech.rest;

import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class ProductApiTestRestAssured {

    @Test
    public void testEndpointShouldReturn200(){
        //not really a test, just validates setup
        get("/test")
                .then()
                .statusCode(200);
    }

    @Test
    public void whenUsePathParamValidId_thenOk() {
        given().pathParam("id", 1)
                .when().get("/vehicle/{id}")
                .then().statusCode(200);
    }

    @Test
    public void whenUsePathParamInValidId_thenNOT_FOUND() {
        given().pathParam("id", 999)
                .when().get("/vehicle/{id}")
                .then().statusCode(404);
    }

    @Test
    public void whenUseQueryParamValidId_thenOk() {
        given().queryParam("id", 0)
                .when().get("/vehicle")
                .then().statusCode(200);
    }

    @Test
    public void whenFindByIdAssertProductDescription() {
        given().pathParam("id", 1)
                .when().get("/vehicle/{id}")
                .then().statusCode(200)
                .assertThat()
                .body("year", equalTo(1995));
    }

    @Test
    public void getResponseTime(){
        System.out.println("Response time: " + get("/product/").timeIn(TimeUnit.MILLISECONDS) + "ms");
    }

    @Test
    public void getResponseContentType() {
        System.out.println("Content Type of response: " + get("/product/").then().extract().contentType());
    }

    @Test
    public void saveProductShouldReturnAnID() {
        JSONObject requestBody = new JSONObject();
        requestBody.put("sku", "A17-223");
        requestBody.put("description", "Apple iPad");

        given().header("Content-Type", "application/json")
                .body(requestBody.toJSONString())
                .post("/product/")
                .then().statusCode(201)
                .assertThat()
                .body("$", hasKey("id"))
                .body("sku", equalTo("A17-223"))
                .body("description", equalTo("Apple iPad"));
    }

    @Test
    public void patchProductWithInvalidIDShouldThrowException() {
        JSONObject requestBody = new JSONObject();
        requestBody.put("sku", "A17-223");

        given().header("Content-Type", "application/json")
                .body(requestBody.toJSONString())
                .pathParam("id", 999)
                .when()
                .patch("/product/{id}")
                .then()
                .statusCode(404);
    }

    @Test
    public void patchProductWithInvalidFieldNameShouldThrowException() {
        JSONObject requestBody = new JSONObject();
        requestBody.put("dog", "A17-223");

        given().header("Content-Type", "application/json")
                .body(requestBody.toJSONString())
                .pathParam("id", 1)
                .when()
                .patch("/product/{id}")
                .then()
                .statusCode(422);
    }

    @Test
    public void patchProductWithValidFieldsShouldUpdateResource() {
        JSONObject requestBody = new JSONObject();
        requestBody.put("quantity", 72);
        requestBody.put("price", 295.55);
        requestBody.put("weight", 15.4);
        requestBody.put("description", "23x2x60");

        given().header("Content-Type", "application/json")
                .body(requestBody.toJSONString())
                .patch("/product/1")
                .then()
                .statusCode(202)
                .assertThat()
                .body("quantity", equalTo(72))
                .body("price", equalTo(295.55f))
                .body("weight", equalTo(15.4f))
                .body("description", equalTo("23x2x60"));
    }

}
