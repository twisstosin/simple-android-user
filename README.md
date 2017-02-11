# Simple-Android-User
![alt tag](https://github.com/twisstosin/simple-android-user/blob/master/app/src/main/res/drawable/icon.png)

#Demo
![alt tag](https://github.com/twisstosin/simple-android-user/blob/master/device_demo.gif)

#Importing
Copy the simple-android-user-release.aar file to your computer and in Android Studio Goto File - New - Import Module
Then browse to the aar file location and import.

Now Goto File - Project Structure - Dependencies
Then Click the green "+" button and click Module Dependency.

You can now choose simple-android-user, apply and wait to sync.
And you're good to go.

#Usage
Create a reference to initialize in any activity or fragment with your user class
```
SimpleUserStore simpleUserStore = new SimpleUserStore(this,User.class);
```


To Store User, Call SimpleUserStore.storeUser(YOUR_USER_OBJECT);
```
//save user
simpleUserStore.storeUser(newUser);
```

To Retrieve User, Call SimpleUserStore.getLoggedInUser();
```
//get user
User user = simpleUserStore.getLoggedInUser();
```

# License

Copyright 2017 Tosin Omotoyinbo

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.