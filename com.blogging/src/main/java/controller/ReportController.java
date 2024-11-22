import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import entity.Blog;
import entity.User;
import service.BlogService;
import util.JwtUtil;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private BlogService blogService;

    @GetMapping("/top-words")
    public List<String> getTopWords(@RequestHeader("Authorization") String token) {
        User user = JwtUtil.getUserFromToken(token);
        List<Blog> blogs = blogService.findByUser(user);
        Map<String, Integer> wordCount = new HashMap<>();
        for (Blog blog : blogs) {
            String[] words = blog.getBody().replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+"); // Clean words
            for (String word : words) {
                wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
            }
        }
        return wordCount.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .limit(5)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}
