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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.prodapt.learningcycles.entity.Cycles;
import com.prodapt.learningcycles.exception.UnsupportedActionException;
import com.prodapt.learningcycles.repository.CycleRepository;

@Controller
@RequestMapping("/cycle")
public class CycleController {

	@Autowired
	private Optional<Cycles> cycle;
	
	@Autowired
	private CycleRepository cycleRepo;
	
	@GetMapping("/list")
	public String getFrontPage(Model model,String action){
		List<Cycles> cycleList = new ArrayList<>();
		cycleRepo.findAll().forEach(cycle -> cycleList.add(cycle));
		model.addAttribute("cycleList", cycleList);
		if("take!".equals(action)){
			return "redirect:/cycle/borrow";
		}
		else if("restock!".equals(action))
			return "redirect:/cycle/restock";
		return "cyclelist";
	}
	
	@GetMapping("/borrow")
	public String takeCycles(Model model) throws IOException, UnsupportedActionException {
		List<Cycles> cycleList = new ArrayList<>();
		cycleRepo.findAll().forEach(cycle -> cycleList.add(cycle));
		model.addAttribute("cycleList", cycleList);
		return "borrow";
	}
	
	@GetMapping("/borrow/{id}")
	public String takeCycle(@PathVariable("id") int id,@RequestParam(name="action") String action,Model model) throws IOException, UnsupportedActionException {
		cycle = cycleRepo.findById(id);
		if("take!".equals(action)) {
			int count = cycle.get().getCount();
			if(count>0){
				count--;
				cycle.get().setCount(count);
				cycleRepo.save(cycle.get());
			}else{
				throw new UnsupportedActionException(cycle.get().getCompany()+" Stock Empty");
			}
		}
		List<Cycles> cycleList = new ArrayList<>();
		cycleRepo.findAll().forEach(cycle -> cycleList.add(cycle));
		model.addAttribute("cycleList", cycleList);
		return "redirect:/cycle/list";
	}
	
	@GetMapping("/restock")
	public String returnCycle(Model model) throws IOException, UnsupportedActionException {
		List<Cycles> cycleList = new ArrayList<>();
		cycleRepo.findAll().forEach(cycle -> cycleList.add(cycle));
		model.addAttribute("cycleList", cycleList);
		return "restock";
	}

	@GetMapping("/restock/{id}")
	public String returnCycle(@PathVariable("id") int id,@RequestParam(name="action") String action,@RequestParam(name="number") String newStock,Model model) throws IOException, UnsupportedActionException {
		cycle = cycleRepo.findById(id);
		if("restock!".equals(action)) {
			int count = cycle.get().getCount();
			if(count<100){
				count+=Integer.parseInt(newStock);
				cycle.get().setCount(count);
				cycleRepo.save(cycle.get());
			}else{
				throw new UnsupportedActionException(cycle.get().getCompany()+" Stock Empty");
			}
		}
		List<Cycles> cycleList = new ArrayList<>();
		cycleRepo.findAll().forEach(cycle -> cycleList.add(cycle));
		model.addAttribute("cycleList", cycleList);
		return "redirect:/cycle/list";
	}
}

