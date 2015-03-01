package com.prographer.epub.contorller;

import com.prographer.epub.Constant;
import com.prographer.epub.model.Toc;
import com.prographer.epub.util.FileDownload;
import com.prographer.epub.util.ZipUtil;
import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.TOCReference;
import nl.siegmann.epublib.epub.EpubReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/viewer")
public class EPubViewerController {
    Logger log = LoggerFactory.getLogger(MainController.class);

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index(
            @RequestParam(value = "epub-url") String fileUrl,
            HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("viewer");
        mav.addObject("app-name", Constant.APP_NAME);
        mav.addObject("app-version", Constant.Version);

        if (fileUrl != null && !fileUrl.equals("")) {
            ServletContext context = request.getServletContext();
            String appPath = context.getRealPath("/epub-contents");
            String localFilePath = FileDownload.download(fileUrl, appPath);

            try {
                ZipUtil.unzip(localFilePath);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }

            /**
             * Epub Load
             */
            EpubReader reader = new EpubReader();
            try {
                Book book = reader.readEpub(new FileInputStream(localFilePath), "UTF-8");
                mav.addObject("book-title", book.getTitle());

                String unzip_url = localFilePath.replace(appPath, "");
                unzip_url = unzip_url.substring(1, unzip_url.lastIndexOf(("."))) + "/";
                String root_href = book.getOpfResource().getHref().substring(0, book.getOpfResource().getHref().lastIndexOf("/") + 1);
                StringBuilder content_url = new StringBuilder();
                content_url.append("/epub-contents/").append(unzip_url).append("/").append(root_href);

                mav.addObject("book-root-href", root_href);
                mav.addObject("book-url", content_url.toString() + book.getCoverPage().getHref());

                //toc -> spec2에만 적용됨
                if(book.getTableOfContents().getTocReferences().size()>0){
                    List<Toc> tocList = new ArrayList<Toc>();
                    for(TOCReference toc : book.getTableOfContents().getTocReferences()){
                        tocList.add(new Toc(toc.getTitle(),content_url.toString() + toc.getResource().getHref()));
                    }
                    mav.addObject("tocList",tocList);
                }


            } catch (IOException e) {
                log.error("Epub not read!", e);
            }

        }


        return mav;
    }

}