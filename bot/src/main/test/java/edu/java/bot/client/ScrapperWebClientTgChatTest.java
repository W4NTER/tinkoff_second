package edu.java.bot.client;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.github.tomakehurst.wiremock.matching.UrlPattern;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;

import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.junit.Assert.assertEquals;

@SpringBootTest
public class ScrapperWebClientTgChatTest {

    ScrapperWebClient scrapperWebClient;

    @RegisterExtension
    static WireMockExtension extension = new WireMockExtension.Builder()
            .options(options().port(8087)).build();

    @BeforeEach
    void configure() {
        String response = "ok";
        UrlPattern url = urlEqualTo("/tg-chat/1");

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
    void testThatRegisterChatIsOkReturnedSucceed() {
        var res = scrapperWebClient.registerChat(1L);

        var expectedValue = "ok";
        assertEquals(res, expectedValue);
    }

    @Test
    void testThatUnRegisterChatIsOkReturnedSucceed() {
        var res = scrapperWebClient.deleteChat(1L);

        var expectedValue = "ok";
        assertEquals(res, expectedValue);
    }
}
