# LinkedinIntegration

This repo contains sample code for integrating android app with Linkedin Api's. Make sure you have Linkedin App on your phone.
<br />
Follow these steps to integrate your apps with Linkedin:
<br />


# Step 1 :<br />

Please include the following as a dependency in your android project build.gradle :<br />
compile 'com.github.nandinicbit1981:Linkedinintegration:2.0.1'

In your android activity 'oncreate' method, Include this :

        Intent intent = new Intent(this, LinkedinIntegrationActivity.class);
        intent.putExtra(SHOW_KEYHASH, true);
        intent.putExtra(POST_LINKEDIN, true);
        intent.putExtra(SIGN_IN_LINKEDIN, true);
        startActivity(intent);

This will enable keyhash, linkedin sign in , posting to linkedin buttons.

# Step 2 :<br />
Login to Linkedin Account using your existing credentials or create a linkedin account.
<br />

# Step 3 :<br />
Create an app on Linkedin developer apps : 
   -  https://www.linkedin.com/developer/apps
   -  Click on "Create Application" and fill in all the fields to create the app.

# Step 4 :<br />
In order for android app to integrate with Linkedin, we need to add Package hash and application package name to linkedin app
we create in step 2.
<br />
   - Run the app and click on "Get the HashKey" Button, In the "Android Monitor" of your android studio, you will see the 
   Keyhash.Copy the keyhash.
   
   - Open https://www.linkedin.com/developer/apps in the browser, and click on the app you created in step 2.
   
   - Click on the mobile option from the menu, and add Package name(Your android apps package name) and Keyhash from above.Don't
   forget to click Update on the bottom of the page.
   
# Step 5 : <br />
Now relaunch this app on your android phone and click on "Sign in with Linkedin" and then you should be able to connect to the app.

# Step 6 :<br />
In order to create a post on the linkedin profile, Click on "Post to Linkedin" on this app.


 <br/><br/>

 # License

  Copyright (C) 2015 The Android Open Source Project
 <br/><br/>
  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at
 <br/><br/>
     http://www.apache.org/licenses/LICENSE-2.0
 <br/><br/>
  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License. <br/><br/>
