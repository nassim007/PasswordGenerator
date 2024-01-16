package com.example.passwordgenerator


import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.getSystemService
import com.example.passwordgenerator.models.password.PasswordGenerator
import com.example.passwordgenerator.models.password.contents.CustomPwdContent
import com.example.passwordgenerator.ui.theme.PasswordGeneratorTheme
import org.w3c.dom.Text

class PasswordGeneratorActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PasswordGen()
        }
    }
}
@Composable
fun PasswordGen() {
    var generatedPassword by remember { mutableStateOf("") }
    var passwordSize by remember { mutableStateOf("8") }
    var customPasswordSetting by remember { mutableStateOf("?!%*@&#") }
    var isUpper by remember { mutableStateOf(true) }
    var isLower by remember { mutableStateOf(true) }
    var isNumeric by remember { mutableStateOf(false) }
    var isCustom by remember { mutableStateOf(false) }



    val context = LocalContext.current

    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                stringResource(id = R.string.genereer_wachtwoord),
                modifier = Modifier.align(CenterHorizontally),
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(10.dp))

            Passwordsize(aantalKarakter = passwordSize, onValueChange =  {
                if ((it.isNotEmpty() && it.toInt() < 200) || it.isEmpty()) {
                    passwordSize = it
                }})
            Column(
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {

                    Text(
                        text = generatedPassword,
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Button(
                        onClick = {
                            val clipboardManager = getSystemService(
                                context,
                                ClipboardManager::class.java
                            ) as ClipboardManager
                            val clipData = ClipData.newPlainText("text", generatedPassword)
                            clipboardManager.setPrimaryClip(clipData)
                            Toast.makeText(context, "Password Copied", Toast.LENGTH_SHORT).show()
                                  },
                        enabled = generatedPassword.isNotEmpty(),

                    ) {
                        Text(stringResource(id = R.string.copy))
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))


            LabeledCheckBox(
                label = stringResource(id = R.string.upper),
                onCheckChange = { isUpper = !isUpper },
                isCheked = isUpper)


            LabeledCheckBox(
                label = stringResource(id = R.string.lower),
                onCheckChange = { isLower = !isLower },
                isCheked =isLower )


            LabeledCheckBox(
                label = stringResource(id = R.string.numbers),
                onCheckChange = { isNumeric = !isNumeric },
                isCheked = isNumeric)


            LabeledCheckBox(label = stringResource(id = R.string.karakter) ,
                onCheckChange = { if (customPasswordSetting.isNotEmpty())
                    isCustom = !isCustom },
                isCheked = isCustom)


            Spacer(modifier = Modifier.height(10.dp))
            Button(
                onClick = {  val pwdGen = PasswordGenerator.Builder()
                    .addUpper(isUpper)
                    .addLower(isLower)
                    .addNumeric(isNumeric)
                    .addCustom(isCustom, CustomPwdContent(customPasswordSetting))
                    .setSize(if (passwordSize.isEmpty()) 8 else passwordSize.toInt())
                    .build()

                    generatedPassword = pwdGen.generatePassword() },
                modifier = Modifier.fillMaxWidth(),
                enabled = (isUpper || isLower || isCustom || isNumeric) && passwordSize.isNotEmpty() && passwordSize.toInt() > 0
            ) {
                Text(text = stringResource(id = R.string.genereer_wachtwoord))
            }

        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Passwordsize(aantalKarakter: String, onValueChange: (String) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.Gray, shape = RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.aantal_karakter),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )
            TextField(
                value = aantalKarakter,
                onValueChange = onValueChange,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                placeholder = { Text("1-100") },
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
fun LabeledCheckBox(label : String, onCheckChange : ()->Unit, isCheked : Boolean ){
    Row(
        modifier = Modifier
            .clickable(
                onClick = onCheckChange

            )
            .fillMaxWidth()
            .padding(vertical = 5.dp),
            horizontalArrangement = Arrangement.SpaceBetween

    ) {
        Text(text = label)
        Switch(checked = isCheked, onCheckedChange = null )

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditableCheckBox(value : String, onCheckChange : ()->Unit,onValueChange :(String)->Unit, isCheked : Boolean ){
    Row(
        modifier = Modifier
            .clickable(
                onClick = onCheckChange

            )
            .fillMaxWidth()
            .padding(vertical = 5.dp),
        verticalAlignment = CenterVertically
    ) {
        Checkbox(checked = isCheked, onCheckedChange = null )
        TextField(value = value, onValueChange = onValueChange)
    }
}





@Preview(showBackground = false, device = "id:Nexus One", showSystemUi = true)
@Composable
fun PasswordGenPreview() {
    PasswordGeneratorTheme {
        PasswordGen()
    }

}