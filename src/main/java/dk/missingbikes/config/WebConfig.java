package dk.missingbikes.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Makes the uploaded bike photos reachable from the browser.
 *
 * Spring only publishes what is on the classpath, under src/main/resources/static.
 * The photos are written to a folder on disk instead, so without the mapping below
 * every image request would return 404.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${bike.photos.upload-dir}")
    private String photoDir;

    @Value("${bike.photos.url-prefix}")
    private String photoUrl;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        // Swaps the "/photos/" prefix for the upload directory and leaves the rest of
        // the path untouched, so "/photos/1/1.jpg" is served from "<upload dir>/1/1.jpg".
        // That remainder is exactly what bike_photos.path holds.
        // The trailing slash is required: without it the location is read as a file
        // rather than a directory, and nothing is found.
        registry.addResourceHandler(photoUrl + "/**").addResourceLocations("file:"+photoDir+"/");
    }
}
