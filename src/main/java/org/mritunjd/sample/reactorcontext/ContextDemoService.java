package org.mritunjd.sample.reactorcontext;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

import java.util.function.Function;

@Service
public class ContextDemoService {
    public Mono<String> printContextData(String id) {
        return Mono
                .subscriberContext()
                .map(ContextDemoService::checkContext)
                .map(s -> "IPAddress for Id=> " + s);
    }

    private static String checkContext(Context context) {
        String ipAdresss = context.get("IP Address");
        String location = context.get("Location");
        System.out.println("Recived call from " + ipAdresss + " and Location " + location);
        return ipAdresss;
    }

//    for the latest spring
//    private Mono<String> printFromContext( contextView) {
//        String ipAdresss = contextView.get("IP Address");
//        String location = contextView.get("Location");
//        System.out.println("Recived call from " + ipAdresss + " and Location " + location);
//        return Mono.just(ipAdresss);
//    }
}
