package top.haibaraai.wx_login_pay.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import top.haibaraai.wx_login_pay.domain.JsonData;

/**
 * 异常处理控制器
 */
@ControllerAdvice
public class MyExceptionHandler {

    /**
     * 捕获所有异常
     * @param e
     * @return
     */
    @ExceptionHandler
    @ResponseBody
    public JsonData Handler(Exception e) {

        if (e instanceof MyException) {
            MyException myException = (MyException) e;
            return JsonData.buildError(myException.getCode(), myException.getMsg());
        } else {
            return JsonData.buildError("全局异常，未知错误");
        }

    }

}
