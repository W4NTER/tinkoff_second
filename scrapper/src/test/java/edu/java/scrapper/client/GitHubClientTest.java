package edu.java.scrapper.client;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.github.tomakehurst.wiremock.matching.UrlPattern;
import edu.java.client.github.GitHubClientImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;


@SpringBootTest
@TestPropertySource(locations = "classpath:test")
public class GitHubClientTest {
    private final static String USER_NAME = "Owner";
    private final static String REPO = "Repo";
    private final static String RESPONSE_BODY = "{\"id\": 123,\"name\":\"testRepo\",\"full_name\":\"Owner/Repo\", " +
            "\"html_url\":\"https://github.com/testOwner/testRepo\",\"updated_at\":\"2024-02-06T19:19:01Z\"}";

    @RegisterExtension
    static WireMockExtension extension = new WireMockExtension.Builder()
            .options(options().port(8089))
            .build();


    @BeforeEach
    void configure() {
        UrlPattern externalUrl = urlPathEqualTo("/repos/" + USER_NAME + "/" + REPO);

        extension.stubFor(WireMock.get(externalUrl)
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withHeader("content-type", "application/json")
                        .withBody(RESPONSE_BODY)
                )
        );
    }


    @Test
    void testThatGitHubClientGetLastUpdateReturnedSucceed() {
        OffsetDateTime expectedDate = OffsetDateTime.parse("2024-02-06T19:19:01Z", DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        Long expectedId = 123L;
        String expectedName = "Owner/Repo";

        var client = new GitHubClientImpl("http://localhost:8089");
        var dto = client.getLastUpdate(USER_NAME, REPO);

        Assertions.assertEquals(dto.id(), expectedId);
        Assertions.assertEquals(dto.name(), expectedName);
        Assertions.assertEquals(dto.updatedAt(), expectedDate);
    }
}
