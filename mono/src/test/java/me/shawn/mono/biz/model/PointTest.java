package me.shawn.mono.biz.model;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class PointTest {

    private Point point;

    @Before
    public void setUp() {
        point = Point.builder()
                .id("shawn")
                .amount(100L)
                .refreshTime(LocalDateTime.now())
                .build();
    }

    @Test
    public void validate_포인트검증_성공() {
        assertThat(point.validate()).isTrue();
    }

    @Test
    public void validate_포인트는_0이상이어야한다() {
        point = Point.builder()
                .amount(-100L)
                .build();

        assertThat(point.validate()).isFalse();
    }

    @Test
    public void refresh_refreshTime이최신이면_포인트값은변경된다() {
        // GIVEN
        LocalDateTime refreshTime = LocalDateTime.now().plusMinutes(3);
        Long refreshAmount = 123L;

        // WHEN
        point.refresh(refreshAmount, refreshTime);

        // THEN
        assertThat(point.getRefreshTime()).isEqualTo(refreshTime);
        assertThat(point.getAmount()).isEqualTo(refreshAmount);
    }

    @Test
    public void refresh_refreshTime이과거이면_포인트값은변경되지않는다() {
        // GIVEN
        LocalDateTime refreshTime = LocalDateTime.now().minusMinutes(1);
        Long refreshAmount = 123L;

        // WHEN
        point.refresh(refreshAmount, refreshTime);

        // THEN
        assertThat(point.getAmount()).isEqualTo(100L);
    }

    @Test
    public void saveUp_포인트를적립한다() {
        // GIVEN
        Long savingPoint = 100L;

        // WHEN
        point.saveUp(savingPoint);

        // THEN
        assertThat(point.getAmount()).isEqualTo(200L);
    }

    @Test
    public void cut_포인트가있으면_포인트를삭감한다() throws Exception {
        // GIVEN
        Long cuttingPoint = 10L;

        // WHEN
        point.cut(cuttingPoint);

        // THEN
        assertThat(point.getAmount()).isEqualTo(90L);
    }

    @Test(expected = Exception.class)
    public void cut_포인트가부족할때_삭감하면_오류가발생한다() throws Exception {
        // GIVEN
        Long cuttingPoint = 60L;

        // WHEN
        point.cut(cuttingPoint);
        point.cut(cuttingPoint);

        // THEN
        fail("삭감되면 안됨.");

    }

    @Test
    public void hasEnoughPoint_포인트가충분한지확인() {
        // GIVEN
        Long comparePoint = 123L;

        // WHEN & THEN
        assertThat(point.hasEnoughPoint(comparePoint)).isFalse();
    }
}
