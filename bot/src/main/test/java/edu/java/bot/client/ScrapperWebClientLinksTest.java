package edu.java.bot.client;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.github.tomakehurst.wiremock.matching.UrlPattern;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;

import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ScrapperWebClientLinksTest {
    ScrapperWebClient scrapperWebClient;

    @RegisterExtension
    static WireMockExtension extension = new WireMockExtension.Builder()
            .options(options().port(8087)).build();

    @BeforeEach
    void configure() {
        String response = "ok";
        UrlPattern url = urlEqualTo("/links");

        extension.stubFor(WireMock.get(url)
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withBody(response)));

        extension.stubFor(WireMock.post(url)
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withBody(response)));

        extension.stubFor(WireMock.delete(url)
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withBody(response)));

        scrapperWebClient = new ScrapperWebClient(WebClient.builder().baseUrl("http://localhost:8087").build());
    }

    @Test
    void testThatAllLinksClientsIsOk() {
        var res1 = scrapperWebClient.getAllTrackedLinks(1L);
        var res2 = scrapperWebClient.trackLink(1L, URI.create("http://site.com"));
        var res3 = scrapperWebClient.untrackLink(1L, URI.create("http://site.com"));

        var expectedValue = "ok";
        assertEquals(res1, expectedValue);
        assertEquals(res2, expectedValue);
        assertEquals(res3, expectedValue);
    }
}
