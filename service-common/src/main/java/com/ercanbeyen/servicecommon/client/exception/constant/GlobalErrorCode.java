package com.ercanbeyen.servicecommon.client.exception.constant;

public class GlobalErrorCode {
    public static final String AUTH_CONFLICT_ERROR = "AUTH-SERVICE-1004";
    public static final String AUTH_EXPECTATION_FAILED_ERROR = "AUTH-SERVICE-1005";

    public static final String EXAM_GENERIC_ERROR = "EXAM-SERVICE-1000";
    public static final String EXAM_BAD_REQUEST_ERROR = "EXAM-SERVICE-1001";
    public static final String EXAM_NOT_FOUND_ERROR = "EXAM-SERVICE-1002";
    public static final String EXAM_FORBIDDEN_ERROR = "EXAM-SERVICE-1003";

    public static final String CANDIDATE_GENERIC_ERROR = "CANDIDATE-SERVICE-1000";
    public static final String CANDIDATE_BAD_REQUEST_ERROR = "CANDIDATE-SERVICE-1001";
    public static final String CANDIDATE_NOT_FOUND_ERROR = "CANDIDATE-SERVICE-1002";
    public static final String CANDIDATE_FORBIDDEN_ERROR = "CANDIDATE-SERVICE-1003";

    public static final String SCHOOL_GENERIC_ERROR = "SCHOOL-SERVICE-1000";
    public static final String SCHOOL_BAD_REQUEST_ERROR = "SCHOOL-SERVICE-1001";
    public static final String SCHOOL_NOT_FOUND_ERROR = "SCHOOL-SERVICE-1002";
    public static final String SCHOOL_CONFLICT_ERROR = "SCHOOL-SERVICE-1004";

    public static final String NOTIFICATION_GENERIC_ERROR = "NOTIFICATION-SERVICE-1000";
    public static final String NOTIFICATION_BAD_REQUEST_ERROR = "NOTIFICATION-SERVICE-1001";
    public static final String NOTIFICATION_NOT_FOUND_ERROR = "NOTIFICATION-SERVICE-1002";
    public static final String NOTIFICATION_FORBIDDEN_ERROR = "NOTIFICATION-SERVICE-1003";

    private GlobalErrorCode() {}
}
