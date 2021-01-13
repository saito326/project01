package com.example.demo;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TaskAdd {

	@Autowired
	HttpSession session;

	@Autowired
	AcountRepository acountRepository;

	@Autowired
	TaskRepository taskRepository;

	@Autowired
	GoalRepository goalRepository;
	
	@Autowired
	RoutineRepository routineRepository;
	

	// タスクの追加
	@RequestMapping(value = "/addTask")
	public ModelAndView add(@RequestParam("task") String task, @RequestParam("start") String start,
			@RequestParam("finish") String finish, ModelAndView mv) {

		Integer goalCode = (Integer) session.getAttribute("goalCode");
		Task add = new Task(task, start, finish, goalCode);
		taskRepository.saveAndFlush(add);

		// 一覧表示
		List<Routine> routineList = routineRepository.findByGoalCode(goalCode);
		List<Task> taskList = taskRepository.findByGoalCode(goalCode);

		mv.addObject("routineList", routineList);
		mv.addObject("taskList", taskList);
		mv.setViewName("taskRoutine");
		return mv;
	}

}
