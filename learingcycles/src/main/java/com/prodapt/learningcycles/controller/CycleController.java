package com.prodapt.learningcycles.controller;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.prodapt.learningcycles.entity.Cycles;
import com.prodapt.learningcycles.repository.CycleRepository;

import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/cycle")
public class CycleController {

	@Autowired
	private Optional<Cycles> cycle;
	
	@Autowired
	private CycleRepository cycleRepo;
	
	@GetMapping
	public void getFrontPage(HttpServletResponse resp) throws IOException {
		resp.getWriter().append("hello");
	}
	
	@GetMapping("/listAllAvailableCycles")
	public String getCycles(Model model) {
		List<Cycles> cycleList = new ArrayList<>();
		cycleRepo.findAll().forEach(cycle -> cycleList.add(cycle));
		model.addAttribute("cycleList", cycleList);
		return "cyclelist";
	}
	
	@PostMapping("/listAllAvailableCycles")
	public String takeCycle(@RequestParam(name="taken") Integer id,@RequestParam(name="action") String action) {
		cycle = cycleRepo.findById(id);
		if("take!".equals(action)) {
			return String.format("redirect:/cycle/borrow/%d",id);
		}
		else if("return!".equals(action))
		{
			return String.format("redirect:/cycle/return/%d",id);
		}
		else
			return "redirect:/cycle/listAllAvailableCycles";
	}
	
	@GetMapping("/borrow/{id}")
	public String getResponse(@PathVariable int id,Model model) throws IOException {
		if(!cycle.get().isTaken()) {
			cycle.get().setTaken(true);
			cycleRepo.setTakenById(cycle.get().isTaken(), id);
		}
		model.addAttribute("Cycles", cycle.get());
		return "borrowForm";
	}
	
	@PostMapping("/borrow/{id}")
	public String redirectFromBorrow(@RequestParam(name="action") String action) {
		if("goback".equals(action))
			return "redirect:/cycle/listAllAvailableCycles";
		else
			return "borrowForm";
	}
	
	@GetMapping("/return/{id}")
	public String putResponse(@PathVariable int id,Model model) {
		if(cycle.get().isTaken()) {
			cycle.get().setTaken(false);
			cycleRepo.setTakenById(cycle.get().isTaken(), id);
		}
		model.addAttribute("Cycles", cycle.get());
		return "returnForm";
	}
	
	@PostMapping("/return/{id}")
	public String redirectFromReturn(@RequestParam(name="action") String action) {
		if("goback".equals(action))
			return "redirect:/cycle/listAllAvailableCycles";
		else
			return "returnForm";
	}
	
	
}

