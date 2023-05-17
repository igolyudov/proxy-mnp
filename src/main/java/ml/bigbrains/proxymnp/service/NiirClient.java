package ml.bigbrains.proxymnp.service;

import lombok.extern.slf4j.Slf4j;
import ml.bigbrains.proxymnp.model.MNPData;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Slf4j
public class NiirClient {

    private Pattern contentPattern = Pattern.compile("<div.*?>Номер:\\s?<b>(\\d+)<.*?>Оператор:\\s?.*?([А-ЯЁа-яё0-9\\s\",.-]+)<.*?>Регион:\\s?([А-ЯЁа-яё,.\\s-]+)<br></div>");

    @Value("${check.url}")
    private String niirCheckUrl;

    private final OkHttpClient client = new OkHttpClient.Builder().build();

    public MNPData getMNPData(String number) throws Exception
    {
        if(number==null || StringUtils.isEmpty(number))
            return null;
        RequestBody formBody = new FormBody.Builder()
                .add("num", number)
                .build();
        Request request = new Request.Builder()
                .url(niirCheckUrl)
                .post(formBody)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                log.error("Unexpected code {}",response);
                return null;
            };
            String content = response.body().string();

            Matcher contentMatcher = contentPattern.matcher(content);
            if(!contentMatcher.find())
                return null;
            else
            {
                MNPData data = new MNPData();
                data.setNumber(contentMatcher.group(1));
                data.setOperator(contentMatcher.group(2));
                data.setRegion(contentMatcher.group(3));
                return data;
            }

        }
    }
}
