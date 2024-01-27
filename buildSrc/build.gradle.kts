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
  implementation(libs.dotenv.kotlin)
}
