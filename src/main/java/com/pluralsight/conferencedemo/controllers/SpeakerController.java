package com.pluralsight.conferencedemo.controllers;

import com.pluralsight.conferencedemo.models.Speaker;
import com.pluralsight.conferencedemo.repositories.SpeakerRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/speakers")
public class SpeakerController {

    @Autowired
    private SpeakerRepository speakerRepository;

    @GetMapping
    public List<Speaker> list() {
        return speakerRepository.findAll();
    }

    @GetMapping
    @RequestMapping("{speakerId}")
    public Speaker getSpeakerById(@PathVariable Long speakerId) {
        return speakerRepository.getOne(speakerId);
    }

    @PostMapping
    public Speaker createSpeaker(@RequestBody Speaker speaker) {
        return speakerRepository.saveAndFlush(speaker);
    }

    @RequestMapping(value = "{speakerId}", method = RequestMethod.DELETE)
    public void deleteSpeaker(@PathVariable Long speakerId) {
        // need to check records in other tables having reference
        speakerRepository.deleteById(speakerId);
    }

    @RequestMapping(value = "{speakerId}", method = RequestMethod.PUT)
    public Speaker updateSpeaker(@PathVariable Long speakerId, @RequestBody Speaker speaker) {
        Speaker existingSpeaker = speakerRepository.getOne(speakerId);
        BeanUtils.copyProperties(speaker, existingSpeaker, "speakerId");
        return speakerRepository.saveAndFlush(existingSpeaker);
    }

}
