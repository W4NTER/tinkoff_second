package edu.java.scrapper.client;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.github.tomakehurst.wiremock.matching.UrlPattern;
import edu.java.client.GitHubClient;
import edu.java.client.StackOverFlowClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestPropertySource(locations = "classpath:test")
@WireMockTest
public class StackoverflowTest {
    private final static Logger LOGGER = LogManager.getLogger();
    private final static int ID = 12345;

    @Autowired
    private StackOverFlowClient stackOverFlowClient;

    @RegisterExtension
    static WireMockExtension extension = new WireMockExtension.Builder()
            .options(options().port(8089))
            .build();
    @Test
    void testThatSmfgReturnedSucceed() {
        String searchQuery = "order=desc&sort=activity&site=stackoverflow&filter=withbody";
        UrlPattern externalUrl = urlPathEqualTo("/" + ID + "?" + searchQuery);
        LOGGER.info(externalUrl);
        String responseBody = "{\"items\": [{\"question_id\": 12345, \"last_activity_date\": 1687479446}]}";

        extension.stubFor(WireMock.get(externalUrl)
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withHeader("content-type", "application/jason")
                        .withBody(responseBody)
                )
        );
        String link = "https://stackoverflow.com/questions/12345/quistion";
//        LOGGER.info(stackOverFlowClient.getLastActivity(link));
        assertEquals(stackOverFlowClient.getLastActivity(link).id(), ID);
    }
}
