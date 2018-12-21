import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class WriteImg extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        System.out.println("bsmImgSer");
        System.out.println(request.getParameter("username"));
        System.out.println(request.getParameter("password"));
        //获得发送HTTP请求的参数
        String username=request.getParameter("username");
        String password=request.getParameter("password");
        //向HTTP发送方返回响应数据
        if("123".equals(username)&&"456".equals(password)){
            response.getWriter().write("{\"falg\":\"success\"}");
        }else{
            response.getWriter().write("{\"falg\":\"error\"}");
        }

    }
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.doGet(request, response);
    }

}
