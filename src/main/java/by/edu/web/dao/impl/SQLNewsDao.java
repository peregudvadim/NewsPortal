package by.edu.web.dao.impl;

import by.edu.web.bean.News;
import by.edu.web.dao.DaoException;
import by.edu.web.dao.NewsDao;
import by.edu.web.dao.connectpool.ConnectionPool;
import by.edu.web.dao.connectpool.ConnectionPoolException;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static by.edu.web.command.constant.ParamConst.ALL_CATEGORIES;

public class SQLNewsDao implements NewsDao {
    private static final ConnectionPool pool = ConnectionPool.getInstance();

    private static final String SQL_INSERT_NEWS = "INSERT INTO news (title, text, post_date, image_path, user_id, categories_news_id) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SQL_SELECT_CATEGORY_ID = "SELECT id_categories_news FROM categories_news WHERE title = ?";
    private static final String SQL_SELECT_CATEGORY_TITLE = "SELECT title FROM categories_news WHERE id_categories_news = ?";
    private static final String SQL_SELECT_NEWS_BY_ID = "SELECT * FROM news WHERE news_id = ?";
    private static final String SQL_COUNT_ALL_NEWS = "SELECT COUNT(*) FROM news";
    private static final String SQL_COUNT_NEWS_BY_CATEGORY = "SELECT COUNT(*) FROM news WHERE categories_news_id = ?";
    private static final String SQL_ALL_NEWS = "SELECT * FROM news ORDER BY post_date DESC LIMIT ? OFFSET ?";
    private static final String SQL_NEWS_BY_CATEGORY = "SELECT * FROM news WHERE categories_news_id = ? ORDER BY post_date DESC LIMIT ? OFFSET ?";
    private static final String SQL_SELECT_CATEGORY_TITLES = "SELECT title FROM categories_news";
    private static final String SQL_SELECT_TITLES_NEWS = "SELECT title FROM news ORDER BY title";
    private static final String SQL_SELECT_NEWS_BY_TITLE = "SELECT * FROM news WHERE title = ?";
    private static final String SQL_UPDATE_NEWS_BY_ID = "UPDATE news SET title = ?, text = ?, image_path = ?, post_date = ? WHERE news_id = ?";

    @Override
    public boolean addNews(News news) throws DaoException {
        try (Connection con = pool.takeConection();
             PreparedStatement ps = con.prepareStatement(SQL_INSERT_NEWS)) {

            ps.setString(1, news.getTitle());
            ps.setString(2, news.getText());
            ps.setDate(3, Date.valueOf(news.getPostDate()));
            ps.setString(4, news.getImagePath());
            ps.setInt(5, news.getUserId());
            ps.setInt(6, getCategoryId(news.getCategory()));
            return ps.executeUpdate() > 0;

        } catch (ConnectionPoolException | SQLException e) {
            throw new DaoException("Error adding news", e);
        }
    }

