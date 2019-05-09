package me.shawn.mono.biz.service;

import me.shawn.mono.biz.model.Operation;
import me.shawn.mono.biz.model.Point;
import me.shawn.mono.biz.repository.PointRedisRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PointService {
    private PointRedisRepository pointRepository;

    public PointService(PointRedisRepository pointRepository) {
        this.pointRepository = pointRepository;
    }

    public Point getPointsById(String userId) {
        Optional<Point> current = pointRepository.findById(userId);
        return current.orElseGet(() -> savePointsById(userId, 0L));
    }

    public Point savePointsById(String userId, Long pointAmount) {
        Point point = Point.builder()
                .id(userId)
                .amount(pointAmount)
                .refreshTime(LocalDateTime.now())
                .build();
        return pointRepository.save(point);
    }

    public Point saveUpPoints(String userId, Long pointAmount) {
        /*
        Optional<Point> currentPoint = pointRepository.findById(userId);
        if(currentPoint.isPresent()) {
            pointAmount += currentPoint.get().getAmount();
        }

        Point savingPoint = Point.builder()
                .id(userId)
                .amount(pointAmount)
                .refreshTime(LocalDateTime.now())
                .build();

        return pointRepository.save(savingPoint);
         */
        return this.manipulatePoints(userId, pointAmount, Long::sum);
    }

    public Point usePoints(String userId, Long pointAmount) throws Exception {
        /*
        Optional<Point> currentPoint = pointRepository.findById(userId);
        if(currentPoint.isPresent()) {
            pointAmount += currentPoint.get().getAmount();
        }

        Point savingPoint = Point.builder()
                .id(userId)
                .amount(pointAmount)
                .refreshTime(LocalDateTime.now())
                .build();

        return pointRepository.save(savingPoint);
         */
        if(!hasEnoughPoint(userId, pointAmount)) {
            throw new Exception(String.format("포인트가 부족합니다."));
        }
        return this.manipulatePoints(userId, pointAmount, (a, b) -> a - b);
    }

    private Boolean hasEnoughPoint(String userId, Long pointAmount) {
        Point current = this.getPointsById(userId);
        if(current.getAmount() >= pointAmount) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    private Point manipulatePoints(String userId, Long pointAmount, Operation<Long> operation) {
        Optional<Point> currentPoint = pointRepository.findById(userId);
        Long currentPointAmount = 0L;
        if(currentPoint.isPresent()) {
            currentPointAmount = currentPoint.get().getAmount();
        }

        Long manipulatedPointAmount= operation.operate(currentPointAmount, pointAmount);

        Point manipulatedPoint = Point.builder()
                .id(userId)
                .amount(manipulatedPointAmount)
                .refreshTime(LocalDateTime.now())
                .build();

        return pointRepository.save(manipulatedPoint);
    }

}

