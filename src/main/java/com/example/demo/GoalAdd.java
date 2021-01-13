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

	@Autowired
	GoalService gs;

	// 目標の追加
	@RequestMapping(value = "/addGoal")
	public ModelAndView add(
			@RequestParam("goal") String goal, 
			@RequestParam("start") String start,
			@RequestParam("finish") String finish, ModelAndView mv) {
		
		session.setAttribute("goal", goal);
		session.setAttribute("start", start);
		session.setAttribute("finish", finish);

		if (goal.equals("") || start.equals("") || finish.equals("")) {
			List<String> msgList = gs.checkAdd(goal, start, finish);
			mv.addObject("msgList", msgList);
			mv.setViewName("messageG");

		} else {

			// 追加機能
			String name = (String) session.getAttribute("name");
			String password = (String) session.getAttribute("password");
			Acount acountList = acountRepository.findByNameAndPassword(name, password);
			Integer acountCode = acountList.getCode();
			Goal add = new Goal(goal, start, finish, acountCode);
			goalRepository.saveAndFlush(add);

			// 一覧表示
			List<Goal> goalList = goalRepository.findByAcountCode(acountCode);
			
			//ポイントの総合得点計算
			Integer totalPoint = 0;
			for(int i = 0 ; i < goalList.size() ; i++) {
			Goal gl = goalRepository.findByAcountCode(acountCode).get(i);
			Integer glLevel = gl.getLevel();
			totalPoint += glLevel;
			session.setAttribute("totalPoint", totalPoint);
			}		
					
			mv.addObject("messageTP","現在、あなたの合計得点は"+totalPoint+"ポイントです！");
			mv.addObject("goalList", goalList);
			mv.setViewName("goal");
		
		}

		return mv;
	}

}
