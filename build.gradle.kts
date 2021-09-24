plugins {
    val kotlinVersion = "1.5.10"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion

    id("net.mamoe.mirai-console") version "2.6.7"
}

group = "dada"
version = "1.0-SNAPSHOT"

repositories {
    maven("https://maven.aliyun.com/repository/public")
    mavenCentral()
}

dependencies {
    compileOnly(files("C:\\Users\\admin\\.gradle\\my-jars\\coinManager-1.0-SNAPSHOT.jar"))
    implementation( "org.jetbrains.kotlin:kotlin-reflect:1.5.10")
}
