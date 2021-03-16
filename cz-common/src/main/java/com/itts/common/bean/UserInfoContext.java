package com.itts.common.bean;


    public class UserInfoContext {
        private static ThreadLocal<UserInfo> userInfo = new ThreadLocal<UserInfo>();
        public static String KEY_USERINFO_IN_HTTP_HEADER = "X-AUTO-FP-USERINFO";

        public UserInfoContext() {
        }

        public static UserInfo getUser(){
            return (UserInfo)userInfo.get();
        }

        public static void setUser(UserInfo user){
            userInfo.set(user);
        }
    }

