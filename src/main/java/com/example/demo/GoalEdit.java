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
public class GoalEdit {

	@Autowired
	HttpSession session;

	@Autowired
	AcountRepository acountRepository;

	@Autowired
	GoalRepository goalRepository;

	// 目標の編集
	@RequestMapping(value = "/editGoal", method = RequestMethod.POST)
	public ModelAndView add(
			@RequestParam("goal") String goal, 
			@RequestParam("start") String start,
			@RequestParam("finish") String finish, 
			 ModelAndView mv) {

		Integer check = (int) session.getAttribute("editCode");

		Goal editList = goalRepository.findById(check).get();

		editList.setGoal(goal);
		editList.setStart(start);
		editList.setFinish(finish);
		

		goalRepository.saveAndFlush(editList);

		// 一覧表示
		String name = (String) session.getAttribute("name");
		String password = (String) session.getAttribute("password");
		Acount acountList = acountRepository.findByNameAndPassword(name, password);
		Integer acountCode = acountList.getCode();
		List<Goal> goalList = goalRepository.findByAcountCode(acountCode);
		
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

		return mv;
	}

}
