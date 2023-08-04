val jdkVersion = "17"
val kotlinVersion = "1.9.0"

plugins {
  id("java")
  id("org.jetbrains.kotlin.jvm") version "1.9.0"
  id("org.jetbrains.intellij") version "1.15.0"
}

group = "br.com.emersonmendes"
version = "1.0.2"

repositories {
  mavenCentral()
}

intellij {
  version.set("2023.2")
  plugins.set(listOf("org.jetbrains.kotlin"))
//  plugins.set(listOf("com.intellij.java"))
}

dependencies {
  compileOnly ("org.jetbrains.kotlin:kotlin-compiler-embeddable:$kotlinVersion")
}

tasks {
  withType<JavaCompile> {
    sourceCompatibility = jdkVersion
    targetCompatibility = jdkVersion
  }

  withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = jdkVersion
  }
  compileKotlin {
    kotlinOptions.jvmTarget = jdkVersion
  }

  compileTestKotlin {
    kotlinOptions.jvmTarget = jdkVersion
  }

  patchPluginXml {
    sinceBuild.set("232.0000")
    untilBuild.set("233.*")
  }

  signPlugin {
    certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
    privateKey.set(System.getenv("PRIVATE_KEY"))
    password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
  }

  publishPlugin {
    token.set(System.getenv("PUBLISH_TOKEN"))
  }
}
