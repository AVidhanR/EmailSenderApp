package com.example.emailsenderapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.emailsenderapp.ui.theme.EmailSenderAppTheme
import com.example.emailsenderapp.ui.theme.MyBlue
import com.example.emailsenderapp.ui.theme.Purple40

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EmailSenderAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    EmailSenderApp()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun EmailSenderApp() {
    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.background(color = Purple40),
                title = {
                    Text(
                        text = "Email Sender App",
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            )
        }
    ) {
        val emailBody = remember { mutableStateOf(TextFieldValue())}
        val emailSubject = remember { mutableStateOf(TextFieldValue()) }
        val emailSenderAddress = remember { mutableStateOf(TextFieldValue()) }

        /** we are creating a variable for a context
         * here [LocalContext] is a Composition Local which find the context of current screen through .current */
        val cxt = LocalContext.current

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth().fillMaxSize().fillMaxHeight()
        ) {

            TextFiledBodies(emailState = emailSenderAddress, data = "Enter the senders email address")
            Spacer(modifier = Modifier.padding(top = 12.dp))
            TextFiledBodies(emailState = emailSubject, data = "Enter the email subject")
            Spacer(modifier = Modifier.padding(top = 12.dp))
            TextFiledBodies(emailState = emailBody, data = "Enter the email body")
            Spacer(modifier = Modifier.padding(top = 12.dp))
            Button(
                onClick = {

                    /** [Intent] -> It's like an intention to do something.
                     * below line we are creating an intent to send an email */
                    val i = Intent(Intent.ACTION_SEND)

                    /** Below lines we are sending the email subject and body
                     * Here our intention is not only sending an email but also
                     * */
                    val emailAddress = arrayOf(emailSenderAddress.value.text)
                    i.putExtra(Intent.EXTRA_EMAIL, emailAddress)
                    i.putExtra(Intent.EXTRA_SUBJECT, emailSubject.value.text)
                    i.putExtra(Intent.EXTRA_TEXT, emailBody.value.text)

                    /** below line, we are setting the type of [Intent]
                     * The [MIME] type of the data being handled by this intent. */
                    i.setType("message/rfc822")

                    // Starting our activity to open an email application
                    cxt.startActivity(
                        Intent.createChooser(i,"Choose an Email client : ")
                    )
                }
            ) {
                Text(
                    text = "Send mail",
                    modifier = Modifier.padding(10.dp),
                    color = Color.White,
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
fun TextFiledBodies(
    emailState: MutableState<TextFieldValue>,
    data: String
) {
    TextField(
        value = emailState.value,
        onValueChange = { emailState.value = it },
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        placeholder = { Text(text = data) }
    )
}

@Preview(showBackground = true)
@Composable
fun EmailSenderAppPreview() {
    EmailSenderAppTheme {
        EmailSenderApp()
    }
}