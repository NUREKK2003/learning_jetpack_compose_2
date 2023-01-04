package com.example.basicscodelab

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.basicscodelab.ui.theme.BasicsCodelabTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BasicsCodelabTheme {
                MyApp(modifier = Modifier.fillMaxSize())
            }
        }
    }
}
@Preview
@Composable
fun MyApp(modifier: Modifier = Modifier){
    // rememberSavable potrafi w odróżnieniu do remember przeżyć zmianę rotacji
    var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }

    Surface(modifier) {
        if(shouldShowOnboarding){
            OnboardingScreen(onContinueClicked = {shouldShowOnboarding = false})
        }else{
            Greetings()
        }
    }
}

@Composable
private fun Greetings(
    modifier: Modifier = Modifier,
    //names: List<String> = listOf("World", "Compose")
    names: List<String> = List(1000) {"$it"} // lambda
){

    LazyColumn(modifier = Modifier.padding(vertical = 4.dp)) {
        items(items = names){ name->
            Greeting(name = name)
        }
    }
}


@Composable
private fun Greeting(name: String) {
    // Dzięki temu remember wartość jeśli już wcześniej istniała zostanie zapamiętana (podobnie jak w przypadku statica w c++)
    val expanded = remember {mutableStateOf(false)}


    Card(
        colors = CardDefaults.cardColors(),
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ){
        Row(
            modifier = Modifier
                .padding(24.dp)
                .animateContentSize { initialValue, targetValue -> }
        )
        {
            Column(
                modifier = Modifier
                    .weight(1f)
            ){
                Text(text = "Hello,")
                Text(
                    text = name,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.ExtraBold // dopisanie do stylu
                    )
                )
                if(expanded.value){
                    Text("Composeum ipsum")
                }
            }
            IconButton(
                // przyjmuje funkcję, a nie wartość!
                onClick = { expanded.value = !expanded.value },
                modifier = Modifier.padding(4.dp)
            ) {
                Icon(
                    if(expanded.value) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                    contentDescription = if(expanded.value) stringResource(R.string.show_less) else stringResource(R.string.show_more)
                )
            }
        }
    }
}

// wyświetlanie dwóch konfiguracji previewów jednocześnie
@Preview(
    showBackground = true,
    widthDp = 320,
    uiMode = UI_MODE_NIGHT_YES,
    name = "Dark"
)
@Preview(showBackground = true, widthDp = 320)
@Composable
fun DefaultPreview() {
    BasicsCodelabTheme {
        Greetings()
    }
}

@Composable
fun OnboardingScreen(
    onContinueClicked: () -> Unit, // callback
    modifier: Modifier = Modifier
){
    // TODO: This state should be hoisted
    // Dzięki temu "by" nie trzeba za każdym razem pisać .value przy odwoływaniu się do wartości zmiennej
    //var shouldShowOnboarding by remember { mutableStateOf(true) }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Welcome to the Basics Codelab!")
        Button(
            modifier = Modifier.padding(vertical = 24.dp),
            onClick =  onContinueClicked
        ) {
            Text("Continue")
        }
    }

}

@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun OnboardingPreview(){
    BasicsCodelabTheme {
        OnboardingScreen(onContinueClicked = {}) // funkcja lambda
    }
}