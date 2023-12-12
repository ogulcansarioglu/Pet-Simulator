Ogulcan Sarioglu, D20123805

Class Descriptions:

AuthActivity: This class handles the authorization of user. Used in Login and Registration Screens and it also inserts and read from the user database established
in DatabaseHelper & UserModel. 
CustomAdapter: This class is a custom adapter tailored to be used in ShoppingActivity screen to populate the ListView with items. Inside, there is a ViewHolder class that is
used as a cache for the items. 
DatabaseHelper: This class is designed to be function with user.db and provide CRUD operations, and handles user's registration, login, along with update of information like user's pet. Uses UserModel.
InvDatabaseHelper: This Class is created to be used with inventory database, and also provide CRUD operations, handles the creation, insert, delete of particilar item-sets that is present in the inventory. Make use of Inventory Data Model to perform this functionalities.
InventoryActivity: This class creates the Inventory Screen, populates the listview from the inventory database for user to quickly look up their remaining items. It also creates and enables the use of Meow Bottom Navigator. 
InventoryDataModel: This class is a basic datamodel for the Inventory.
LoginActivity: This class allows user to enter a username and password and looks them up from the database, deciding on either login user to the main screen or given matching error messages.
MainActivity: This class is where the user sees and interacts with the Pet. It uses a thread to implement main logic where with the time, core stats of the Pet decreases until it runs away. It allows for an input with imagebuttons, implements meow bottom navigator and controls the changes, setting and duration of each animation for eat, medicine, play via Lottie animation library. Lastly, it creates random "runaways" and health events that creates a more dynamic sitatuion, and the thread contuines to clock down while the user on other screens. 
SelectPetActivity: This class allows user to select between two pet types, dog or a cat with a imageButton input and navigates to mainActivity. 
ShopListDataModel: This class is the data model for the shoppingActivity.
ShoppingActivity: This class uses CustomAdapter class mentioned above, along with ShopListDataModel to populate the listView for shopping. User also can buy items via ShoppingActivity and with every purchase, the inventory database is updated.
SplashScreenActivity: This class creates a splash screen with an circling animation to show user the app is loading. 
UserModel: this class is the data model for the user used in AuthActivity & LoginActivity and all around the app.

Extra Features:

1. Animations:

The app uses Lottie Library and Assests to create animation for the dog, cat and the splash screen. User can see the immediate effect if they feed their pet, or heal or play with them. The Animations are controlled by the buttonInput of the user, along with a main animation for both dog and cat that is the default. 

In the splash screen there is a circling animation of a picture of two cats hugging, which both sets the tone for the app and creates a "loading" screen.

2. Interactive Bottom Navigation

The app uses a Interactive Bottom Navigation to travel between different pages after the initial login/registration. I used MeowBottomNavigation library to create a modern looking, interactive GUI that is also very simple to use and informative (the active page is marked by an animation on the bottomnavigation).

3. Touch Input and Modern GUI

Both Animations and Interactive Bottom Navigation that I created, along with almost exclusively using interactive touch input all around the app (other than registration) allows for a modern experince when using the app whether it is playing with the pet, or just travelling or scrolling in the shoppingScreen with its' own scrolling animation. 

4. Threads for Main Logic

I used a thread for the main logic of the app, instead of a timer, to keep the thread working when the user are travelling other screens. My main reason is to keep idea of a real pet with needs alive which cannot be just shut downed by going to a different screen. 

What Would I Do If I Had More Time?:

I tried to implement AR into the app, the idea was to allow user to see their pet on AR with animation if they choose to. Both I encountered a lot of problems. Firstly, Google Scene Form library was depricated and even though I attempt to use it with a workaround, it made the app very unstable and didn't work as intended. Other solutions included the use of OpenGL, which I needed to learn, so I have to leave it for now. 

I also wanted to include notifications, that is gonna basically broadcast the events that happens to the pet (whether they got sick, or hungry or run away) but I took a lot more than I realized. 

4. Link:

https://youtu.be/MvBeXV8bd_A

5. Disclaimer Regarding Assests:

I used Lottie's free assets for animations, along with Canvas app for the main logo design. 

