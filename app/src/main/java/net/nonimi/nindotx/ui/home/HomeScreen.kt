package net.nonimi.nindotx.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import net.nonimi.nindotx.R

/**
 * This is the Home Screen of our application. In Jetpack Compose, a screen is just a
 * "Composable" function. The @Composable annotation tells the compiler that this
 * function is used to describe a piece of UI.
 *
 * @param username The name of the user who logged in. It can be null if no user is logged in.
 * @param onExitClicked A function that will be called when the "Exit" button is clicked.
 *                      This is an example of a "lambda" or an anonymous function.
 * @param onLogoutClicked A function that will be called when the "Logout" button is clicked.
 */
@Composable
fun HomeScreen(username: String?, onExitClicked: () -> Unit, onLogoutClicked: () -> Unit) {
    // Column is a layout composable that places its children in a vertical sequence.
    Column(
        // Modifiers are used to decorate or add behavior to Composables.
        // .fillMaxSize() makes the Column take up the entire available screen space.
        // .border() adds a border around the Column.
        modifier = Modifier
            .fillMaxSize()
            .border(width = 2.dp, color = MaterialTheme.colorScheme.primary)
    ) {
        // Box is a layout composable that places its children on top of each other.
        // We are using it here to create the header bar.
        Box(
            modifier = Modifier
                // .fillMaxWidth() makes the Box take up the full width of its parent (the Column).
                .fillMaxWidth()
                // .background() sets the background color of the Box.
                .background(MaterialTheme.colorScheme.primary)
                // .padding() adds space around the content inside the Box.
                .padding(horizontal = 16.dp, vertical = 4.dp),
            // contentAlignment aligns the children within the Box. Here, it aligns them to the start (left).
            contentAlignment = Alignment.CenterStart
        ) {
            // Row is a layout composable that places its children in a horizontal sequence.
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Text is a composable that displays text.
                Text(
                    // stringResource() is used to load strings from the strings.xml file.
                    // This is the best practice for handling text in Android apps.
                    text = stringResource(R.string.home_title),
                    color = Color.White,
                    fontWeight = FontWeight.Bold // Makes the text bold.
                )
                // Spacer adds a space between composables.
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    // This is a conditional check. If the username is null or empty, it shows "Welcome".
                    // Otherwise, it shows "Welcome <username>".
                    text = if (username.isNullOrEmpty()) stringResource(R.string.welcome_message) else stringResource(R.string.welcome_user_message, username),
                    color = Color.White
                )
            }
        }
        // This Column will hold the main content of the screen, below the header.
        Column(
            modifier = Modifier.fillMaxSize(),
            // verticalArrangement and horizontalAlignment are used to center the content
            // both vertically and horizontally within this Column.
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // This Row will hold our buttons.
            Row {
                // Button is a composable that the user can click.
                Button(onClick = onLogoutClicked) { // The 'onClick' lambda is called when the button is pressed.
                    Text(stringResource(R.string.action_logout))
                }
                Spacer(modifier = Modifier.width(16.dp))
                Button(onClick = onExitClicked) {
                    Text(stringResource(R.string.action_exit))
                }
            }
        }
    }
}
