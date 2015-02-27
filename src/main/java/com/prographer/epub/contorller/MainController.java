package com.prographer.epub.contorller;

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
import java.util.List;

@Controller
@RequestMapping("/")
public class MainController {
    Logger log = LoggerFactory.getLogger(MainController.class);

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index(
            @RequestParam(value = "url") String fileUrl,
            HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("main");

        if (fileUrl != null) {
            ServletContext context = request.getServletContext();
            String appPath = context.getRealPath("/epub-contents");
            String localFilePath = FileDownload.download(fileUrl, appPath);
            try {
                ZipUtil.unzip(localFilePath);
                String unzip_url = localFilePath.replace(appPath, "");
                unzip_url = unzip_url.substring(1, unzip_url.lastIndexOf((".")));
                mav.addObject("unzip_url", "/epub-contents/" + unzip_url + "/OEBPS/text/book_0000.xhtml");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        mav.addObject("message", "Hello world!");
        mav.addObject("email", "sexylion369@gmail.com");

        List<Integer> count = new ArrayList<Integer>();
        for (int i = 0; i < 10; i++) {
            count.add(i);
        }
        mav.addObject("count", count);
        return mav;
    }

}