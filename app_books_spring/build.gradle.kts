plugins {
	java
	id("org.springframework.boot") version "3.4.0"
	id("io.spring.dependency-management") version "1.1.6"
	id("java")
}

dependencyManagement {
	imports {
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:2024.0.0");
	}
}

group = "com.programacion.distribuida"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	//implementation("org.springframework.boot:spring-boot-starter-webflux")
	compileOnly("org.projectlombok:lombok")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	runtimeOnly("org.postgresql:postgresql")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.projectreactor:reactor-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

	implementation("org.springframework.cloud:spring-cloud-starter-loadbalancer:4.2.0")
	//implementation("org.springframework.cloud:spring-cloud-starter-loadbalancer")

	implementation("org.springframework.cloud:spring-cloud-starter-consul-discovery:4.2.0")
	implementation("org.springframework.cloud:spring-cloud-loadbalancer:4.2.0")

	//implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client:4.2.0")

	//para transformacion de DTO y TO
	implementation("org.modelmapper:modelmapper:3.2.2")

}



tasks.withType<Test> {
	useJUnitPlatform()
}

