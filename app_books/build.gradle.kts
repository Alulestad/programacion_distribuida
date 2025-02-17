val quarkusPlatformGroupId: String by project
val quarkusPlatformArtifactId: String by project
val quarkusPlatformVersion: String by project
var quarkusVersion="3.17.0"
plugins {
    id("java")
    //java
    id("io.quarkus") version "3.17.0"
    id("io.freefair.lombok") version "8.11"

}

group = "com.programacion.distribuida"
version = "1.0.0-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }

    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

repositories {
    mavenCentral()
}

dependencies {
    //implementation(enforcedPlatform("${quarkusPlatformGroupId}:${quarkusPlatformArtifactId}:${quarkusPlatformVersion}"))
    implementation(enforcedPlatform("io.quarkus.platform:quarkus-bom:${quarkusVersion}"))

    //Cdi
    implementation("io.quarkus:quarkus-arc")

    //rest
    implementation("io.quarkus:quarkus-rest")
    implementation("io.quarkus:quarkus-rest-jsonb")
    implementation("io.quarkus:quarkus-rest-client")
    implementation("io.quarkus:quarkus-rest-client-jsonb")

    implementation("io.smallrye.stork:stork-service-discovery-static-list")

    //JPA
    //implementation("io.quarkus:quarkus-hibernate-orm")
    implementation("io.quarkus:quarkus-hibernate-orm-panache")
    implementation("io.quarkus:quarkus-jdbc-postgresql")

    //Driver
    implementation("org.postgresql:postgresql:42.7.4")



    /*
    testImplementation("io.quarkus:quarkus-junit5")*/

    // https://mvnrepository.com/artifact/org.projectlombok/lombok
    compileOnly("org.projectlombok:lombok:1.18.36")
    annotationProcessor("org.projectlombok:lombok:1.18.36")

    testCompileOnly("org.projectlombok:lombok:1.18.36")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.36")

    //Registro este es para registrar CONFIRMADO es para lo del cliente en consul
    // es para que detecte, junto al la clase ciclo de vida, esto se puede borrar
    // para tener la version anterior
    implementation("io.smallrye.reactive:smallrye-mutiny-vertx-consul-client")


    //consul
    implementation("io.smallrye.stork:stork-service-discovery-consul")

    //health
    implementation("io.quarkus:quarkus-smallrye-health")

    //Metrics: Prometheus and micrometer
    //DESCOMENTAR
//    implementation("io.quarkus:quarkus-micrometer-registry-prometheus")
//    implementation("io.quarkus:quarkus-jackson")

    //Tracing
    //DESCOMENTAR
//    implementation("io.quarkus:quarkus-opentelemetry")
//    implementation("io.opentelemetry.instrumentation:opentelemetry-jdbc")


    //Fault Tolerance
    implementation("io.quarkus:quarkus-smallrye-fault-tolerance")

    //



    //########################################### OpenAPI ###########################################
    //Quarkus REST Jackson
    implementation("io.quarkus:quarkus-rest-jackson")

    //Quarkus SmallRye OpenAPI
    implementation("io.quarkus:quarkus-smallrye-openapi")

    //Quarkus JUnit5
//    testImplementation("io.quarkus:quarkus-junit5")

    //Rest Assured
//    testImplementation("io.quarkus:quarkus-rest-assured")

}

tasks.test {
    useJUnitPlatform()
}


repositories {
    mavenCentral()
    mavenLocal()
}



/*tasks.withType<Test> {
    systemProperty("java.util.logging.manager", "org.jboss.logmanager.LogManager")
}*/
tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.compilerArgs.add("-parameters")
}

