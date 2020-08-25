package com.example.springdemo.util;

public class URL {
    public static final String API = "/api";

    //account
    public static final String ACCOUNT = "/Account";
    public static final String GET_ACCOUNT = ACCOUNT + "/getAllAccount";
    public static final String LOGIN_ACCOUNT = "/login";
    public static final String CREATE_ACCOUNT = ACCOUNT +  "/createAccount";
    public static final String UPDATE_ACCOUNT = ACCOUNT + "/updateAccount";
    public static final String DISABLE_ACCOUNT = ACCOUNT + "/disableAccount";
    public static final String ENABLE_ACCOUNT = ACCOUNT + "/enableAccount";
    public static final String ACCOUNT_INFORMATION = ACCOUNT + "/getInformationAccount";

    //role
    public static final String ROLE = "Role";
    public static final String GET_ALL_ROLE = ROLE + "/getAllRole";
    public static final String CREATE_ROLE = ROLE + "/createRole";

    //News
    public static final String NEWS = "News";
    public static final String GET_NEWS = NEWS + "/getAllNews";
    public static final String CREATE_NEWS = NEWS + "/createNews";
    public static final String UPDATE_NEWS = NEWS + "/updateNews";
    public static final String DISABLE_NEWS = NEWS + "/disableNews";
    public static final String ENABLE_NEWS = NEWS + "/enableNews";

    //Category
    public static final String CATEGORY = "Category";
    public static final String GET_CATEGORY = CATEGORY + "/getAllCategory";
    public static final String CREATE_CATEGORY = CATEGORY + "/createCategory";

}
