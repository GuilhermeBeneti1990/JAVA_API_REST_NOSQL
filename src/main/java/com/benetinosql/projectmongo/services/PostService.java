package com.benetinosql.projectmongo.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.benetinosql.projectmongo.domain.Post;
import com.benetinosql.projectmongo.repository.PostRepository;
import com.benetinosql.projectmongo.services.exception.ObjectNotFoundException;

@Service
public class PostService {
	
	@Autowired
	private PostRepository repo;
	
	public Post findById(String id) {
		Post post = repo.findOne(id);
		if(post == null) {
			throw new ObjectNotFoundException("Objects not found");
		}
		return post;
		
	}
	
	public List<Post> findByTitle(String text) {
		return repo.findByTitleContainingIgnoreCase(text);
	}
	
	public List<Post> fullSearch(String text, Date minDate, Date maxDate) {
		maxDate = new Date(maxDate.getTime() + 24 * 60 * 60 * 1000 );
		return repo.fullSearch(text, minDate, maxDate);
	}
	
}
