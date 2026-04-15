# Hands-On Examples: Try It Yourself

This document provides runnable code examples to demonstrate the concepts.

---

## Example 1: Database vs API Provider

### Setup - Common Interface

```java
// UserDataProvider.java
package com.examples;

public interface UserDataProvider {
    String getUserDetails();
}
```

### Implementation 1 - Database Provider

```java
// DatabaseUserProvider.java
package com.examples;

public class DatabaseUserProvider implements UserDataProvider {
    @Override
    public String getUserDetails() {
        // Simulate database call
        System.out.println("🗄️  Fetching from Database...");
        return "User: John Doe | Email: john@db.com | Source: Database";
    }
}
```

### Implementation 2 - API Provider

```java
// APIUserProvider.java
package com.examples;

public class APIUserProvider implements UserDataProvider {
    @Override
    public String getUserDetails() {
        // Simulate API call
        System.out.println("🌐 Fetching from API...");
        return "User: John Doe | Email: john@api.com | Source: External API";
    }
}
```

### Implementation 3 - Cache Provider

```java
// CacheUserProvider.java
package com.examples;

public class CacheUserProvider implements UserDataProvider {
    @Override
    public String getUserDetails() {
        // Simulate cache lookup
        System.out.println("⚡ Fetching from Cache...");
        return "User: John Doe | Email: john@cache.com | Source: Cache (Fast!)";
    }
}
```

### Client Code - Works With ALL

```java
// UserManager.java
package com.examples;

public class UserManager {
    private UserDataProvider provider;
    
    // Constructor injection
    public UserManager(UserDataProvider provider) {
        this.provider = provider;
    }
    
    public String getUserInfo() {
        return provider.getUserDetails();
    }
}
```

### Running the Example

```java
// Main.java
package com.examples;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Loose Coupling Example ===\n");
        
        // Version 1: Using Database
        System.out.println("1️⃣  Using Database Provider:");
        UserDataProvider dbProvider = new DatabaseUserProvider();
        UserManager manager1 = new UserManager(dbProvider);
        System.out.println("Result: " + manager1.getUserInfo());
        System.out.println();
        
        // Version 2: Using API
        System.out.println("2️⃣  Using API Provider:");
        UserDataProvider apiProvider = new APIUserProvider();
        UserManager manager2 = new UserManager(apiProvider);
        System.out.println("Result: " + manager2.getUserInfo());
        System.out.println();
        
        // Version 3: Using Cache
        System.out.println("3️⃣  Using Cache Provider:");
        UserDataProvider cacheProvider = new CacheUserProvider();
        UserManager manager3 = new UserManager(cacheProvider);
        System.out.println("Result: " + manager3.getUserInfo());
        System.out.println();
        
        System.out.println("✅ Same UserManager, different providers!");
        System.out.println("✅ No code changes needed to switch providers!");
    }
}

/* Expected Output:
=== Loose Coupling Example ===

1️⃣  Using Database Provider:
🗄️  Fetching from Database...
Result: User: John Doe | Email: john@db.com | Source: Database

2️⃣  Using API Provider:
🌐 Fetching from API...
Result: User: John Doe | Email: john@api.com | Source: External API

3️⃣  Using Cache Provider:
⚡ Fetching from Cache...
Result: User: John Doe | Email: john@cache.com | Source: Cache (Fast!)

✅ Same UserManager, different providers!
✅ No code changes needed to switch providers!
*/
```

---

## Example 2: Testing with Mocks

### Mock Implementation for Testing

```java
// MockUserProvider.java - For Testing
package com.examples;

public class MockUserProvider implements UserDataProvider {
    @Override
    public String getUserDetails() {
        // Return predictable test data
        System.out.println("🧪 Using Mock Data (for testing)");
        return "Test User | Email: test@example.com";
    }
}
```

### Unit Test Example

