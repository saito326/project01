package com.example.demo;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RankingController {

	@Autowired
	HttpSession session;

	@Autowired
	AcountRepository acountRepository;

	@Autowired
	GoalRepository goalRepository;

	@Autowired
	AcountService as;

	@Autowired
	RankingRepositiry rankingRepository;

	// ログイン
	@RequestMapping(value = "/ranking")
	public ModelAndView doLogin(ModelAndView mv) {

		// 初めてのランキング表示か判定
		Integer point = (Integer) session.getAttribute("totalPoint");
		String name = (String) session.getAttribute("name");
		String password = (String) session.getAttribute("password");
		Acount checkList = acountRepository.findByNameAndPassword(name, password);
		Integer acountCode = checkList.getCode();

		Ranking checkRank = rankingRepository.findByAcountCode(acountCode);
		if (checkRank == null) {
			// 一回目のランキング表示
			Ranking ranking = new Ranking(acountCode, name, point);
			
			// ゼロポイントの場合、０を入れる
			if (point == null || point == 0) {
				point = 0;
				ranking = new Ranking(acountCode, name, point);
				rankingRepository.saveAndFlush(ranking);
				// ランキングの順位つけ
				List<Ranking> rankingList = rankingRepository.findByOrderByPointDesc();
				Integer pointCheck = 0;
				Integer rank = 0;
				for (Ranking r : rankingList) {
					if (pointCheck != r.getPoint()) {
						rank++;
						r.setRank(rank);
					}
					r.setRank(rank);
					pointCheck = r.getPoint();
				}
				mv.addObject("rankingList", rankingList);
				mv.setViewName("ranking");
				
			} else {
				rankingRepository.saveAndFlush(ranking);				
			
				// ランキングの順位つけ
				List<Ranking> rankingList = rankingRepository.findByOrderByPointDesc();
				Integer pointCheck = 0;
				Integer rank = 0;
				for (Ranking r : rankingList) {
					if (pointCheck != r.getPoint()) {
						rank++;
						r.setRank(rank);
					}
					r.setRank(rank);
					pointCheck = r.getPoint();
				}
				mv.addObject("rankingList", rankingList);
				mv.setViewName("ranking");
			}
			} else {
			// ２回目以降のランキング表示

			// ランキングのポイントを更新
			Integer code = rankingRepository.findByAcountCode(acountCode).getCode();
			Ranking ranking = new Ranking(code,acountCode, name, point);
			rankingRepository.saveAndFlush(ranking);

			// ゼロポイントの場合、０を入れる
			if (point == null || point == 0) {
				point = 0;
				ranking = new Ranking(code,acountCode, name, point);
				rankingRepository.saveAndFlush(ranking);
			} 

				// ランキングの順位付け
				List<Ranking> rankingList = rankingRepository.findByOrderByPointDesc();
				Integer pointCheck = -1;
				Integer rank = 0;
				for (Ranking r : rankingList) {
					if (pointCheck != r.getPoint()) {
						rank++;
						r.setRank(rank);
					}
					pointCheck = r.getPoint();
					r.setRank(rank);
				}
				mv.addObject("rankingList", rankingList);
				mv.setViewName("ranking");
			}

		return mv;
	}

}