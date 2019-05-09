package me.shawn.mono.biz.model;

public interface Operation<N> {
    public N operate(N a, N b);
}