```java
// UserManagerTest.java
package com.examples;

public class UserManagerTest {
    
    public static void main(String[] args) {
        System.out.println("=== Testing UserManager ===\n");
        
        // ✅ Easy to test with mock
        System.out.println("Test 1: getUserInfo returns correct data");
        UserDataProvider mockProvider = new MockUserProvider();
        UserManager manager = new UserManager(mockProvider);
        
        String result = manager.getUserInfo();
        assert result.contains("Test User");
        System.out.println("✓ PASSED\n");
        
        // ✅ No database needed
        System.out.println("Test 2: UserManager doesn't care about data source");
        UserDataProvider dbProvider = new DatabaseUserProvider();
        UserManager manager2 = new UserManager(dbProvider);
        
        String result2 = manager2.getUserInfo();
        assert result2 != null;
        assert result2.length() > 0;
        System.out.println("✓ PASSED\n");
        
        System.out.println("✅ All tests passed!");
        System.out.println("✅ Tests are fast and reliable!");
        System.out.println("✅ No database setup needed!");
    }
}

/* Expected Output:
=== Testing UserManager ===

Test 1: getUserInfo returns correct data
🧪 Using Mock Data (for testing)
✓ PASSED

Test 2: UserManager doesn't care about data source
🗄️  Fetching from Database...
✓ PASSED

✅ All tests passed!
✅ Tests are fast and reliable!
✅ No database setup needed!
*/
```

---

## Example 3: Payment Processing with Fallback

### Multiple Payment Gateways

```java
// PaymentGateway.java
package com.examples;

public interface PaymentGateway {
    PaymentResult processPayment(double amount);
}

public class PaymentResult {
    public boolean success;
    public String transactionId;
    public String message;
    
    public PaymentResult(boolean success, String transactionId, String message) {
        this.success = success;
        this.transactionId = transactionId;
        this.message = message;
    }
}
```

### Gateway Implementations

```java
// StripePaymentGateway.java
package com.examples;

public class StripePaymentGateway implements PaymentGateway {
    @Override
    public PaymentResult processPayment(double amount) {
        System.out.println("💳 Processing $" + amount + " via Stripe...");
        return new PaymentResult(true, "stripe_123456", "Payment successful");
    }
}

// PayPalPaymentGateway.java
package com.examples;

public class PayPalPaymentGateway implements PaymentGateway {
    @Override
    public PaymentResult processPayment(double amount) {
        System.out.println("💰 Processing $" + amount + " via PayPal...");
        return new PaymentResult(true, "paypal_654321", "PayPal payment successful");
    }
}
```

### Smart Payment Service with Fallback

```java
// SmartPaymentService.java
package com.examples;

public class SmartPaymentService {
    private PaymentGateway[] gateways;
    
    public SmartPaymentService(PaymentGateway... gateways) {
        this.gateways = gateways;
    }
    
    // Try primary gateway, fallback to others if needed
    public PaymentResult processPayment(double amount) {
        System.out.println("\n🔄 Processing payment with fallback logic...\n");
        
        for (int i = 0; i < gateways.length; i++) {
            try {
                System.out.println("Attempt " + (i + 1) + ": Using " + 
                    gateways[i].getClass().getSimpleName());
                
                PaymentResult result = gateways[i].processPayment(amount);
                
                if (result.success) {
                    System.out.println("✅ Success! Transaction: " + result.transactionId);
                    return result;
                }
            } catch (Exception e) {
                System.out.println("❌ Failed: " + e.getMessage());
                if (i < gateways.length - 1) {
                    System.out.println("Trying fallback gateway...\n");
                }
            }
        }
        
        return new PaymentResult(false, "", "All payment gateways failed");
    }
}
```

### Usage

```java
// PaymentExample.java
package com.examples;

public class PaymentExample {
    public static void main(String[] args) {
        System.out.println("=== Payment Processing with Fallback ===");
        
        // Setup: Stripe as primary, PayPal as fallback
        SmartPaymentService paymentService = new SmartPaymentService(
            new StripePaymentGateway(),
            new PayPalPaymentGateway()
        );
        
        // Process payment
        PaymentResult result = paymentService.processPayment(99.99);
        
        System.out.println("\n" + (result.success ? "✅" : "❌") + " " + result.message);
        
        // If we want to change primary gateway...
        System.out.println("\n\n=== Now using PayPal first ===");
        SmartPaymentService paymentService2 = new SmartPaymentService(
            new PayPalPaymentGateway(),
            new StripePaymentGateway()
        );
        
        PaymentResult result2 = paymentService2.processPayment(49.99);
        System.out.println("\n" + (result2.success ? "✅" : "❌") + " " + result2.message);
        
        System.out.println("\n\n✅ Easy to add new gateways!");
        System.out.println("✅ Easy to change order of gateways!");
        System.out.println("✅ Easy to implement fallback logic!");
    }
}

/* Expected Output:
=== Payment Processing with Fallback ===

🔄 Processing payment with fallback logic...

Attempt 1: Using StripePaymentGateway
💳 Processing $99.99 via Stripe...
✅ Success! Transaction: stripe_123456

✅ Payment successful


=== Now using PayPal first ===

🔄 Processing payment with fallback logic...

Attempt 1: Using PayPalPaymentGateway
💰 Processing $49.99 via PayPal...
✅ Success! Transaction: paypal_654321

✅ PayPal payment successful

✅ Easy to add new gateways!
✅ Easy to change order of gateways!
✅ Easy to implement fallback logic!
*/
```

