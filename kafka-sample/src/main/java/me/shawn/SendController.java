package me.shawn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SendController {

    @Autowired
    private KafkaTemplate<Object, Object> template;

    @PostMapping("kf/send/{what}")
    public ResponseEntity<KfMessage> send(@PathVariable String what) {
        KfMessage body = new KfMessage(what);
        this.template.send("topic1", body);

        return ResponseEntity.ok(body);
    }
}