    @Override
    public int getCategoryId(String category) throws DaoException {
        try (Connection con = pool.takeConection();
             PreparedStatement ps = con.prepareStatement(SQL_SELECT_CATEGORY_ID)) {

            ps.setString(1, category);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getInt("id_categories_news") : 0;
            }

        } catch (ConnectionPoolException | SQLException e) {
            throw new DaoException("Error fetching category ID", e);
        }
    }

    @Override
    public String getCategoryTitle(int categoryId) throws DaoException {
        try (Connection con = pool.takeConection();
             PreparedStatement ps = con.prepareStatement(SQL_SELECT_CATEGORY_TITLE)) {

            ps.setInt(1, categoryId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getString("title") : null;
            }

        } catch (ConnectionPoolException | SQLException e) {
            throw new DaoException("Error fetching category title", e);
        }
    }

    @Override
    public List<News> getNewsList(int page, int limit, String category) throws DaoException {
        List<News> list = new ArrayList<>();
        boolean filterByCategory = category != null && !category.equals(ALL_CATEGORIES);

        try (Connection con = pool.takeConection();
             PreparedStatement ps = con.prepareStatement(filterByCategory ? SQL_NEWS_BY_CATEGORY : SQL_ALL_NEWS)) {

            if (filterByCategory) {
                ps.setInt(1, getCategoryId(category));
                ps.setInt(2, limit);
                ps.setInt(3, page);
            } else {
                ps.setInt(1, limit);
                ps.setInt(2, page);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultToNews(rs));
                }
            }

            return list;

        } catch (ConnectionPoolException | SQLException e) {
            throw new DaoException("Error retrieving news list", e);
        }
    }

    @Override
    public int getNoOfRecords(String category) throws DaoException {
        boolean filterByCategory = category != null && !category.equals(ALL_CATEGORIES);
        try (Connection con = pool.takeConection();
             PreparedStatement ps = con.prepareStatement(filterByCategory ? SQL_COUNT_NEWS_BY_CATEGORY : SQL_COUNT_ALL_NEWS)) {

            if (filterByCategory) {
                ps.setInt(1, getCategoryId(category));
            }

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getInt(1) : 0;
            }

        } catch (ConnectionPoolException | SQLException e) {
            throw new DaoException("Error getting news count", e);
        }
    }

    @Override
    public List<String> getTitlesNews() throws DaoException {
        List<String> titles = new ArrayList<>();
        try (Connection con = pool.takeConection();
             PreparedStatement ps = con.prepareStatement(SQL_SELECT_TITLES_NEWS);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                titles.add(rs.getString("title"));
            }

            return titles;

        } catch (ConnectionPoolException | SQLException e) {
            throw new DaoException("Error retrieving news titles", e);
        }
    }

    @Override
    public News getNewsByTitle(String title) throws DaoException {
        try (Connection con = pool.takeConection();
             PreparedStatement ps = con.prepareStatement(SQL_SELECT_NEWS_BY_TITLE)) {

            ps.setString(1, title);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapResultToNews(rs) : null;
            }

        } catch (ConnectionPoolException | SQLException e) {
            throw new DaoException("Error retrieving news by title", e);
        }
    }

    @Override
    public News getNewsByID(int id) throws DaoException {
        try (Connection con = pool.takeConection();
             PreparedStatement ps = con.prepareStatement(SQL_SELECT_NEWS_BY_ID)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapResultToNews(rs) : null;
            }

        } catch (ConnectionPoolException | SQLException e) {
            throw new DaoException("Error retrieving news by ID", e);
        }
    }

    @Override
    public boolean changeNews(News news) throws DaoException {
        try (Connection con = pool.takeConection();
             PreparedStatement ps = con.prepareStatement(SQL_UPDATE_NEWS_BY_ID)) {

            ps.setString(1, news.getTitle());
            ps.setString(2, news.getText());
            ps.setString(3, news.getImagePath());
            ps.setDate(4, Date.valueOf(news.getPostDate()));
            ps.setInt(5, news.getId());

            return ps.executeUpdate() > 0;

        } catch (ConnectionPoolException | SQLException e) {
            throw new DaoException("Error updating news", e);
        }
    }

    @Override
    public List<String> getCategoryList() throws DaoException {
        List<String> categories = new ArrayList<>();
        try (Connection con = pool.takeConection();
             PreparedStatement ps = con.prepareStatement(SQL_SELECT_CATEGORY_TITLES);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                categories.add(rs.getString("title"));
            }

            return categories;

        } catch (ConnectionPoolException | SQLException e) {
            throw new DaoException("Error retrieving category list", e);
        }
    }

    private News mapResultToNews(ResultSet rs) throws SQLException {
        return new News(
                rs.getInt("news_id"),
                rs.getString("title"),
                rs.getString("text"),
                rs.getDate("post_date").toLocalDate(),
                rs.getString("image_path"),
                rs.getInt("user_id")
        );
    }
}