---

## Example 4: Real-World Comparison

### ❌ TIGHT COUPLING Example

```java
// TightNotificationService.java - BAD
package com.examples.tight;

public class TightNotificationService {
    private GmailSender emailSender = new GmailSender();  // ❌ HARDCODED
    
    public void notifyUser(String email, String message) {
        emailSender.send(email, message);  // Only works with Gmail
    }
}

// Concrete implementation
class GmailSender {
    public void send(String email, String message) {
        System.out.println("📧 Sending via Gmail to: " + email);
    }
}

// To test this, you MUST:
// 1. Have Gmail configured
// 2. Actually send emails
// 3. Check Gmail account
// ❌ SLOW, UNRELIABLE, EXPENSIVE
```

### ✅ LOOSE COUPLING Example

```java
// NotificationProvider.java - INTERFACE
package com.examples.loose;

public interface NotificationProvider {
    void send(String recipient, String message);
}

// LooseNotificationService.java - GOOD
public class LooseNotificationService {
    private NotificationProvider provider;  // ✅ ABSTRACTION
    
    public LooseNotificationService(NotificationProvider provider) {
        this.provider = provider;
    }
    
    public void notifyUser(String recipient, String message) {
        provider.send(recipient, message);
    }
}

// Multiple implementations
class GmailNotificationProvider implements NotificationProvider {
    @Override
    public void send(String email, String message) {
        System.out.println("📧 Sending via Gmail to: " + email);
    }
}

class SMSNotificationProvider implements NotificationProvider {
    @Override
    public void send(String phone, String message) {
        System.out.println("📱 Sending SMS to: " + phone);
    }
}

class MockNotificationProvider implements NotificationProvider {
    @Override
    public void send(String recipient, String message) {
        System.out.println("🧪 Mock sending to: " + recipient);
    }
}

// Usage
public class Demo {
    public static void main(String[] args) {
        // Production: use Gmail
        LooseNotificationService emailService = 
            new LooseNotificationService(new GmailNotificationProvider());
        emailService.notifyUser("user@example.com", "Hello!");
        
        // Production: use SMS
        LooseNotificationService smsService = 
            new LooseNotificationService(new SMSNotificationProvider());
        smsService.notifyUser("+1234567890", "Hello!");
        
        // Testing: use mock (instant, no side effects)
        LooseNotificationService testService = 
            new LooseNotificationService(new MockNotificationProvider());
        testService.notifyUser("test@example.com", "Hello!");
        
        // ✅ FAST, RELIABLE, CHEAP
    }
}
```

---

## Key Takeaways from Examples

| Aspect | What You Learned |
|--------|------------------|
| **Flexibility** | Can swap implementations without changing client code |
| **Testing** | Mock providers make unit tests fast and reliable |
| **Scalability** | Can add new providers without modifying existing ones |
| **Maintainability** | Changes isolated to single provider implementation |
| **Reusability** | Same manager works with different providers |
| **Fallback Logic** | Easy to implement intelligent provider selection |

---

## Quick Practice Exercise

Try this yourself:

1. Create a `LoggingProvider` interface
2. Create implementations:
   - `ConsoleLogProvider` - logs to console
   - `FileLogProvider` - logs to file
   - `DatabaseLogProvider` - logs to database
   - `MockLogProvider` - for testing
3. Create an `AuthenticationService` that uses any provider
4. Test swapping between different providers

This reinforces the concept and shows how easy it is to work with loose coupling!

