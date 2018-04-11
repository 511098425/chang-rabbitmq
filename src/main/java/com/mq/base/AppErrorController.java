package com.mq.base;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 自定义springboot white error page返回值
 * @author Mr.Chang
 * @create 2018-04-11 17:35
 **/
@Slf4j
@Controller
public class AppErrorController implements ErrorController {

    private static AppErrorController appErrorController;
    /**
     * 错误消息参数
     */
    @Autowired
    private ErrorAttributes errorAttributes;

    /**
     * Controller for the Error Controller
     * @param errorAttributes
     * @return
     */

    public AppErrorController(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    public AppErrorController() {
        if(appErrorController == null){
            appErrorController = new AppErrorController(errorAttributes);
        }
    }

    /**
     * 返回html格式错误页面
     * @param request
     * @return
     */
    @RequestMapping(value = "/error", produces = "text/html")
    public ModelAndView errorHtml(HttpServletRequest request) {
        return new ModelAndView("error", getErrorAttributes(request, false));
    }
    /**
     * 返回json格式的错误
     * @param request
     * @return
     */
    @RequestMapping(value = "/error")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        Map<String, Object> body = getErrorAttributes(request, getTraceParameter(request));
        HttpStatus status = getStatus(request);
        return new ResponseEntity<>(body, status);
    }
    /**
     * 重写返回的错误页面.
     * @return the error path
     */
    @Override
    public String getErrorPath() {
        return "/error";
    }
    /**
     * 将错误消息转换为小写
     * @param request
     * @return
     */
    private boolean getTraceParameter(HttpServletRequest request) {
        String parameter = request.getParameter("trace");
        if (parameter == null) {
            return false;
        }
        return !"false".equals(parameter.toLowerCase());
    }
    /**
     * 错误消息组装
     * @param request
     * @param includeStackTrace
     * @return
     */
    private Map<String, Object> getErrorAttributes(HttpServletRequest request,boolean includeStackTrace) {
        WebRequest requestAttributes = new ServletWebRequest(request);
        Map<String, Object> map = this.errorAttributes.getErrorAttributes(requestAttributes,includeStackTrace);
        //请求地址
        String URL = request.getRequestURL().toString();
        //状态码
        int statusCode = Integer.valueOf(map.get("status").toString());
        if (statusCode == HttpStatus.METHOD_NOT_ALLOWED.value()){
            map.put("error","API不支持该请求方式");
        }
        log.debug("method [error info]: status-" + map.get("status") +", request url-" + URL);
        return map;
    }
    /**
     * http状态码转换
     * @param request
     * @return
     */
    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode != null) {
            try {
                return HttpStatus.valueOf(statusCode);
            }
            catch (Exception ignored) {
            }
        }
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

}