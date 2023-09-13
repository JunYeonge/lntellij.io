package webtoon.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Value("${newWebtoonFilePath}")
    private String newWebtoonFilePath;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//                                          /webtoon/ 경로로 뭐가 들어오면
        registry.addResourceHandler("/webtoon/**")
//                                          c드라이브에서 webtoon 폴더 > webtoonList 폴더 안에서 니가 원하는 거 찾는다
                .addResourceLocations("file:///webtoon/webtoonList/");
    }

}