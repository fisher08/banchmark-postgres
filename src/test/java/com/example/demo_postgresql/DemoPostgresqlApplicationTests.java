package com.example.demo_postgresql;

import com.example.demo_postgresql.model.Config;
import com.example.demo_postgresql.model.DocumentEntity;
import com.example.demo_postgresql.model.NestedArray;
import com.example.demo_postgresql.repository.DocumentEntityRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomStringUtils;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class DemoPostgresqlApplicationTests {

	@Autowired
	DocumentEntityRepository repository;

	@Autowired
	PostgreSQLContainer<?> postgresContainer;

	@BeforeAll
	static void setupDatabaseProperties(@Autowired PostgreSQLContainer<?> postgresContainer) {
		System.setProperty("spring.datasource.url", postgresContainer.getJdbcUrl());
		System.setProperty("spring.datasource.username", postgresContainer.getUsername());
		System.setProperty("spring.datasource.password", postgresContainer.getPassword());
		System.setProperty("user.timezone", "Europe/Kyiv"); // Align JVM timezone
	}

	@Test
	void contextLoads() {
	}


	@Test
	void testSaveEntity() {
		DocumentEntity entity = new DocumentEntity();
		entity.setName("Test Document");

		DocumentEntity savedEntity = repository.save(entity);

		assertThat(savedEntity.getId()).isNotNull();
		assertThat(savedEntity.getName()).isEqualTo("Test Document");
	}

	@Test
	public void should_perform_save() {
		var entity = new DocumentEntity();
		var config = new Config();
		config.setSize(RandomUtils.nextDouble());
		config.setSet(RandomStringUtils.randomAlphabetic(10));
		config.setConfigId(RandomStringUtils.randomAlphabetic(10));
		config.setMultiply(RandomUtils.nextDouble());
		config.setDocumentEntity(entity);

		NestedArray nestedArray = new NestedArray();
		nestedArray.setValue(RandomStringUtils.randomAlphabetic(10));
		nestedArray.setDocumentEntity(entity);

		entity.setName(RandomStringUtils.randomAlphabetic(10));
		entity.setConfig(config);

		var arr = new ArrayList<NestedArray>(){{
			add(nestedArray);
		}};

		entity.setNestedArr(arr);


		repository.save(entity);

		var saved = repository.findAll();
		assertEquals(1, saved.size());
	}
}
