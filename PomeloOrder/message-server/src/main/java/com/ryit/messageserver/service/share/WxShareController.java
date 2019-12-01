package com.ryit.messageserver.service.share;

import com.ryit.messageserver.util.WinXinEntity;
import com.ryit.messageserver.util.WxShareUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author : 刘修
 * @Date : 2019/10/13 8:38
 */
@RestController
@RequestMapping("/wxShare")
public class WxShareController {

    @Autowired
    private WxShareUtil wxShareUtil;

    @GetMapping("/anon/share")
    public WinXinEntity share (HttpServletRequest request) {
        System.out.println(request.getParameter("url"));
//        System.out.println(request.getContextPath());
//        System.out.println(request.getServletPath());
        String strUrl = request.getParameter("url");
//                + request.getContextPath()   //项目名称
//                + request.getServletPath()   //请求页面或其他地址
//                + "?" + (request.getQueryString()); //参数
        return wxShareUtil.getWinXinEntity(strUrl);
    }
}
