package info16.controller;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.ext.interceptor.SessionInViewInterceptor;
import info16.MyInterceptor;
import info16.model.CardModel;
import info16.model.DiscussModel;
import info16.model.UserModel;
import info16.service.Card_service;
import info16.service.Disc_service;

import java.util.List;

@Before(SessionInViewInterceptor.class)
public class DisController extends Controller{

    // 添加评论
    @Before(MyInterceptor.class)
    public void addDis(){
        UserModel user = getSessionAttr("user");
        String u_id = user.get("id");
        String  c_id = getPara();
        String content = getPara("content");
        Disc_service.add(u_id, c_id,content);
        redirect("/msg/content/"+c_id);
    }
}
