package util;

import jakarta.servlet.http.HttpServletRequest;

public class CSSUtil {

    public static String getCSSPath(HttpServletRequest request) {
        String contextPath = request.getContextPath();
        return contextPath + "/css/styles.css";
    }
}
