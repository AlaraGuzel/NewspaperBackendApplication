package io.github.AlaraGuzel.NewspaperBackendApplication.configuration;

import io.github.AlaraGuzel.NewspaperBackendApplication.Configuration.NewsConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.data.couchbase.core.CouchbaseTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class NewsConfigurationTest {
    @Autowired
    private ApplicationContext context;

    @Test
    public void CouchbaseConfigurationTest() {
        assertNotNull(context.getBean(CouchbaseTemplate.class));
        NewsConfiguration couchbaseConfiguration = context.getBean(NewsConfiguration.class);
        assertNotNull(couchbaseConfiguration);
        assertEquals("couchbase://127.0.0.1",couchbaseConfiguration.getConnectionString(),"Connection string is 'couchbase://127.0.0.1'");
        assertEquals("User1",couchbaseConfiguration.getUserName(),"Username is 'User1'");
        assertEquals("123456",couchbaseConfiguration.getPassword(),"Password is '123456'");
        assertEquals("News",couchbaseConfiguration.getBucketName(),"Bucket name is 'News'");
    }
}
