package com.mx.server.util.captcha;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import com.pig4cloud.captcha.SpecCaptcha;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

/**
 * @author Maoxian
 */
@SuppressWarnings("Duplicates")
@Slf4j
@Component
public class ImageCaptchaUtil {
    /**
     * 验证码字符串使用的所有字符
     */
    private static final String MAP_TABLE = "23456789ABCDEFGHJKLMNPQRSTUVWXYZ";

    /**
     * 运算验证码使用的运算符，无除法
     */
    private static final String MAP_TABLE_OP = "+-*";

    /**
     * 验证码图片宽度，可由配置文件配置
     */
    private static int WIDTH = 86;
    @Value("${util.captcha.width:86}")
    private void setWidth(int width){
        WIDTH = width;
    }
    public static int getWidth() { return WIDTH; }

    /**
     * 验证码图片高度，可由配置文件配置
     */
    private static int HEIGHT = 37;
    @Value("${util.captcha.height:37}")
    private void setHeight(int height){
        HEIGHT = height;
    }
    public static int getHeight() { return HEIGHT; }

    /**
     * 生成图片验证码并塞到response里
     */
    public static CaptchaData getImageCode() {
        boolean type = RandomUtil.randomBoolean();
        // 验证码答案
        String code;
        // 验证码图片文本
        String imgCode;
        if (type) {
            // 标准图片字符验证码
            code = imgCode = RandomUtil.randomString(MAP_TABLE, 4);
        } else {
            // 运算验证码
            int a = RandomUtil.randomInt(9);
            // 避开除法
            char op = RandomUtil.randomChar(MAP_TABLE_OP);
            int b = RandomUtil.randomInt(9);
            int res = calNum(a, b, op);
            imgCode = "" + a + op + b;
            code = String.valueOf(res);
        }

        return new CaptchaData(code, imgCode, IdUtil.fastSimpleUUID());
    }

    public static void setCaptchaImgToResponse(HttpServletResponse response, BufferedImage image, CaptchaData captchaData) {
        // 设置不缓存
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        // response 整个为图片
        response.setContentType("image/jpeg");

        response.setHeader(captchaData.getHeaderKey(), captchaData.getKey());
        response.setHeader(captchaData.getHeaderTimeKey(), captchaData.getExpireTime().toString());

        OutputStream os = null;
        try {
            os = response.getOutputStream();
            ImageIO.write(image, "jpg", os);
        } catch (Exception e) {
            log.error("验证码写入 输出流 异常: " + e.getMessage());
        } finally {
            if (os != null) {
                try {
                    os.flush();
                    os.close();
                } catch (IOException e) {
                    log.error("验证码 输出流 关闭异常: " + e.getMessage());
                }
            }
        }
    }

    public static void setCaptchaImgToResponse(HttpServletResponse response, SpecCaptcha specCaptcha, CaptchaData captchaData) {
        // 设置不缓存
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        // response 整个为图片
        response.setContentType("image/jpeg");

        response.setHeader(captchaData.getHeaderKey(), captchaData.getKey());
        response.setHeader(captchaData.getHeaderTimeKey(), captchaData.getExpireTime().toString());

        OutputStream os = null;
        try {
            os = response.getOutputStream();
            specCaptcha.out(os);
        } catch (Exception e) {
            log.error("验证码写入 输出流 异常: " + e.getMessage());
        } finally {
            if (os != null) {
                try {
                    os.flush();
                    os.close();
                } catch (IOException e) {
                    log.error("验证码 输出流 关闭异常: " + e.getMessage());
                }
            }
        }
    }

    private static int calNum(int a, int b, char op)  {
        switch (op) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            default:
                throw new IllegalArgumentException("运算符不能为除号");
        }
    }

    /**
     * 生成图形验证码
     * 当前code是计算表达式 "3+9=" 时，数字应在0-9间
     */
    public static BufferedImage genImage(String code) {
        log.debug("gen captcha: {}", code);
        BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        // 获取图形上下文
        Graphics2D g = (Graphics2D) image.getGraphics();
        //生成随机类
        Random random = new Random();
        // 设定背景色
        g.setColor(getRandColor(200, 250));
        g.fillRect(0, 0, WIDTH, HEIGHT);
        //设定字体
        g.setFont(new Font("Times New Roman", Font.BOLD, 24));
        // 随机产生干扰线，使图象中的认证码不易被其它程序探测到
        g.setColor(getRandColor(160, 200));
        for (int i = 0; i < 64; i++) {
            int x = random.nextInt(WIDTH);
            int y = random.nextInt(HEIGHT);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.drawLine(x, y, x + xl, y + yl);
        }
        for (int i = 0; i < code.length(); i++) {
            String str = code.substring(i, i + 1);
            // 将认证码显示到图象中
            g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));

            // 设置字体旋转角度
            // 角度小于30度
            int degree = random.nextInt() % 20;
            int x = 19 * i + 13;
            int y = 25;
            //正向旋转
            g.rotate(degree * Math.PI / 180, x, y);
            // 设置随便码在背景图图片上的位置
            g.drawString(str, x, y);
            //反向旋转
            g.rotate(-degree * Math.PI / 180, x, y);
        }

        // 释放图形上下文
        g.dispose();
        return image;
    }

    //给定范围获得随机颜色
    private static Color getRandColor(int fc, int bc) {
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
}