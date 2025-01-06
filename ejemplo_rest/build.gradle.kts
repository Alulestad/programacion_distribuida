plugins {
    id("java")
    //id("application")
    id("io.freefair.lombok") version "8.11"
    id("com.github.johnrengelman.shadow") version "8.1.1"

}

group = "com.programacion.distribuida"
version = "unspecified"

repositories {
    mavenCentral()
}
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }

    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}


dependencies {
    // https://mvnrepository.com/artifact/io.helidon.webserver/helidon-webserver
    implementation("io.helidon.webserver:helidon-webserver:4.1.4")
    // https://mvnrepository.com/artifact/com.google.code.gson/gson
    implementation("com.google.code.gson:gson:2.11.0")

    // https://mvnrepository.com/artifact/org.hibernate/hibernate-core
    implementation("org.hibernate:hibernate-core:6.6.3.Final")

    // https://mvnrepository.com/artifact/org.postgresql/postgresql
    implementation("org.postgresql:postgresql:42.7.4")

    // https://mvnrepository.com/artifact/org.projectlombok/lombok
    compileOnly("org.projectlombok:lombok:1.18.36")
    annotationProcessor("org.projectlombok:lombok:1.18.36")

    testCompileOnly("org.projectlombok:lombok:1.18.36")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.36")

    // https://mvnrepository.com/artifact/org.jboss.weld.se/weld-se-core
    implementation("org.jboss.weld.se:weld-se-core:5.1.3.Final")

}

sourceSets {
    main {
        output.setResourcesDir( file("${buildDir}/classes/java/main") )
    }
}

tasks.test {
    useJUnitPlatform()
}

/*tasks.shadowJar{
    mainClassName='com.programacion.distribuida.Main'
}*/

tasks.jar {

    manifest {

        attributes(

            mapOf("Main-Class" to "com.programacion.distribuida.PrincipalRest",

                "Class-Path" to configurations.runtimeClasspath

                    .get()

                    .joinToString(separator = " ") { file ->

                        "${file.name}"

                    })

        )

    }

}
