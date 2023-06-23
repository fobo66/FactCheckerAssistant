plugins {
  `kotlin-dsl`
}
kotlin {
  jvmToolchain(11)
}

repositories {
  mavenCentral()
}

dependencies {
  implementation("io.github.cdimascio:dotenv-kotlin:6.4.1")
}
