# GoveeJavaWrapper
A lightweight Java Wrapper for Govee API calls

## Obtaining a Govee API Key
1. Log into your account on the Govee Home mobile application
2. Navigate to the Profile tab
3. Select "About Us" and then Request API Key
4. Fill out the form and your API key should be sent in the next few minutes

### Using the Test Code
1. Follow the steps above for obtaining an API Key
2. Fill the `API_KEY` string with your API key
3. Add a breakpoint after line 21 to select your device's MAC and Model information
4. Fill the `DEVICE_MODEL` and `DEVICE_MAC` fields in the code
5. Run!

### Usage
The Govee API limits requests to 100 per minute. If you exceed this limit, you must wait until your calls reset. You can check your current usage using the `getRateLimit()` method.

Before utilizing any API calls, check the documentation provided when you requested your API Key to check which operations are supported by what devices. This information can be found on page 1 (as of API Version 1.1) under "Getting Started with the Govee API". Example: Don't use `setRGB()` for a Wi-Fi plug

## Installing

Simply implement the govee-java-wrapper-1.0.jar in your project in order to use the included features.
