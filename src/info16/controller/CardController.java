package info16.controller;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import info16.CreateFile.ReadFile;
import info16.MyInterceptor;
import info16.model.CardModel;
import info16.model.DiscussModel;
import info16.model.UserModel;
import info16.service.Card_service;
import info16.service.Disc_service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@Before(MyInterceptor.class)
public class CardController extends Controller{

    // 我的帖子
    public void myCard() throws IOException {
        UserModel user = getSessionAttr("user");
        List<CardModel> list = Card_service.all(user.get("id"));
        if (list == null){
            setAttr("msg","您还没有发表过文章哦！");
        }else {
            setAttr("myCard",list);
        }
        render("/html/myCard.html");
    }

    // 跳转到新帖子编辑界面
    public void newCard(){
        render("/html/newCard.html");
    }

    // 所有帖子的标题
    public void allTitle(){
        List list = Card_service.all();
        setAttr("allCard",list);
        render("/html/allCard.html");
    }

    // 查看具体内容
    public void content() throws IOException {
        CardModel card = Card_service.query(getPara());
        List<DiscussModel> list = Disc_service.query(getPara());
        String content = ReadFile.getContent(card.get("c_content"));
        setAttr("card",card);
        setAttr("dis",list);
        setAttr("content",content);
        render("/html/content.html");
    }

    // 添加到数据库
    public void doAdd(){
        UserModel user = getSessionAttr("user");
        String u_id = user.get("id");
        String title = getPara("title");
        String content = getPara("content");
        Card_service.add(u_id,title,content);
        setAttr("msg","发布成功!");
        render("/html/newCard.html");
    }

    // 删除帖子
    public void delete(){
        String c_id = getPara();
        Card_service.delete(c_id);
        redirect("/msg/myCard");
    }

    // 编辑帖子
    public void edit() throws IOException {
        String c_id = getPara();
        CardModel card = Card_service.query(c_id);
        String content = ReadFile.getContent(card.get("c_content"));
        setAttr("card", card);
        setAttr("content",content);
        render("/html/editCard.html");
    }

    // 进行修改操作
    public void doEdit() throws IOException {
        String c_id = getPara();
        String title = getPara("title");
        String content = getPara("content");
        if(c_id.equals("") || title.equals("") || content.equals("")){
            redirect("/msg/edit");
        }else{
            Card_service.update(c_id,title,content);
            redirect("/msg/myCard");
        }
    }
}
