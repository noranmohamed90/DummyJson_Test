package com.dummyjson.apis;

import com.dummyjson.base.Specifications;
import com.dummyjson.models.UserInfo;
import com.dummyjson.utilites.Route;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class activitesApis {
    public static Response UserCart(int id){
        return  given().
                spec(Specifications.getReqSpecifications())
                .pathParam("cartId", id)
                .when().get(Route.GetCartsPath)
                .then()
                .log().all()
                .extract().response();
    }


    public static Response UserPosts(int id){
        return   given().
                spec(Specifications.getReqSpecifications())
                .pathParam("userId", id)
                .when().get(Route.GetPostsPath)
                .then()
                .log().all()
                .extract().response();
    }

}
