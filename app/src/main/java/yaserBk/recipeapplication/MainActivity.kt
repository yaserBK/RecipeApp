package yaserBk.recipeapplication
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.NetworkOnMainThreadException
import android.widget.TextView
import com.google.gson.Gson
import yaserBk.recipeapplication.models.Recipe
import java.net.URL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.IOException
import java.io.InputStream


class MainActivity : AppCompatActivity() {
    private val gson = Gson() // library for JSON parsing
    // url pointing to ingredients listed in a json format
    private var targetURL = "https://run.mocky.io/v3/45a5a07f-e981-4918-9c31-090b121d6c5f"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Datastructures to hold available ingredients and list of recipes
        var availableIngredients: Set<String>
        var recipeArray: Array<Recipe>


        GlobalScope.launch(Dispatchers.IO) { // dispatches a coroutine to perform network tasks
            availableIngredients = getIngredientsFromURL(targetURL) // parsing list of ingredients from URL
            recipeArray = parseJsonFromFile("recipes.json") // parsing JSON into an Array of Kotlin Data Class Objects
            setMainText(getPlausibleRecipes(availableIngredients, recipeArray))
        }
    }

    // method used to set text displayed in application when run.
    private fun setMainText(recipeStringName: String){
        var displayText = findViewById<TextView>(R.id.main_text)
        displayText.setText(recipeStringName).toString()
    }

     // function that returns a set of all available ingredients from specified URL
     private fun getIngredientsFromURL(targetURL: String): Set<String>{
        var rawData: String? = null
        try{
            rawData = URL(targetURL).readText()
        }
        catch(e: NetworkOnMainThreadException){
            e.printStackTrace()
        }
        // data stored in HashSet to prevent duplicates.
        return gson.fromJson(rawData, Array<String>::class.java).toSet()
    }

    //
    private fun parseJsonFromFile(assetFile: String): Array<Recipe> {
        var rawJsonData: String? = null
        try{
            val inputStream: InputStream = assets.open(assetFile)
            rawJsonData = inputStream.bufferedReader().use {it.readText() }
        }
        catch(e: IOException){
            e.printStackTrace()
        }
        return gson.fromJson(rawJsonData, Array<Recipe>::class.java)
    }

    // returns a string containing the names of all plausible recipes with available ingredients.
    private fun getPlausibleRecipes(availableIngredients: Set<String>, recipes: Array<Recipe>): String{
        var plausibleRecipes = ""
        for(recipe in recipes){ // iterating through all recipes
            // comparing contents of available ingredients and ingredients necessary for each recipe.
            if(availableIngredients.containsAll(recipe.ingredients)){
                plausibleRecipes+= recipe.name+"\n" // appending plausible recipe name to string.
            }
        }
        return plausibleRecipes
    }
}