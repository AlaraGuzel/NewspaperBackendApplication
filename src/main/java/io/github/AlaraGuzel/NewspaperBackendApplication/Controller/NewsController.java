package io.github.AlaraGuzel.NewspaperBackendApplication.Controller;

import io.github.AlaraGuzel.NewspaperBackendApplication.Model.News;
import io.github.AlaraGuzel.NewspaperBackendApplication.Repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
@RestController
@RequestMapping("/news")
public class NewsController {

    @Autowired
    private NewsRepository newsRepository;

    @PostMapping
    public String saveNews(@RequestBody News news){
        newsRepository.save(news);
        return "News added successfully";
    }
    @GetMapping
    public List<News> getAllNews() {
        return newsRepository.findAll();
    }
    @DeleteMapping("{NewsId}")
    public void deleteNews(@PathVariable("NewsId") Integer id){
        newsRepository.deleteById(id);
    }
    @GetMapping("{NewsStartDate},{NewsEndDate}")
    public List<News> getNewsByPeriod(@PathVariable("NewsStartDate") LocalDate start, @PathVariable("NewsEndDate")LocalDate end) {
        return newsRepository.findByDateBetween(start, end);
    }
}
