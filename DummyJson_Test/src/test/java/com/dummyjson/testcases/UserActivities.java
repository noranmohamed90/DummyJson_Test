package com.dummyjson.testcases;

import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class UserActivities {

    @Test
    public void userCart(){
        int id = 9 ;
        given().
                baseUri("https://dummyjson.com")
                .pathParam("userId", id)
                .contentType(ContentType.JSON)
                .when().get("/users/{userId}/carts")
                .then()
                .log().all()
                .statusCode(200)
                .body("carts",notNullValue())
                .body("carts[0].userId",equalTo(id))
                .body("total", equalTo(1))
                .body("skip", equalTo(0))
                .body("limit", equalTo(1));

    }

    @Test
    public void NegativeUserCart(){
        int id = 999 ;
        given().
                baseUri("https://dummyjson.com")
                .pathParam("userId", id)
                .contentType(ContentType.JSON)
                .when().get("/users/{userId}/carts")
                .then()
                .log().all()
                .statusCode(404)
                .body("message",equalTo("User with id '999' not found"));


    }

    @Test
    public void userPosts(){
        int id = 9 ;
        given().
                baseUri("https://dummyjson.com")
                .pathParam("userId", id)
                .contentType(ContentType.JSON)
                .when().get("/users/{userId}/posts")
                .then()
                .log().all()
                .statusCode(200)
                .body("posts",notNullValue())
                .body("posts.size()", greaterThan(0))
                .body("posts[0].id", notNullValue())
                .body("posts[0].title", notNullValue());

    }

    @Test
    public void NegativeUserPosts(){
        int id = 9 ;
        given().
                baseUri("https://dummyjson.com")
                .pathParam("userId", id)
                .contentType(ContentType.JSON)
                .when().post("/users/{userId}/posts")
                .then()
                .log().all()
                .statusCode(404);
    }



}
