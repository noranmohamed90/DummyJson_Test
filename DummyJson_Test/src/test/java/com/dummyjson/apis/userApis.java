package com.dummyjson.apis;

import com.dummyjson.base.Specifications;
import com.dummyjson.models.UserInfo;
import com.dummyjson.utilites.Route;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class userApis {

    public static Response Login(UserInfo user){
        return given().
                spec(Specifications.getReqSpecifications())
                .body(user)
                .when().post(Route.LoginPath)
                .then()
                .log().all()
                .extract().response();
    }


    public static Response GatAllUsers(){
        return given().
                spec(Specifications.getReqSpecifications())
                .when().get(Route.GetAllUsersPath)
                .then()
                .log().all()
                .extract().response();
    }

    public static Response GatSingleUser( int id){
        return given().
                spec(Specifications.getReqSpecifications())
                .when().get(Route.GetSingleUserPath +id)
                .then()
                .log().all()
                .extract().response();
    }
}
