package edu.java.scrapper.client.botClient;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.github.tomakehurst.wiremock.matching.UrlPattern;
import edu.java.client.botClient.BotWebClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;

import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.junit.Assert.assertEquals;

@SpringBootTest
public class BotClientTest {
    BotWebClient client;

    @RegisterExtension
    static WireMockExtension extension = new WireMockExtension.Builder()
            .options(options().port(8087)).build();

    @BeforeEach
    void configure() {
        String response = "link: https://gist.github.com";
        UrlPattern externalUrl = urlEqualTo("/updates");

        extension.stubFor(WireMock.post(externalUrl)
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withBody(response)
                )
        );
        client = new BotWebClient(WebClient.builder().baseUrl("http://localhost:8087").build());
    }
    @Test
    void testThatClientUpdateIsWorkingReturnedSucceed() {
        var response = client.sendUpdates(1L, "https://gist.github.com", "description", new Long[]{1L});

        String expectedResponse = "link: https://gist.github.com";
        assertEquals(response, expectedResponse);

    }
}
