package by.edu.web.dao;

import by.edu.web.bean.News;

import java.util.List;

public interface NewsDao {

    boolean addNews(News news) throws DaoException;

    int getCategoryId(String category) throws DaoException;

    String getCategoryTitle(int CategoryId) throws DaoException;

    List<News> getNewsList(int page, int limit, String category)throws DaoException;

    int getNoOfRecords(String category) throws DaoException;

    List<String>getTitlesNews() throws DaoException;

    News getNewsByTitle(String title) throws DaoException;
    News getNewsByID(int id) throws DaoException;

    boolean changeNews(News news) throws DaoException;

    List<String> getCategoryList() throws DaoException;


}
