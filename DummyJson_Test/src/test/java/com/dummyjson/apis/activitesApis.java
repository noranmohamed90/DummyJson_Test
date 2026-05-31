package com.dummyjson.apis;

import com.dummyjson.models.UserInfo;
import com.dummyjson.utilites.Route;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class activitesApis {
    public static Response UserCart(int id){
        return  given().
                baseUri("https://dummyjson.com")
                .pathParam("cartId", id)
                .contentType(ContentType.JSON)
                .when().get(Route.GetCartsPath)
                .then()
                .log().all()
                .extract().response();
    }


    public static Response UserPosts(int id){
        return   given().
                baseUri("https://dummyjson.com")
                .pathParam("userId", id)
                .contentType(ContentType.JSON)
                .when().get(Route.GetPostsPath)
                .then()
                .log().all()
                .extract().response();
    }

}
