package com.vasanjee.post.services;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.vasanjee.post.entities.Post;

@Service
public class PostService {

	
	private RestTemplate restTemplate;
	
	
	private static final String url = "https://jsonplaceholder.typicode.com/posts/";
	
	@Autowired
	public PostService(RestTemplate temp) {
		this.restTemplate = temp;
	}
	
	public ResponseEntity<List<Post>> getPosts(){
		Post[] allPosts = restTemplate.getForObject(url, Post[].class);
		List<Post> postList = Arrays.asList(allPosts);
		return new ResponseEntity<List<Post>>(postList, HttpStatus.OK);
	}
	
	public Post getPostById(Integer id) {
		String URL = getUrl(id);
		return restTemplate.getForObject(URL, Post.class);
	}
	
	public ResponseEntity<Post> addPost(Post p){
		Post newPost = new Post();
		newPost.setId(p.getId());
		newPost.setTitle(p.getTitle());
		newPost.setBody(p.getBody());
		newPost.setUserId(p.getUserId());
		ResponseEntity<Post> resp = restTemplate.postForEntity(url, newPost, Post.class);
		return resp;
	}
	
	public ResponseEntity<String> patchPost(Integer id, String patch){
		String val = getUrl(id);
		Post p = getPostById(id);
		p.setTitle(patch);
		Post resp = restTemplate.patchForObject(val, p, Post.class);
		if(resp == null) {
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	public ResponseEntity<String> updatePost(Integer id){
		String title = "foo";
		String body = "bar";
		String URL = getUrl(id);
		Post p = getPostById(id);
		p.setTitle(title);
		p.setBody(body);
		restTemplate.put(URL, p);
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	public ResponseEntity<String> deletePost(Integer id){
		String builtUrl = getUrl(id);
		restTemplate.delete(builtUrl);
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	public String getUrl(Integer id) {
		return url+id.toString();
	}
	
}
