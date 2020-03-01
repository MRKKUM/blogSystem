package info16.getVer;

import info16.getVer.HttpUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.util.HashMap;
import java.util.Map;
public class GetVer {


        public static String sentMsg(String phoneNum) {
            String num = getNum();
            String host = "https://fesms.market.alicloudapi.com";
            String path = "/sms/";
            String method = "GET";
            String appcode = "d9d5e7b31c9d4b7e8374136b9e6c2ded";
            Map<String, String> headers = new HashMap<String, String>();
            headers.put("Authorization", "APPCODE " + appcode);
            Map<String, String> querys = new HashMap<String, String>();
            querys.put("code", num);// !!! 请求参数
            querys.put("phone", phoneNum);// !!! 请求参数
            querys.put("skin", "2");// !!! 请求参数
            try {
                HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
                System.out.println(EntityUtils.toString(response.getEntity())); //输出json
            } catch (Exception e) {
                e.printStackTrace();
            }
            return num;
        }

        public static String getNum(){
            int random = 0;
            for (int i = 0; ; i++) {
                random = (int)(Math.random()*1000000);
                if (random > 99999 && random <1000000){
                    return String.valueOf(random);
                }
            }

        }

    public static String sentMsg(String phone,String pwd) {

        String host = "https://fesms.market.alicloudapi.com";
        String path = "/sms/";
        String method = "GET";
        String appcode = "d9d5e7b31c9d4b7e8374136b9e6c2ded";
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("code", pwd);// !!! 请求参数
        querys.put("phone", phone);// !!! 请求参数
        querys.put("skin", "15");// !!! 请求参数
        try {
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
            System.out.println(EntityUtils.toString(response.getEntity())); //输出json
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "ok";
    }

}


