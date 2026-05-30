package com.dummyjson.testcases;

import com.dummyjson.models.Errors;
import com.dummyjson.models.UserInfo;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.awt.*;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class UserTest {


    @Test
    public void LoginUserTC() {
        UserInfo user = new UserInfo("emilys", "emilyspass", 30);
        Response response = given().
                baseUri("https://dummyjson.com")
                .contentType(ContentType.JSON)
                .body(user)
                .when().post("/user/login")
                .then()
                .log().all()
                .extract().response();
        UserInfo getResponse =response.body().as(UserInfo.class);
        assertThat(response.statusCode(), equalTo(200));
        assertThat(getResponse.getUsername(), equalTo(user.getUsername()));
        assertThat(getResponse.getAccessToken(), not(equalTo(null)));
    }

    @Test
    public void NegativeLoginUserTC(){

        UserInfo user =new UserInfo("emilys","emilysss",30);
        Response response= given().
                baseUri("https://dummyjson.com")
                .contentType(ContentType.JSON)
                .body(user)
                .when().post("/user/login")
                .then()
                .log().all()
                .extract().response();
        Errors getResponse =response.body().as(Errors.class);
        assertThat(response.statusCode(),equalTo(400));
        assertThat(getResponse.getMessage(),equalTo("Invalid credentials"));
    }
    @Test
    public void GetAllUsersTC(){

         Response response =given().
                baseUri("https://dummyjson.com")
                .contentType(ContentType.JSON)
                .when().get("/users")
                .then()
                .log().all()
                 .extract().response();
         assertThat(response.statusCode(),equalTo(200));
         assertThat(response.path("users"),notNullValue());
         assertThat(response.path("users.size()"),greaterThan(0));
         assertThat(response.path("users[0].id"),greaterThan(0));

    }
    @Test
    public void negativeGetAllUsersTC(){

        Response response =given().
                baseUri("https://dummyjson.com")
                .contentType(ContentType.JSON)
                .when().post("/users")
                .then()
                .log().all()
                .extract().response();
        assertThat(response.statusCode(),equalTo(404));
    }

    @Test
    public void GetSingleUsersTC(){
        int id = 5 ;
        Response response = given().
                baseUri("https://dummyjson.com")
                .contentType(ContentType.JSON)
                .when().get("/users/" +id)
                .then()
                .log().all()
                .extract().response();

        UserInfo getResponse =response.body().as(UserInfo.class);
        assertThat(getResponse.getId(),equalTo(id));
        assertThat(getResponse.getFirstName(),notNullValue());
        assertThat(getResponse.getEmail(),containsString("@"));

    }
    @Test
    public void negativeSingleUsersTC(){

             int id = 9999 ;
             Response response = given().
                    baseUri("https://dummyjson.com")
                    .contentType(ContentType.JSON)
                    .when().get("/users/" +id)
                    .then()
                    .log().all()
                     .extract().response();
             Errors getResponse =response.body().as(Errors.class);
             assertThat(response.statusCode(),equalTo(404));
             assertThat(getResponse.getMessage(),equalTo("User with id '9999' not found"));

    }

}
