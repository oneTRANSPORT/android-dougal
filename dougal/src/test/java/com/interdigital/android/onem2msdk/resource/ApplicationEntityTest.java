package com.interdigital.android.dougal.resource;

import org.junit.Test;

import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

import static org.junit.Assert.assertEquals;

public class ApplicationEntityTest {

    private static final String ID = "C-PAUL-TEST-10";
    private static final String NAME = "PAUL-TEST-APP-10";
    private static final String CSE_BASE = "cse-01.onetransport.uk.net";
    private static final int PORT = 443;
    private static final String CSE_NAME = "ONETCSE01";
    private static final String USER_NAME = "pthomas";
    private static final String PASSWORD = "EKFYGUCC";
    private static final String APP_NAME = NAME;
    private static final String APPLICATION_ID = "C-JSON-TEST-APP-ID-10";
    private static final String BASE_URL = "https://" + CSE_BASE + "/";
    private static final String PATH = "/" + CSE_NAME + "/" + NAME;

    @Test
    public void createSuccess() throws Exception {
        MockWebServer server = new MockWebServer();
        server.enqueue(new MockResponse().setBody("{\"m2m:ae\":{\"aei\":\"C-PAUL-TEST-10\",\"api\":\"C-JSON-TEST-APP-ID-10\",\"ct\":\"20160223T161429\",\"et\":\"20160227T033429\",\"lt\":\"20160223T161429\",\"pi\":\"ONET-CSE-01\",\"ri\":\"C-PAUL-TEST-10\",\"rn\":\"PAUL-TEST-APP-10\",\"rr\":false,\"ty\":2}}")
                .addHeader("Content - Type:application / vnd.onem2m - res+json")
                .addHeader("X-M2M-RI: 34543") // We can't set this really.
                .addHeader("X-M2M-RSC: 2000")); // All ok.
        server.start();
        HttpUrl httpUrl = server.url("");
        ApplicationEntity applicationEntity = ApplicationEntity.retrieveAe(
                httpUrl.toString(), PATH, ID, USER_NAME, PASSWORD);

        RecordedRequest recordedRequest = server.takeRequest();
        assertEquals(PATH, recordedRequest.getPath());

        server.shutdown();
    }
}
