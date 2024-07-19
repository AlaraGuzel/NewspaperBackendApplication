package io.github.AlaraGuzel.NewspaperBackendApplication.repository;

import io.github.AlaraGuzel.NewspaperBackendApplication.Model.News;
import io.github.AlaraGuzel.NewspaperBackendApplication.Repository.NewsRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.couchbase.DataCouchbaseTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
@DataCouchbaseTest
public class NewsRepositoryTest {
    @Autowired
    private NewsRepository newsRepository;

    @Test
    public void NewsRepository_SaveAll_ReturnsSavedNews() {
        News news = News.builder().title("Test Title").
                content("Test Content").
                author("Test Author").id(1).date(LocalDate.of(2024,12,7)).image("base64encodedImageString".getBytes()).build();
        News savedNews = newsRepository.save(news);
        Assertions.assertThat(savedNews).isNotNull();
        Assertions.assertThat(savedNews.getId()).isGreaterThan(0);
    }
    @Test
    public void NewsRepository_GetMoreThanOne_ReturnsAllNews() {
        News news1 = News.builder().title("Test Title").
                content("Test Content").
                author("Test Author").id(1).date(LocalDate.of(2024,12,7)).image("base64encodedImageString".getBytes()).build();
        News news2 = News.builder().title("Test Title").
                content("Test Content").
                author("Test Author").id(2).date(LocalDate.of(2024,12,7)).image("base64encodedImageString".getBytes()).build();
        newsRepository.save(news1);
        newsRepository.save(news2);

        List<News> newsList= newsRepository.findAll();

        Assertions.assertThat(newsList).isNotNull();
        Assertions.assertThat(newsList.size()).isEqualTo(2);
    }
    @Test
    public void NewsRepository_FindById_ReturnsNews() {
        News news = News.builder().title("Test Title").
                content("Test Content").
                author("Test Author").id(1).date(LocalDate.of(2024,12,7)).image("base64encodedImageString".getBytes()).build();
        newsRepository.save(news);
        News FindNews= newsRepository.findById(news.getId()).get();
        Assertions.assertThat(FindNews).isNotNull();

    }
    @Test
    public void NewsRepository_findByDateBetween_ReturnNewsBetweenPeriod(){
        News news3 = News.builder().title("Test Title").
                content("Test Content").
                author("Test Author").id(1)
                .date(LocalDate.of(2020,12,7))
                .image("base64encodedImageString".getBytes()).build();
        News news4 = News.builder().title("Test Title").
                content("Test Content").
                author("Test Author").id(2).date(LocalDate.of(2024,12,7)).image("base64encodedImageString".getBytes()).build();
        newsRepository.save(news3);
        newsRepository.save(news4);

        List<News> findNews= newsRepository.findByDateBetween(LocalDate.of(2019,1,7),LocalDate.of(2021,12,7));

        Assertions.assertThat(findNews).isNotNull();
        Assertions.assertThat(findNews.size()).isEqualTo(1);

        News retrievedNews = findNews.get(0);
        Assertions.assertThat(retrievedNews).isNotNull();
        assertThat(retrievedNews.getId()).isEqualTo(1);
        assertThat(retrievedNews.getTitle()).isEqualTo("Test Title");
        assertThat(retrievedNews.getContent()).isEqualTo("Test Content");
        assertThat(retrievedNews.getAuthor()).isEqualTo("Test Author");
        assertThat(retrievedNews.getDate()).isEqualTo(LocalDate.of(2020,12,7));
        assertThat(retrievedNews.getImage()).isEqualTo("base64encodedImageString".getBytes());
    }
    @Test
    public void NewsRepository_Update_ReturnsUpdatedNews() {
        News news = News.builder().title("Test Title").
                content("Test Content").
                author("Test Author").id(1).date(LocalDate.of(2024,12,7)).image("base64encodedImageString".getBytes()).build();
        newsRepository.save(news);
        News SaveNews = newsRepository.findById(news.getId()).get();
        SaveNews.setTitle("Updated Title");
        SaveNews.setContent("Updated Content");
        SaveNews.setAuthor("Updated Author");
        SaveNews.setDate(LocalDate.of(2020,12,7));
        SaveNews.setImage("base64encodedImageStringUpdated".getBytes());

        News updatedNews = newsRepository.save(SaveNews);
        Assertions.assertThat(updatedNews.getTitle()).isNotNull();
        Assertions.assertThat(updatedNews.getContent()).isNotNull();
        Assertions.assertThat(updatedNews.getAuthor()).isNotNull();
        Assertions.assertThat(updatedNews.getDate()).isNotNull();
        Assertions.assertThat(updatedNews.getImage()).isNotNull();
    }
    @Test
    public void NewsRepository_Delete_Return_News_Empty() {
        News news = News.builder().title("Test Title").
                content("Test Content").
                author("Test Author").id(1).date(LocalDate.of(2024,12,7)).image("base64encodedImageString".getBytes()).build();
        newsRepository.save(news);
        newsRepository.deleteById(news.getId());
        Optional<News> ReturnNews= newsRepository.findById(news.getId());
        Assertions.assertThat(ReturnNews.isPresent()).isFalse();

    }
}
