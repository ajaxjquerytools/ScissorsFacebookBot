package com.wowapp.domain;

import com.restfb.DefaultJsonMapper;
import com.restfb.types.webhook.WebhookObject;
import org.junit.Test;

/**
 * Created by volodymyr on 23.08.17.
 */
public class JsonUtilTest {

    @Test
    public void shouldConvertObj() {
        String json = "{\"object\":\"page\",\"entry\":[{\"id\":\"1897699287114333\",\"time\":1504895271442,\"messaging\":[{\"sender\":{\"id\":\"1222789367825377\"},\"recipient\":{\"id\":\"1897699287114333\"},\"timestamp\":1504895271042,\"message\":{\"mid\":\"mid.$cAAa98ubPJAZklATIgleYr9_81PN7\",\"seq\":98037,\"text\":\"hi\"}}]}]}\n";


        DefaultJsonMapper jsonMapper  = new DefaultJsonMapper();
        WebhookObject whObj = jsonMapper.toJavaObject(json, WebhookObject.class);

        System.out.println(whObj);
    }


}
