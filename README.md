![Logo](https://github.com/Abiri99/GithubUserFinder/blob/main/app/src/main/res/mipmap-xxhdpi/ic_launcher.png?raw=true)

# Github User Finder

You can use this application to search for Github users and view their profile details.

## Color Palette
| Color             | Hex                                                                |
| ----------------- | ------------------------------------------------------------------ |
| Primary Color | ![#365479](http://via.placeholder.com/10/365479/365479) #365479 |
| Primary Variant Color | ![#2f4858](http://via.placeholder.com/10/2f4858/2f4858) #2f4858 |
| Secondary Color | ![#c6509e](http://via.placeholder.com/10/c6509e/c6509e) #c6509e |
| Secondary Variant Color | ![#f64685](http://via.placeholder.com/10/f64685/f64685) #f64685 |
| Surface Color | ![#5a5a95](http://via.placeholder.com/10/5a5a95/5a5a95) #5a5a95 |

## Download APK

Download the APK
from [here](https://drive.google.com/file/d/12cISV9DCBnfoGdp2u0mgt604eJ8zOle9/view?usp=sharing).

## Demo

![](https://github.com/Abiri99/GithubUserFinder/blob/main/asset/demo.gif)

## Technical Topics

These are some of the technical topics of the application. Read more about them in the sections
below.

- Manual Dependency Injection
- Leveraged _javax.net.ssl.HttpsURLConnection_ for network requests
- Structured Concurrency
- SSL Certificate Pinning
- Root Device Access Prevention
- Prevented Running Application on Emulator
- String Obfuscation
- Background Resource Usage optimization
- Security Provider Update
- Automated Test

## Manual Dependency Injection

Created all dependencies in the root composable and injected them manually to the features. The
alternative option was to use ***Hilt*** or ***Dagger*** or a ***Service locator*** which I didn't
find necessary in this project because it is not a large one.

## HttpsURLConnection

In this project, a library like ***Retrofit*** isn't used because it has lots of boilerplate which
is not logical for such a small project and a basic network connection.

## Structured Concurrency

Taking these flows in the application as an example:

- When user have searched for a name in the UserFinder screen and before the data is fetched from
  the server, user changes the query.
- When user have navigated to the UserDetail screen and before the data is fetched from server, user
  navigates back. The application must cancel fetching data from network.

This is what I mean by the term ***Structured Concurrency***.
Handled such a case by using ***Kotlin Coroutines*** cancellation APIs.

## SSL Certificate Pinning

Certificate Pinning is a technique to ensure that the application wouldn't not trust a self-signed
certificate a hacker may have installed on the device.

Pinned the certificate of the api.github.com in the ***network_security_config.xml*** file. Multiple
fingerprints pinned due to the high risk of future server configuration changes.

## Root Device Access Prevention

Referred
to [this solution](https://medium.com/mindorks/restricting-access-of-android-apps-on-root-devices-ed68055c7883)
from [MindOrks](https://mindorks.com/) which has implemented 3 methods where one of them is based on
checking Build Tags, and the other two, try to access system files that by default the app doesn't
have access to.

## Preventing Running on Emulator

Referred to [this solution](https://stackoverflow.com/a/21505193/11604909) to prevent the
application from running on the emulator. It detects emulator by checking ***Build Environment***
and ***System Properties*** but it wouldn't guarantee to detect all emulators.

## String Obfuscation

By default, ***R8*** and ***Proguard*** wouldn't obfuscate Strings, so a Gradle plugin named
[Paranoid](https://github.com/MichaelRocks/paranoid) is used to handle this. All Strings of every
layer are defined in a separate class and annotated with ***@Obfuscate***.

**Why do Strings need to be obfuscated?** Because a hacker could figure out which part of code is
for which part of the UI by searching for UI Strings in the code.

## Background Resource Usage Optimization

Developed a utility named ***rememberStateWithLifecycle()*** which is used in the UI layer and
prevents collecting ***ViewModel***'s state when the application goes to the background. This leads
to a less UI update in the background. Consequently background battery and cpu usage would decrease.

## Security Provider Update

Leveraged ***GooglePlayServices*** APIs to update security providers when the application launches.
This will ensure that the application is safe against SSL probable vulnerabilities.

## Automated Test

Developed 18 unit tests for important functionalities. Note that if you want to run the tests with
coverage, make sure that you have configured your test coverage runner to use JaCoCo.