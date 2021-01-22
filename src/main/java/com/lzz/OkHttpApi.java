package com.lzz;

import okhttp3.*;

import java.io.IOException;
import java.util.Objects;

public class OkHttpApi {
    OkHttpClient client=new OkHttpClient();

   public String run(String url) throws IOException {

        Request request=new Request.Builder().url(url).build();
        try (Response response=client.newCall(request).execute()) {
           return Objects.requireNonNull(response.body()).string();
        }

    }
}
