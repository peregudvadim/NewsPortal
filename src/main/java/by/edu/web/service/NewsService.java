package by.edu.web.service;

import by.edu.web.bean.News;
import by.edu.web.bean.User;
import by.edu.web.dao.DaoException;

import java.util.List;

public interface NewsService {
    boolean addNews(News news) throws ServiceException;
    void find(int idNews) throws ServiceException;
    boolean update(News news) throws ServiceException;
    boolean delete(String idNews) throws ServiceException;
    boolean addLike(String idUser, String idNews) throws ServiceException;
    List<String> getLikedNews(String idUser) throws ServiceException;
    List<News> sortByCategory(String category) throws ServiceException;
    List<News> sortByDate(String local) throws ServiceException;
    List<News> list(String local) throws ServiceException;
    News findById(String local, String id) throws ServiceException;

    List<News> getNewsList(int page, int limit,String category)throws ServiceException;
    int getNoOfRecords(String category)throws ServiceException;

    List<String>getTitlesNews() throws ServiceException;

    News getNewsByTitle(String title) throws ServiceException;
    News getNewsByID(int id) throws ServiceException;

    boolean changeNews(News news) throws ServiceException;

    List<String> getCategoryList() throws ServiceException;




}

