package me.shawn.mono.biz.service;

import me.shawn.mono.biz.model.Point;
import me.shawn.mono.biz.repository.PointRedisRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PointServiceTests {

    @Mock
    private PointRedisRepository pointRedisRepository;
    private PointService pointService;
    private Point availablePoint;
    private Point defaultPoint;

    @Before
    public void setUp() {
        this.pointService = new PointService(pointRedisRepository);
        availablePoint = Point.builder()
                .id("shawn")
                .amount(100L)
                .refreshTime(LocalDateTime.of(2019, 5, 8, 12, 30, 59))
                .build();
        defaultPoint = Point.builder()
                .id("shawn")
                .amount(0L)
                .refreshTime(LocalDateTime.now())
                .build();
    }

    @Test
    public void 포인트조회테스트_데이터존재() {
        // GIVEN: availablePoint, 기존 데이터 존재

        // WHEN
        when(pointRedisRepository.findById("shawn")).thenReturn(Optional.ofNullable(availablePoint));
        Point savedPoint = pointService.getPointsById("shawn");

        // THEN
        assertThat(savedPoint.getAmount()).isEqualTo(100L);
    }

    @Test
    public void 포인트조회테스트_데이터없음() {
        // GIVEN: Nothing
        // WHEN
        when(pointRedisRepository.findById(any())).thenReturn(Optional.empty());
        when(pointRedisRepository.save(any())).thenReturn(defaultPoint);
        Point savedPoint = pointService.getPointsById("shawn");

        // THEN
        assertThat(savedPoint.getAmount()).isEqualTo(0L);
        assertThat(savedPoint.getId()).isEqualTo("shawn");
        assertThat(savedPoint.getRefreshTime()).isBefore(LocalDateTime.now());
    }

    @Test
    public void 포인트적립_이력없음() {
        // GIVEN
        String id = "shawn";
        Long amount = 123L;
        LocalDateTime refreshTime = LocalDateTime.of(2019, 05, 8, 13, 20, 30);
        Point savingPoint = Point.builder()
                .id(id)
                .amount(amount)
                .refreshTime(refreshTime)
                .build();

        // WHEN
        when(pointRedisRepository.findById(id)).thenReturn(Optional.empty());
        when(pointRedisRepository.save(any())).thenReturn(savingPoint);
        Point savedPoint = pointService.saveUpPoints(id, amount);

        // THEN
        assertThat(savedPoint.getId()).isEqualTo(id);
        assertThat(savedPoint.getAmount()).isEqualTo(amount);
        assertThat(savedPoint.getRefreshTime()).isEqualTo(refreshTime);
    }


}