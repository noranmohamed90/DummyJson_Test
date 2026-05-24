package com.dummyjson.testcases;

import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import java.awt.*;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class UserTest {



    @Test
    public void LoginUserTC(){

        String body ="{\n" +
                "    \n" +
                "    \"username\": \"emilys\",\n" +
                "    \"password\": \"emilyspass\",\n" +
                "    \"expiresInMins\": 30\n" +
                "}";
         given().
                baseUri("https://dummyjson.com")
                .contentType(ContentType.JSON)
                 .body(body)
         .when().post("/user/login")
         .then()
                 .log().all()
                 .assertThat().statusCode(200)
                 .assertThat().body("username",equalTo("emilys"))
                 .assertThat().body("accessToken", not(equalTo(null)));
    }

    @Test
    public void NegativeLoginUserTC(){

        String body ="{\n" +
                "    \n" +
                "    \"username\": \"emilys\",\n" +
                "    \"password\": \"  \",\n" +
                "    \"expiresInMins\": 30\n" +
                "}";
        given().
                baseUri("https://dummyjson.com")
                .contentType(ContentType.JSON)
                .body(body)
                .when().post("/user/login")
                .then()
                .log().all()
                .assertThat().statusCode(400)
                .assertThat().body("message",equalTo("Invalid credentials"));
    }
    @Test
    public void GetAllUsersTC(){

        given().
                baseUri("https://dummyjson.com")
                .contentType(ContentType.JSON)
                .when().get("/users")
                .then()
                .log().all()
                 .statusCode(200)
                .body("users",  notNullValue())
                .body("users.size()" , greaterThan(0))
                .body("users[0].id", greaterThan(0));
    }
    @Test
    public void negativeGetAllUsersTC(){

        given().
                baseUri("https://dummyjson.com")
                .contentType(ContentType.JSON)
                .when().post("/users")
                .then()
                .log().all()
                .statusCode(404);
    }

    @Test
    public void GetSingleUsersTC(){
        int id = 5 ;
        given().
                baseUri("https://dummyjson.com")
                .contentType(ContentType.JSON)
                .when().get("/users/" +id)
                .then()
                .log().all()
                .statusCode(200)
                .body("id", equalTo(id))
                .body("firstName", notNullValue())
                .body("email", containsString("@"));
    }
    @Test
    public void negativeSingleUsersTC(){

            int id = 9999 ;
            given().
                    baseUri("https://dummyjson.com")
                    .contentType(ContentType.JSON)
                    .when().get("/users/" +id)
                    .then()
                    .log().all()
                    .statusCode(404)
                    .body("message", containsString("User with id '9999' not found"));
    }

}
