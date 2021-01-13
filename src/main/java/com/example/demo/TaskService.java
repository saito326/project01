package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class TaskService {
	public List<String> checkAdd(String task,String start, String finish){
	
	List<String> messageList = new ArrayList<String>();
	
	if(task==null||task.equals("")) {
		messageList.add("タスクを記入してください");
		
	}if(start==null||start.equals("")){
		messageList.add("開始日程を記入してください");
	
	}if(finish==null||finish.equals("")){
		messageList.add("終了日程を記入してください");
	}
	return messageList;
	}
	
}