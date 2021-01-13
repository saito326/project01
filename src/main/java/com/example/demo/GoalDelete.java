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

	// 目標削除ボタン
	@RequestMapping(value = "/deleteGoal", method = RequestMethod.POST)
	public ModelAndView deleteGoal(ModelAndView mv) {

		Goal checkList = goalRepository.findById((Integer) session.getAttribute("deleteCode")).get();
		goalRepository.delete(checkList);

		// 一覧表示
		String name = (String) session.getAttribute("name");
		String password = (String) session.getAttribute("password");
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

		return mv;
	}
}
