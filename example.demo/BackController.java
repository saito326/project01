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

	@RequestMapping(value = "/backlogin")
	public ModelAndView backlogin(ModelAndView mv) {

		String name = (String) session.getAttribute("name");

		session.setAttribute("name", name);
		Acount acountList = acountRepository.findByName((String) session.getAttribute("name"));
		Integer acountCode = acountList.getCode();
		List<Goal> goalList = goalRepository.findByAcountCode(acountCode);
		mv.addObject("goalList", goalList);
		mv.setViewName("goal");
		return mv;
	}
	
	@RequestMapping(value = "/backedit")
	public ModelAndView backedit(ModelAndView mv) {
	
		Integer goalCode=(Integer) session.getAttribute("goalCode");
		
	List<Routine> routineList = routineRepository.findByGoalCode(goalCode);
	List<Task> taskList = taskRepository.findByGoalCode(goalCode);
	
	mv.addObject("routineList",routineList);
	mv.addObject("taskList",taskList);
	mv.setViewName("taskRoutine");
	return mv;
}

}