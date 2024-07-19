package io.github.AlaraGuzel.NewspaperBackendApplication.controller;

import io.github.AlaraGuzel.NewspaperBackendApplication.Controller.NewsController;
import io.github.AlaraGuzel.NewspaperBackendApplication.Model.News;
import io.github.AlaraGuzel.NewspaperBackendApplication.Repository.NewsRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.reset;
@WebMvcTest(NewsController.class)
public class NewsControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private NewsRepository newsRepository;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @AfterEach
    public void resetMocks() {
        reset(newsRepository);
    }
    @Test
    public void NewsController_SaveNews() throws Exception {
        News news= News.builder().id(1)
                .title("Test Title")
                .content("Test Content")
                .author("Test Author")
                .date(LocalDate.of(2024,7,17))
                .image(new byte[0]).build();
        when(newsRepository.save(any(News.class))).thenReturn(news);

        mockMvc.perform(post("/news")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\": \"Test Title\", \"content\": \"Test Content\",\"author\": \"Test Author\",\"date\": \"2024-07-17\",\"image\": [] }"))
                .andExpect(status().isOk())
                .andExpect(content().string("News added successfully"));
        verify(newsRepository, times(1)).save(any(News.class));
    }
    @Test
    public void NewsController_GetNews() throws Exception {
        News news1= News.builder().id(1)
                .title("Test Title")
                .content("Test Content")
                .author("Test Author")
                .date(LocalDate.of(2024,7,17))
                .image(new byte[0]).build();
        News news2= News.builder().id(2)
                .title("Test Title 2")
                .content("Test Content 2")
                .author("Test Author 2")
                .date(LocalDate.of(2024,7,17))
                .image(new byte[0]).build();
        List<News> newsList=Arrays.asList(news1,news2);

        when(newsRepository.findAll()).thenReturn(newsList);

        mockMvc.perform(get("/news")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(1))
        .andExpect(jsonPath("$[1].id").value(2))
        .andExpect(jsonPath("$[0].title").value("Test Title"))
        .andExpect(jsonPath("$[1].title").value("Test Title 2"))
        .andExpect(jsonPath("$[0].content").value("Test Content"))
        .andExpect(jsonPath("$[1].content").value("Test Content 2"))
        .andExpect(jsonPath("$[0].author").value("Test Author"))
        .andExpect(jsonPath("$[1].author").value("Test Author 2"))
        .andExpect(jsonPath("$[0].date").value("2024-07-17"))
        .andExpect(jsonPath("$[1].date").value("2024-07-17"))
        .andExpect(jsonPath("$[0].image").value(""))
        .andExpect(jsonPath("$[1].image").value(""));
    }
    @Test
    public void NewsController_DeleteNews() throws Exception {
        doNothing().when(newsRepository).deleteById(anyInt());
         mockMvc.perform(delete("/news/1")
                 .contentType(MediaType.APPLICATION_JSON))
                 .andExpect(status().isOk());
         verify(newsRepository, times(1)).deleteById(anyInt());
    }
    @Test
    public void NewsController_GetNewsByPeriod() throws Exception {
        News news1= News.builder().id(1)
                .title("Test Title")
                .content("Test Content")
                .author("Test Author")
                .date(LocalDate.of(2019,7,17))
                .image(new byte[0]).build();
        News news2= News.builder().id(2)
                .title("Test Title 2")
                .content("Test Content 2")
                .author("Test Author 2")
                .date(LocalDate.of(2024,7,17))
                .image(new byte[0]).build();
        List<News> newsList=Arrays.asList(news1,news2);

        when(newsRepository.findByDateBetween(any(LocalDate.class), any(LocalDate.class))).thenReturn(newsList);

        mockMvc.perform(get("/news/2024-07-14,2024-07-17")//news1 is published between these dates.
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(1))
        .andExpect(jsonPath("$[1].id").value(2))
        .andExpect(jsonPath("$[0].title").value("Test Title"))
        .andExpect(jsonPath("$[0].content").value("Test Content"))
        .andExpect(jsonPath("$[0].author").value("Test Author"))
        .andExpect(jsonPath("$[0].date").value("2019-07-17"))
        .andExpect(jsonPath("$[0].image").value(""));
    }
}
