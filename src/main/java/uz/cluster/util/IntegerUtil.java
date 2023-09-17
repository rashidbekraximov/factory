package uz.cluster.util;

import javax.servlet.http.HttpServletRequest;

public class IntegerUtil {

    public static int getActiveUser(HttpServletRequest request) {
        try {
            return Integer.parseInt(request.getSession().getAttribute("activeUserId").toString());
        } catch (Exception exception) {
            return -10;
        }
    }

    public static int getActiveFormId(HttpServletRequest request) {
        try {
            return Integer.parseInt(request.getSession().getAttribute("activeFormId").toString());
        } catch (Exception exception) {
            return -10;
        }
    }
}
