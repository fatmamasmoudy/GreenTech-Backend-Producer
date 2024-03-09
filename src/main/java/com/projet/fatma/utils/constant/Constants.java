package com.projet.fatma.utils.constant;

public class Constants {
    public static final String VIDEO_RECORD = "video_record";
    public static final String VIDEO_RECORD_RETRIEVE = "video_record_retrieve";
    public static final String VIDEO_RECORD_PATH = "/video/**";
    public static final String VIDEO_RETRIEVE_RECORD_PATH = "/video/record";
    public static final String VIDEO_RETRIEVE_RECORD_PATH_REPLACEMENT = "/video/record$\\{segment}";

    public static final String RECORD_URI_ENV = "${env.record.uri}";
    public static final String EVENT_MANAGEMENT_PATH_ENDING = "/graphql/**";
    public static final String EVENT_MANAGEMENT_URI_ENV = "${env.eventManagement.uri}";
    public static final String EVENT_MANAGEMENT_ROUTE = "event_management_graphql_route";
    public static final String GRAPHQL_PATH = "/graphql";
    public static final String GRAPHIQL_PATH = "/graphql";
    public static final String EVENT_MANAGEMENT_VERIFY_REPLACEMENT = "/verify$\\{segment}";
    public static final String EVENT_MANAGEMENT_VERIFY_API = "/event/verify";








    public static final String CHAT_API = "/chat";
    public static final String CHAT_ID = "chat";
    public static final String CHAT_REPLACEMENT = "/chat$\\{segment}";
    public static final String WS_CHAT_URI_ENV = "${env.wsChatEndpoint.uri}";
    public static final String QUERY_PARAMS = "(?<segment>.*)";
    public static final String QUERY_PARAMS_REPLACEMENT = "$\\{segment}";
    public static final String GRAPHQL_DASHBOARD_ID = "graphql-dashboard";
    public static final String GRAPHQL_DASHBOARD = "/dashboard/graphql";
    public static final String ANALYTICS_PATH_ENDING = "/monitoring/**";
    public static final String ANALYTICS_MONITORING = "/monitoring";
    public static final String ANALYTICS_URI_ENV = "${env.analytics.uri}";
    public static final String ANALYTICS_URI_ROUTE = "analytics_route";
    public static final String SIGNALING_SERVER_PATH_ENDING = "/**";
    public static final String SIGNALING_SERVER_URI_ENV = "${env.signalingServer.uri}";
    public static final String SIGNALING_SERVER_URI_ROUTE = "signaling_route";
    public static final String SSE_QUIZ_ENDPOINT_PATH_ENDING = "/api/**";
    public static final String GRAPHQL_QUIZ_ENDPOINT_PATH_ENDING = "/quiz/graphql";
    public static final String GRAPHQL_QUIZ_ID = "quiz_graphql";
    public static final String GRAPHIQL_QUIZ_ID = "quiz_graphiql";
    public static final String GRAPHIQL_QUIZ_ENDPOINT_PATH_ENDING = "/quiz/graphiql";
    public static final String SSE_QUIZ_ENDPOINT_URI_ENV = "${env.sseQuizEndpoint.uri}";
    public static final String SSE_QUIZ_ENDPOINT_URI_ROUTE = "quiz_route";
    public static final String WS_NOTIFICATION_URI_ENV = "${env.wsNotificationEndpoint.uri}";
    public static final String WS_NOTIFICATION_SOCKET = "/notification/socket";
    public static final String WS_NOTIFICATION_SOCKET_REPLACEMENT = "/socket";
    public static final String WS_NOTIFICATION_ENDPOINT_ID = "notification_ws_route";
    public static final String HTTP_NOTIFICATION_URI_ENV = "${env.httpNotificationEndpoint.uri}";
    public static final String NOTIFICATION_GRAPHQL = "/notification/graphql";
    public static final String NOTIFICATION_ENDPOINT_ROUTE = "notification_graphql_route";
}