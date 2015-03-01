package com.prographer.epub.contorller;

import com.prographer.epub.Constant;
import com.prographer.epub.model.EpubUrl;
import com.prographer.epub.util.FileDownload;
import com.prographer.epub.util.ZipUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
public class MainController {
    Logger log = LoggerFactory.getLogger(MainController.class);

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("main");
        List<EpubUrl> epub_urls = new ArrayList<EpubUrl>();
        epub_urls.add(new EpubUrl("대한민국 국가서지 2013 상권", request.getRequestURL().append("res/koreaNation_20141208_01702.epub").toString(),"예제"));
        epub_urls.add(new EpubUrl("EPUB2 셈플", request.getRequestURL().append("res/vPrompt-Sample-EPUB2.epub").toString(),"epub2 예제"));

        epub_urls.add(new EpubUrl("mymedia_lite", "https://epub-samples.googlecode.com/files/mymedia_lite-20130621.epub","Vertical writing, alternate stylesheets for vertical/horizontal writing, page-progression direction rtl"));
        epub_urls.add(new EpubUrl("EPUB 3.0 Specifications", "https://epub-samples.googlecode.com/files/epub30-spec-20121128.epub","EPUB 3.0 Specifications"));
        epub_urls.add(new EpubUrl("moby dick", "https://epub-samples.googlecode.com/files/moby-dick-20120118.epub","moby dick basic"));
        epub_urls.add(new EpubUrl("wasteland", "https://epub-samples.googlecode.com/files/wasteland-20120118.epub","wasteland basic"));

        mav.addObject("epub_urls",epub_urls);
        mav.addObject("default_url", request.getRequestURL().append("res/koreaNation_20141208_01702.epub"));
        mav.addObject("app-name", Constant.APP_NAME);
        mav.addObject("app-version", Constant.Version);
        return mav;
    }

}