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

	@Autowired
	GoalRepository goalRepository;

	// タスクの編集
	@RequestMapping(value = "/editTask", method = RequestMethod.POST)
	public ModelAndView add(@RequestParam("task") String task, @RequestParam("start") String start,
			@RequestParam("finish") String finish, @RequestParam("level") String level, ModelAndView mv) {

		Integer check = (int) session.getAttribute("editCode");

		Task editList = taskRepository.findById(check).get();

		Task taskCheck = (Task) session.getAttribute("taskList");
		String checkTask = taskCheck.getTask();
		String checkStart = taskCheck.getStart();
		String checkFinish = taskCheck.getFinish();

		if (checkTask.equals(task) && checkStart.equals(start) && checkFinish.equals(finish)) {

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

		} else {
			editList.setTask(task);
			editList.setStart(start);
			editList.setFinish(finish);
			editList.setLevel(level);

			taskRepository.saveAndFlush(editList);

			Integer goalCode = (Integer) session.getAttribute("goalCode");

			// 目標の達成ポイントに5ポイント消費する
			Goal goalList = goalRepository.findByCode(goalCode);
			Integer goal = goalList.getLevel();
			Integer point = 5;
			Integer newlevel = goal - point;

			// ポイントがマイナスになっていないかチェックする
			if (newlevel < 0) {
				Integer editCode = (Integer) session.getAttribute("editCode");
				Task taskList = taskRepository.findById(editCode).get();
				mv.addObject("taskList",taskList);
				mv.addObject("messageET", "ポイントが足りません");
				mv.setViewName("editTask");
				
			} else {

				mv.addObject("messageP", point + "ポイント消費しました");
				// 目標テーブルの更新
				session.setAttribute("point", newlevel);
				goalList.setLevel(newlevel);
				goalRepository.saveAndFlush(goalList);

				// 一覧表示
				List<Routine> routineList = routineRepository.findByGoalCode(goalCode);
				List<Task> taskList = taskRepository.findByGoalCode(goalCode);

				mv.addObject("routineList", routineList);
				mv.addObject("taskList", taskList);
				mv.setViewName("taskRoutine");

			}
		}

		return mv;
	}

}
