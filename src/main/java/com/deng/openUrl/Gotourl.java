package com.deng.openUrl;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by deng on 2018/1/10.
 */
public class Gotourl {


    /**
     * 打开IE浏览器访问页面
     */
    public static void openIEBrowser(){
        //启用cmd运行IE的方式来打开网址。
        String str = "cmd /c start iexplore http://blog.csdn.net/l1028386804";
        try {
            Runtime.getRuntime().exec(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 打开默认浏览器访问页面
     */
    public static void openDefaultBrowser(String url){
        //启用系统默认浏览器来打开网址。
        try {
            URI uri = new URI(url);
            Desktop.getDesktop().browse(uri);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
