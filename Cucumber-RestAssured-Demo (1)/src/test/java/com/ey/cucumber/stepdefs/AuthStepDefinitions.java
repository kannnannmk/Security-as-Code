package com.ey.cucumber.stepdefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;

import static org.junit.Assert.assertEquals;

public class AuthStepDefinitions {
    RequestSpecification request = RestAssured.given();
    Response response;


    @Given("User sets the base API request {string}")
    public void userSetsTheBaseAPIRequest(String url) {
        RestAssured.baseURI = url;
    }

    @When("User makes auth request with {string} and {string}")
    public void userMakesAuthRequestWithUsernameAndPassword(String username, String password){
        RestAssured.defaultParser = Parser.JSON;
        String path = "/api/authenticate";

        JSONObject requestParams = new JSONObject();
        requestParams.put("username", username);
        requestParams.put("password", password);

        response = RestAssured
                .given()
                .header("Content-Type", "application/json")
                .and()
                .body(requestParams.toJSONString())
                .request()
                .post(path)
                .then().extract().response();
    }

    @Then("The response should have {string}")
    public void theResponseShouldHave(String statusCode) {
        assertEquals(statusCode, String.valueOf(response.getStatusCode()));
    }
}