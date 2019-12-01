package com.ryit.walletwebgateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

import javax.servlet.http.HttpServletRequest;

/**
 * @author : 刘修
 * @Date : 2019/9/11 17:30
 */
public class AccessFilter extends ZuulFilter {

    @Override
    public String filterType () {
        return "pre";
    }

    @Override
    public int filterOrder () {
        return 0;
    }

    @Override
    public boolean shouldFilter () {
        return true;
    }

    @Override
    public Object run () throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String url = request.getRequestURL().toString();

        if (!url.contains("/admin") && !url.endsWith("/oss/uploadFile")) {
            ctx.setSendZuulResponse(false);// 过滤该请求，不对其进行路由
            ctx.getResponse().setCharacterEncoding("UTF-8");
            ctx.setResponseStatusCode(401);// 返回错误码
            ctx.setResponseBody("{\"code\":0,\"msg\":\"无权限\"}");// 返回错误内容
            ctx.set("isSuccess", false);
            return null;
        }
        ctx.setSendZuulResponse(true);// 对该请求进行路由
        ctx.setResponseStatusCode(200);
        ctx.set("isSuccess", true);// 设值，让下一个Filter看到上一个Filter的状态
        return null;

    }
}
