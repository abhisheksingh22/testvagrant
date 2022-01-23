package com.testvagrant.api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;

public class BaseAPIConfiguration {

    protected static RequestSpecification requestSpecification = null;

    /**
     *
     * @return RequestSpecification
     */
    public static RequestSpecification getRequestSpecification() {
        requestSpecification = RestAssured.given().contentType(ContentType.JSON).log().all();
        return requestSpecification;
    }

    /**
     * Returns response of GET APIs call
     * @param requestSpecification requestSpecification
     * @param endpoint endpoint
     * @param status status
     * @return response
     */
    public static Response getCallResponse(RequestSpecification requestSpecification, String endpoint, int status){
        Response response = requestSpecification.get(endpoint);
        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(), status);
        return response;
    }
}
