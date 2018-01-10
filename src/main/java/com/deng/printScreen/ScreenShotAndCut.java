package com.deng.printScreen;

import com.android.ddmlib.AdbCommandRejectedException;
import com.android.ddmlib.AndroidDebugBridge;
import com.android.ddmlib.IDevice;
import com.android.ddmlib.RawImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by deng on 2018/1/10.
 */
public class ScreenShotAndCut {

    public IDevice huawei;

    public ScreenShotAndCut(String id){

         huawei = getDevice(id);

    }

    public IDevice getDevice(String id) {
        AndroidDebugBridge bridge = AndroidDebugBridge
                .createBridge("C:\\adb\\adb.exe", true);
        waitDevicesList(bridge);
        IDevice devices[] = bridge.getDevices();

        for (IDevice onlinedeivce : devices) {
            if (onlinedeivce.getSerialNumber().equals(id))
                return onlinedeivce;
        }

        return null;
    }

    private void waitDevicesList(AndroidDebugBridge bridge) {
        int count = 0;
        while (bridge.hasInitialDeviceList() == false) {
            try {
                Thread.sleep(100); // 如果没有获得设备列表，则等待
                count++;
            } catch (InterruptedException e) {
            }
            if (count > 300) {    // 设定时间超过300×100 ms的时候为连接超时
                System.err.print("Time out");
                break;
            }
        }
    }

    public boolean getScreenShot(IDevice device, String filepath) {
        BufferedImage image = null;
        RawImage rawScreen = null;
        try {
            rawScreen = device.getScreenshot();
        } catch (AdbCommandRejectedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (com.android.ddmlib.TimeoutException e) {
            e.printStackTrace();
        }
        if (rawScreen != null) {
            Boolean landscape = false;
            int width2 = landscape ? rawScreen.height : rawScreen.width;
            int height2 = landscape ? rawScreen.width : rawScreen.height;
            if (image == null) {
                image = new BufferedImage(width2, height2,
                        BufferedImage.TYPE_INT_RGB);
            } else {
                if (image.getHeight() != height2 || image.getWidth() != width2) {
                    image = new BufferedImage(width2, height2,
                            BufferedImage.TYPE_INT_RGB);
                }
            }
            int index = 0;
            int indexInc = rawScreen.bpp >> 3;
            for (int y = 0; y < rawScreen.height; y++) {
                for (int x = 0; x < rawScreen.width; x++, index += indexInc) {
                    int value = rawScreen.getARGB(index);
                    if (landscape)
                        image.setRGB(y, rawScreen.width - x - 1, value);
                    else
                        image.setRGB(x, y, value);
                }
            }
            try {

                ImageIO.write((RenderedImage) image, "PNG", new File(filepath));
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return false;
    }
}
