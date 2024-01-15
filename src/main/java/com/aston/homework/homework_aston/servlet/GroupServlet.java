package com.aston.homework.homework_aston.servlet;

import com.aston.homework.homework_aston.dto.GroupDto;
import com.aston.homework.homework_aston.service.GroupService;
import com.aston.homework.homework_aston.service.impl.GroupServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j
@WebServlet(name = "GroupController", value = "/group")
public class GroupServlet extends HttpServlet {
    private final GroupService groupService;
    private final ObjectMapper objectMapper;

    public GroupServlet(){
        groupService = new GroupServiceImpl();
        objectMapper = new ObjectMapper();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        GroupDto groupDto = groupService.read(Long.parseLong(request.getParameter("id")));
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(objectMapper.writeValueAsString(groupDto));
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try{
            GroupDto groupDto = groupService
                    .create(objectMapper.readValue(request.getReader(), GroupDto.class));
            response.setStatus(HttpServletResponse.SC_CREATED);
            log.info("Группа добавлена успешно");
            response.getWriter().print(objectMapper.writeValueAsString(groupDto));
        } catch (JsonProcessingException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    public void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try{
            groupService.update(objectMapper.readValue(request.getReader(), GroupDto.class));
            log.info("Группа успешно обновлена");
        } catch (JsonProcessingException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        long id = Long.parseLong(request.getParameter("id"));
        groupService.delete(id);
        response.setStatus(HttpServletResponse.SC_ACCEPTED);
    }
}
