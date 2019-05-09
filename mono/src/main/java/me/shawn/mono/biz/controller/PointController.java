package me.shawn.mono.biz.controller;

import me.shawn.mono.biz.model.ApiResponse;
import me.shawn.mono.biz.model.Point;
import me.shawn.mono.biz.service.PointService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/points")
public class PointController {

    private PointService pointService;

    public PointController(PointService pointService) {
        this.pointService = pointService;
    }

    @GetMapping(value = "", produces = {"application/json", "application/xml"})
    public Point getPointsById(@RequestParam(required = true) String userId) {
        return this.pointService.getPointsById(userId);
    }

//    @PostMapping("")
//    public Point savePointsById(@RequestParam(required = true) String userId, Long point) {
//        return this.pointService.savePointsById(userId, point);
//    }

    @PostMapping("")
    public ApiResponse saveUpPoints(@RequestParam String userId, @RequestParam Long savePointAmount) {
        Point savedPoint = this.pointService.saveUpPoints(userId, savePointAmount);

        return ApiResponse.<Point>builder()
                .status(HttpStatus.OK)
                .message("포인트가 적립되었습니다.")
                .data(savedPoint)
                .build();
    }

    @PatchMapping("")
    public ApiResponse<Point> usePoints(@RequestParam String userId, @RequestParam Long usePointAmount) {
        try {
            Point currentPoint = this.pointService.usePoints(userId, usePointAmount);

            return ApiResponse.<Point>builder()
                    .status(HttpStatus.OK)
                    .message("포인트가 정상적으로 사용되었습니다.")
                    .data(currentPoint)
                    .build();
        } catch (Exception e) {
            return ApiResponse.<Point>builder()
                    .status(HttpStatus.IM_USED)
                    .message(e.getMessage())
                    .data(this.pointService.getPointsById(userId))
                    .build();
        }
    }

}
