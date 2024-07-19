package io.github.AlaraGuzel.NewspaperBackendApplication.Configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;
@Configuration
public class NewsConfiguration extends AbstractCouchbaseConfiguration {
    @Override
    public String getConnectionString(){
         return "couchbase://127.0.0.1";
    }
    @Override
    public String getUserName(){
        return "User1";
    }
    @Override
    public String getPassword(){
        return "123456";
    }
    @Override
    public String getBucketName(){
        return "News";
    }
}
