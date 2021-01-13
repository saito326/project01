package com.example.demo;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class GoalDelete {

	@Autowired
	HttpSession session;

	@Autowired
	AcountRepository acountRepository;

	@Autowired
	GoalRepository goalRepository;

	//目標削除ボタン
	@RequestMapping(value = "/deleteGoal", method = RequestMethod.POST)
	public ModelAndView deleteGoal(ModelAndView mv) {
        
		Goal checkList = goalRepository.findById((Integer)session.getAttribute("deleteCode")).get();
		goalRepository.delete(checkList);

		//一覧表示
		Acount acountList = acountRepository.findByName((String) session.getAttribute("name"));
		Integer acountCode = acountList.getCode();
		List<Goal> goalList = goalRepository.findByAcountCode(acountCode);
		mv.addObject("goalList", goalList);
		mv.setViewName("goal");

		return mv;
	}
}
