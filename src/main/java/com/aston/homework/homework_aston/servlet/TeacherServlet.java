package com.aston.homework.homework_aston.servlet;

import com.aston.homework.homework_aston.service.impl.TeacherServiceImpl;
import com.aston.homework.homework_aston.dto.TeacherDto;
import com.aston.homework.homework_aston.service.TeacherService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "TeacherController", value = "/teacher")
@Log4j
public class TeacherServlet extends HttpServlet {
    private final TeacherService teacherService;
    private final ObjectMapper objectMapper;

    public TeacherServlet() {
        teacherService = new TeacherServiceImpl();
        objectMapper = new ObjectMapper();
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try{
            TeacherDto groupDto = teacherService.create(objectMapper.readValue(request.getReader(), TeacherDto.class));
            response.setStatus(HttpServletResponse.SC_CREATED);
            log.info("Учитель добавлен успешно");
            response.getWriter().print(objectMapper.writeValueAsString(groupDto));
        } catch (JsonProcessingException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        TeacherDto groupDto = teacherService.read(Long.parseLong(request.getParameter("id")));
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(objectMapper.writeValueAsString(groupDto));
    }

    @Override
    public void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try{
            teacherService.update(objectMapper.readValue(request.getReader(), TeacherDto.class));
            log.info("Учитель успешно обновлён");
        } catch (JsonProcessingException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        long id = Long.parseLong(request.getParameter("id"));
        teacherService.delete(id);
        response.setStatus(HttpServletResponse.SC_ACCEPTED);
        log.info(String.format("Учитель с id %d удалён", id));
    }
}
