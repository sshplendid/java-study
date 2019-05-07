package me.shawn.mono.biz.repository;

import me.shawn.mono.biz.model.Point;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PointRedisRepositoryTests {

    @Autowired
    private PointRedisRepository pointRedisRepository;

    @After
    public void tearDown() {
        pointRedisRepository.deleteAll();
    }

    @Test
    public void 기본_등록_조회기능() {
        // GIVEN
        String id = "shawn";
        LocalDateTime refreshTime = LocalDateTime.of(2019, 5, 7, 0, 0);
        Point point = Point.builder()
                .id(id)
                .amount(1000L)
                .refreshTime(refreshTime)
                .build();

        // WHEN
        pointRedisRepository.save(point);

        // THEN
        Point savedPoint = pointRedisRepository.findById(id).get();
        assertThat(savedPoint.getAmount()).isEqualTo(1000L);
        assertThat(savedPoint.getRefreshTime()).isEqualTo(refreshTime);
    }
}