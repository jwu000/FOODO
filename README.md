# FOODO

Mobile app to help users decide if they should eat out or cook at home. Allows users to compare recipes they find and nearby restaurants. Users will be provided with information such as distance, time, price, and rating. Users can save favorite restaurants and recipes and view past decisions. 

Powered by Yelp API and Spoonacular API. 

Created by Javis Wu and Yi Hou.

Currently on the app store as a beta release. https://play.google.com/store/apps/details?id=com.foodoapp.foodo

## Instructions to run locally
1. Git clone the repo
2. Open the project in Android Studio
3. in /FOODO/res/values/ create a new Values resource file named keys.xml
4. Add this to the file and add in your Spoonacular API key, Yelp API key, and Google Maps API key. They can be obtained at their respective sites. <br>
Spoonacular - https://spoonacular.com/food-api <br>
Yelp - https://www.yelp.com/developers <br>
Google - https://console.cloud.google.com/ <br>
For Google be sure to make a project and enable the Distance Matrix API and Maps SDK for Android <br>
```
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <string name="api_key"></string>
    <string name="api_host">spoonacular-recipe-food-nutrition-v1.p.rapidapi.com</string>
    <string name="yelp_key"></string>
    <string name="google_key"></string>
</resources>
```
5. Setup a Firebase project and follow the instructions. 
6. Press the run button at the top of Android Studio
