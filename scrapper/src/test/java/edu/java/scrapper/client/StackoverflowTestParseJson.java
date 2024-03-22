package edu.java.scrapper.client;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.github.tomakehurst.wiremock.matching.UrlPattern;
import edu.java.scrapper.client.stackoverflow.StackoverflowClientImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.reactive.function.client.WebClient;

import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

@SpringBootTest
@TestPropertySource(locations = "classpath:test")
@WireMockTest
public class StackoverflowTestParseJson {

    private StackoverflowClientImpl client;

    @RegisterExtension
    static WireMockExtension extension = new WireMockExtension.Builder()
            .options(options().port(8089))
            .build();

    @BeforeEach
    void configure() {
        String responseBody = "{}";
        UrlPattern externalUrl = urlEqualTo("/12345?order=desc&sort=activity&site=stackoverflow&filter=withbody");

        extension.stubFor(WireMock.get(externalUrl)
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withHeader("content-type", "application/json")
                        .withBody(responseBody)
                )
        );
        client = new StackoverflowClientImpl(WebClient.builder().baseUrl("http://localhost:8089").build());
    }

    @Test
    void testThatParseJsonThrowsExceptionReturnedSucceed() {
        String link = "https://stackoverflow.com/questions/12345/question";

       var data = client.getLastActivity(link);

       String expectedValue = null;
        Assertions.assertEquals(expectedValue, data);
    }
}
