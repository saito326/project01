package com.example.demo;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class GoalController {

	@Autowired
	HttpSession session;

	@Autowired
	AcountRepository acountRepository;

	@Autowired
	GoalRepository goalRepository;

	// 編集ボタン
	@RequestMapping(value = "/goal", params = "edit", method = RequestMethod.POST)
	public ModelAndView editGoal(@RequestParam(name = "edit", defaultValue = "") Integer editCode, ModelAndView mv) {
		
		session.setAttribute("editCode", editCode);
		Goal goalList = goalRepository.findById(editCode).get();
		mv.addObject("goalList", goalList);
		mv.setViewName("editGoal");
		return mv;
	}

	//目標削除ボタン(確認画面に飛ぶ）
	@RequestMapping(value = "/goal", params = "delete", method = RequestMethod.POST)
	public ModelAndView deleteGoal(@RequestParam(name = "delete", defaultValue = "") Integer deleteCode,
		ModelAndView mv) {
		
		session.setAttribute("deleteCode", deleteCode);
		Goal goalList = goalRepository.findById(deleteCode).get();
		mv.addObject("goalList", goalList);
		mv.setViewName("deleteGoal");

		return mv;
	}

	// 追加ボタン（追加画面に飛ぶ）
	@RequestMapping(value = "/goal", params = "add", method = RequestMethod.POST)
	public ModelAndView add(ModelAndView mv) {
		session.removeAttribute("goal");
		session.removeAttribute("start");
		session.removeAttribute("finish");
		mv.setViewName("addGoal");
		return mv;
	}

}
