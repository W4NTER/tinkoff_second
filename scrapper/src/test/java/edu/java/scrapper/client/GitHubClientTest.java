package edu.java.scrapper.client;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.github.tomakehurst.wiremock.matching.UrlPattern;
import edu.java.client.GitHubClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import reactor.test.StepVerifier;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;


@SpringBootTest
@TestPropertySource(locations = "classpath:test")
public class GitHubClientTest {
    private final static Logger LOGGER = LogManager.getLogger();
    private final static String USER_NAME = "Owner";
    private final static String REPO = "Repo";
    private final static String RESPONSE_BODY = "{\"id\": 1234, \"full_name\": Owner/Repo, \"updated_at\": 2024-02-06T19:19:01Z}";

    @RegisterExtension
    static WireMockExtension extension = new WireMockExtension.Builder()
            .options(options().port(80))
            .build();


    @BeforeEach
    void configure() {
        UrlPattern externalUrl = urlPathEqualTo( "/repos/" + USER_NAME + "/" + REPO);
        LOGGER.info(externalUrl);

        extension.stubFor(WireMock.get(externalUrl)
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withHeader("content-type", "application/json")
                        .withBody(RESPONSE_BODY)
                )
        );
    }

    @Autowired
    private GitHubClient gitHubClient;


    @Test
    void testThatGitHubClientGetLastUpdateReturnedSucceed() {

//        StepVerifier.create(gitHubClient.getLastUpdateMono(USER_NAME, REPO))
//                .expectNextMatches(response -> {
//                    OffsetDateTime expectedDate = OffsetDateTime.parse("2024-02-06T19:19:01Z", DateTimeFormatter.ISO_OFFSET_DATE_TIME);
//                    return response.name().equals("https://github.com/Owner/Repo") &&
//                            response.updatedAt().isEqual(expectedDate);
//                })
//                .verifyComplete();
        LOGGER.info(gitHubClient.getLastUpdate(USER_NAME, REPO));
    }
}
