package io.github.AlaraGuzel.NewspaperBackendApplication.Model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;

import java.time.LocalDate;


@Document
@Setter
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class News {
    @Id
    private Integer id;
    @Field
    private String title;
    @Field
    private String content;
    @Field
    private String author;
    @Field
    private LocalDate date;
    @Field
    private byte[] image;
}
