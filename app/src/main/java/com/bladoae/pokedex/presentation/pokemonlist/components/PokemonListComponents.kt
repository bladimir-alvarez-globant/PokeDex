package com.bladoae.pokedex.presentation.pokemonlist.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bladoae.pokedex.domain.model.Pokemon
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@Composable
fun SearchBox(
    onSearch: (value: String) -> Unit
) {
    var textState by remember { mutableStateOf(TextFieldValue()) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .shadow(
                elevation = 10.dp,
                shape = RoundedCornerShape(8.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        // TextField placed within the Box
        val defaultText = "Find your favorite pokemon?"
        BasicTextField(
            value = textState,
            maxLines = 1,
            onValueChange = { textState = it },
            textStyle = LocalTextStyle.current.copy(color = Color.LightGray),
            decorationBox = { innerTextField ->
                Row(
                    Modifier
                        .background(Color.White, RoundedCornerShape(percent = 10))
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    Icon(
                        Icons.Rounded.Search,
                        contentDescription = "",
                        Modifier
                            .padding(2.dp, 0.dp)
                            .size(20.dp),
                        colorResource(id = android.R.color.darker_gray)
                    )
                    if (textState.text.isEmpty()) {
                        Text(
                            text = defaultText,
                            color = Color.LightGray
                        )
                    }
                    // <-- Add this
                    innerTextField()
                }
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = { onSearch.invoke(textState.text) })
        )
    }
}

@ExperimentalGlideComposeApi
@ExperimentalMaterial3Api
@Composable
fun ListPokemon(
    items: List<Pokemon>,
    selectPokemon: (pokemon: Pokemon) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2)
    ) {
        items(items.size) { index ->
            ItemPokemon(items[index], selectPokemon)
        }
    }
}

@ExperimentalGlideComposeApi
@ExperimentalMaterial3Api
@Composable
fun ItemPokemon(
    pokemon: Pokemon,
    selectPokemon: (pokemon: Pokemon) -> Unit
) {
    Card(
        modifier = Modifier.padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        border = BorderStroke(2.dp, Color.Gray),
        onClick = { selectPokemon.invoke(pokemon) }
    ) {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            GlideImage(
                model = pokemon.sprites?.frontDefault,
                contentDescription = "pokemon",
                modifier = Modifier
                    .padding(10.dp, 0.dp)
                    .defaultMinSize(150.dp, 150.dp)
                    .fillMaxWidth(),
            )
            Text(
                text = pokemon.name ?: "",
                modifier = Modifier
                    .padding(10.dp, 5.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
            Text(
                text = "${pokemon.types?.map { it.name }?.joinToString()}",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = Color.Gray,
                fontSize = 12.sp
            )
        }
    }
}
