package kr.code.homes.config;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class ProcessInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        /*
          세션 체크를 해서 로그인 상태가 아닐경우  로그인화면으로 이동시키는 로직이 들어간다.
         */
        //로그인여부를 확인하여 로그인이 된 경우 계속 실행하고 로그인이 안된 경우 컨트롤러 실행을 중지한다.
        HttpSession ses  = request.getSession();

        String logStatus = (String)ses.getAttribute("logStatus");
        //로그인이 안된 경우, 로그인폼으로 이동하고 현재 진행을 중단시킴
        if(logStatus==null || !logStatus.equals("Y")) {
            response.sendRedirect("/account/login");
            return false;
        }

        return true;
    }

    /**
     * 해당 리퀘스트가 ajax 에 의한 것인지
     * 확인하는 메서드
     * @param req
     * @return
     */
    private boolean isAjaxRequest(HttpServletRequest req) {
        String header = req.getHeader("AJAX");
        if ("true".equals(header)){
            return true;
        }else{
            return false;
        }
    }
}