package urujdas.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import urujdas.dao.CommentDao;
import urujdas.dao.NewsDao;
import urujdas.model.comments.Comment;
import urujdas.model.news.News;
import urujdas.model.users.User;
import urujdas.service.CommentService;
import urujdas.service.UserService;
import urujdas.util.Validation;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {

    @Autowired
    private UserService userService;

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private NewsDao newsDao;

    @Override
    public List<Comment> getAll(Long newsId) {
        Validation.isGreaterThanZero(newsId);

        News news = newsDao.getById(newsId);

        return commentDao.getAll(news);
    }

    @Override
    public void create(Comment comment) {
        User currentUser = userService.getCurrentUser();
        News news = newsDao.getById(comment.getNewsId());

        Comment commentToCreate = Comment.builder()
                .withBody(comment.getBody())
                .withNewsId(news.getId())
                .withAuthor(currentUser)
                .build();

        commentDao.create(commentToCreate);
    }
}
