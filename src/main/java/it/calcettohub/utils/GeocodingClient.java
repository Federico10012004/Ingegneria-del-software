package it.calcettohub.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class GeocodingClient {
    private final HttpClient http = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .connectTimeout(Duration.ofSeconds(10))
            .build();
    private final ObjectMapper mapper = new ObjectMapper();
    private final Map<String, double[]> geoCache = new ConcurrentHashMap<>();

    private final Object rateLock = new Object();
    private long lastCallMillis = 0;

    public Optional<double[]> geocode(String query) {
        if (query == null || query.isBlank()) return Optional.empty();

        double[] cached = geoCache.get(query);
        if (cached != null) return Optional.of(cached);

        Optional<double[]> res = doGeocode(query);

        res.ifPresent(latlon -> geoCache.put(query, latlon));
        return res;
    }

    private Optional<double[]> doGeocode(String q) {
        try {
            String url = "https://nominatim.openstreetmap.org/search?format=json&limit=1&q="
                    + URLEncoder.encode(q, StandardCharsets.UTF_8);

            HttpRequest req = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("User-Agent", "CalcettoHub/1.0 (university project)")
                    .header("Accept", "application/json")
                    // .header("Referer", "https://your-app.example") // opzionale
                    .GET()
                    .build();

            HttpResponse<String> resp = sendWithThrottle(req);

            if (resp.statusCode() == 429 || resp.statusCode() >= 500) {
                pause(1200);
                resp = sendWithThrottle(req);
            }

            if (resp.statusCode() != 200) {
                System.out.println("GEOCODE FAIL [" + q + "] status=" + resp.statusCode());
                return Optional.empty();
            }

            JsonNode root = mapper.readTree(resp.body());
            if (!root.isArray() || root.isEmpty()) return Optional.empty();

            JsonNode first = root.get(0);
            if (first.get("lat") == null || first.get("lon") == null) return Optional.empty();

            double lat = first.get("lat").asDouble();
            double lon = first.get("lon").asDouble();

            return Optional.of(new double[]{lat, lon});
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
            return Optional.empty();
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    private HttpResponse<String> sendWithThrottle(HttpRequest req) throws Exception {
        throttle();
        return http.send(req, HttpResponse.BodyHandlers.ofString());
    }

    private void throttle() throws InterruptedException {
        synchronized (rateLock) {
            while (true) {
                long now = System.currentTimeMillis();
                long waitMs = 1100 - (now - lastCallMillis);

                if (waitMs <= 0) {
                    lastCallMillis = now;
                    return;
                }

                rateLock.wait(waitMs);
            }
        }
    }

    private void pause(long millis) throws InterruptedException {
        synchronized (rateLock) {
            long end = System.currentTimeMillis() + millis;
            long remaining = millis;

            while (remaining > 0) {
                rateLock.wait(remaining);
                remaining = end - System.currentTimeMillis();
            }
        }
    }
}