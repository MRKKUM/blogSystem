package info16;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;


public class MyInterceptor implements Interceptor {
    @Override
    public void intercept(Invocation invocation) {
        Controller controller = invocation.getController();
        if(controller.getSessionAttr("user") == null){
            controller.setAttr("msg","登陆失效");
            controller.render("/html/login.html");
        }else {
            invocation.invoke();
        }
    }
}
