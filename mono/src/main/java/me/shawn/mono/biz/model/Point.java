package me.shawn.mono.biz.model;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;

@Getter
@RedisHash("point")
public class Point {
    @Id
    private String id;
    private Long amount;
    private LocalDateTime refreshTime;

    @Builder
    public Point(String id, Long amount, LocalDateTime refreshTime) {
        this.id = id;
        this.amount = amount;
        this.refreshTime = refreshTime;
    }

    public void refresh(long amount, LocalDateTime refreshTime) {
        if(refreshTime.isAfter(this.refreshTime)) {
            this.amount = amount;
            this.refreshTime = refreshTime;
        }
    }

    public void saveUp(long amount) {
        this.amount += amount;
    }

    public void cut(Long point) throws Exception {
        if(isAmountLesserThan(point)) {
            throw new Exception("포인트가 부족합니다. 현재 포인트: " + this.amount + ", 삭감포인트: " + point);
        }

        this.amount -= point;
    }

    private Boolean isAmountGreaterOrEqualThan(Long compare) {
        if(this.amount >= compare) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    private Boolean isAmountLesserThan(Long compare) {
        return !this.isAmountGreaterOrEqualThan(compare);
    }

    public boolean validate() {
        if(isAmountLesserThan(0L)) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    public boolean hasEnoughPoint(Long comparePoint) {
        return isAmountGreaterOrEqualThan(comparePoint);
    }
}
