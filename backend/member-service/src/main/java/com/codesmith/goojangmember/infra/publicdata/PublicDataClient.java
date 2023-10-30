package com.codesmith.goojangmember.infra.publicdata;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

@Component
@RequiredArgsConstructor
public class PublicDataClient {

    private final RestTemplate restTemplate;

    @Value("${publicdata.api.key}")
    private String serviceKey;

    public HashMap<String, Long> getRealTimeERBedInfo() throws IOException {
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B552657/ErmctInfoInqireService/getEmrrmRltmUsefulSckbdInfoInqire");
        urlBuilder.append("?" + "serviceKey=" + serviceKey);
        urlBuilder.append("&" + "pageNo=1");
        urlBuilder.append("&" + "numOfRows=500");

        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");

        System.out.println("Response code: " + conn.getResponseCode());

        BufferedReader rd;
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }

        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(sb.toString());

        JsonNode items = rootNode.path("response").path("body").path("items").path("item");

        HashMap<String, Long> resultMap = new HashMap<>();

        for (JsonNode item : items) {
            String hpid = item.path("hpid").asText();
            long hvec = item.path("hvec").asLong();
            resultMap.put(hpid, hvec);
        }

        return resultMap;
    }
}
