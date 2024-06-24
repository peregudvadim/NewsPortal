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
import static by.edu.web.command.constant.ParamConst.*;
public class SQLNewsDao implements NewsDao {
    private static final ConnectionPool pool = ConnectionPool.getInstance();
    private static final String SQL_INSERT_NEWS = "INSERT INTO news (title, text, post_date, image_path, user_id, categories_news_id) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SQL_SELECT_CATEGORY_ID = "SELECT * FROM categories_news WHERE title = ?";
    private static final String SQL_SELECT_CATEGORY_TITLE = "SELECT * FROM categories_news WHERE id_categories_news = ?";
    private static final String SQL_SELECT_NEWS_BY_ID = "SELECT * FROM news WHERE news_id = ?";
    private static final String SQL_COUNT_ALL_NEWS = "SELECT COUNT(*) FROM news";
    private static final String SQL_COUNT_NEWS_BY_CATEGORY = "SELECT COUNT(*) FROM news WHERE categories_news_id = ?";
    private static final String SQL_ALL_NEWS = "SELECT * FROM news ORDER BY post_date DESC LIMIT ? OFFSET ?";
    private static final String SQL_NEWS_BY_CATEGORY = "SELECT * FROM news WHERE categories_news_id = ? ORDER BY post_date DESC LIMIT ? OFFSET ?";

    private static final String SQL_SELECT_CATEGORY_TITLES = "SELECT * FROM categories_news";
    private static final String SQL_SELECT_TITLES_NEWS = "SELECT * FROM news ORDER BY title";
    private static final String SQL_SELECT_NEWS_BY_TITLE = "SELECT * FROM news WHERE title = ?";
    private static final String SQL_UPDATE_NEWS_BY_ID = "UPDATE news SET title = ?, text = ?, image_path = ?, post_date = ? WHERE news_id = ?";


