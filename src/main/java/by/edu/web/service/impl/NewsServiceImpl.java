package by.edu.web.service.impl;

import by.edu.web.bean.News;
import by.edu.web.dao.DaoException;
import by.edu.web.dao.DaoProvider;
import by.edu.web.dao.NewsDao;
import by.edu.web.service.NewsService;
import by.edu.web.service.ServiceException;

import java.util.List;

public class NewsServiceImpl implements NewsService {
    private final NewsDao newsDao = DaoProvider.getInstance().getNewsDao();



    @Override
    public boolean addNews(News news) throws ServiceException {
        try {
            return newsDao.addNews(news);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void find(int idNews) throws ServiceException {

    }

    @Override
    public boolean update(News news) throws ServiceException {
        return false;
    }

    @Override
    public boolean delete(String idNews) throws ServiceException {
        return false;
    }

    @Override
    public boolean addLike(String idUser, String idNews) throws ServiceException {
        return false;
    }

    @Override
    public List<String> getLikedNews(String idUser) throws ServiceException {
        return null;
    }

    @Override
    public List<News> sortByCategory(String category) throws ServiceException {
        return null;
    }

    @Override
    public List<News> sortByDate(String local) throws ServiceException {
        return null;
    }

    @Override
    public List<News> list(String local) throws ServiceException {
        return null;
    }

    @Override
    public News findById(String local, String id) throws ServiceException {
        return null;
    }

    @Override
    public List<News> getNewsList(int page, int limit, String category) throws ServiceException {
        try {
            return newsDao.getNewsList(page,limit,category);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public int getNoOfRecords(String category) throws ServiceException{
        try {
            return newsDao.getNoOfRecords(category);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<String> getTitlesNews() throws ServiceException {
        try {
            return newsDao.getTitlesNews();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public News getNewsByTitle(String title) throws ServiceException {
        try {
            return newsDao.getNewsByTitle(title);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public News getNewsByID(int id) throws ServiceException {
        try {
            return newsDao.getNewsByID(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean changeNews(News news) throws ServiceException {
        try {
            return newsDao.changeNews(news);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<String> getCategoryList() throws ServiceException {
        try {
            return newsDao.getCategoryList();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }


}
