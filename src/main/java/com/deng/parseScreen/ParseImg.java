package com.deng.parseScreen;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.awt.image.BufferedImage;

/**
 * Created by deng on 2018/1/10.
 */
public class ParseImg {

    Tesseract instance;

    public ParseImg(String dataPath){
        instance=new Tesseract();
        instance.setDatapath(dataPath);//设置训练库
        instance.setLanguage("chi_sim");
    }

    public String parseImgFunc(BufferedImage image){

        if(instance!=null){
            try {
                return instance.doOCR(image);
            } catch (TesseractException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

}
