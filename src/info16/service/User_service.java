package info16.service;

import com.jfinal.plugin.activerecord.Db;
import info16.model.UserModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
// 用户操作类
public class User_service {
    // 得到当前时间
    public static String getDate(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = dateFormat.format(new Date());
        return date;
    }
    // 添加用户
    public static String add_user(String id, String name, String pwd ,String phone){
        UserModel user = new UserModel();
        // 判断用户是否曾经注册过
        UserModel isexits = user.findFirst("select * from t_user where id=?", id);
        // 注册过的话，更改status值
        if(isexits != null) {
            if(isexits.getInt("status") == 1){
                return "用户已注册";
            }else{
                Db.update("update t_user set uname = ? where id = ?", name, id);
                Db.update("update t_user set pwd = ? where id = ?", pwd, id);
                Db.update("update t_user set status = 1 where id = ?",id);
                Db.update("update t_user set phone = ? where id = ?",phone,id);
            }
        }else{
            user.set("id", id);
            user.set("uname", name);
            user.set("pwd", pwd);
            user.set("phone", phone);
            user.set("u_time",getDate());
            user.set("tx","/HeadPic/default.jpg");
            user.save();
        }
        return "注册成功";
    }

    public static  UserModel query(String id, String pwd){
        //验证登陆
        UserModel user = new UserModel();
        List<UserModel> lu = user.find("select * from t_user where id=? and pwd=? and status=1", id, pwd);
        if(lu.size()==0)
            return null;
        else
            return lu.get(0);
    }

    public static int delete(String id){
        // 此处不删除数据，设置状态为注销状态
        int result = Db.update("update t_user set status = 0 where id = ?",id);
        return result;
    }

    public static  UserModel query(String id){
        //查询信息
        UserModel user = new UserModel();
        UserModel userModel= user.findFirst("select * from t_user where id=? and status=1", id);
        return userModel;
    }

    //修改信息
    public static boolean update(String oIdId,String newId, String newName, String newPwd, String newPhone){
        Db.update("update t_user set uname = ? where id = ?", newName, oIdId);
        Db.update("update t_user set pwd = ? where id = ?", newPwd, oIdId);
        Db.update("update t_user set id = ? where id = ?",newId,oIdId);
        Db.update("update t_user set phone = ? where id = ?",newPhone,oIdId);
        return true;
    }
    // 设置头像
    public static String setTx(String id, String path){
        Db.update("update t_user set tx = ? where id = ?", path, id);
        return "修改成功";
    }
}
