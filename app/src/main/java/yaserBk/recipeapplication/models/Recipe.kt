package yaserBk.recipeapplication.models

/*
* GSon uses this data class to map JSON objects from recipe.json to
* kotlin objects
* A data class was chosen over standard classes as the program only requires
* access to the data-members (name and ingredients) present in each object and
* does not necessitate additional functions/methods.
*/

data class Recipe(
    val name: String,
    val ingredients: LinkedHashSet<String>
)