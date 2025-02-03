# FIS_API_TESTING

Here’s a instruction file that will guide you on how to run and compile your Java code:

## Compilation

To compile the Java file, use the following command:

```bash
javac CoinDeskAPITest.java
```

This will create a `CoinDeskAPITest.class` file in the same directory.

## Running the Program

To run the program, use the following command:

```bash
java CoinDeskAPITest
```

### Expected Output

If the API response contains the currency keys (USD, GBP, EUR), and the GBP description matches the expected value, you will see the following message:

```
All tests passed.
```

### Error Handling

If any of the checks fail, you will see an error message describing the issue, for example:

```
Error: USD not found in response
```

