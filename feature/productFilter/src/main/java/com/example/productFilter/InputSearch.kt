package com.example.productFilter

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.theme.ui.theme.MainColor
import com.example.theme.ui.theme.TertiaryColor
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce

@Composable
fun InputSearch(
    onChange: (String) -> Unit
) {
    var search by rememberSaveable { mutableStateOf("") }

    LaunchedEffect(search) {
        snapshotFlow { search }
            .debounce(300L)
            .collectLatest { debouncedSearch ->
                onChange(debouncedSearch)
            }
    }

    OutlinedTextField(
        value = search,
        onValueChange = { search = it },
        placeholder = {
            Text(
                text = stringResource(R.string.search_placeholder),
                color = Color.Gray,
                fontSize = 14.sp
            )
        },
        shape = RoundedCornerShape(30.dp),
        modifier = Modifier.fillMaxWidth(),
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search icon",
                tint = MainColor
            )
        },
        textStyle = TextStyle(fontSize = 14.sp),
        singleLine = true,
        minLines = 1,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = TertiaryColor,
            unfocusedBorderColor = MainColor
        )
    )
}

@Preview(showBackground = true)
@Composable
fun InputSearchPreview() {
    InputSearch(onChange = {})
}
