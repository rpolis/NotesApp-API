package com.udemy.core;

import java.util.*;

public class BoardsResponse {
    int code;
    List<Boards> boards;
    List<Boards> shared;
    List<Boards> notShared;

    public BoardsResponse() {
    }

    public BoardsResponse(int code, List<Boards> shared, List<Boards> notShared) {
        this.code = code;
        this.shared = shared;
        this.notShared = notShared;
    }

    public List<Boards> getShared() {
        return shared;
    }

    public void setShared(List<Boards> shared) {
        this.shared = shared;
    }

    public List<Boards> getNotShared() {
        return notShared;
    }

    public void setNotShared(List<Boards> notShared) {
        this.notShared = notShared;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<Boards> getBoards() {
        return boards;
    }

    public void setBoards(List<Boards> boards) {
        this.boards = boards;
    }
}
