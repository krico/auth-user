package to.cwa.auth.http;

import to.cwa.auth.config.AuthConfig;
import to.cwa.auth.config.AuthConfigFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;

/**
 * @author krico
 * @since 19/07/16.
 */
public class AuthUserGitkitServlet extends HttpServlet {

    private static final String WIDGET_PATH_INFO = "/widget.html";
    private static final String CSS_PATH_INFO = "/widget.css";
    private static final String CSS_URL = "http://www.gstatic.com/authtoolkit/css/gitkit.css";
    private static final String JS_PATH_INFO = "/widget.js";
    private static final String JS_URL = "http://www.gstatic.com/authtoolkit/js/gitkit.js";
    private static final String JS_BOOT_PATH_INFO = "/boot.js";

    private static char[] widgetData;

    private void serveUrl(String contentType, URL url, HttpServletResponse resp) throws IOException {
        //TODO: cache
        resp.setContentType(contentType);
        int read;
        try (InputStream in = url.openStream(); OutputStream out = resp.getOutputStream()) {
            byte[] buf = new byte[4096];
            while ((read = in.read(buf)) >= 0) {
                out.write(buf, 0, read);
            }
        }
    }

    private void serveBoot(HttpServletResponse resp) throws IOException {
        //TODO: get this from context config
        AuthConfig config = new AuthConfigFactory().newFromJson(new File(System.getProperty("user.home"), "trocado.json"));

        resp.setContentType("application/javascript");
        try (Writer writer = resp.getWriter()) {
            writer.append("var c={");

            writer.append("};");

            writer.append("window.google.identitytoolkit.start(");
            writer.append(");");

            writer.append("window.google.identitytoolkit.start(" +
                    "                '#gitkitWidgetDiv', // accepts any CSS selector\n" +
                    "                config,\n" +
                    "                'JAVASCRIPT_ESCAPED_POST_BODY');");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();

        if (WIDGET_PATH_INFO.equals(pathInfo)) {
            serveUrl("text/html", getClass().getResource("widget.html"), resp);
        } else if (CSS_PATH_INFO.equals(pathInfo)) {
            serveUrl("text/css", new URL(CSS_URL), resp);
        } else if (JS_BOOT_PATH_INFO.equals(pathInfo)) {
            serveBoot(resp);
        } else if (JS_PATH_INFO.equals(pathInfo)) {
            serveUrl("application/javascript", new URL(JS_URL), resp);
        } else {
            String location = req.getServletPath() + WIDGET_PATH_INFO;
            resp.sendRedirect(location);
        }
    }
}
