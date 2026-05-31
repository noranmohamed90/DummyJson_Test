package com.dummyjson.models;

import java.util.List;

public class userResponse {

    private List<UserInfo> users;
    private int total;
    private int skip;
    private int limit;

    public List<UserInfo> getUsers() {
        return users;
    }

    public int getTotal() {
        return total;
    }

    public int getSkip() {
        return skip;
    }

    public int getLimit() {
        return limit;
    }
}