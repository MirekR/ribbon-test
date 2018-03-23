package cz.mirek.template;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.client.DefaultLoadBalancerRetryHandler;
import com.netflix.client.RetryHandler;
import com.netflix.loadbalancer.*;
import com.netflix.loadbalancer.reactive.LoadBalancerCommand;
import rx.Observable;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class RibbonTemplate {
    private ObjectMapper mapper = new ObjectMapper();
    private RetryHandler retryHandler;
    private ILoadBalancer loadBalancer;

    public RibbonTemplate(List<Server> serverList) {
        // retry handler that does not retry on same server, but on a different server
        retryHandler =  new DefaultLoadBalancerRetryHandler(0, serverList.size(), true);

        loadBalancer = LoadBalancerBuilder.newBuilder()
                .withPing(getPingRule())
                .withRule(new WeightedResponseTimeRule())
                .buildFixedServerListLoadBalancer(serverList);
    }

    public <T> T call(String path, Class<T> clazz) throws Exception {
        return LoadBalancerCommand.<T>builder()
                .withLoadBalancer(loadBalancer)
                .withRetryHandler(retryHandler)
                .build()
                .submit(server -> {
                    URL url;
                    try {
                        url = new URL("http://" + server.getHost() + ":" + server.getPort() + path);
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                        T result = mapper.readValue(new InputStreamReader(conn.getInputStream()), clazz);
                        return Observable.just(result);
                    } catch (Exception e) {
                        return Observable.error(e);
                    }
                }).toBlocking().first();
    }

    private IPing getPingRule() {
        return server -> {
            URL url;
            try {
                url = new URL("http://" + server.getHost() + ":" + server.getPort());
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                return conn.getResponseCode() == 200;
            } catch (Exception e) {
                return false;
            }
        };
    }

    public LoadBalancerStats getLoadBalancerStats() {
        return ((BaseLoadBalancer) loadBalancer).getLoadBalancerStats();
    }
}
