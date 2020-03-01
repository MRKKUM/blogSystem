package info16.controller;

import com.jfinal.core.Controller;

public class IndexController extends Controller {
    // 首页
    public void index(){
        render("/html/login.html");
    }

}
