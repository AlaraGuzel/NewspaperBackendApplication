package io.github.AlaraGuzel.NewspaperBackendApplication.Repository;

import io.github.AlaraGuzel.NewspaperBackendApplication.Model.News;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface NewsRepository extends CouchbaseRepository <News,Integer>{
    List<News> findByDateBetween(LocalDate from, LocalDate to);
}
