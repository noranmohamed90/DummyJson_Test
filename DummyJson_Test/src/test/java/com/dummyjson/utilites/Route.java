package com.dummyjson.utilites;

public class Route {
    public static final String LoginPath ="/user/login";
    public static final String GetAllUsersPath ="/users";
    public static final String GetSingleUserPath ="/users/";
    public static final String GetPostsPath ="/users/{userId}/posts";
    public static final String GetCartsPath ="/users/{cartId}/carts";
    public static final String GetTodoPath ="/users/{todoId}/todos";
    public static final String AddUserPath ="/users/add";
    public static final String updatedUserPath ="/users/{userId}";
    public static final String deleteUserPath ="/users/{userId}";
    public static final String searchUserPath ="/users/search";
}
