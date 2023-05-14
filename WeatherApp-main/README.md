Instructions and Procedure for API:
Weather data for this app will come from Visual Crossing Weather Data & API (https://www.visualcrossing.com/).
You will need to set up a free account and get an API key to use this API. You can create your account at https://www.visualcrossing.com/sign-up. Follow the instructions there to set up your free account. When asked your reason for setting up an account, you can say something like “Signing up for education”.
Once you have your account, log in, click on “Account” (upper-right). You can find you’re your API key on that account page, in the “Your Details” section, under the “Key” label. Your free account has a limit of 1000 calls/day.
We will use their “Timeline Weather” API endpoint (documentation at https://www.visualcrossing.com/resources/documentation/weather-api/timeline-weather-api/).
That call will look like:
https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/<loc string>?unitGroup=us&lang=en&key=<your API key>

'''Preview of the Application'''

![image](https://user-images.githubusercontent.com/17993648/201844875-cc480af5-9db4-42a5-882c-313140134b1f.png)

![image](https://user-images.githubusercontent.com/17993648/201844931-afe67781-f0ec-4d5e-963e-f2b65c905fd5.png)

