package com.sparta.trello.boardworkspace.entity;

public enum InvitationEnum {
    PENDING(InvitationStatus.PENDING),  // 초대 대기 중
    ACCEPTED(InvitationStatus.ACCEPTED), // 초대 승락
    DECLINED(InvitationStatus.DECLINED)  ;// 초대 거절

    private final String invitationStatus;

    InvitationEnum(String invitationStatus) {
        this.invitationStatus = invitationStatus;
    }

    public static class InvitationStatus {
        public static final String PENDING = "PENDING";
        public static final String ACCEPTED = "ACCEPTED";
        public static final String DECLINED = "DECLINED";
    }
}
