package com.dummyjson.testcases;

import com.dummyjson.models.*;
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
        CartResponse returnedResponse = response.body().as(CartResponse.class);
         assertThat(response.statusCode(), equalTo(200));
         assertThat(returnedResponse.getCarts(), notNullValue());
        assertThat(returnedResponse.getCarts().get(0).getId(), equalTo(id));
        assertThat(returnedResponse.getTotal(),  equalTo(1));
        assertThat(returnedResponse.getSkip(), equalTo(0));
        assertThat(returnedResponse.getLimit(), equalTo(1));
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
       Response response =  given().
                baseUri("https://dummyjson.com")
                .pathParam("userId", id)
                .contentType(ContentType.JSON)
                .when().get("/users/{userId}/posts")
                .then()
                .log().all()
               .extract().response();
        PostsResponse returnedResponse = response.body().as(PostsResponse.class);
        assertThat(response.statusCode(), equalTo(200));
        assertThat(returnedResponse.getPosts(), notNullValue());
        assertThat(returnedResponse.getPosts().size(), notNullValue());
        assertThat(returnedResponse.getPosts().get(0).getId(), notNullValue());
        assertThat(returnedResponse.getPosts().get(0).getTitle(), notNullValue());


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
