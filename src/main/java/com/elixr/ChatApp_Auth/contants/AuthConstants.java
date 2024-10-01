package com.elixr.ChatApp_Auth.contants;

public class AuthConstants {
    public static final String ALLOWED_HEADERS = "*";
    public static final String AUTHORIZATION_HEADER_TYPE = "Authorization";
    public static final String BEARER = "Bearer";
    public static final String KEY_GENERATOR_ALGORITHM = "HmacSHA256";
    public static final String REGISTERED_CORS_PATTERN = "/**";
    public static final String USER_COLLECTION_NAME = "users";
    public static final String DEFAULT_SUCCESS_URL = "/index.html";
    public static final String LOGIN_API_ENDPOINT = "/login";
    public static final String LOGIN_PAGE_URL = "/login.html";
    public static final String LOGOUT_SUCCESS_URL = "/login?logout";
    public static final String LOGOUT_URL = "/logout";
    public static final String VERIFY_TOKEN_ENDPOINT = "/verifyToken";
    private static final String MESSAGE_BASEURL = "message.baseurl";
    public static final String MESSAGE_URL_VALUE = "${" + MESSAGE_BASEURL + "}";
    private static final String UI_BASEURL = "ui.baseurl";
    public static final String UI_URL_VALUE = "${"+UI_BASEURL+"}";
    private static final String USER_BASEURL = "user.baseurl";
    public static final String USER_SERVICE_URL_VALUE = "${"+USER_BASEURL+"}";
}
