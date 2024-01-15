package com.aston.homework.homework_aston.servlet;

import com.aston.homework.homework_aston.service.StudentService;
import com.aston.homework.homework_aston.service.impl.StudentServiceImpl;
import com.aston.homework.homework_aston.dto.StudentDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "StudentController", value = "/student")
@Log4j
public class StudentServlet extends HttpServlet {
    private final StudentService studentService;
    private final ObjectMapper objectMapper;

    public StudentServlet() {
        studentService = new StudentServiceImpl();
        objectMapper = new ObjectMapper();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        StudentDto studentDto = studentService.read(Long.parseLong(request.getParameter("id")));
        response.setContentType("application/json");
        response.getWriter().print(objectMapper.writeValueAsString(studentDto));
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            StudentDto studentDto = studentService.create(objectMapper.readValue(request.getReader(), StudentDto.class));
            response.setStatus(HttpServletResponse.SC_CREATED);
            log.info("Студент добавлен успешно");
            response.getWriter().print(objectMapper.writeValueAsString(studentDto));
        } catch (JsonProcessingException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    public void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            studentService.update(objectMapper.readValue(request.getReader(), StudentDto.class));
            log.info("Студент успешно обновлён");
        } catch (JsonProcessingException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        long id = Long.parseLong(request.getParameter("id"));
        studentService.delete(id);
        response.setStatus(HttpServletResponse.SC_ACCEPTED);
        log.info(String.format("Студент с id %d удалён", id));
    }
}
