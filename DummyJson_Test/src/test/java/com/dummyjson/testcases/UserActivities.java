package com.dummyjson.testcases;

import com.dummyjson.apis.activitesApis;
import com.dummyjson.models.*;
import com.dummyjson.utilites.ErrorMessages;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static com.dummyjson.utilites.generateIds.getInvalidRandomUserId;
import static com.dummyjson.utilites.generateIds.getRandomUserId;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class UserActivities {

    int userId = getRandomUserId();
    int invalidUserId = getInvalidRandomUserId();
    @Test
    public void userCart(){
        Response response = activitesApis.UserCart(userId);
        CartResponse returnedResponse = response.body().as(CartResponse.class);
         assertThat(response.statusCode(), equalTo(200));
         assertThat(returnedResponse.getCarts(), notNullValue());
        assertThat(returnedResponse.getCarts().get(0).getId(), equalTo(userId));
        assertThat(returnedResponse.getTotal(),  equalTo(1));
        assertThat(returnedResponse.getSkip(), equalTo(0));
        assertThat(returnedResponse.getLimit(), equalTo(1));
    }

    @Test
    public void NegativeUserCart(){
        Response response = activitesApis.UserCart(invalidUserId);
        Errors getResponse =response.body().as(Errors.class);
        assertThat(response.statusCode(),equalTo(404));
        assertThat(getResponse.getMessage(),equalTo(String.format(ErrorMessages.invalidUsersMessage, invalidUserId)));


    }

    @Test
    public void userPosts(){
        Response response = activitesApis.UserPosts(userId);
        PostsResponse returnedResponse = response.body().as(PostsResponse.class);
        assertThat(response.statusCode(), equalTo(200));
        assertThat(returnedResponse.getPosts(), notNullValue());
        assertThat(returnedResponse.getPosts().size(), notNullValue());
        assertThat(returnedResponse.getPosts().get(0).getId(), notNullValue());
        assertThat(returnedResponse.getPosts().get(0).getTitle(), notNullValue());


    }

    @Test
    public void NegativeUserPosts(){
        Response response = activitesApis.UserPosts(invalidUserId);
        Errors getResponse =response.body().as(Errors.class);
        assertThat(response.statusCode(),equalTo(404));
        assertThat(getResponse.getMessage(),equalTo(String.format(ErrorMessages.invalidUsersMessage, invalidUserId)));
    }



}
