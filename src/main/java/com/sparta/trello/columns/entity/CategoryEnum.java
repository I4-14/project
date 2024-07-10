package com.sparta.trello.columns.entity;

public enum CategoryEnum {
    BackLog(CategoryType.BackLog),
    InProgress(CategoryType.InProgress),
    Done(CategoryType.Done),
    Emergency(CategoryType.Emergency);

    private final String categoryType;
    CategoryEnum(String categoryType) {
        this.categoryType = categoryType;
    }

    public static class CategoryType {
        public static final String BackLog = "BackLog";
        public static final String InProgress = "InProgress";
        public static final String Done = "Done";
        public static final String Emergency = "Emergency";
    }
}
