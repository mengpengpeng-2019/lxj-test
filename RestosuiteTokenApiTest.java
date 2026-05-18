import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class RestosuiteTokenApiTest {
    private static final String BASE_URL = env("RESTO_API_BASE_URL", "");
    private static final String APP_KEY = env("RESTO_APP_KEY", "");
    private static final String SECRET_KEY = env("RESTO_SECRET_KEY", "");
    private static final String REFRESH_TOKEN = env("RESTO_REFRESH_TOKEN", "");

    public static void main(String[] args) throws Exception {
        if (BASE_URL.isEmpty() || APP_KEY.isEmpty() || SECRET_KEY.isEmpty()) {
            System.out.println("请先设置环境变量：RESTO_API_BASE_URL、RESTO_APP_KEY、RESTO_SECRET_KEY");
            System.out.println("示例：");
            System.out.println("export RESTO_API_BASE_URL=https://你的接口域名");
            System.out.println("export RESTO_APP_KEY=你的appKey");
            System.out.println("export RESTO_SECRET_KEY=你的secretKey");
            return;
        }

        String tokenResponse = getAccessToken();
        System.out.println("获取 Access Token 返回：");
        System.out.println(tokenResponse);

        if (!REFRESH_TOKEN.isEmpty()) {
            String refreshResponse = refreshAccessToken(REFRESH_TOKEN);
            System.out.println("刷新 Access Token 返回：");
            System.out.println(refreshResponse);
        }
    }

    private static String getAccessToken() throws IOException, NoSuchAlgorithmException {
        String requestBody = "{"
                + "\"appKey\":\"" + escapeJson(APP_KEY) + "\","
                + "\"secretCode\":\"" + sha256(SECRET_KEY) + "\""
                + "}";
        return postJson("/oauth/getToken", requestBody);
    }

    private static String refreshAccessToken(String refreshToken) throws IOException {
        String requestBody = "{"
                + "\"refreshToken\":\"" + escapeJson(refreshToken) + "\""
                + "}";
        return postJson("/oauth/refreshToken", requestBody);
    }

    private static String postJson(String path, String requestBody) throws IOException {
        URL url = new URL(trimTrailingSlash(BASE_URL) + path);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setConnectTimeout(10000);
        connection.setReadTimeout(10000);
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
        connection.setRequestProperty("Accept", "application/json");

        byte[] bodyBytes = requestBody.getBytes(StandardCharsets.UTF_8);
        try (OutputStream outputStream = connection.getOutputStream()) {
            outputStream.write(bodyBytes);
        }

        int statusCode = connection.getResponseCode();
        InputStream responseStream = statusCode >= 200 && statusCode < 300
                ? connection.getInputStream()
                : connection.getErrorStream();
        String responseBody = readAll(responseStream);
        return "HTTP " + statusCode + System.lineSeparator() + responseBody;
    }

    private static String sha256(String value) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(value.getBytes(StandardCharsets.UTF_8));
        StringBuilder hex = new StringBuilder();
        for (byte b : hash) {
            hex.append(String.format("%02x", b));
        }
        return hex.toString();
    }

    private static String readAll(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            return "";
        }

        StringBuilder builder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append(System.lineSeparator());
            }
        }
        return builder.toString().trim();
    }

    private static String escapeJson(String value) {
        return value.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    private static String trimTrailingSlash(String value) {
        while (value.endsWith("/")) {
            value = value.substring(0, value.length() - 1);
        }
        return value;
    }

    private static String env(String name, String defaultValue) {
        String value = System.getenv(name);
        return value == null ? defaultValue : value.trim();
    }
}
