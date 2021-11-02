package com.prathamesh.app.redditCloneApp.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prathamesh.app.redditCloneApp.dto.SubredditDto;
import com.prathamesh.app.redditCloneApp.exceptions.SpringRedditException;
import com.prathamesh.app.redditCloneApp.mapper.SubredditMapper;
import com.prathamesh.app.redditCloneApp.model.Subreddit;
import com.prathamesh.app.redditCloneApp.repository.SubredditRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class SubredditService {

	private final SubredditRepository subredditRepository;
	private final SubredditMapper subredditMapper;
	
	@Transactional
	public SubredditDto save(SubredditDto subredditDto) {
		Subreddit save = subredditRepository.save(subredditMapper.mapDtoToSubreddit(subredditDto));
		subredditDto.setId(save.getId());
		return subredditDto;
	}

	@Transactional(readOnly = true)
	public List<SubredditDto> getAll() {
		return subredditRepository.findAll()
		.stream()
		.map(subredditMapper::mapSubredditToDto)
		.collect(Collectors.toList());
	}

	public SubredditDto getSubreddit(Long id) {
		Subreddit subreddit = subredditRepository.findById(id)
				.orElseThrow(() -> new SpringRedditException("No Subreddit found with this id:"+id));
		
		return subredditMapper.mapSubredditToDto(subreddit);
	}
}
