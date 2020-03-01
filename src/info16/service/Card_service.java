package info16.service;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import info16.CreateFile.CreateFile;
import info16.model.CardModel;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Card_service {
    // 得到当前时间
    public static String getDate(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = dateFormat.format(new Date());
        return date;
    }
    // 添加帖子
    public static String add (String id, String title, String content){
        try {
            String path = CreateFile.newFile(content);
            String time = getDate();
            CardModel card = new CardModel();
            card.set("u_id",id);
            card.set("title",title);
            card.set("c_content",path);
            System.out.println(path.length());
            card.set("f_time",time);
            card.save();
        }catch (Exception e){
            System.out.println(e);
        }

        return "ok";
    }
    // 删除帖子
    public static String delete(String c_id){
        Db.update("update card set status = 0 where c_id = ?",  c_id);
        return "ok";
    }
    // 所有自己发布的帖子
    public static List all(String u_id){
        CardModel card = new CardModel();
        List list = card.find("select *from card where u_id = ? and status = 1",u_id);
        return list;
    }
    // 所有的帖子
    public static List all(){
        CardModel card = new CardModel();
        List list = card.find("select *from  n_content");
        return list;
    }
    // 查询具体的帖子
    public static CardModel query(String c_id){
        CardModel cardModel = new CardModel();
        List<CardModel> list = cardModel.find("select *from  n_content where c_id = ?",c_id);
        return list.get(0);
    }
    // 编辑帖子
    public static String update(String c_id, String title, String content) throws IOException {
        String path = CreateFile.newFile(content);
        Db.update("update card set title = ? where c_id = ?",title,c_id);
        Db.update("update card set c_content = ? where c_id = ?",path,c_id);
        Db.update("update card set f_time = ? where c_id = ?",getDate(),c_id);

        return "ok";
    }
}
