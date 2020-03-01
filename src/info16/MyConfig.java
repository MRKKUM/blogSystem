package info16;

import com.jfinal.config.*;
import com.jfinal.ext.handler.UrlSkipHandler;
import com.jfinal.ext.interceptor.SessionInViewInterceptor;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.dialect.SqlServerDialect;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.template.Engine;
import info16.controller.*;
import info16.model.CardModel;
import info16.model.DiscussModel;
import info16.model.UserModel;

public class MyConfig extends JFinalConfig{

    @Override
    public void configConstant(Constants constants) {
        PropKit.use("dataConfig.txt");
        constants.setDevMode(true);
        constants.setBaseUploadPath("/OurHomework/web/HeadPic");
    }

    @Override
    public void configRoute(Routes routes) {
        routes.add("/", IndexController.class);
        routes.add("/user", UserControler.class);
        routes.add("/msg", CardController.class);
        routes.add("/dis", DisController.class);
        routes.add("/chat",ChatController.class);
    }

    @Override
    public void configEngine(Engine engine) {
        engine.setDevMode(true);
        engine.addSharedFunction("/html/base/layout.html");
        engine.addSharedFunction("/html/base/UserBas.html");
        engine.addSharedFunction("/html/base/footer.html");
        engine.addSharedFunction("/html/base/lHeader.html");
        engine.addSharedFunction("/html/base/uHeader.html");
    }

    public static DruidPlugin createDruidPlugin() {
        return new DruidPlugin(PropKit.get("jdbcUrl"), PropKit.get("user"), PropKit.get("password").trim(),PropKit.get("driverClass"));
    }

    @Override
    public void configPlugin(Plugins plugins) {

        DruidPlugin druidPlugin = createDruidPlugin();
        plugins.add(druidPlugin);

        ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
        arp.addMapping("t_user", UserModel.class);
        arp.addMapping("card", CardModel.class);
        arp.addMapping("discuss", DiscussModel.class);
        arp.setDialect(new SqlServerDialect());
        plugins.add(arp);

    }

    @Override
    public void configInterceptor(Interceptors interceptors) {
        interceptors.add(new SessionInViewInterceptor());
    }

    @Override
    public void configHandler(Handlers handlers) {
        handlers.add(new UrlSkipHandler("/webSocket/(.*?)",false));
    }
}
