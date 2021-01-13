package com.example.demo;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AcountController {

	@Autowired
	HttpSession session;

	@Autowired
	AcountRepository acountRepository;

	@Autowired
	GoalRepository goalRepository;

	@Autowired
	AcountService as;

	@RequestMapping(value = "/")
	public String login() {
		session.invalidate();
		return "index";
	}

	// ログイン
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView doLogin(@RequestParam("name") String name, @RequestParam("password") String password,
			ModelAndView mv) {

		session.setAttribute("name", name);
		session.setAttribute("password", password);

		// 名前とパスワードが記入されているかの確認
		if (name.equals("") || password.equals("")) {
			List<String> msgList = as.checkAdd(name, password);
			mv.addObject("msgList", msgList);
			mv.setViewName("messageA");

		} else {
			Acount acountCheck = acountRepository.findByNameAndPassword(name, password);
			if (acountCheck == null) {
				session.invalidate();
				mv.addObject("message", "登録されていません");
				mv.setViewName("index");
			} else {

				Acount acountList = acountRepository.findByNameAndPassword(name, password);
				Integer acountCode = acountList.getCode();
				List<Goal> goalList = goalRepository.findByAcountCode(acountCode);

				// ポイントの総合得点計算
				Integer totalPoint = 0;
				for (int i = 0; i < goalList.size(); i++) {
					Goal gl = goalRepository.findByAcountCode(acountCode).get(i);
					Integer glLevel = gl.getLevel();
					totalPoint += glLevel;
					session.setAttribute("totalPoint", totalPoint);
				}

				mv.addObject("messageTP", "現在、あなたの合計得点は" + totalPoint + "ポイントです！");
				mv.addObject("goalList", goalList);
				mv.setViewName("goal");
			}
		}
		return mv;
	}

	@RequestMapping(value = "/addAcount", method = RequestMethod.POST)
	public ModelAndView addAcount(@RequestParam("name") String name, @RequestParam("password") String password,
			ModelAndView mv) {

		session.setAttribute("name2", name);
		session.setAttribute("password2", password);

		// 名前とパスワードが記入されているかの確認
		if (name.equals("") || password.equals("")) {
			List<String> msgList = as.checkAdd(name, password);
			mv.addObject("msgList", msgList);
			mv.setViewName("messageA2");

		} else {
			Acount acountCheck = acountRepository.findByNameAndPassword(name, password);
			if (acountCheck == null) {
				Acount addAcount = new Acount(name, password);
				acountRepository.saveAndFlush(addAcount);
				session.invalidate();
				mv.addObject("message", "登録しました");
				mv.setViewName("index");

			} else {
				session.invalidate();
				mv.addObject("message2", "入力されたアカウント名とパスワードは既に使われています");
				mv.setViewName("addAcount");
			}
		}

		return mv;
	}

	@RequestMapping(value = "/addAcount")
	public ModelAndView addAcount(ModelAndView mv) {
		session.invalidate();
		mv.setViewName("addAcount");
		return mv;
	}
}
