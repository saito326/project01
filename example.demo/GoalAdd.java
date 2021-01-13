package com.example.demo;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class GoalAdd {

	@Autowired
	HttpSession session;

	@Autowired
	AcountRepository acountRepository;

	@Autowired
	GoalRepository goalRepository;

	// 目標の追加
	@RequestMapping(value = "/addGoal")
	public ModelAndView add(
			@RequestParam("goal") String goal,
			@RequestParam("start") String start,
			@RequestParam("finish") String finish, ModelAndView mv) {
		
		// 追加機能
		Acount checkList = acountRepository.findByName((String)session.getAttribute("name"));
		Integer acountCode=checkList.getCode();
		Goal add = new Goal(goal, start, finish,acountCode);
		goalRepository.saveAndFlush(add);

		// 一覧表示
		Acount acountList = acountRepository.findByName((String) session.getAttribute("name"));
	    acountCode = acountList.getCode();
		List<Goal> goalList = goalRepository.findByAcountCode(acountCode);
		mv.addObject("goalList", goalList);
		mv.setViewName("goal");

		return mv;
	}

}
