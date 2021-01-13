package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class AcountService {
	public List<String> checkAdd(String name,String password){
	
	List<String> messageList = new ArrayList<String>();
	
	if(name==null||name.equals("")) {
		messageList.add("名前を入力してください");
		
	}if(password==null||password.equals("")){
		messageList.add("パスワードを入力してください");
	}
	return messageList;
	}
	
}