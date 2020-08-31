### 🌌 Micronaut + JWT + Kotlin

A boilerplate with configured json web tokens + registration system

##### Libraries used:

- [Micronaut](https://github.com/micronaut-projects)
- [Exposed](https://github.com/JetBrains/Exposed)
- [HikariCP](https://github.com/brettwooldridge/HikariCP)
- [Kotlinx.coroutines](https://github.com/Kotlin/kotlinx.coroutines)
- [Kotlinx.serialization](https://github.com/Kotlin/kotlinx.serialization/)
- [BCrypt](https://github.com/patrickfav/bcrypt)

---

### 🛠 Installation

1. Open the project in Intellij IDEA
2. Change `dev.jonaz.server` to your awesome package name

    In Intellij IDEA select the package and press `SHIFT + F6`.
    After that open the `build.gradle.kts` and change the `group` variable to the same path.
3. Create a new Kotlin run/debug configuration

    - VM options: 
        ```
        -Dmicronaut.environments=dev
        ```
    - Environment variables:
    
         ![set up environment variables](https://i.imgur.com/yJxnYpZ.png)

    - Launch before:
        
        Click add and select "Run Gradle Task"
        
         ![select run gradle task](https://i.imgur.com/pL8QGGF.png)

        Type `classes` into the "Tasks" field
        
         ![type classes](https://i.imgur.com/rJdvft5.png)
