package com.dummyjson.testcases;

import com.dummyjson.apis.userApis;
import com.dummyjson.models.Errors;
import com.dummyjson.models.UserInfo;
import com.dummyjson.models.userResponse;
import com.dummyjson.utilites.ErrorMessages;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.awt.*;

import static com.dummyjson.utilites.generateIds.getInvalidRandomUserId;
import static com.dummyjson.utilites.generateIds.getRandomUserId;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


@Feature("User Feature")
public class UserTest {
    int userId = getRandomUserId();
    int invalidUserId = getInvalidRandomUserId();

    @Test
    @Story("Login Story")
    public void LoginUserTC() {
        UserInfo user = new UserInfo("emilys", "emilyspass", 30);
        Response response = userApis.Login(user);
        UserInfo getResponse =response.body().as(UserInfo.class);
        assertThat(response.statusCode(), equalTo(200));
        assertThat(getResponse.getUsername(), equalTo("emilys"));
        assertThat(getResponse.getAccessToken(), not(equalTo(null)));
    }

    @Test
    @Story("LoginFailed Story")
    public void NegativeLoginUserTC(){
        UserInfo user =new UserInfo("emilys","emily",30);
        int userId = user.getId();
        Response response = userApis.Login(user);
        Errors getResponse =response.body().as(Errors.class);
        assertThat(response.statusCode(),equalTo(400));
        assertThat(getResponse.getMessage(),equalTo(ErrorMessages.invalidLoginMessage));
    }
    @Test
    @Story("GetAllUsers Story")
    public void GetAllUsersTC(){
         Response response = userApis.GatAllUsers();
         userResponse getResponse = response.body().as(userResponse.class);
         assertThat(response.statusCode(),equalTo(200));
         assertThat(getResponse.getUsers(),notNullValue());
         assertThat(getResponse.getUsers().size(),greaterThan(0));
         assertThat(getResponse.getUsers().get(0).getId(),greaterThan(0));

    }
    @Test
    @Story("GetAllUsers Failed Story")
    public void negativeGetAllUsersTC(){
        Response response = userApis.failGatAllUsers();
        assertThat(response.statusCode(),equalTo(404));
    }

    @Test
    @Story("GetSingleUser Story")
    public void GetSingleUsersTC(){
        Response response = userApis.GatSingleUser(userId);
        UserInfo getResponse =response.body().as(UserInfo.class);
        assertThat(response.statusCode(),equalTo(200));
        assertThat(getResponse.getId(),equalTo(userId));
        assertThat(getResponse.getFirstName(),notNullValue());
        assertThat(getResponse.getEmail(),containsString("@"));

    }
    @Test
    @Story("GetSingleUserFailed Story")
    public void negativeSingleUsersTC(){
        Response response = userApis.GatSingleUser(invalidUserId);
        Errors getResponse =response.body().as(Errors.class);
        assertThat(response.statusCode(),equalTo(404));
        assertThat(getResponse.getMessage(),equalTo(String.format(ErrorMessages.invalidUsersMessage, invalidUserId)));
    }

    @Test
    @Story("SearchUser Story")
    public void SearchUsersTC(){
        Response response = userApis.searchUsers("Emily");
        UserInfo getResponse =response.body().as(UserInfo.class);
        assertThat(response.statusCode(),equalTo(200));
        assertThat(getResponse.getId(),notNullValue());
        assertThat(response.jsonPath().getString("users[0].firstName"), equalTo("Emily"));
        assertThat(response.jsonPath().getString("users[0].email"),
                containsString("@"));

    }
    @Test
    public void negativeSearchUsersTC(){
        Response response = userApis.searchUsers("Noran");
        assertThat(response.statusCode(),equalTo(200));
        assertThat(response.jsonPath().getList("users"), empty());
        assertThat(response.jsonPath().getInt("total"), equalTo(0));
        assertThat(response.jsonPath().getInt("skip"), equalTo(0));
        assertThat(response.jsonPath().getInt("limit"), equalTo(0));


    }

}
