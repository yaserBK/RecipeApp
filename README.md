# RecipeApp

## Dependencies used (app/build.gradle): 

1. GSon - to map JSON data to Kotlin Objects 
2. kotlinx.coroutines - Used to start a couroutine concurrent to the main thread. 

## Permissions added to app/src/main/AndroidManifest.xml:

1. ```<uses-permission android:name="android.permission.INTERNET"/>``` - so as to allow network commands/actions. 

## DesignChoices:

### 1. ```data class Recipe(...)```: 

GSon maps JSON recipe objects from /app/src/main/assets/recipes.json 
Data class used rather than a standard class as Recipe objects do not need to encapsulate additional logic
and merely act as data containers.

this data class contains two data members:
```
 val name: String,
 val ingredients: LinkedHashSet<String>
```

The name of the recipe is stored as a String - straight forward choice.
<br />
Ingredients are stored in a HashSet - the reasoning behind which is explained further below.

Code for this class can be found in the following directory path:
```app/src/main/java/yaserBk/recipeapplication/models/Recipe.kt```

### 2. ```class MainActivity: AppCompatActivity(){...}```:

The main logic/functionality asked for in the assessmenet specifications is carried out by 3 specific functions: 

2.1. __```getIngredientsFromULR(targetURL: String)`: Set<String>```__:
<br />
Takes the URL specified (https://run.mocky.io/v3/45a5a07f-e981-4918-9c31-090b121d6c5f) and maps the list of ingredients
to a HashSet of Strings
  
2.2. __```parseJsonFromFile(assetFile: String): Array<Recipe>```__: 
<br />
Takes the name of the recipe.json file (kept in the assets folder), parses the file and maps the JSON objects to an Array of Recipe Data Objects, making the the recipes 
easily iteratable. 

2.3. __```getPlausibleRecipes(availableIngredients Set<String>, recipes: Array<Recipe>): String```__: 
<br />

This function iterates through every  recipe in the recipe array and checks to see whether the entire set of ingredients (ie recipe.ingredients) exists in the set of availbleIngredients 
(using the ```Set.containsAll()``` function). 
If recipe.ingredients exist in availableIngredients, then ```recipe.name``` is appended on to ```var plausibleRecipes: String```. Once the recipe array is iterated through, the plausibleRecipes string is returned to the function call. 

Once this string is returned, funciton called SetMainText changes the ```TextView.main_text``` attribute to the ```plausibleRecipes``` string (i.e. the text displayed in-app on start)

The code for this class can be found in the follow directory path:
```/app/src/main/java/yaserBk/recipeapplication/MainActivity.kt```


__Why is a HashSet used to store availableIngredients and recipe.ingredients rather than an Array/ArrayList/List?__ 
1. The ```ingredients``` and ```availableIngredients``` do not need to be iterated through. 
2. Use of HashSet prevents duplicate entries.
3. The use of a HashSet, rather than an ArrayList, reduces time complexity when performing the ```compareAll()``` operation on the two sets (in the ```getPlausibleRecipes()``` function).


__The use of a coroutine for I/O operations:__

A majority of the logic for this application is carried out within a co-routine (using ```Dispatcher.IO```) as networking and read/write operations are not allowed within the main thread (for the sake of main safety).
Further, Dispatcher.IO is optimized to perform disk and network I/O tasks. 
 

__Notes:__

I am very open to feedback regarding logic and structure! I understand that the code in ```MainActivity.kt``` doesnt need to be broken down so heavily into functions, especially since the declared functions do not need to be reused (within the scope of the assessment). If youd like for me to re-do parts of the program with some feedback or specifics in mind, please do let me know. 
<br />
<br />
Thanks! 
<br />
\- Yaser Bk.
