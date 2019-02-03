package com.mobile.quiz.utility;

public class URLs {
    private static final String ROOT_URL = "http://twowaylive.us-east-2.elasticbeanstalk.com";
    public static final String URL_SENDOTP = ROOT_URL + "/api/SendOTP";
    public static final String URL_CONFORMOTP = ROOT_URL + "/api/ConfirmOTP";
    public static final String URL_REGISTER = ROOT_URL + "/api/RegisterStudent";
    public static final String URL_GETRRQDETAIL = ROOT_URL + "/api/GetRRQDetails/";
    public static final String URL_SENDRRQDETAIL = ROOT_URL + "/api/SaveMobileRRQResponse";
}