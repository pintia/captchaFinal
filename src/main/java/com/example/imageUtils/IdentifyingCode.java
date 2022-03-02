package com.example.imageUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;

import java.util.Random;

import javax.imageio.ImageIO;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;


public class IdentifyingCode {
    /**     * 验证码长度     */
    int length = 4;

    /**     * 验证码字体大小     */
    int fontSize = 30;

    /**     * 边框补     */
    int padding = 0;

    /**     * 是否输出燥点（默认输出）     */
    boolean chaos = true;

    /**     * 输出燥点的颜色（默认灰色）     */
    Color chaosColor = Color.lightGray;

    /**     * 自定义背景色（默认白色）     */
    Color backgroundColor = Color.white;

    /**     * 自定义字体数组     */
    String[] fonts = { "Arial", "Georgia", "Times New Roman", "Blue", "Yellow" };

    /**     * 自定义随机码字符串序列（使用逗号分隔）     */
    String codeSerial = "0,1,2,3,4,5,6,7,8,9,a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z,A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z";

    /**     * 产生波形滤镜效果     */
    private final double PI = 3.1415926535897932384626433832799; //此值越大，扭曲程度越大

    public static void main(String[] args) {
        IdentifyingCode io = new IdentifyingCode();
        String picNum = io.CreateVerifyCode(4);
        BufferedImage bi = io.CreateImageCode(picNum);

        try {
            File file = new File("./test.jpg");

            if (!file.exists()) {
                file.mkdirs();
            }

            ImageIO.write(bi, "jpg", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**     * 字体长度的一对方法     * @return     */
    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    /**     * 字体长度的一对方法     * @return     */
    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    /** * 边框的一对方法 * @return */
    public int getPadding() {
        return padding;
    }

    public void setPadding(int padding) {
        this.padding = padding;
    }

    /** * 是否输出躁点的一对方法 * @return */
    public boolean isChaos() {
        return chaos;
    }

    public void setChaos(boolean chaos) {
        this.chaos = chaos;
    }

    /** * 躁点的颜色 * @return */
    public Color getChaosColor() {
        return chaosColor;
    }

    public void setChaosColor(Color chaosColor) {
        this.chaosColor = chaosColor;
    }

    /** * 背景颜色 * @return */
    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    /** * 自定义字体样式的数组 * @return */
    public String[] getFonts() {
        return fonts;
    }

    public void setFonts(String[] fonts) {
        this.fonts = fonts;
    }

    /** * 自定义随机码字符串序列，表示可输出的数字的范围 * @return */
    public String getCodeSerial() {
        return codeSerial;
    }

    public void setCodeSerial(String codeSerial) {
        this.codeSerial = codeSerial;
    }

    /**     * 给定范围获得随机颜色     *      * @param fc     * @param bc     * @return     */
    private Color getRandColor(int fc, int bc) {
        Random random = new Random();

        if (fc > 255) {
            fc = 255;
        }

        if (bc > 255) {
            bc = 255;
        }

        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);

        return new Color(r, g, b);
    }

    /**     * 生成校验码图片     *      * @param code     * @return     */
    private BufferedImage CreateImageCode(String code) {
        int fWidth = this.fontSize + this.padding;
        int imageWidth = (int) (code.length() * fWidth) + 4 +
                (this.padding * 2);
        int imageHeight = this.fontSize * 2;
        BufferedImage bi = new BufferedImage(imageWidth, imageHeight,
                BufferedImage.TYPE_INT_RGB);
        Graphics graphics = bi.getGraphics();
        graphics.setColor(this.backgroundColor);
        graphics.fillRect(0, 0, bi.getWidth(), bi.getHeight());

        Random random = new Random();

        if (this.chaos) {
            int c = this.length * 10;

            for (int i = 0; i < c; i++) {
                int x = random.nextInt(bi.getWidth());
                int y = random.nextInt(bi.getHeight());
                graphics.setColor(this.chaosColor);
                graphics.drawRect(x, y, 1, 1);
                graphics.drawLine(x, y, y, x);
            }
        }

        for (int i = 0; i < code.length(); i++) {
            int findex = random.nextInt(this.fonts.length);
            Font font = new Font(fonts[findex], Font.BOLD, this.fontSize);
            graphics.setFont(font);

            int top = (int) ((imageHeight + (code.length() * 2)) / 1.5);

            if ((i % 2) != 1) {
                top = top - code.length();
            }

            int left = (i * fWidth) + code.length();
            graphics.setColor(this.getRandColor(1 + i, 250 - i));

            try {
                graphics.drawString(code.substring(i, i + 1), left, top);
            } catch (StringIndexOutOfBoundsException e) {
                System.out.print(e.toString());
            }
        }

        graphics.setColor(Color.gray);
        graphics.drawRect(0, 0, bi.getWidth() - 1, bi.getHeight() - 1);
        graphics.dispose(); // bi = TwistImage(bi, true, 8, 4);

        return bi;
    }

    /**     * 将创建好的图片输出到页面     *      * @param bi     * @param response     */
    public void CreateImageOnPage(String code, HttpServletResponse response) {
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        BufferedImage bi = this.CreateImageCode(code);
        ServletOutputStream os = null;

        try {
            os = response.getOutputStream();
            ImageIO.write(bi, "JPEG", os);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**     * 生成随机字符码     *      * @param codeLen     *            生成字符码的个数,0则默认的个数     * @return     */
    public String CreateVerifyCode(int codeLen) {
        if (codeLen == 0) {
            codeLen = this.length;
        }

        String[] arr = this.codeSerial.split(",");
        String code = "";
        int randValue = -1;
        Random random = new Random();

        for (int i = 0; i < codeLen; i++) {
            randValue = random.nextInt(arr.length - 1);
            code += arr[randValue];
        }

        return code;
    }

    /**     * 生成随机码，默认的方法     * @return     */
    public String CreateVerifyCode() {
        return this.CreateVerifyCode(this.length);
    }
}
