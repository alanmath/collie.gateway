package insper.collie.gateway.security;

import java.util.List;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
public class RouterValidator {

    @Value("${api.endpoints.open}")
    public List<String> openApiEndpoints;

    final public Predicate<ServerHttpRequest> isSecured =
        request -> openApiEndpoints
            .stream()
            .noneMatch(uri -> {
                String[] parts = uri.replaceAll("[^a-zA-Z0-9// ]", "").split(" ");
                if (parts.length != 2) return false;
                final String method = parts[0];
                final String path = parts[1];
                // System.out.println("Method : [" + method + "] Path: [" + path + "]");
                // System.out.println("Request: [" + request.getMethod().toString() + "] Path: [" + request.getURI().getPath() + "]");
                return (request.getMethod().toString().equalsIgnoreCase(method) || method.equalsIgnoreCase("ANY"))
                    && request.getURI().getPath().contains(path);
            });
    
}
