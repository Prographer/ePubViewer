package com.prographer.epub;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
public class MainController {

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("main");
        mav.addObject("message", "Hello world!");
        mav.addObject("email", "sexylion369@gmail.com");

        List<Integer> count = new ArrayList<Integer>();
        for(int i=0; i<10; i++){
            count.add(i);
        }
        mav.addObject("count",count);
        return mav;
    }

}