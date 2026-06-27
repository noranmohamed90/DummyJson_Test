package com.dummyjson.testcases;

import com.dummyjson.apis.activitesApis;
import com.dummyjson.models.*;
import com.dummyjson.utilites.ErrorMessages;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static com.dummyjson.utilites.generateIds.getInvalidRandomUserId;
import static com.dummyjson.utilites.generateIds.getRandomUserId;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Feature("UserActivities Feature")
public class UserActivitiesTest {

    int userId = getRandomUserId();
    int invalidUserId = getInvalidRandomUserId();
     @Test
    @Story("UserCart Story")
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
    @Story("NegativeUserCart Story")
    public void NegativeUserCart(){
        Response response = activitesApis.UserCart(invalidUserId);
        Errors getResponse =response.body().as(Errors.class);
        assertThat(response.statusCode(),equalTo(404));
        assertThat(getResponse.getMessage(),equalTo(String.format(ErrorMessages.invalidUsersMessage, invalidUserId)));


    }

    @Test
    @Story("UserPosts Story")
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
    @Story("Negative UserPosts Story")
    public void NegativeUserPosts(){
        Response response = activitesApis.UserPosts(invalidUserId);
        Errors getResponse =response.body().as(Errors.class);
        assertThat(response.statusCode(),equalTo(404));
        assertThat(getResponse.getMessage(),equalTo(String.format(ErrorMessages.invalidUsersMessage, invalidUserId)));
    }
    @Test
    @Story("UserTodo Story")
    public void userTodos(){
        Response response = activitesApis.UserTodos(userId);
        TodoResponse returnedResponse = response.body().as(TodoResponse.class);
        assertThat(response.statusCode(), equalTo(200));
        assertThat(returnedResponse.getTodos().size(),notNullValue());
        assertThat(returnedResponse.getTodos().get(0).getId(), notNullValue());
    }

    @Test
    @Story("Negative UserTodo Story")
    public void NegativeUserTodos(){
        Response response = activitesApis.UserTodos(invalidUserId);
        Errors getResponse =response.body().as(Errors.class);
        assertThat(response.statusCode(),equalTo(404));
        assertThat(getResponse.getMessage(),equalTo(String.format(ErrorMessages.invalidUsersMessage, invalidUserId)));
    }



}
