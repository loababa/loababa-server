package com.loababa.api;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(value = "classpath:secret/.env")
class LoababaApplicationTest {

    @Test
    void contextLoads() {
        // 애플리케이션_컨텍스트를_정상적으로_로드할_수_있다
    }

}
