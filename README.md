# Secure Data Factory Demo

Demo project showing how to use [Secure Data Factory](https://github.com/JhonmySoftware/secure-data-factory) to generate synthetic test data.

## Features

- **SecureDataFactory**: Generate synthetic data programmatically
- **Annotation-based**: Use `@SdfField` annotations on your model classes
- **Multiple data types**: Person, Company, Address, Bank Account, and more
- **Configurable security levels**: LOW, MEDIUM, HIGH

## Usage

```bash
# Build and run
mvn clean compile exec:java
```

## Quick Example

```java
SecureDataFactory factory = SecureDataFactory.builder()
    .securityLevel(SecurityLevel.HIGH)
    .build();

SecureData<Person> person = factory.generatePerson();
System.out.println(person.getData().getFullName());
```

Or use annotations:

```java
public class User {
    @SdfField(DataType.FIRST_NAME)
    private String firstName;
    
    @SdfField(DataType.EMAIL)
    private String email;
}

User user = AnnotationProcessor.process(User.class);
```

## Requirements

- Java 8+
- Maven 3.6+

## License

MIT