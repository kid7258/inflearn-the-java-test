package me.kkkong.infleanthejavatest.domain;

import me.kkkong.infleanthejavatest.study.StudyStatus;

import java.time.LocalDateTime;

public class Study {
    private StudyStatus status = StudyStatus.DRAFT;
    private int limit;
    private String name;
    private Member owner;
    private LocalDateTime openedDateTime;

    public Study(int limit, String name) {
        this.limit = limit;
        this.name = name;
    }

    public Study(int limit) {
        if (limit < 0) {
            throw new IllegalArgumentException("limit은 0보다 커야한다.");
        }
        this.limit = limit;
    }

    public StudyStatus getStatus() {
        return this.status;
    }

    public int getLimit() {
        return limit;
    }

    public String getName() {
        return name;
    }

    public void setOwner(Member owner) {
        this.owner = owner;
    }

    public Member getOwner() {
        return owner;
    }

    public LocalDateTime getOpenedDateTime() {
        return openedDateTime;
    }

    public void setOpenedDateTime(LocalDateTime openedDateTime) {
        this.openedDateTime = openedDateTime;
    }

    public void open() {
        this.status = StudyStatus.OPENED;
        this.openedDateTime = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Study{" +
                "status=" + status +
                ", limit=" + limit +
                ", name='" + name + '\'' +
                '}';
    }
}
