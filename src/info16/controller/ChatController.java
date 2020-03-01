package info16.controller;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import info16.MyInterceptor;

@Before(MyInterceptor.class)
public class ChatController extends Controller{
    public void room(){
        render("/html/chatRoom.html");
    }
}
