package com.bchengchat.common.utils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * @Author Bcheng
 * @Create 2021/8/18
 * @Description <p>Excel 导出流</p>
 */
public class ResponseUtil {

    public static ServletOutputStream getServletOutputStream(HttpServletResponse response, String fileName) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String name = URLEncoder.encode(fileName, "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + name + ".xlsx");
        return response.getOutputStream();
    }
}
