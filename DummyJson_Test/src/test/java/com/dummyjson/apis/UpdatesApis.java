package com.dummyjson.apis;

import com.dummyjson.base.Specifications;
import com.dummyjson.models.UserInfo;
import com.dummyjson.utilites.Route;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UpdatesApis {

    public static Response addUser(UserInfo user){
        return given().
                spec(Specifications.getReqSpecifications())
                .body(user)
                .when().post(Route.AddUserPath)
                .then()
                .log().all()
                .extract().response();
    }
    public static Response negativeAddUser(){
        return given().
                spec(Specifications.getReqSpecifications())
                .when().get(Route.AddUserPath)
                .then()
                .log().all()
                .extract().response();
    }

    public static Response updateUser(UserInfo user ,int id){
        return given().
                spec(Specifications.getReqSpecifications())
                .body(user)
                .pathParam("userId", id)
                .when().put(Route.updatedUserPath)
                .then()
                .log().all()
                .extract().response();
    }
    public static Response negativeUpdateUser(int id){
        return given().
                spec(Specifications.getReqSpecifications())
                .pathParam("userId", id)
                .when().put(Route.updatedUserPath)
                .then()
                .log().all()
                .extract().response();
    }
    public static Response deleteUser(int id){
        return given().
                spec(Specifications.getReqSpecifications())
                .pathParam("userId", id)
                .when().delete(Route.deleteUserPath)
                .then()
                .log().all()
                .extract().response();
    }

}
