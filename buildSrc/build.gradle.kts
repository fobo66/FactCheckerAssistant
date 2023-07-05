plugins {
  `kotlin-dsl`
}
kotlin {
  jvmToolchain(17)
}

repositories {
  mavenCentral()
}

dependencies {
  implementation("io.github.cdimascio:dotenv-kotlin:6.4.1")
}
