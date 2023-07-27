package com.bladoae.pokedex.presentation.pokemondetail.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun ItemDetail(label: String, text: String) {
    Row(
        modifier = Modifier
            .padding(0.dp, 3.dp)
    ) {
        LabelItemDetail(label)
        TextItemDetail(text)
    }
}

@Composable
fun TextItemDetail(text: String) {
    Text(
        text = text,
        modifier = Modifier.padding(5.dp, 0.dp, 0.dp, 0.dp)
    )
}

@Composable
fun LabelItemDetail(text: String) {
    Text(
        text = text,
        fontWeight = FontWeight.Bold,
    )
}