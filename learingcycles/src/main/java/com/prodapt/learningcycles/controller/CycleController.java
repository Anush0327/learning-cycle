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
import com.prodapt.learningcycles.entity.User;
import com.prodapt.learningcycles.exception.UnsupportedActionException;
import com.prodapt.learningcycles.repository.CycleRepository;
import com.prodapt.learningcycles.repository.UserRepository;

@Controller
@RequestMapping("/cycle")
public class CycleController {

	@Autowired
	private Optional<Cycles> cycle;
	
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private CycleRepository cycleRepo;

	@GetMapping("/login")
	public String loginPage(Model model){
		List<User> users = new ArrayList<>();
		userRepo.findAll().forEach(user -> users.add(user));
		model.addAttribute("Users", users);
		return "login";
	}
	
	@GetMapping("/list/{name}")
	public String getFrontPage(Model model,@PathVariable("name") String name){
		List<Cycles> cycleList = new ArrayList<>();
		var user = userRepo.findByName(name);
		model.addAttribute("name",user.isCustomer());
		cycleRepo.findAll().forEach(cycle -> cycleList.add(cycle));
		model.addAttribute("cycleList", cycleList);
		return "cyclelist";
	}
	
	
	@GetMapping("/borrow/{id}")
	public String takeCycle(@PathVariable("id") int id,@RequestParam(name="action") String action) throws IOException, UnsupportedActionException {
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
		return "redirect:/cycle/list";
	}
	
	@GetMapping("/restock/{id}")
	public String returnCycle(@PathVariable("id") int id,@RequestParam(name="action") String action,@RequestParam(name="number") String newStock) throws IOException, UnsupportedActionException {
		cycle = cycleRepo.findById(id);
		if("return!".equals(action)) {
			int count = cycle.get().getCount();
			if(count<100){
				count+=Integer.parseInt(newStock);
				cycle.get().setCount(count);
				cycleRepo.save(cycle.get());
			}else{
				throw new UnsupportedActionException(cycle.get().getCompany()+" Stock Empty");
			}
		}
		return "redirect:/cycle/list";
	}
}

