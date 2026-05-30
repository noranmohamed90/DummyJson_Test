package com.dummyjson.testcases;

import com.dummyjson.models.Errors;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class UserActivities {

    @Test
    public void userCart(){
        int id = 9 ;
        Response response = given().
                baseUri("https://dummyjson.com")
                .pathParam("cartId", id)
                .contentType(ContentType.JSON)
                .when().get("/users/{cartId}/carts")
                .then()
                .log().all()
                .extract().response();
         assertThat(response.statusCode(), equalTo(200));
         assertThat(response.path("carts"), notNullValue());
         assertThat(response.path("carts[0].cartId"), equalTo(id));
         assertThat(response.path("total"),  equalTo(1));
         assertThat(response.path("skip"), equalTo(0));
         assertThat(response.path("limit"), equalTo(1));
    }

    @Test
    public void NegativeUserCart(){
        int id = 999 ;
        Response response = given().
                baseUri("https://dummyjson.com")
                .pathParam("userId", id)
                .contentType(ContentType.JSON)
                .when().get("/users/{userId}/carts")
                .then()
                .log().all()
                .extract().response();
        Errors getResponse =response.body().as(Errors.class);
        assertThat(response.statusCode(),equalTo(404));
        assertThat(getResponse.getMessage(),equalTo("User with id '999' not found"));


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
        int id = 999 ;
         Response response = given().
                baseUri("https://dummyjson.com")
                .pathParam("userId", id)
                .contentType(ContentType.JSON)
                .when().get("/users/{userId}/posts")
                .then()
                .log().all()
                .extract().response();
        Errors getResponse =response.body().as(Errors.class);
        assertThat(response.statusCode(),equalTo(404));
        assertThat(getResponse.getMessage(),equalTo("User with id '999' not found"));
    }



}
