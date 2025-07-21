package com.example.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.navigation.Screen
import com.example.theme.AppAndroidTheme

@Composable
fun Menu(
    navController: NavHostController,
    drawerState: DrawerState,
    onSelectItem: () -> Unit,
    onLogout: () -> Unit,
    content: @Composable () -> Unit
) {
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Spacer(Modifier.height(20.dp))

                Screen.all.forEach { screen ->
                    val isSelected = currentRoute == screen.route

                    NavigationDrawerItem(
                        icon = {
                            Icon(
                                imageVector = screen.icon,
                                contentDescription = screen.title
                            )
                        },
                        label = { Text(screen.title) },
                        selected = isSelected,
                        onClick = {
                            if (!isSelected) {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                                onSelectItem()
                            }
                        },
                        shape = RectangleShape,
                        colors = NavigationDrawerItemDefaults.colors(
                            selectedContainerColor = MaterialTheme.colorScheme.primary,
                            unselectedIconColor = MaterialTheme.colorScheme.onSurface,
                            unselectedTextColor = MaterialTheme.colorScheme.onSurface,
                            selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                            selectedIconColor = MaterialTheme.colorScheme.onPrimary

                        )
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                HorizontalDivider()

                NavigationDrawerItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally),
                    label = {
                        Text(
                            text = stringResource(R.string.log_out),
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Medium)
                        )
                    },
                    selected = false,
                    onClick = { onLogout() },
                    shape = RectangleShape,
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = MaterialTheme.colorScheme.tertiary,
                        unselectedContainerColor = MaterialTheme.colorScheme.primary,
                        unselectedTextColor = Color.White
                    )
                )
            }
        }
    ) {
        content()
    }
}

@Preview(showBackground = true)
@Composable
fun MenuPreview() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Open)

    AppAndroidTheme(darkTheme = false, dynamicColor = false) {
        Menu(
            navController = navController,
            drawerState = drawerState,
            onSelectItem = {},
            onLogout = {},
            content = {
                Text(
                    text = "Contenido principal",
                    modifier = Modifier.padding(16.dp)
                )
            }
        )
    }
}