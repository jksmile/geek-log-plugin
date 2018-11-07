package com.geek.pf.log.loader;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import com.geek.pf.log.exception.LoadException;

/**
 * samples: <br>
 * classpath*:META-INF/log/*.log.xml <br>
 * classpath*:META-INF/log/*.message.xml
 *
 * @author jinkai.xu
 */
public class ResourceMessageLoader extends XmlMessageLoader {

    public ResourceMessageLoader(String type, String... configLocations) {

        super(type);
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

        for (String location : configLocations) {
            try {
                Resource[] resources = resolver.getResources(location);

                for (Resource resource : resources) {
                    parseResource(resource.getInputStream());
                }
            } catch (Exception e) {
                throw new LoadException(location);
            }
        }
    }
}
