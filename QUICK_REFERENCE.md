# Quick Reference Guide: Tight vs Loose Coupling

## One-Minute Summary

### TIGHT COUPLING ❌
- **Definition:** Class directly uses concrete classes from other classes
- **Problem:** Hard to change, hard to test, hard to reuse
- **Metaphor:** Welded parts - can't be separated
- **Avoid:** In professional code

### LOOSE COUPLING ✅
- **Definition:** Class uses interfaces/abstractions, not concrete classes
- **Benefit:** Easy to change, easy to test, easy to reuse
- **Metaphor:** Plug-and-play components - easily swappable
- **Goal:** In all professional code

---

## Code Patterns at a Glance

### TIGHT COUPLING - Pattern to AVOID

```java
public class BadExample {
    private SpecificDatabase db = new SpecificDatabase();  // ❌ AVOID THIS
    
    public void doSomething() {
        db.execute();  // ❌ Direct dependency
    }
}
```

### LOOSE COUPLING - Pattern to USE

```java
public interface DataProvider {  // ✅ USE INTERFACE
    void execute();
}

public class GoodExample {
    private DataProvider provider;  // ✅ Depend on interface
    
    public GoodExample(DataProvider provider) {  // ✅ Inject dependency
        this.provider = provider;
    }
    
    public void doSomething() {
        provider.execute();  // ✅ Talk to interface
    }
}
```

---

## The Three Steps to Loose Coupling

```
Step 1: CREATE AN INTERFACE
        ↓
    Define the contract
    
Step 2: CREATE IMPLEMENTATIONS
        ↓
    Each concrete implementation
    implements the interface
    
Step 3: INJECT THE DEPENDENCY
        ↓
    Pass implementation via constructor
```

### Example

```java
// Step 1: Interface
public interface PaymentGateway {
    boolean processPayment(double amount);
}

// Step 2: Implementations
public class StripeGateway implements PaymentGateway {
    public boolean processPayment(double amount) { return true; }
}

public class PayPalGateway implements PaymentGateway {
    public boolean processPayment(double amount) { return true; }
}

// Step 3: Injection
public class OrderService {
    private PaymentGateway gateway;
    
    public OrderService(PaymentGateway gateway) {
        this.gateway = gateway;  // ✅ Injected
    }
    
    public void checkout(Order order) {
        gateway.processPayment(order.getTotal());
    }
}

// Usage
OrderService service1 = new OrderService(new StripeGateway());
OrderService service2 = new OrderService(new PayPalGateway());
```

---

## Common Mistakes to Avoid

### ❌ Mistake 1: Creating dependency inside class
```java
public class UserService {
    public UserService() {
        this.database = new MySQLDatabase();  // ❌ WRONG
    }
}
```

### ✅ Correct: Inject dependency
```java
public class UserService {
    public UserService(UserRepository repository) {
        this.repository = repository;  // ✅ RIGHT
    }
}
```

---

### ❌ Mistake 2: Depending on concrete class
```java
private PostgresConnection connection;  // ❌ WRONG - specific to Postgres
```

### ✅ Correct: Depend on interface
```java
private DatabaseConnection connection;  // ✅ RIGHT - any database works
```

---

### ❌ Mistake 3: Mixing creation with usage
```java
public void processOrder(Order order) {
    PaymentGateway gateway = new StripeGateway();  // ❌ Created here
    gateway.charge(order.getTotal());
}
```

### ✅ Correct: Separate creation from usage
```java
public class OrderProcessor {
    private PaymentGateway gateway;
    
    public OrderProcessor(PaymentGateway gateway) {  // ✅ Injected
        this.gateway = gateway;
    }
    
    public void processOrder(Order order) {
        gateway.charge(order.getTotal());  // ✅ Just used
    }
}
```

---

## When to Apply Each

### Use LOOSE COUPLING When:

✅ Multiple implementations might exist
✅ External dependencies (database, API, file system)
✅ Code needs to be tested
✅ Code will be reused elsewhere
✅ Requirements might change
✅ Professional/production code

### TIGHT COUPLING Might Be OK For:

🟡 Learning projects
🟡 Throwaway prototypes
🟡 Simple scripts
🟡 Internal utilities with single use

BUT: Even then, loose coupling rarely hurts!

---

## Your Project Examples

### From Your Code:

**Tight Coupling (your `tight` package):**
```java
public class UserManager {
    private UserDatabase userDatabase = new UserDatabase();  // Tightly coupled
}
```

**Loose Coupling (your `loose` package):**
```java
public interface UserDataProvider {
    String getUserDetails();
}

public class UserManager {
    private UserDataProvider provider;
    
    public UserManager(UserDataProvider provider) {
        this.provider = provider;  // Loosely coupled
    }
}
```

