package com.pluralsight.conferencedemo.controllers;

import com.pluralsight.conferencedemo.models.Session;
import com.pluralsight.conferencedemo.repositories.SessionRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sessions")
public class SessionsController {

    @Autowired
    private SessionRepository sessionRepository;

    @GetMapping
    public List<Session> listAllSessions() {
        return sessionRepository.findAll();
    }

    @GetMapping
    @RequestMapping("{sessionId}")
    public Session getSessionById(@PathVariable Long sessionId) {
        return sessionRepository.getOne(sessionId);
    }

    @PostMapping
    public Session createSession(@RequestBody final Session session) {
        return sessionRepository.saveAndFlush(session);
    }

    @RequestMapping(value = "{sessionId}", method = RequestMethod.DELETE)
    public void deleteSession(@PathVariable Long sessionId) {
        // need to check records in other tables having reference
        sessionRepository.deleteById(sessionId);
    }

    @RequestMapping(value = "{sessionId}", method = RequestMethod.PUT)
    public Session updateSession(@PathVariable Long sessionId, @RequestBody Session session) {
        // need to do validation of attributes, its been not done here
        Session existingSession = sessionRepository.getOne(sessionId);
        BeanUtils.copyProperties(session, existingSession, "sessionId");
        return sessionRepository.saveAndFlush(existingSession);
    }

}
