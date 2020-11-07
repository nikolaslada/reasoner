package cz.nikolaslada.reasoner.config;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;


@Configuration
public class HttpRestConfig {

    @Value("${httpclient.connectionTimeout:30}")
    private int clientConnectionTimeout;

    @Value("${httpclient.connectionCount:25}")
    private int clientConnectionCount;

    @Value("${httpclient.connectTimeout:10}")
    private int clientConnectTimeout;

    @Bean
    RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        restTemplate.setRequestFactory(
            new BufferingClientHttpRequestFactory(
                new HttpComponentsClientHttpRequestFactory(
                    httpClient()
                )
            )
        );

        return restTemplate;
    }

    @Bean
    public CloseableHttpClient httpClient() {
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(clientConnectTimeout * 1000)
                .setConnectionRequestTimeout(clientConnectionTimeout * 1000)
                .setSocketTimeout(clientConnectionTimeout * 1000)
                .build();

        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(clientConnectionCount);
        connectionManager.setDefaultMaxPerRoute(clientConnectionCount);

        return HttpClients
                .custom()
                .setDefaultRequestConfig(requestConfig)
                .setConnectionManager(connectionManager)
                .build();
    }

}
