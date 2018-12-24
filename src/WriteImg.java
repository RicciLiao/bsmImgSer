import sun.misc.BASE64Decoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WriteImg extends HttpServlet {

    private static final String imgDir = "/userImg/";
    private static final String imgLog = "log.txt";

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        File userPath = null;
        String imgB64 = null;
        String imgName = null;
        String imgType = null;
        String userGuid = null;
        OutputStream out = null;
        SimpleDateFormat df = null;
        StringBuffer reqUrl = null;
        StringBuffer serPath = null;
        StringBuffer locPath = null;
        BASE64Decoder base64Decoder = null;

        try {
            request.setCharacterEncoding("utf-8");
            response.setContentType("text/html;charset=utf-8");

            imgB64 = request.getParameter("imgB64");
            imgType = request.getParameter("imgType");
            userGuid = request.getParameter("userGuid");

            reqUrl = request.getRequestURL();
            serPath = reqUrl.delete(reqUrl.length() - request.getRequestURI().length(), reqUrl.length())
                    .append(request.getContextPath())
                    .append(imgDir)
                    .append(userGuid);

            locPath = new StringBuffer();
            locPath.append(request.getServletContext().getRealPath("/"))
                    .append(imgDir);

            userPath = new File(locPath.append(userGuid).toString());
            if (!userPath.exists()) {
                userPath.mkdirs();
            }

            base64Decoder = new BASE64Decoder();
            byte[] bytes = base64Decoder.decodeBuffer(imgB64);

            for (int i = 0; i < bytes.length; ++i) {
                if (bytes[i] < 0) {
                    bytes[i] += 256;
                }
            }

            df = new SimpleDateFormat("yyyyMMddHHmmss");
            imgName = df.format(new Date());

            out = new FileOutputStream(locPath + File.separator + imgName + "." + imgType);
            out.write(bytes);
            out.flush();
            out.close();

            serPath.append("/")
                    .append(imgName + "." + imgType);

            response.getWriter().write("{\"success\" : 1,\"message\" : \"\",\"url\" : \"" + serPath + "\"}");

        } catch (IOException e) {
            response.getWriter().write("{\"success\" : 0,\"message\" : \"上传失败！\"}");
            e.printStackTrace();
        } finally {

        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        this.doGet(request, response);
    }

}
