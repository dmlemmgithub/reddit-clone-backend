package com.programming.techie.springredditclone.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.programming.techie.springredditclone.dto.SubredditDto;
import com.programming.techie.springredditclone.service.SubredditService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/subreddit")
@AllArgsConstructor
public class SubredditController {
	
	 private final SubredditService subredditService;

	    @GetMapping
	    public List<SubredditDto> getAllSubreddits() {
	        return subredditService.getAll();
	    }

	    @GetMapping("/{id}")
	    public SubredditDto getSubreddit(@PathVariable Long id) {
	        return subredditService.getSubreddit(id);
	    }

	    @PostMapping
	    public SubredditDto create(@RequestBody @Valid SubredditDto subredditDto) {
	        return subredditService.save(subredditDto);
	    }

}
