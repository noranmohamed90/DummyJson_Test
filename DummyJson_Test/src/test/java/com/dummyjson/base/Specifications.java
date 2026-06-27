package com.dummyjson.base;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class Specifications {
    public static  RequestSpecification getReqSpecifications(){
        return given()
                .baseUri("https://dummyjson.com")
                .contentType(ContentType.JSON)
                .log().all();
    }
}
