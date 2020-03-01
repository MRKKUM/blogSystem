package info16.service;

import info16.model.DiscussModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Disc_service {
    // 得到当前时间
    public static String getDate(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = dateFormat.format(new Date());
        return date;
    }
    // 添加评论
    public static String add(String u_id, String c_id, String content){
        String time = getDate();
        DiscussModel disc = new DiscussModel();
        disc.set("u_id",u_id);
        disc.set("c_id",c_id);
        disc.set("d_content",content);
        disc.set("d_time",time);
        disc.save();
        return "ok";
    }
    // 取出对应评论
    public static List<DiscussModel> query(String c_id){
        DiscussModel dis = new DiscussModel();
        List<DiscussModel> list = dis.find("select *from  n_dis where c_id = ?",c_id);
        return list;
    }

}
