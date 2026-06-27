package com.dummyjson.testcases;

import com.dummyjson.apis.UpdatesApis;
import com.dummyjson.models.Errors;
import com.dummyjson.models.UserInfo;
import com.dummyjson.utilites.ErrorMessages;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static com.dummyjson.utilites.generateIds.getInvalidRandomUserId;
import static com.dummyjson.utilites.generateIds.getRandomUserId;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

public class UserUpdatesTest {

    int userId = getRandomUserId();
    int invalidUserId = getInvalidRandomUserId();
    @Test
    @Story("Add User Story")
    public void AddUserTC() {
        UserInfo user = new UserInfo("Muhammad", "Ovi");
        Response response = UpdatesApis.addUser(user);
        UserInfo getResponse =response.body().as(UserInfo.class);
        assertThat(response.statusCode(), equalTo(201));
        assertThat(getResponse.getFirstName(), equalTo(user.getFirstName()));
        assertThat(getResponse.getLastName(),equalTo(user.getLastName()));
    }

    @Test
    @Story("AddUserFailed Story")
    public void NegativeAddUserTC(){
        Response response = UpdatesApis.negativeAddUser();
        Errors getResponse =response.body().as(Errors.class);
        assertThat(response.statusCode(),equalTo(400));
        assertThat(getResponse.getMessage(),equalTo(ErrorMessages.invalidAddUserMessage));
    }

    @Test
    @Story("UpdateUser Story")
    public void updateUserTC() {
        UserInfo user = new UserInfo("Omar", "Ali");
        user.setId(userId);
        Response response = UpdatesApis.updateUser(user,userId);
        UserInfo getResponse =response.body().as(UserInfo.class);
        assertThat(response.statusCode(), equalTo(200));
        assertThat(getResponse.getFirstName(), equalTo("Omar"));
        assertThat(getResponse.getLastName(),equalTo("Ali"));
    }

    @Test
    @Story("UpdateUser Story")
    public void negativeUpdateUserTC() {
        Response response = UpdatesApis.negativeUpdateUser(invalidUserId);
        Errors getResponse =response.body().as(Errors.class);
        assertThat(response.statusCode(), equalTo(404));
        assertThat(getResponse.getMessage(),equalTo(String.format(ErrorMessages.invalidUsersMessage, invalidUserId)));
    }
    @Test
    @Story("DeleteUser Story")
    public void deleteUserTC() {
        Response response = UpdatesApis.deleteUser(userId);
        assertThat(response.statusCode(), equalTo(200));
    }

    @Test
    @Story("DeleteUser Story")
    public void negativeDeleteUserTC() {
        Response response = UpdatesApis.deleteUser(invalidUserId);
        Errors getResponse =response.body().as(Errors.class);
        assertThat(response.statusCode(), equalTo(404));
        assertThat(getResponse.getMessage(),equalTo(String.format(ErrorMessages.invalidUsersMessage, invalidUserId)));
    }
}
