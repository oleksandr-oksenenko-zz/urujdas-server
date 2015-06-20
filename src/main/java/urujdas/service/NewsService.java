package urujdas.service;

import urujdas.model.favourites.FavourResult;
import urujdas.model.likes.LikeResult;
import urujdas.model.news.News;

import java.util.List;

public interface NewsService {

    List<News> getLatestAll(int latestCount);

    List<News> getAllFromId(long id, int count);

    List<News> getLatestByUser(int count);

    List<News> getByUserFromId(Long id, int count);

    List<News> getLatestBySubscription(int count);

    List<News> getBySubscriptionFromId(Long id, int count);

    List<News> getLatestFavourites(int count);

    List<News> getFavouritesFromId(Long id, int count);

    void create(News news);

    FavourResult favour(Long newsId);

    void subscribe(Long userId);

    LikeResult like(Long newsId);
}
