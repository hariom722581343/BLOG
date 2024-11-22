package repository;

import java.util.List;

import entity.Blog;

public interface BlogRepository extends JpaRepository<Blog, Long> {

    List<Blog> findByUser(User user);

    Blog findByIdAndUser(Long id, User user);
}




