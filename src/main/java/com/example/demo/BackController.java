package com.example.demo;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BackController {

	@Autowired
	HttpSession session;

	@Autowired
	AcountRepository acountRepository;

	@Autowired
	GoalRepository goalRepository;

	@Autowired
	RoutineRepository routineRepository;

	@Autowired
	TaskRepository taskRepository;

	@Autowired
	AcountService as;

	@RequestMapping(value = "/back")
	public String login() {

		return "index";
	}
	@RequestMapping(value = "/backadd")
	public ModelAndView backad(ModelAndView mv) {
		mv.setViewName("addAcount");
		return mv;
	}

	@RequestMapping(value = "/backlogin")
	public ModelAndView backlogin(ModelAndView mv) {

		String name = (String) session.getAttribute("name");
		String password = (String) session.getAttribute("password");
		Acount acountList = acountRepository.findByNameAndPassword(name, password);
		Integer acountCode = acountList.getCode();
		List<Goal> goalList = goalRepository.findByAcountCode(acountCode);

		// ポイントの総合得点チェック
		Integer totalPoint = 0;
		for (int i = 0; i < goalList.size(); i++) {
			Goal gl = goalRepository.findByAcountCode(acountCode).get(i);
			Integer glLevel = gl.getLevel();
			totalPoint += glLevel;
			session.setAttribute("totalPoint", totalPoint);
		}
		
		mv.addObject("messageTP","現在、あなたの総得点は"+totalPoint+"ポイントです！");
		mv.addObject("goalList", goalList);
		mv.setViewName("goal");
		return mv;
	}

	@RequestMapping(value = "/backedit")
	public ModelAndView backedit(ModelAndView mv) {

		Integer goalCode = (Integer) session.getAttribute("goalCode");

		List<Routine> routineList = routineRepository.findByGoalCode(goalCode);
		List<Task> taskList = taskRepository.findByGoalCode(goalCode);

		mv.addObject("routineList", routineList);
		mv.addObject("taskList", taskList);
		mv.setViewName("taskRoutine");
		return mv;
	}

	@RequestMapping(value = "/backG")
	public ModelAndView backG(ModelAndView mv) {
		
		mv.setViewName("addGoal");

		return mv;
	}
	@RequestMapping(value = "/backT")
	public ModelAndView backT(ModelAndView mv) {

		mv.setViewName("addTask");

		return mv;
	}

	

}