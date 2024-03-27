package com.example.demo.bookcontrl;

import com.example.demo.Service.MediService;
import com.example.demo.books.Medi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class MediController {
    @Autowired
    private MediService mediService;

    @GetMapping("/Medis")
    public ModelAndView findALlBooks(){
        List<Medi> medis = mediService.findAllMedi();
        ModelAndView mv=new ModelAndView();
        mv.addObject("Medis", medis);
        mv.setViewName("Medilist");
        return mv;
    }

    @GetMapping("/Medi/{id}")
    public ModelAndView findStudentByid(@PathVariable("id") int id){
        Medi medi = mediService.findMediByid(id);
        ModelAndView mv=new ModelAndView();
        mv.addObject("Medi", medi);
        mv.setViewName("Medidetail");
        return mv;
    }

    @PostMapping("/addMedi")
    public  ModelAndView addMedi(Medi medi){
        mediService.addMedi(medi);
        ModelAndView mv=new ModelAndView();
        mv.setViewName("redirect:/Medis");
        return mv;
    }

    @GetMapping("/addMedi")
    public String addMedi(){
        return "addMedi";
    }

    @GetMapping("/deleteDedi/{id}")
    public ModelAndView deleteMedi(@PathVariable("id") int id){
        mediService.deleteMedi(id);
        ModelAndView mv=new ModelAndView();

        mv.setViewName("redirect:/Medi");
        return mv;
    }

    @GetMapping("/toupdateDedi/{id]")
    public  ModelAndView toUpdateBook(@PathVariable("id") int id){
        Medi medi = mediService.findMediByid(id);
        ModelAndView mv=new ModelAndView();
        mv.addObject("Medi", id);
        mv.setViewName("updateMedi");
        return mv;
    }

    @PostMapping("updateDedi")
    public ModelAndView UpdateBook(MediService di){
        Medi Medi = null;
        mediService.updateMedi(Medi);
        ModelAndView mv= new ModelAndView();

        mv.setViewName("redirect:/books");
        return mv;
    }
}