**The Key Difference:**
- Tight: `UserManager` must use `UserDatabase`
- Loose: `UserManager` works with ANY `UserDataProvider`

---

## Testing Differences

### Tight Coupling Testing

```java
// ❌ Hard to test
@Test
public void testUserManager() {
    UserManager manager = new UserManager();  // Creates real database
    String result = manager.getUserInfo();
    // Must use real database - SLOW & UNRELIABLE
}
```

### Loose Coupling Testing

```java
// ✅ Easy to test
@Test
public void testUserManager() {
    UserDataProvider mock = new MockUserProvider();
    UserManager manager = new UserManager(mock);  // Uses mock
    String result = manager.getUserInfo();
    // Uses mock - FAST & RELIABLE
}
```

---

## SOLID Principles Connection

Loose coupling helps you follow **SOLID**:

- **S**ingle Responsibility: Each class has one job
- **O**pen/Closed: Open for extension, closed for modification
- **L**iskov Substitution: Can substitute implementations
- **I**nterface Segregation: Small, focused interfaces
- **D**ependency Inversion: Depend on abstractions, not concretions ← KEY!

Tight coupling **violates Dependency Inversion** (the D in SOLID).

---

## Refactoring: From Tight to Loose

### Before: Tight Coupling

```java
public class EmailService {
    private GmailProvider gmail = new GmailProvider();
    
    public void sendEmail(String to, String message) {
        gmail.send(to, message);
    }
}
```

### After: Loose Coupling

```java
// Step 1: Create interface
public interface EmailProvider {
    void send(String to, String message);
}

// Step 2: Make Gmail implement interface
public class GmailProvider implements EmailProvider {
    public void send(String to, String message) {
        // Same implementation
    }
}

// Step 3: Update EmailService
public class EmailService {
    private EmailProvider provider;  // Changed to interface
    
    public EmailService(EmailProvider provider) {  // Added constructor
        this.provider = provider;
    }
    
    public void sendEmail(String to, String message) {
        provider.send(to, message);  // Same logic
    }
}

// Step 4: Update usage
// Before: EmailService service = new EmailService();
// After:
EmailService service = new EmailService(new GmailProvider());
```

---

## Interview Questions You Can Now Answer

**Q: What's the difference between tight and loose coupling?**
A: Tight coupling means classes depend directly on concrete classes. Loose coupling means they depend on interfaces/abstractions. Loose coupling is always better for maintainability.

**Q: Why is loose coupling important?**
A: It allows you to easily change implementations without modifying client code, makes testing easier with mocks, and makes code more reusable.

**Q: How do you achieve loose coupling?**
A: Use interfaces, dependency injection, and depend on abstractions rather than concrete classes.

**Q: What's dependency injection?**
A: Instead of creating dependencies inside a class, you pass them in through the constructor or setter. This allows you to provide different implementations.

**Q: What does "program to interfaces not implementations" mean?**
A: Declare variables/parameters as interface types, not concrete class types. This lets you swap implementations easily.

---

## Checklist: Is My Code Loosely Coupled?

- [ ] Does my class create its own dependencies? → If yes, refactor
- [ ] Can I easily mock my dependencies for testing? → If no, refactor
- [ ] Would changing a dependency require changing my class? → If yes, refactor
- [ ] Can I add a new implementation without touching existing code? → If no, refactor
- [ ] Does my class depend on interfaces/abstractions? → If no, refactor
- [ ] Are dependencies injected via constructor? → If no, refactor

If all check marks: ✅ Your code is loosely coupled!

---

## Quick Decision Tree

```
Does your class directly instantiate other classes?
    ↓
    YES → Tight coupling ❌
    ↓
    NO → Check next
    
Does it depend on interfaces/abstractions?
    ↓
    NO → Tight coupling ❌
    ↓
    YES → Check next
    
Are dependencies injected?
    ↓
    NO → Consider loose coupling ⚠️
    ↓
    YES → Loose coupling ✅
```

---

## Resources to Explore Further

- **Design Patterns**: Strategy, Adapter, Decorator, Factory
- **SOLID Principles**: Especially Dependency Inversion
- **Dependency Injection Frameworks**: Spring, Guice, CDI
- **Unit Testing**: Mocking frameworks like Mockito
- **Clean Code**: Read Robert C. Martin's principles

---

## Final Thoughts

**Tight Coupling** is like having a specific tool for each job:
- Screwdriver for screws
- Hammer for nails
- Wrench for bolts
- Limited flexibility, need many tools

**Loose Coupling** is like having a multi-tool:
- One tool with multiple attachments
- Swap attachments as needed
- Flexible, minimal tools needed

In software: **Always go for the multi-tool approach!**

