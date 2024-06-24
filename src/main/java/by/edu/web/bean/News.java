package by.edu.web.bean;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

public class News implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private int id;
    private String title;
    private String text;
    private LocalDate postDate;
    private String imagePath;
    private String category;
    private int userId;

    public News() {}



    public News(String title, String text, String imagePath, int userId, String category) {
        this.title = title;
        this.text = text;
        this.postDate = LocalDate.now();
        this.imagePath = imagePath;
        this.userId = userId;
        this.category=category;

    }

    public News(int id, String title, String text, LocalDate postDate, String imagePath, int userId) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.postDate = postDate;
        this.imagePath = imagePath;
        this.userId = userId;
    }


    public News(String title, String text, String imagePath, int id) {
        this.title = title;
        this.text = text;
        this.imagePath = imagePath;
        this.postDate = LocalDate.now();
        this.id=id;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDate getPostDate() {
        return postDate;
    }

    public void setPostDate(LocalDate postDate) {
        this.postDate = postDate;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        News news = (News) o;

        if (id != news.id) return false;
        if (userId != news.userId) return false;
        if (!title.equals(news.title)) return false;
        if (!text.equals(news.text)) return false;
        if (!postDate.equals(news.postDate)) return false;
        if (!imagePath.equals(news.imagePath)) return false;
        return category.equals(news.category);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + title.hashCode();
        result = 31 * result + text.hashCode();
        result = 31 * result + postDate.hashCode();
        result = 31 * result + imagePath.hashCode();
        result = 31 * result + category.hashCode();
        result = 31 * result + userId;
        return result;
    }

    @Override
    public String toString() {
        return "News{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", postDate='" + postDate + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", category='" + category + '\'' +
                ", userId=" + userId +
                '}';
    }
}