package com.deng.main;

import com.deng.openUrl.Gotourl;
import com.deng.parseScreen.ImageUtils;
import com.deng.parseScreen.ParseImg;
import com.deng.printScreen.ScreenShot;
import com.deng.printScreen.ScreenShotAndCut;
import net.sourceforge.tess4j.Tesseract;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by deng on 2018/1/10.
 */
public class Million {

    public static void main(String[] args) throws IOException {

        ParseImg parseImg = new ParseImg("src/main/resources");

        ScreenShotAndCut screenShotAndCut = new ScreenShotAndCut("W8R0215829003641");

        Scanner sc = new Scanner(System.in);

        while (sc.nextInt() == 1) {

            double start = System.currentTimeMillis();

//            String print = "adb shell /system/bin/screencap -p /sdcard/screenshot.png";
//
//            String toPc = "adb pull /sdcard/screenshot.png d:/screenshot.png";
//
//            ScreenShot.exeCmd(print);
//            double end1 = System.currentTimeMillis();
//            System.out.println("截屏耗时" + (end1 - start) / 1000 + " s");
//            ScreenShot.exeCmd(toPc);
//

            screenShotAndCut.getScreenShot(screenShotAndCut.huawei,"d:/screenshot.png");
            double end2 = System.currentTimeMillis();
            System.out.println("读取耗时" + (end2 - start) / 1000 + " s");

            ImageUtils.cutPNG(new FileInputStream("d:/screenshot.png"),
                    new FileOutputStream("d:/screenshotcut.png"), 60,200,1000,400);

            File imageFile = new File("d:/screenshotcut.png");
            BufferedImage textImage = ImageIO.read(imageFile);
            double end3 = System.currentTimeMillis();
            System.out.println("截取耗时" + (end3 - end2) / 1000 + " s");

            String result = parseImg.parseImgFunc(textImage);
            double end4 = System.currentTimeMillis();
            System.out.println("解析耗时" + (end4 - end3) / 1000 + " s");

            StringBuffer sb = new StringBuffer("https://www.baidu.com/s?ie=utf-8&f=8&rsv_bp=1&rsv_idx=1&tn=baidu&wd=");
            sb.append(result.replaceAll("[^\u4E00-\u9FA5]", ""));
            String wd = sb.toString();
            Gotourl.openDefaultBrowser(wd);

        }


    }

    /**
     * @param srImage 图片路径
     * @param ZH_CN   是否使用中文训练库,true-是
     * @return 识别结果
     */
    public static String FindOCR(String srImage, boolean ZH_CN) {
        try {
            System.out.println("start");
            double start = System.currentTimeMillis();
            File imageFile = new File(srImage);
            if (!imageFile.exists()) {
                return "图片不存在";
            }
            BufferedImage textImage = ImageIO.read(imageFile);
            Tesseract instance = new Tesseract();
            instance.setDatapath("src/main/resources");//设置训练库
            if (ZH_CN)
                instance.setLanguage("chi_sim");//中文识别
            String result = null;
            result = instance.doOCR(textImage);
            double end = System.currentTimeMillis();
            System.out.println("耗时" + (end - start) / 1000 + " s");
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return "发生未知错误";
        }
    }

}
