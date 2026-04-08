package com.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.github.sdf.SecureData;
import io.github.sdf.SecureDataFactory;
import io.github.sdf.annotation.AnnotationProcessor;
import io.github.sdf.annotation.DataType;
import io.github.sdf.annotation.SdfField;
import io.github.sdf.crypto.SecurityLevel;
import io.github.sdf.model.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Demo {

    public static void main(String[] args) throws Exception {
        System.out.println("=== Secure Data Factory Demo ===\n");

        // Demo 1: Using SecureDataFactory
        demoSecureDataFactory();

        // Demo 2: Using Annotations
        demoAnnotations();

        // Demo 3: Generate JSON file
        demoGenerateJsonFile();
    }

    private static void demoSecureDataFactory() {
        System.out.println("--- Demo 1: SecureDataFactory ---");

        SecureDataFactory factory = SecureDataFactory.builder()
                .securityLevel(SecurityLevel.HIGH)
                .enableAudit(true)
                .build();

        // Generate Person
        SecureData<Person> person = factory.generatePerson();
        System.out.println("Person: " + person.getData().getFullName());
        System.out.println("  Email: " + person.getData().getEmail());
        System.out.println("  Checksum: " + person.getChecksum());

        // Generate Company
        SecureData<Company> company = factory.generateCompany();
        System.out.println("Company: " + company.getData().getLegalName());
        System.out.println("  Website: " + company.getData().getWebsite());

        // Generate Address
        SecureData<Address> address = factory.generateAddress();
        System.out.println("Address: " + address.getData().getFullAddress());

        // Generate Bank Account
        SecureData<BankAccount> bank = factory.generateBankAccount();
        System.out.println("Bank Account: " + bank.getData().getAccountNumber());

        // Generate multiple persons
        List<SecureData<Person>> persons = factory.generatePersons(5);
        System.out.println("\nGenerated " + persons.size() + " persons\n");
    }

    private static void demoAnnotations() {
        System.out.println("--- Demo 2: Annotation-Based Generation ---");

        // Generate using annotations
        User user = AnnotationProcessor.process(User.class);
        System.out.println("User: " + user.getFirstName() + " " + user.getLastName());
        System.out.println("  Email: " + user.getEmail());
        System.out.println("  Phone: " + user.getPhone());
        System.out.println("  Age: " + user.getAge());
        System.out.println("  UUID: " + user.getUserId());

        // Product with annotations
        ProductStore product = AnnotationProcessor.process(ProductStore.class);
        System.out.println("\nProduct: " + product.getProductName());
        System.out.println("  SKU: " + product.getSku());
        System.out.println("  Price: " + product.getPrice());
        System.out.println("  Category: " + product.getCategory());

        // Custom fields
        Order order = AnnotationProcessor.process(Order.class);
        System.out.println("\nOrder: " + order.getOrderId());
        System.out.println("  Customer: " + order.getCustomerName());
        System.out.println("  Amount: " + order.getTotalAmount());
    }

    private static void demoGenerateJsonFile() throws Exception {
        System.out.println("--- Demo 3: Generate JSON File ---");

        SecureDataFactory factory = SecureDataFactory.builder()
                .securityLevel(SecurityLevel.MEDIUM)
                .enableAudit(true)
                .build();

        // Generate dataset
        List<Object> dataset = new ArrayList<>();

        // Add persons
        for (int i = 0; i < 10; i++) {
            User user = AnnotationProcessor.process(User.class);
            dataset.add(user);
        }

        // Add products
        for (int i = 0; i < 5; i++) {
            ProductStore product = AnnotationProcessor.process(ProductStore.class);
            dataset.add(product);
        }

        // Write to JSON
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        File output = new File("output.json");
        mapper.writeValue(output, dataset);

        System.out.println("Generated " + dataset.size() + " records to output.json");
    }

    // Model classes with annotations
    public static class User {
        @SdfField(DataType.FIRST_NAME)
        private String firstName;

        @SdfField(DataType.LAST_NAME)
        private String lastName;

        @SdfField(DataType.EMAIL)
        private String email;

        @SdfField(value = DataType.PHONE, country = "US")
        private String phone;

        @SdfField(DataType.UUID)
        private String userId;

        @SdfField(value = DataType.INTEGER, min = 18, max = 65)
        private int age;

        @SdfField(DataType.CITY)
        private String city;

        @SdfField(DataType.COUNTRY)
        private String country;

        public String getFirstName() { return firstName; }
        public String getLastName() { return lastName; }
        public String getEmail() { return email; }
        public String getPhone() { return phone; }
        public String getUserId() { return userId; }
        public int getAge() { return age; }
        public String getCity() { return city; }
        public String getCountry() { return country; }
    }

    public static class ProductStore {
        @SdfField(DataType.PRODUCT_NAME)
        private String productName;

        @SdfField(DataType.PRODUCT_SKU)
        private String sku;

        @SdfField(value = DataType.DOUBLE, min = 10.0, max = 1000.0)
        private double price;

        @SdfField(DataType.WORD)
        private String category;

        @SdfField(DataType.UUID)
        private String productId;

        public String getProductName() { return productName; }
        public String getSku() { return sku; }
        public double getPrice() { return price; }
        public String getCategory() { return category; }
        public String getProductId() { return productId; }
    }

    public static class Order {
        @SdfField(DataType.UUID)
        private String orderId;

        @SdfField(DataType.FULL_NAME)
        private String customerName;

        @SdfField(DataType.EMAIL)
        private String customerEmail;

        @SdfField(value = DataType.DOUBLE, min = 50.0, max = 10000.0)
        private double totalAmount;

        @SdfField(DataType.CURRENCY_CODE)
        private String currency;

        @SdfField(DataType.DATETIME)
        private String orderDate;

        public String getOrderId() { return orderId; }
        public String getCustomerName() { return customerName; }
        public String getCustomerEmail() { return customerEmail; }
        public double getTotalAmount() { return totalAmount; }
    }
}