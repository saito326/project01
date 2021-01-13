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
	
	@Autowired
	TaskService ts;

	// タスクの追加
	@RequestMapping(value = "/addTask")
	public ModelAndView add(@RequestParam("task") String task, @RequestParam("start") String start,
			@RequestParam("finish") String finish, ModelAndView mv) {
		
		session.setAttribute("task", task);
		session.setAttribute("start", start);
		session.setAttribute("finish", finish);
		
		if (task.equals("") || start.equals("") || finish.equals("")) {
			List<String> msgList = ts.checkAdd(task, start, finish);
			mv.addObject("msgList", msgList);
			mv.setViewName("messageT");
			
		}else {
			
			Task taskList = new Task(task, start, finish);
			mv.addObject("taskList",taskList);
			mv.setViewName("addTaskCheck");
			return mv;
		}
		return mv;
	}
	
	@RequestMapping(value = "/doAddTask")
	public ModelAndView addCheck(ModelAndView mv) {
		
		String task = (String) session.getAttribute("task");
		String start = (String) session.getAttribute("start");
		String finish = (String) session.getAttribute("finish");
		
		Integer goalCode = (Integer) session.getAttribute("goalCode");
		Task add = new Task(task, start, finish, goalCode);
		taskRepository.saveAndFlush(add);
		
		//目標の達成ポイントに10ポイント獲得する
		Goal goalList = goalRepository.findByCode(goalCode);
		Integer goal = goalList.getLevel();
		Integer point = 10;
		Integer newlevel=goal+point;
		mv.addObject("messageP", point+"ポイント獲得しました");
		//目標テーブルの更新
		session.setAttribute("point",newlevel);
		goalList.setLevel(newlevel);
		goalRepository.saveAndFlush(goalList);
		

		// 一覧表示
		List<Routine> routineList = routineRepository.findByGoalCode(goalCode);
		List<Task> taskList = taskRepository.findByGoalCode(goalCode);

		mv.addObject("routineList", routineList);
		mv.addObject("taskList", taskList);
		mv.setViewName("taskRoutine");
	
		return mv;
	
	}

}
