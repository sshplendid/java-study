package me.shawn.web.ping;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/ping")
public class PingController {
    @GetMapping
    public String ping() {
        String hostname = "Unknown";
        String hostAddress = "UnknownIP";
        try {
            hostname = InetAddress.getLocalHost().getHostName();
            hostAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.error(e.getMessage());
        }
        return String.format("pong... from %s, %s at %s", hostname, hostAddress, LocalDateTime.now());
    }
}
