package com.example.taller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.engine.discovery.predicates.IsPotentialTestContainer;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(IsPotentialTestContainer.class)
@SpringBootTest


@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TallerApplicationTests {

	@Test
	void contextLoads() {
	}

}
