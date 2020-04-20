package com.community.advice;

import com.alibaba.fastjson.JSON;
import com.community.dto.ResultDTO;
import com.community.exception.CustomizedErrorCode;
import com.community.exception.CustomizedException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@ControllerAdvice
public class CustomizedExceptionHandler {
    @ExceptionHandler(Exception.class)
    Object handle(HttpServletRequest request, Throwable e, Model model, HttpServletResponse response) {
        String contentType = request.getContentType();
        if ("application/json".equals(contentType)) { // return json data
            ResultDTO resultDTO;
            if (e instanceof CustomizedException) {
                resultDTO = ResultDTO.errorOf((CustomizedException) e);
            } else {
                resultDTO = ResultDTO.errorOf(CustomizedErrorCode.SYS_ERROR);
            }
            try {
                response.setContentType(contentType);
                response.setStatus(200);
                response.setCharacterEncoding("utf-8");
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(resultDTO));
                writer.close();
            } catch (IOException ex) {
            }
        } else {  //redirect to error page
            if (e instanceof CustomizedException) {
                model.addAttribute("message", e.getMessage());
            } else {
                model.addAttribute("message", CustomizedErrorCode.SYS_ERROR.getMessage());
            }
            return new ModelAndView("error");
        }


        return new ModelAndView("error");
    }

}
