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
public class TaskEdit {

	@Autowired
	HttpSession session;

	@Autowired
	AcountRepository acountRepository;

	@Autowired
	TaskRepository taskRepository;

	@Autowired
	RoutineRepository routineRepository;

	// タスクの編集
	@RequestMapping(value = "/editTask", method = RequestMethod.POST)
	public ModelAndView add(@RequestParam("task") String task, @RequestParam("start") String start,
			@RequestParam("finish") String finish, @RequestParam("level") String level, ModelAndView mv) {

		Integer check = (int) session.getAttribute("editCode");

		Task editList = taskRepository.findById(check).get();

		editList.setTask(task);
		editList.setStart(start);
		editList.setFinish(finish);
		editList.setLevel(level);

		taskRepository.saveAndFlush(editList);

		// 一覧表示
		Integer goalCode = (Integer) session.getAttribute("goalCode");

		List<Routine> routineList = routineRepository.findByGoalCode(goalCode);
		List<Task> taskList = taskRepository.findByGoalCode(goalCode);

		mv.addObject("routineList", routineList);
		mv.addObject("taskList", taskList);
		mv.setViewName("taskRoutine");
		return mv;
	}

}
