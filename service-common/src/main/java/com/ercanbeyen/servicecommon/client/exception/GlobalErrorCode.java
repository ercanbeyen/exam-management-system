package com.ercanbeyen.servicecommon.client.exception;

public class GlobalErrorCode {
    public static final String EXAM_GENERIC_ERROR = "EXAM-SERVICE-1000";
    public static final String EXAM_NOT_FOUND_ERROR = "EXAM-SERVICE-1001";
    public static final String EXAM_EXTERIOR_SERVICE_ERROR = "EXAM-SERVICE-1002";

    public static final String CANDIDATE_GENERIC_ERROR = "CANDIDATE-SERVICE-1000";
    public static final String CANDIDATE_NOT_FOUND_ERROR = "CANDIDATE-SERVICE-1001";
    public static final String CANDIDATE_EXTERIOR_SERVICE_ERROR = "CANDIDATE-SERVICE-1002";

    public static final String SCHOOL_GENERIC_ERROR = "SCHOOL-SERVICE-1000";
    public static final String SCHOOL_NOT_FOUND_ERROR = "SCHOOL-SERVICE-1001";

    public static final String NOTIFICATION_GENERIC_ERROR = "NOTIFICATION-SERVICE-1000";
    public static final String NOTIFICATION_NOT_FOUND_ERROR = "NOTIFICATION-SERVICE-1001";

    private GlobalErrorCode() {}
}
