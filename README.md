# version-thing

```
apply plugin: 'version-thing'

buildscript {
    repositories {
        maven {
            url = uri("https://maven.pkg.github.com/joshgvines/version-thing")
        }
    }
    dependencies {
        classpath 'com.version-thing:version-thing:0.1.0'
    }
}
```
