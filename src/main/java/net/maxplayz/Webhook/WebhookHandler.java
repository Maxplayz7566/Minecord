package net.maxplayz.Webhook;

import net.maxplayz.Minecord;
import okhttp3.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class WebhookHandler {
    private static final Map<Integer, String> STATUS_MAP = new HashMap<>();

    static {
        // Mapping HTTP status codes to their messages
        STATUS_MAP.put(100, "Continue");
        STATUS_MAP.put(101, "Switching Protocols");
        STATUS_MAP.put(200, "OK");
        STATUS_MAP.put(201, "Created");
        STATUS_MAP.put(202, "Accepted");
        STATUS_MAP.put(203, "Non-Authoritative Information");
        STATUS_MAP.put(204, "No Content");
        STATUS_MAP.put(205, "Reset Content");
        STATUS_MAP.put(206, "Partial Content");

        STATUS_MAP.put(300, "Multiple Choices");
        STATUS_MAP.put(301, "Moved Permanently");
        STATUS_MAP.put(302, "Found");
        STATUS_MAP.put(303, "See Other");
        STATUS_MAP.put(304, "Not Modified");
        STATUS_MAP.put(305, "Use Proxy");
        STATUS_MAP.put(307, "Temporary Redirect");
        STATUS_MAP.put(308, "Permanent Redirect");

        STATUS_MAP.put(400, "Bad Request");
        STATUS_MAP.put(401, "Unauthorized");
        STATUS_MAP.put(402, "Payment Required");
        STATUS_MAP.put(403, "Forbidden");
        STATUS_MAP.put(404, "Not Found");
        STATUS_MAP.put(405, "Method Not Allowed");
        STATUS_MAP.put(406, "Not Acceptable");
        STATUS_MAP.put(407, "Proxy Authentication Required");
        STATUS_MAP.put(408, "Request Timeout");
        STATUS_MAP.put(409, "Conflict");
        STATUS_MAP.put(410, "Gone");
        STATUS_MAP.put(411, "Length Required");
        STATUS_MAP.put(412, "Precondition Failed");
        STATUS_MAP.put(413, "Payload Too Large");
        STATUS_MAP.put(414, "URI Too Long");
        STATUS_MAP.put(415, "Unsupported Media Type");
        STATUS_MAP.put(416, "Range Not Satisfiable");
        STATUS_MAP.put(417, "Expectation Failed");

        STATUS_MAP.put(426, "Upgrade Required");
        STATUS_MAP.put(428, "Precondition Required");
        STATUS_MAP.put(429, "Too Many Requests");
        STATUS_MAP.put(431, "Request Header Fields Too Large");

        STATUS_MAP.put(500, "Internal Server Error");
        STATUS_MAP.put(501, "Not Implemented");
        STATUS_MAP.put(502, "Bad Gateway");
        STATUS_MAP.put(503, "Service Unavailable");
        STATUS_MAP.put(504, "Gateway Timeout");
        STATUS_MAP.put(505, "HTTP Version Not Supported");
        STATUS_MAP.put(511, "Network Authentication Required");

        // Add more codes and messages as needed
    }

    public static String convertHttpStatusCodeToString(int code) {
        String statusMessage = STATUS_MAP.getOrDefault(code, "Unknown Status Code");
        String statusType = getStatusType(code);
        return statusMessage + " (" + statusType + ")";
    }

    private static String getStatusType(int code) {
        if (code >= 200 && code < 300) {
            return "Success";
        } else if (code >= 300 && code < 400) {
            return "Redirection";
        } else if (code >= 400 && code < 500) {
            return "Client Error";
        } else if (code >= 500) {
            return "Server Error";
        } else {
            return "Informational";
        }
    }

    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private static final OkHttpClient client = new OkHttpClient();

    public static void send_data(String json, String webhookUrl) {
        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .url(webhookUrl)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            Minecord.LOGGER.info("Webhook response code: " + response.code() + " " + convertHttpStatusCodeToString(response.code()));
        } catch (IOException e) {
            Minecord.LOGGER.info("Error while sending webhook ): message lost to history.");
        }
    }
}
