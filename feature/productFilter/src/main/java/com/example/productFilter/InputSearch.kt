package com.example.productFilter

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.theme.AppAndroidTheme
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
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 14.sp
            )
        },
        shape = RoundedCornerShape(30.dp),
        modifier = Modifier.fillMaxWidth(),
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search icon",
                tint = MaterialTheme.colorScheme.primary
            )
        },
        textStyle = TextStyle(fontSize = 14.sp),
        singleLine = true,
        minLines = 1,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline,
            cursorColor = MaterialTheme.colorScheme.primary,
            focusedLabelColor = MaterialTheme.colorScheme.primary,
            unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant
        )
    )
}

@Preview(showBackground = true)
@Composable
fun InputSearchPreview() {
    AppAndroidTheme(darkTheme = true, dynamicColor = false) {
        InputSearch(onChange = {})
    }
}
