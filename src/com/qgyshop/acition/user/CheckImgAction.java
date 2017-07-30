package com.qgyshop.acition.user;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Created by vivid on 2017/3/25.
 * 生成验证码的程序
 */

@Controller
@Scope("prototype")
public class CheckImgAction extends ActionSupport{
    @Override
    public String execute() throws Exception {
//        宽高
        int width=120;
        int height=30;

        //第一步 绘制一张内存中的图片
        BufferedImage bufferedImage=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
//       第二 图片绘制的背景颜色 --通过绘图的对象
        Graphics graphics=bufferedImage.getGraphics();// 获得图像对象 获得画笔
        //绘制任何图形之前必须指定一个颜色
        graphics.setColor(getRandColor(200,250));
        graphics.fillRect(0,0,width,height);

//        第三 绘制边框
        graphics.setColor(Color.WHITE);
        graphics.drawRect(0,0,width-1,height-1);

        //第四 生成四个随机的数字
        Graphics2D graphics2D= (Graphics2D) graphics;
        //设置输出的字体
        graphics2D.setFont(new Font("宋体",Font.BOLD,18));

        String words="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";

        Random random=new Random(); //随机数

//        定义StringBuffer
        StringBuffer sb=new StringBuffer();
        //定义x坐标
        int x=10;

        for (int i = 0; i < 4; i++) {
            //随机的色彩
            graphics2D.setColor(getRandColor(20,130));

            //旋转 在-30 到 30 度之间
            //random.nextInt 注意是nextInt 要是不带参 取值比较大 1118486465 感觉 都是 9或10位的数
            //  加了bound 60后 [0,60） 60是取不到的 减30 则[-30,30）
            int jiaodu=random.nextInt(60)-30;
            // 角度换算成弧度
            double theta=(jiaodu/180)*Math.PI;

            //一个随机的数 字符串的索引
            int index=random.nextInt(words.length());
            //获得该索引的字符
            char c=words.charAt(index);

            sb.append(c);

            //将字符c 输出到图片
            graphics2D.rotate(theta,x,20);
            graphics2D.drawString(String.valueOf(c),x,20);
            graphics2D.rotate(-theta,x,20);
            //x加30 为了放下一个字符
            x+=30;
        }
        //将生成的验证码 放到session中
        ServletActionContext.getRequest().getSession().setAttribute("checkcode",sb.toString());
        //第五绘制干扰线

        graphics.setColor(getRandColor(160,200));
        int x1,x2,y1,y2;

        for (int i = 0; i < 30; i++) {
            x1=random.nextInt(width);
            x2=random.nextInt(12);
            y1=random.nextInt(height);
            y2=random.nextInt(12);

            graphics.drawLine(x1,y1,x1+x2,x2+y2);
        }

        //将图片输出到浏览器
        graphics.dispose(); //释放资源
        ImageIO.write(bufferedImage,"jpg",ServletActionContext.getResponse().getOutputStream());
        return NONE;
    }


    //获得随机的色彩
    private Color getRandColor(int fc, int bc) {
        //随机数对象
        Random random=new Random();
        if (fc>255){
            fc=255;
        }
        if (bc>255){
            bc=255;
        }
        int r=fc + random.nextInt(bc-fc);
        int g=fc + random.nextInt(bc-fc);
        int b=fc + random.nextInt(bc-fc);
        return new Color(r,g,b);
    }
}
