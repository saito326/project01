package com.example.demo;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TaskAndRoutineController {
	
	@Autowired
	HttpSession session;
	
	@Autowired
	GoalRepository goalRepository;
	
	@Autowired
	AcountRepository acountRepository;
	
	@Autowired
	TaskRepository taskRepository;
	
	@Autowired
	RoutineRepository routineRepository;
	
	
	
	//タスクとルーチン一覧表示
	@RequestMapping(value = "/goal/code/{goalCode}")
	public ModelAndView taskAndRoutine(@PathVariable(name="goalCode")Integer goalCode,
			ModelAndView mv){
		
		Goal goalList = goalRepository.findByCode(goalCode);
		Integer goalLevel = goalList.getLevel();
		session.setAttribute("point", goalLevel);
		
		String goal = goalRepository.findById(goalCode).get().getGoal();
		session.setAttribute("goal", goal);
		session.setAttribute("goalCode", goalCode);
		
		List<Routine> routineList = routineRepository.findByGoalCode(goalCode);
		List<Task> taskList = taskRepository.findByGoalCode(goalCode);
		
		mv.addObject("routineList",routineList);
		mv.addObject("taskList",taskList);
		mv.setViewName("taskRoutine");
		return mv;
	}
	
	//編集ボタン（編集画面に飛ぶ）
		@RequestMapping(value = "/task", params = "edit", method = RequestMethod.POST)
		public ModelAndView edit(
				@RequestParam(name="edit",defaultValue="")Integer editCode,
				ModelAndView mv) {
		    Task taskList = taskRepository.findById(editCode).get();
			session.setAttribute("editCode", editCode);
			session.setAttribute("taskList", taskList);
			mv.addObject("taskList",taskList);
		    mv.setViewName("editTask");
			return mv;
		}
		
		//昇華ボタン（完結）
		@RequestMapping(value = "/task", params = "rankup", method = RequestMethod.POST)
		public ModelAndView rankup(
				@RequestParam(name="rankup",defaultValue="")Integer rankupCode,ModelAndView mv) {
			
			Task checkList = taskRepository.findById(rankupCode).get();
			String task=checkList.getTask();
			Integer goalCode=(Integer) session.getAttribute("goalCode");
			Routine routineCheck = new Routine(task,goalCode);
			
			//ルーチンに追加と、タスクからの削除
			routineRepository.saveAndFlush(routineCheck);
			taskRepository.deleteById(rankupCode);
			
			//目標の達成ポイントに２０ポイント追加する
			Goal goalList = goalRepository.findByCode(goalCode);
			Integer goal = goalList.getLevel();
			Integer plus = 20;
			Integer newlevel=goal+plus;
			session.setAttribute("point", newlevel);
			mv.addObject("messageP", plus+"ポイント獲得しました");
			
			//目標テーブルの更新
			goalList.setLevel(newlevel);
			goalRepository.saveAndFlush(goalList);
			
			//一覧表示
			List<Routine> routineList=routineRepository.findByGoalCode(goalCode);
			List<Task> taskList=taskRepository.findByGoalCode(goalCode);
			mv.addObject("routineList", routineList);
			mv.addObject("taskList", taskList);
			mv.setViewName("taskRoutine");
		
			return mv;
		}
		
		//追加ボタン（追加画面に飛ぶ）
		@RequestMapping(value = "/task", params = "add", method = RequestMethod.POST)
		public ModelAndView add(ModelAndView mv) {
			
			session.removeAttribute("task");
			session.removeAttribute("start");
			session.removeAttribute("finish");
			
			List<Task> taskList = taskRepository.findByGoalCode((Integer)session.getAttribute("goalCode"));
			
			if(taskList.size()<5) {
		    mv.setViewName("addTask");
		    
			}else {
				mv.addObject("messageAT", "タスクの追加は最大５つまでです");
				Integer goalCode=(Integer) session.getAttribute("goalCode");
				List<Routine> routineList=routineRepository.findByGoalCode(goalCode);
				taskList=taskRepository.findByGoalCode(goalCode);
				mv.addObject("routineList", routineList);
				mv.addObject("taskList", taskList);
				mv.setViewName("taskRoutine");
			}
			return mv;
		}
		
		

}

