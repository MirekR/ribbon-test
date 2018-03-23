package cz.mirek;


import com.netflix.loadbalancer.Server;
import cz.mirek.domain.UserData;
import cz.mirek.template.RibbonTemplate;

import java.util.Arrays;
import java.util.List;

public class RibbonMain {


    public static void main(String [] args) throws Exception {
        Server server = new Server("https://jsonplaceholder.typicode.com/");
        Server uknownServer = new Server("https://xxxx.xxxx.com/");

        List<Server> serverList = Arrays.asList(uknownServer, uknownServer, uknownServer, uknownServer, uknownServer, server);
        RibbonTemplate template = new RibbonTemplate(serverList);

        UserData result = template.call("/posts/1", UserData.class);
        result = template.call("/posts/1", UserData.class);
        result = template.call("/posts/1", UserData.class);
        result = template.call("/posts/1", UserData.class);
        result = template.call("/posts/1", UserData.class);
        result = template.call("/posts/1", UserData.class);
        result = template.call("/posts/1", UserData.class);
        result = template.call("/posts/1", UserData.class);

        System.out.println(result);

        System.out.println(template.getLoadBalancerStats());
    }
}
