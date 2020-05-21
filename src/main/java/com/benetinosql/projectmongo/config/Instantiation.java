package com.benetinosql.projectmongo.config;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import com.benetinosql.projectmongo.domain.Post;
import com.benetinosql.projectmongo.domain.User;
import com.benetinosql.projectmongo.dto.AuthorDTO;
import com.benetinosql.projectmongo.dto.CommentDTO;
import com.benetinosql.projectmongo.repository.PostRepository;
import com.benetinosql.projectmongo.repository.UserRepository;

public class Instantiation implements CommandLineRunner {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PostRepository postRepository;

	@Override
	public void run(String... args) throws Exception {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		
		userRepository.deleteAll();
		postRepository.deleteAll();
		
		User maria = new User(null, "Maria Brown", "maria@gmail.com");
		User alex = new User(null, "Alex Green", "alex@gmail.com");
		User bob = new User(null, "Bob Grey", "bob@gmail.com");
		
		userRepository.saveAll(Arrays.asList(maria, alex, bob));
		
		Post post1 = new Post(null, sdf.parse("21/03/2018"), "It's time to eat!", "What a delicious hamburguer!", new AuthorDTO(maria));
		Post post2 = new Post(null, sdf.parse("12/05/2018"), "Good moorning world!", "I'm very sleepy today!", new AuthorDTO(maria));
		
		CommentDTO c1 = new CommentDTO("It's a beautiful sunny day!", sdf.parse("22/08/2019"), new AuthorDTO(alex));
		CommentDTO c2 = new CommentDTO("Sooo god meal!", sdf.parse("22/08/2019"), new AuthorDTO(maria));
		CommentDTO c3 = new CommentDTO("Enjoy your holiday!", sdf.parse("22/08/2019"), new AuthorDTO(bob));
		
		post1.getComments().addAll(Arrays.asList(c2));
		post2.getComments().addAll(Arrays.asList(c1, c3));
		
		postRepository.saveAll(Arrays.asList(post1, post2));
		
		maria.getPosts().addAll(Arrays.asList(post1, post2));
		userRepository.save(maria);
	}

}
