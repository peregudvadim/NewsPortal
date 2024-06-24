package by.edu.web.listener;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Enumeration;
import java.util.logging.Logger;

@WebListener
public class RequestLoggingListener implements ServletRequestListener, ServletRequestAttributeListener {

    private static final Logger LOGGER = Logger.getLogger(RequestLoggingListener.class.getName());

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        HttpServletRequest request = (HttpServletRequest) sre.getServletRequest();
        StringBuilder logMessage = new StringBuilder("Request initialized: ");
        logMessage.append(request.getMethod()).append(" ").append(request.getRequestURL());

        if (request.getQueryString() != null) {
            logMessage.append("?").append(request.getQueryString());
        }

        logMessage.append("\nHeaders: ");
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            logMessage.append(headerName).append("=").append(request.getHeader(headerName)).append(", ");
        }

        logMessage.append("\nParameters: ");
        request.getParameterMap().forEach((key, value) -> {
            logMessage.append(key).append("=");
            for (String val : value) {
                logMessage.append(val).append(",");
            }
            logMessage.append(" ");
        });

        LOGGER.info(logMessage.toString());
    }

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        HttpServletRequest request = (HttpServletRequest) sre.getServletRequest();
        LOGGER.info("Request destroyed: " + request.getRequestURL());
    }

    @Override
    public void attributeAdded(ServletRequestAttributeEvent srae) {
        LOGGER.info("Attribute added: " + srae.getName() + " = " + srae.getValue());
    }

    @Override
    public void attributeRemoved(ServletRequestAttributeEvent srae) {
        LOGGER.info("Attribute removed: " + srae.getName());
    }

    @Override
    public void attributeReplaced(ServletRequestAttributeEvent srae) {
        LOGGER.info("Attribute replaced: " + srae.getName() + " = " + srae.getValue());
    }
}


