package edu.java.scrapper.client.stackoverflow;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.github.tomakehurst.wiremock.matching.UrlPattern;
import edu.java.client.stackoverflow.StackoverflowClientImpl;
import edu.java.scrapper.IntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;

import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestPropertySource(locations = "classpath:test")
@WireMockTest
@ActiveProfiles("test")
public class StackoverflowTest extends IntegrationTest {
    private StackoverflowClientImpl client;

    @RegisterExtension
    static WireMockExtension extension = new WireMockExtension.Builder()
            .options(options().port(8089))
            .build();

    @BeforeEach
    void configure() {
        String responseBody = "{\"items\":[{\"last_activity_date\":1709579044,\"question_id\":12345,\"title\":\"question\"}]}";
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
    void testThatStackoverflowClientReturnedSucceed() {
        String link = "https://stackoverflow.com/questions/12345/question";
        int expectedQuestionId = 12345;
        String expectedTitle = "question";
        OffsetDateTime expectedLastActivityDate = OffsetDateTime.ofInstant(Instant.ofEpochSecond(1709579044L), ZoneId.systemDefault());

        var data = client.getLastActivity(link);
        assertEquals(data.questionId(), expectedQuestionId);
        assertEquals(data.questionTitle(), expectedTitle);
        assertEquals(data.lastActivityDate(), expectedLastActivityDate);
    }
}
