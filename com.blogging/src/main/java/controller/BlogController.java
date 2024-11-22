package controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import entity.Blog;
import entity.User;
import exception.BlogNotFoundException;
import service.BlogService;
import util.JwtUtil;

@RestController
@RequestMapping("/api/blogs")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @PostMapping
    public Blog createBlog(@RequestBody BlogDto blogDto, @RequestHeader("Authorization") String token) {
        User user = JwtUtil.getUserFromToken(token);
        Blog blog = new Blog(blogDto.getName(), blogDto.getBody(), user);
        return blogService.save(blog);
    }

    @GetMapping
    public List<Blog> getAllBlogs(@RequestHeader("Authorization") String token) {
        User user = JwtUtil.getUserFromToken(token);
        return blogService.findByUser(user);
    }

    @PutMapping("/{id}")
    public Blog updateBlog(@PathVariable Long id, @RequestBody BlogDto blogDto, @RequestHeader("Authorization") String token) {
        User user = JwtUtil.getUserFromToken(token);
        Blog blog = blogService.findByIdAndUser(id, user);
        if (blog != null) {
            blog.setName(blogDto.getName());
            blog.setBody(blogDto.getBody());
            return blogService.save(blog);
        } else {
            throw new BlogNotFoundException("Blog not found");
        }
    }

    @DeleteMapping("/{id}")
    public void deleteBlog(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        User user = JwtUtil.getUserFromToken(token);
        Blog blog = blogService.findByIdAndUser(id, user);
        if (blog != null) {
            blogService.delete(blog);
        } else {
            throw new BlogNotFoundException("Blog not found");
        }
    }
}
