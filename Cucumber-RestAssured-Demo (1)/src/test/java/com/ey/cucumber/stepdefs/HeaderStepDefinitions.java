package com.ey.cucumber.stepdefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import static org.junit.Assert.*;

import io.restassured.http.Header;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import io.restassured.http.Method;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;

public class HeaderStepDefinitions {
    RequestSpecification request;
    Response response;
    String token;
    String securityHeader;
    String path;

    @Given("User sets the base request {string}")
    public void userSetsTheBaseRequest(String url) {
        RestAssured.baseURI = url;
    }

    @And("an admin token has been obtained")
    public void aTokenHasBeenObtained() {
        RestAssured.defaultParser = Parser.JSON;
        String path = "/api/authenticate";

        JSONObject requestParams = new JSONObject();
        requestParams.put("username", "administrator");
        requestParams.put("password", "admin");

        response = RestAssured
                .given()
                .header("Content-Type", "application/json")
                .and()
                .body(requestParams.toJSONString())
                .request()
                .post(path)
                .then().extract().response();
        token = response.jsonPath().getString("id_token");
    }

    @When("accessing this {string}")
    public void accessingThis(String endpoint) {
        path = endpoint;
    }

    @Then("Then XSS {string} should be present")
    public void thenXSSShouldBePresent(String XSSheader) {
        securityHeader = RestAssured.given()
                .header("Authorization", "Bearer "+token)
                .when()
                .get(path)
                .then()
                .extract()
                .header("x-xss-protection");
        assertEquals(XSSheader, securityHeader);
    }

    @Then("Then x-frame-options {string} should be present")
    public void thenXFrameOptionsShouldBePresent(String xframeheader) {
        securityHeader = RestAssured.given()
                .header("Authorization", "Bearer "+token)
                .when()
                .get(path)
                .then()
                .extract()
                .header("x-frame-options");
        assertEquals(xframeheader, securityHeader);
    }
}
