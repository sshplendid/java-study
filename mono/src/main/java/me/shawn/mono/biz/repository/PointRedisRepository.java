package me.shawn.mono.biz.repository;

import me.shawn.mono.biz.model.Point;
import org.springframework.data.repository.CrudRepository;

public interface PointRedisRepository extends CrudRepository<Point, String> {
}
