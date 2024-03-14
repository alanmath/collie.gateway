package insper.collie.gateway;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GatewayResource {

    @GetMapping("/hello")
    public String hello() {
        return "Hello from Gateway!";
    }

}
