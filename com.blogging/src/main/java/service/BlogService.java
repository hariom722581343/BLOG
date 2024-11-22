package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import entity.Blog;
import entity.User;
import repository.BlogRepository;

@Service
public class BlogService {

    @Autowired
    private BlogRepository blogRepository;

    public Blog save(Blog blog) {
        return blogRepository.save(blog);
    }

    public List<Blog> findByUser(User user) {
        return blogRepository.findByUser(user);
    }

    public Blog findByIdAndUser(Long id, User user) {
        return blogRepository.findByIdAndUser(id, user);
    }

    public void delete(Blog blog) {
        blogRepository.delete(blog);
    }
}


