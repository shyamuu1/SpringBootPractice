package com.vasanjee.post.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.vasanjee.post.entities.Post;
import com.vasanjee.post.services.PostService;

@RestController
@RequestMapping("api/v1/posts")
public class PostController {
	
	
	private PostService ps;
	
	
	@Autowired
	public PostController(PostService ps, RestTemplate temp) {
		this.ps = ps;
	}
	
	@GetMapping
	public ResponseEntity<List<Post>> getPosts(){
		return this.ps.getPosts();
	}
	
	@PostMapping
	public ResponseEntity<String> addPost(@RequestBody Post p){
		ResponseEntity<Post> resp = this.ps.addPost(p);
		return (resp != null)? new ResponseEntity<String>(HttpStatus.CREATED):new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<String> updatePost(@PathVariable Integer id){
		return this.ps.updatePost(id);
	}
	
	@PatchMapping("/{id}/{patch}")
	public ResponseEntity<String> patchPost(@PathVariable Integer Id, @PathVariable String patch){
		return this.ps.patchPost(Id, patch);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deletePost(@PathVariable Integer id){
		return this.ps.deletePost(id);
	}
	

}
