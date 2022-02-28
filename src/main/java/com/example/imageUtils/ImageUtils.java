package com.example.imageUtils;

import org.springframework.util.ResourceUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class ImageUtils {
    static Color backColor = new Color(255,255,255);
    static Color lineColor = new Color(0, 0, 0);
    static Color foreColor = null;

    public static Color getRandomColor(){
        Random r = new Random();
        return new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256));
    }
    private static void shearX(Graphics g, int w1, int h1, Color color) {
        Random random=new Random();
        int period = 2;

        boolean borderGap = true;
        int frames = 1;
        int phase = random.nextInt(2);

        for (int i = 0; i < h1; i++) {
            double d = (double) (period >> 1)* Math.sin((double) i / (double) period + (2.2831853071795862D * (double) phase)/ (double) frames);
            g.copyArea(0, i, w1, 1, (int) d, 0);
            if (borderGap) {
                g.setColor(color);
                g.drawLine((int) d, i, 0, i);
                g.drawLine((int) d + w1, i, w1, i);
            }
        }

    }

    private static void shearY(Graphics g, int w1, int h1, Color color) {
        Random random=new Random();
        int period = random.nextInt(40) + 10; // 50;

        boolean borderGap = true;
        int frames = 20;
        int phase = random.nextInt(2);
        for (int i = 0; i < w1; i++) {
            double d = (double) (period >> 1)
                    * Math.sin((double) i / (double) period
                    + (2.2831853071795862D * (double) phase)/ (double) frames);
            g.copyArea(i, 0, 1, h1, 0, (int) d);
            if (borderGap) {
                g.setColor(color);
                g.drawLine(i, (int) d, i, 0);
                g.drawLine(i, (int) d + h1, i, h1);
            }

        }

    }
    public static BufferedImage getCaptchaImage(int width, int height, int interLine, String textCode, boolean randomLocation, double heightPercent, int rightWhite, boolean isShear){
        BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        //随机操作对象
        Random r=new Random();

        g.setColor(backColor==null?getRandomColor():backColor);
        g.fillRect(0,0,width,height);

        if(interLine>0){
            int x=r.nextInt(4),y=0;
            int x1=width-r.nextInt(4),y1=0;
            for(int i=0;i<interLine;i++){
                g.setColor(lineColor==null?getRandomColor():lineColor);
                y=r.nextInt(height-r.nextInt(4));
                y1=r.nextInt(height-r.nextInt(4));
                g.drawLine(x,y,x1,y1);
            }
        }

        int fsize=(int)(height*heightPercent);
        int fx=0;
        int fy=fsize;
        g.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,fsize));
        //写字符
        for(int i=0;i<textCode.length();i++){
            fy=randomLocation?(int)((Math.random()*0.3+0.6)*height):fy;//每个字符高低是否随机
            g.setColor(foreColor==null?getRandomColor():foreColor);
            g.drawString(textCode.charAt(i)+"",fx,fy);
            fx+=(width - rightWhite) / (textCode.length() - 1) * (randomLocation ? (Math.random() * 0.2 + 0.9) : 1); //依据宽度浮动
        }

        //扭曲图片
        if (isShear){
            shearX(g, width, height, backColor);
            shearY(g, width, height, backColor);
        }


        float yawpRate = 0.05f;// 噪声率
        int area = (int) (yawpRate * width * height);//噪点数量
        for (int i = 0; i < area; i++) {
            int xxx = r.nextInt(width);
            int yyy = r.nextInt(height);
            int rgb = getRandomColor().getRGB();
            image.setRGB(xxx, yyy, rgb);
        }

        g.dispose();
        return image;
    }

    public static boolean create(int width, int height, int interLine, String textCode, boolean randomLocation, String path, double heightPercent, int rightWhite, boolean isShear){
        BufferedImage imageFromCode = getCaptchaImage(width, height, interLine, textCode, randomLocation, heightPercent, rightWhite, isShear);
        try {

            File file = new File(path + textCode.replace(" ","") + ".jpg");
            if(!file.exists()){
                file.mkdirs();
            }
            ImageIO.write(imageFromCode,"jpg",file);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
