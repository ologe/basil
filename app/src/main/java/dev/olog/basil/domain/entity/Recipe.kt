package dev.olog.basil.domain.entity

data class Recipe(
        val id: Long,
        val name: String,
        val description: String,
        val people: Int,
        val calories: Int,
        val ingredients: MutableList<Ingredient>,
        val images: MutableList<String>,
        val tags: MutableList<Tag>
) {

    fun addImage(imageUri: String){
        this.images.add(imageUri)
    }

    fun addTag(tag: Tag) {
        this.tags.add(tag)
    }

    fun addIngredient(ingredient: Ingredient) {
        this.ingredients.add(ingredient)
    }

}