    @Override
    public boolean addNews(News news) throws DaoException {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = pool.takeConection();
            ps = con.prepareStatement(SQL_INSERT_NEWS);
            ps.setString(1, news.getTitle());
            ps.setString(2, news.getText());
            ps.setDate(3, Date.valueOf(news.getPostDate()));
            ps.setString(4, news.getImagePath());
            ps.setInt(5, news.getUserId());
            ps.setInt(6, getCategoryId(news.getCategory()));
            return ps.executeUpdate() > 0;


        } catch (ConnectionPoolException | SQLException e) {
            throw new DaoException("Error to add news!", e);

        } finally {

            pool.closeConnection(ps, con);
        }

    }

    @Override
    public int getCategoryId(String category) throws DaoException {
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        int categoryId = 0;
        try {
            con = pool.takeConection();
            ps = con.prepareStatement(SQL_SELECT_CATEGORY_ID);
            ps.setString(1, category);

            rs = ps.executeQuery();
            if (rs.next()) {
                categoryId = rs.getInt("id_categories_news");
            }
            return categoryId;
        } catch (ConnectionPoolException | SQLException e) {
            throw new DaoException("Error to get category ID", e);

        } finally {
            pool.closeConnection(ps, con, rs);
        }

    }


    @Override
    public String getCategoryTitle(int categoryId) throws DaoException {
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        String categoryTitle = null;
        try {
            con = pool.takeConection();
            ps = con.prepareStatement(SQL_SELECT_CATEGORY_TITLE);
            ps.setInt(1, categoryId);

            rs = ps.executeQuery();
            if (rs.next()) {
                categoryTitle = rs.getString("id_categories_news");
            }
            return categoryTitle;
        } catch (ConnectionPoolException | SQLException e) {
            throw new DaoException("Error to get category ID", e);

        } finally {
            pool.closeConnection(ps, con, rs);
        }
    }


    public List<News> getNewsList(int page, int limit, String category) throws DaoException {
        List<News> list = new ArrayList<>();
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
            try {
                con = pool.takeConection();
                if (category != null&&!category.equals(ALL_CATEGORIES)) {
                    int categoryID = getCategoryId(category);
                    ps = con.prepareStatement(SQL_NEWS_BY_CATEGORY);
                    ps.setInt(1, categoryID);
                    ps.setInt(2, limit);
                    ps.setInt(3, page);
                }else {
                    ps = con.prepareStatement(SQL_ALL_NEWS);
                    ps.setInt(1, limit);
                    ps.setInt(2, page);
                }
                rs = ps.executeQuery();

                while (rs.next()) {
                    News news = new News();
                    news.setId(rs.getInt("news_id"));
                    news.setTitle(rs.getString("title"));
                    news.setText(rs.getString("text"));
                    news.setPostDate(rs.getDate("post_date").toLocalDate());
                    news.setImagePath(rs.getString("image_path"));
                    news.setUserId(rs.getInt("user_id"));
                    list.add(news);
                }
                    return list;
            } catch (ConnectionPoolException | SQLException e) {
                throw new DaoException("Error to get news", e);
            } finally {
                pool.closeConnection(ps, con, rs);
            }
    }


    public int getNoOfRecords(String category) throws DaoException {
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        int noOfRecords = 0;
        try {
            con = pool.takeConection();
            if(category!=null&&!category.equals(ALL_CATEGORIES)){
                int categoryID=getCategoryId(category);
                ps = con.prepareStatement(SQL_COUNT_NEWS_BY_CATEGORY);
                ps.setInt(1,categoryID);
            }else {ps = con.prepareStatement(SQL_COUNT_ALL_NEWS);}
            rs = ps.executeQuery();
            if (rs.next()) {
                noOfRecords = rs.getInt(1);

            }
            return noOfRecords;
        } catch (ConnectionPoolException | SQLException e) {
            throw new DaoException("Error to get Count news", e);
        } finally {
            pool.closeConnection(ps, con, rs);

        }

    }

    @Override
    public List<String> getTitlesNews() throws DaoException {
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        List<String> newsTitlesList = new ArrayList<>();
        try {
            con = pool.takeConection();
            ps = con.prepareStatement(SQL_SELECT_TITLES_NEWS);
            rs = ps.executeQuery();
            while (rs.next()) {

                newsTitlesList.add(rs.getString("title"));

            }
            return newsTitlesList;
        } catch (ConnectionPoolException | SQLException e) {
            throw new DaoException("Error to add titles", e);

        } finally {
            pool.closeConnection(ps, con, rs);
        }
    }

    @Override
    public News getNewsByTitle(String title) throws DaoException {
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        int newsId = 0;
        String newsTitle = null;
        String text = null;
        LocalDate postDate = null;
        String imagepath = null;
        int userId = 0;
        try {
            con = pool.takeConection();
            ps = con.prepareStatement(SQL_SELECT_NEWS_BY_TITLE);
            ps.setString(1, title);
            rs = ps.executeQuery();
            if (rs.next()) {
                newsId = rs.getInt("news_id");
                newsTitle = rs.getString("title");
                text = rs.getString("text");
                postDate = rs.getDate("post_date").toLocalDate();
                imagepath = rs.getString("image_path");
                userId = rs.getInt("user_id");
                return new News(newsId, newsTitle, text, postDate, imagepath, userId);
            }
            return null;
        } catch (ConnectionPoolException | SQLException e) {
            throw new DaoException("Error to get category ID", e);

        } finally {
            pool.closeConnection(ps, con, rs);
        }
    }

    @Override
    public News getNewsByID(int id) throws DaoException {
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        int newsId = 0;
        String newsTitle = null;
        String text = null;
        LocalDate postDate = null;
        String imagepath = null;
        int userId = 0;
        try {
            con = pool.takeConection();
            ps = con.prepareStatement(SQL_SELECT_NEWS_BY_ID);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                newsId = rs.getInt("news_id");
                newsTitle = rs.getString("title");
                text = rs.getString("text");
                postDate = rs.getDate("post_date").toLocalDate();
                imagepath = rs.getString("image_path");
                userId = rs.getInt("user_id");
                return new News(newsId, newsTitle, text, postDate, imagepath, userId);
            }
            return null;
        } catch (ConnectionPoolException | SQLException e) {
            throw new DaoException("Error to get category ID", e);

        } finally {
            pool.closeConnection(ps, con, rs);
        }
    }

    @Override
    public boolean changeNews(News news) throws DaoException {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = pool.takeConection();
            ps = con.prepareStatement(SQL_UPDATE_NEWS_BY_ID);
            ps.setString(1, news.getTitle());
            ps.setString(2, news.getText());
            ps.setString(3, news.getImagePath());
            ps.setDate(4, Date.valueOf(news.getPostDate()));
            ps.setInt(5, news.getId());

            return ps.executeUpdate() > 0;


        } catch (ConnectionPoolException | SQLException e) {
            throw new DaoException("Error to change news!", e);

        } finally {

            pool.closeConnection(ps, con);
        }

    }

    @Override
    public List<String> getCategoryList() throws DaoException {
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        List<String> categoryList = new ArrayList<>();
        try {
            con = pool.takeConection();
            ps = con.prepareStatement(SQL_SELECT_CATEGORY_TITLES);
            rs = ps.executeQuery();
            while (rs.next()) {

                categoryList.add(rs.getString("title"));

            }
            return categoryList;
        } catch (ConnectionPoolException | SQLException e) {
            throw new DaoException("Error to get titles", e);

        } finally {
            pool.closeConnection(ps, con, rs);
        }
    }
}
