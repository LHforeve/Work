package com.example.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

public class ValidCode {

    private int width = 100;//验证码图片的宽度
    private int height = 40;//验证码图片的高度
    private String[] fontNames = { "宋体", "楷体", "隶书", "微软雅黑" };//可选择的字体
    private Color bgColor = new Color(255, 255, 255);//设置验证码图片的背景颜色为白色
    private Random random = new Random();
    private String codes = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private String validcode;// 保存随机数字(即验证码)


    private Color getColor() {  //随机生成一个颜色
        int red = random.nextInt(200);
        int green = random.nextInt(200);
        int blue = random.nextInt(200);
        return new Color(red, green, blue);
    }

    private Font getFont() {//随机字体
        String name = fontNames[random.nextInt(fontNames.length)];
        int style = random.nextInt(4);
        int size = random.nextInt(5) + 24;
        return new Font(name, style, size);
    }


    private char getChar() { //随机字符
        return codes.charAt(random.nextInt(codes.length()));
    }


    private BufferedImage createImage() { //创建BufferedImage对象
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D) image.getGraphics();
        g2.setColor(bgColor);// 设置验证码图片的背景颜色
        g2.fillRect(0, 0, width, height);
        return image;
    }

    public BufferedImage getImage() {
        BufferedImage image = createImage();
        Graphics2D g2 = (Graphics2D) image.getGraphics();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 4; i++) {
            String s = getChar() + "";
            sb.append(s);
            g2.setColor(getColor());
            g2.setFont(getFont());
            float x = i * width * 1.0f / 4;
            g2.drawString(s, x, height - 15);
        }
        this.validcode = sb.toString();
        drawLine(image);
        return image;
    }

    private void drawLine(BufferedImage image) {//绘制干扰线
        Graphics2D g2 = (Graphics2D) image.getGraphics();
        int num = 6;
        for (int i = 0; i < num; i++) {
            int x1 = random.nextInt(width);
            int y1 = random.nextInt(height);
            int x2 = random.nextInt(width);
            int y2 = random.nextInt(height);
            g2.setColor(getColor());
            g2.setStroke(new BasicStroke(1.5f));
            g2.drawLine(x1, y1, x2, y2);
        }
    }

    public String getValidcode() {
        return validcode;
    }

    //输出JPEG图片到前端
    public static void output(BufferedImage image, OutputStream out) throws IOException {
        ImageIO.write(image, "JPEG", out);
    }
}