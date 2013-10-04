package org.bjdrgs.bjwt.authority.controller;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.bjdrgs.bjwt.authority.utils.CipherUtil;
import org.bjdrgs.bjwt.authority.utils.Constants;
import org.bjdrgs.bjwt.core.web.AjaxResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/user")
public class LoginController {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView index() {
		return new ModelAndView("login");
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult login(@RequestParam String username,
			@RequestParam String password, @RequestParam String checkcode,
			HttpServletRequest request) throws Exception {
		username = username != null ? username.trim() : null;
		password = password != null ? password.trim() : null;
		AjaxResult result = new AjaxResult();
		String trueCheckCode = (String) request.getSession().getAttribute(
				Constants.KEY_CHECKCODE);
		if (username == null || username.length() == 0) {
			result.setMessage("用户名为空");
			result.setSuccess(false);
			return result;
		} else if (password == null || password.length() == 0) {
			result.setMessage("密码为空");
			result.setSuccess(false);
			return result;
		} else if (checkcode == null) {
			result.setMessage("验证码为空");
			result.setSuccess(false);
			return result;
		} else if (!trueCheckCode.toLowerCase().equals(checkcode.toLowerCase())) {
			result.setMessage("验证码错误");
			result.setSuccess(false);
			return result;
		}
		return doLogin(username, password);
	}

	@RequestMapping(value = "/loginout")
	public ModelAndView loginout() {
		// 安全退出
		SecurityUtils.getSubject().logout();
		return new ModelAndView(new RedirectView("login.do"));
	}

	/**
	 * 用户登录shiro实现
	 * 
	 * @param username
	 * @param password
	 * @return 登录结果
	 */
	private AjaxResult doLogin(String username, String password) {
		AjaxResult result = new AjaxResult();
		password = CipherUtil.generatePassword(password);
		UsernamePasswordToken token = new UsernamePasswordToken(username,
				password);
		token.setRememberMe(true);
		Subject curUser = SecurityUtils.getSubject();
		try {
			curUser.login(token);
			result.setSuccess(true);
			Session session = curUser.getSession();
			session.setAttribute(Constants.KEY_CURUSER, curUser.getPrincipal());
		} catch (AuthenticationException ae) {
			result.setMessage("用户名或密码错误");
			result.setSuccess(false);
			logger.error(ae.getMessage(), ae);
		}
		return result;
	}

	// 验证码
	public Color getRandColor(int s, int e) {
		Random random = new Random();
		if (s > 255)
			s = 255;
		if (e > 255)
			e = 255;
		int r = s + random.nextInt(e - s);
		int g = s + random.nextInt(e - s);
		int b = s + random.nextInt(e - s);
		return new Color(r, g, b);
	}

	@RequestMapping("/checkcode")
	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 禁止缓存
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "No-cache");
		response.setDateHeader("Expires", 0);
		// 指定生成的响应是图片
		response.setContentType("image/jpeg");
		int width = 130;
		int height = 25;
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB); // 创建BufferedImage类的对象
		Graphics g = image.getGraphics(); // 创建Graphics类的对象
		Graphics2D g2d = (Graphics2D) g; // 通过Graphics类的对象创建一个Graphics2D类的对象
		Random random = new Random(); // 实例化一个Random对象
		Font mFont = new Font("华文宋体", Font.BOLD, 20); // 通过Font构造字体
		g.setColor(getRandColor(200, 250)); // 改变图形的当前颜色为随机生成的颜色
		g.fillRect(0, 0, width, height); // 绘制一个填色矩形
		// 画一条折线
		BasicStroke bs = new BasicStroke(2f, BasicStroke.CAP_BUTT,
				BasicStroke.JOIN_BEVEL); // 创建一个供画笔选择线条粗细的对象
		g2d.setStroke(bs); // 改变线条的粗细
		g.setColor(Color.DARK_GRAY); // 设置当前颜色为预定义颜色中的深灰色
		int[] xPoints = new int[3];
		int[] yPoints = new int[3];
		for (int j = 0; j < 3; j++) {
			xPoints[j] = random.nextInt(width - 1);
			yPoints[j] = random.nextInt(height - 1);
		}
		g.drawPolyline(xPoints, yPoints, 3);
		// 生成并输出随机的验证文字
		g.setFont(mFont);
		String sRand = "";
		int itmp = 0;
		for (int i = 0; i < 4; i++) {
			if (random.nextInt(2) == 1) {
				itmp = random.nextInt(26) + 65; // 生成A~Z的字母
			} else {
				itmp = random.nextInt(10) + 48; // 生成0~9的数字
			}
			char ctmp = (char) itmp;
			sRand += String.valueOf(ctmp);
			Color color = new Color(20 + random.nextInt(110),
					20 + random.nextInt(110), 20 + random.nextInt(110));
			g.setColor(color);
			/**** 随机缩放文字并将文字旋转指定角度 **/
			// 将文字旋转指定角度
			Graphics2D g2d_word = (Graphics2D) g;
			AffineTransform trans = new AffineTransform();
			trans.rotate(random.nextInt(7) * 3.14 / 180, 10 * i + 5, 20);
			// 缩放文字
			float scaleSize = random.nextFloat() + 0.8f;
			if (scaleSize > 1.1f)
				scaleSize = 1f;
			trans.scale(scaleSize, scaleSize);
			g2d_word.setTransform(trans);
			/************************/
			g.drawString(String.valueOf(ctmp), 25 * i + 20, 20);
		}
		// 将生成的验证码保存到Session中
		HttpSession session = request.getSession();
		session.setAttribute(Constants.KEY_CHECKCODE, sRand);
		g.dispose();
		ImageIO.write(image, "JPEG", response.getOutputStream());
	}
}
