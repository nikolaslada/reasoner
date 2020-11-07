package cz.nikolaslada.reasoner.config;

import org.springframework.boot.actuate.info.Info.Builder;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ApiVersionConfig implements InfoContributor {

    @Override
    public void contribute(Builder builder) {
//        builder.withDetail("rest_api_version", ApiApi.API_VERSION);
    }

}
