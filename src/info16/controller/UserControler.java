package info16.controller;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import com.jfinal.ext.interceptor.SessionInViewInterceptor;
import com.jfinal.upload.UploadFile;
import info16.MyInterceptor;
import info16.getVer.GetVer;
import info16.model.UserModel;
import info16.service.User_service;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Before(MyInterceptor.class)
public class UserControler  extends Controller{

        // 注册界面
        @Clear
        public void reg(){
            render("/html/reg.html");
        }

        // 登陆界面
        @Clear
        public void login(){
            render("/html/login.html");
        }

        //找回密码
        @Clear
        public void back(){
            render("/html/back.html");
        }

        // 所有信息界面
        @Clear
        public void all(){
            UserModel userModel = new UserModel();
            List<UserModel> list = userModel.find("select * from t_user where status = 1");
            setAttr("list", list);
            render("/html/allUser.html");
        }

        // 注册
        @Clear
        public void do_reg() {
            String id = getPara("id");
            String name = getPara("name");
            String password = getPara("pwd");
            String phone = getPara("phNum");
            String inVer = getPara("verify");
            String ver = getSessionAttr("ver");
            if (!inVer.equals(ver)){
                setAttr("msg","验证码错误");
                removeSessionAttr("ver");
                render("/html/reg.html");
            }else {
                if (id.equals("") || name.equals("") || password.equals("") || phone.equals("")) {
                    setAttr("msg", "注册失败");
                    removeSessionAttr("ver");
                    render("/html/reg.html");
                } else {
                    String result = User_service.add_user(id, name, password,phone);
                    removeSessionAttr("ver");
                    setAttr("msg",result);
                    render("/html/login.html");
                }
            }
        }

        // 登陆
        @Clear
        @Before(SessionInViewInterceptor.class)
        public void do_login(){
                String id = getPara("id");
                String pwd = getPara("pwd");
                UserModel result = User_service.query(id, pwd);
                if (result == null) {
                    setAttr("msg", "登录失败");
                    render("/html/login.html");
                }else {
                    setSessionAttr("user",result);
                    HttpSession session = getSession();
                    session.setMaxInactiveInterval(30*60);
                    redirect("/msg/allTitle");
                }
        }

        // 注销用户
        @Clear
        public void delete(){
            User_service.delete(getPara());
            redirect("/all");
        }

        // 获取验证码
        @Clear
        public void getVer(){
            String id = getPara("id");
            String name = getPara("name");
            String password = getPara("pwd");
            String phone = getPara("phNum");
            if(phone.length() == 11){
                String num = GetVer.sentMsg(phone);
                setSessionAttr("ver",num);
                setAttr("id", id);
                setAttr("uname", name);
                setAttr("pwd", password);
                setAttr("phone",phone);
                setAttr("msg","获取验证码成功");
                render("/html/reg.html");
            }else{
                setAttr("msg", "获取验证码失败");
                render("/html/reg.html");
            }
        }

        // 找回密码
        @Clear
        public void do_back() {
            String id = getPara("id");
            String phone = getPara("phNum");
            UserModel user = User_service.query(id);
            if (user == null) {
                setAttr("msg", "无此用户");
                render("/html/back.html");
            } else {
                if (phone.equals(user.get("phone"))) {
                    GetVer.sentMsg(user.get("phone"), user.get("pwd"));
                    setAttr("msg", "密码已发送至手机!");
                    render("/html/login.html");
                } else {
                    setAttr("id", id);
                    setAttr("msg", "请输入正确的手机号");
                    render("/html/back.html");
                }
            }
        }

        // 查看用户信息界面
        public void alterU_msg(){
            UserModel user = getSessionAttr("user");
            UserModel userModel = User_service.query(user.get("id"));
            setAttr("user", userModel);
            render("/html/changeMsg/alterU_msg.html");
        }

        // 跳转修改信息
        public void do_alterU_msg() {
            UserModel user = getSessionAttr("user");
            UserModel result = User_service.query(user.get("id"));
            setAttr("user", result);
            render("/html/changeMsg/editMsg.html");
        }

        // 修改信息
        public void do_edit(){
                String id = getPara("id");
                String name = getPara("uname");
                String pwd = getPara("pwd");
                String phone = getPara("phone");
                if (id.equals("") || name.equals("") || pwd.equals("") || phone.equals("") || phone.length()!=11) {
                    redirect("/user/alterU_msg");
                }else{
                    UserModel user = getSessionAttr("user");
                    boolean result = User_service.update(user.get("id"), id, name, pwd,phone);
                    UserModel userModel = User_service.query(id);
                    setSessionAttr("user",userModel);
                    redirect("/user/alterU_msg");
                }
        }

        // 退出登陆
        public  void  quit(){
            HttpSession session = getSession();
            session.removeAttribute("user");
            setAttr("msg", "成功退出登录");
            render("/html/login.html");
        }

        // 修改头像
        public void changeTx(){
            UserModel user = getSessionAttr("user");
            setAttr("imagePath",user.get("tx"));
            render("/html/setTx.html");
        }

        // 命名方式  名字后面加时间戳 避免重复的名称
        public String getTimestamp(){
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            String date = dateFormat.format(new Date());
            return date;
        }

        // do change tx
        public void do_tx(){
            UploadFile pic = getFile();//在磁盘上保存文件
            // 得到当前user的id
            UserModel userModel = getSessionAttr("user");
            String id = userModel.get("id");
            // 组合文件名
            String name = id+getTimestamp()+".jpg";
            // 得到下载的路径
            String uploadPath = pic.getUploadPath();
            String path = uploadPath +"/"+name;
            System.out.println(path);
            // 得到文件对象
            File file = pic.getFile();
            File newFile = new File(path);
            System.out.println(newFile.exists());
            // 文件重命名 注意newFile是不能存在的
            boolean b = file.renameTo(newFile);
            // 写入数据库
            User_service.setTx(id,"/HeadPic/"+name);
            UserModel user = User_service.query(id);
            setSessionAttr("user",user);
            //设置返回的json
            JSONObject json = new JSONObject();
            json.put("status", "success");//设置状态
            renderJson(json);
        }

}
