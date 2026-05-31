package com.dummyjson.testcases;

import com.dummyjson.apis.userApis;
import com.dummyjson.models.Errors;
import com.dummyjson.models.UserInfo;
import com.dummyjson.models.userResponse;
import com.dummyjson.utilites.ErrorMessages;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.awt.*;
import java.util.List;

import static com.dummyjson.utilites.generateIds.getInvalidRandomUserId;
import static com.dummyjson.utilites.generateIds.getRandomUserId;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class UserTest {
    int userId = getRandomUserId();
    int invalidUserId = getInvalidRandomUserId();

    @Test
    public void LoginUserTC() {
        UserInfo user = new UserInfo("emilys", "emilyspass", 30);
        Response response = userApis.Login(user);
        UserInfo getResponse =response.body().as(UserInfo.class);
        assertThat(response.statusCode(), equalTo(200));
        assertThat(getResponse.getUsername(), equalTo(user.getUsername()));
        assertThat(getResponse.getAccessToken(), not(equalTo(null)));
    }

    @Test
    public void NegativeLoginUserTC(){
        UserInfo user =new UserInfo("emilys","emilysss",30);
        int userId = user.getId();
        Response response = userApis.Login(user);
        Errors getResponse =response.body().as(Errors.class);
        assertThat(response.statusCode(),equalTo(400));
        assertThat(getResponse.getMessage(),equalTo(ErrorMessages.invalidLoginMessage));
    }
    @Test
    public void GetAllUsersTC(){
         Response response = userApis.GatAllUsers();
         userResponse getResponse = response.body().as(userResponse.class);
         assertThat(response.statusCode(),equalTo(200));
         assertThat(getResponse.getUsers(),notNullValue());
         assertThat(getResponse.getUsers().size(),greaterThan(0));
         assertThat(getResponse.getUsers().get(0).getId(),greaterThan(0));

    }
    @Test
    public void negativeGetAllUsersTC(){
        Response response = userApis.GatAllUsers();
        assertThat(response.statusCode(),equalTo(404));
    }

    @Test
    public void GetSingleUsersTC(){
        Response response = userApis.GatSingleUser(userId);
        UserInfo getResponse =response.body().as(UserInfo.class);
        assertThat(getResponse.getId(),equalTo(getResponse.getId()));
        assertThat(getResponse.getFirstName(),notNullValue());
        assertThat(getResponse.getEmail(),containsString("@"));

    }
    @Test
    public void negativeSingleUsersTC(){
        Response response = userApis.GatSingleUser(invalidUserId);
        Errors getResponse =response.body().as(Errors.class);
        assertThat(response.statusCode(),equalTo(404));
        assertThat(getResponse.getMessage(),equalTo(String.format(ErrorMessages.invalidUsersMessage, invalidUserId)));

    }

}
