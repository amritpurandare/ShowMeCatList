# ShowMeCatList

This is a demo application for coding practice.

## App Overview

This application displays the list of all the cats in alphabetical order under a heading of the gender of their owner.

## Build Instructions

- Application has debug and release build types

```
buildTypes {
        release {
            debuggable false
            minifyEnabled true
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
        }
        debug {
            debuggable true
            minifyEnabled false
        }
    }
```

## 3rd Party Libraries used

- com.squareup.retrofit2:retrofit:2.5.0
- com.squareup.retrofit2:converter-gson:2.5.0

## Backend Details

URL - http://agl-developer-test.azurewebsites.net/people.json

## Sample JSON Response

```
[{"name":"Bob","gender":"Male","age":23,"pets":[{"name":"Garfield","type":"Cat"},{"name":"Fido","type":"Dog"}]},
{"name":"Jennifer","gender":"Female","age":18,"pets":[{"name":"Garfield","type":"Cat"}]},
{"name":"Steve","gender":"Male","age":45,"pets":null},
{"name":"Fred","gender":"Male","age":40,"pets":[{"name":"Tom","type":"Cat"},{"name":"Max","type":"Cat"},{"name":"Sam","type":"Dog"},{"name":"Jim","type":"Cat"}]},
{"name":"Samantha","gender":"Female","age":40,"pets":[{"name":"Tabby","type":"Cat"}]},
{"name":"Alice","gender":"Female","age":64,"pets":[{"name":"Simba","type":"Cat"},{"name":"Nemo","type":"Fish"}]}]
```