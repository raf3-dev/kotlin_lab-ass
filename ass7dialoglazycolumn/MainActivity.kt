package com.example.ass7dialoglazycolumn

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.ass7dialoglazycolumn.ui.theme.Ass7DialogLazyColumnTheme
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Ass7DialogLazyColumnTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MyScreen(
                        modifier = Modifier.padding(paddingValues = innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun MyScreen(modifier : Modifier) {
    val memberItemsList = remember { mutableStateListOf<Member>() }
    val contextForToast = LocalContext.current

    var showAddDialog by rememberSaveable { mutableStateOf( false) }
    var textFieldName by rememberSaveable { mutableStateOf( "") }
    var textFieldEmail by rememberSaveable { mutableStateOf( "") }
    var textFieldSalary by rememberSaveable { mutableStateOf("") }
    var radioGender by rememberSaveable { mutableStateOf("") }

    var showDeleteDialog by rememberSaveable { mutableStateOf( false) }
    var memberToDelete: Member? by rememberSaveable { mutableStateOf<Member?>( null) }

    Column(modifier = Modifier.padding( 16.dp)) {
        Spacer(modifier = Modifier.height(  20.dp))

        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Member Lists:",
                fontSize = 25.sp,
                modifier = Modifier.weight( 1f)
            )
            Button(onClick = {
                textFieldName = ""
                textFieldEmail = ""
                textFieldSalary = ""
                radioGender = ""

                showAddDialog = true
            }) {
                Text( text = "Add Member")
            }
        }
        Spacer(modifier = Modifier.height(  10.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(  10.dp)
        ) {
            itemsIndexed(items = memberItemsList) { index, item ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    shape = RoundedCornerShape( 6.dp),
                    onClick = {
                        Toast.makeText(contextForToast,  "Click on ${item.name}",
                            Toast.LENGTH_SHORT).show()
                    }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding( 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Name: ${item.name} \nGender: ${item.gender} \nEmail: ${item.email} \nSalary: ${item.salary}" ,

                            modifier = Modifier.weight(  1f)
                        )
                        TextButton(
                            onClick = {
                                memberToDelete = item
                                showDeleteDialog = true
                            }
                        ) {
                            Text(  "Delete", color = Color.Red)
                        }
                    }
                }
            }
        }
    }

    if (showAddDialog) {
        AlertDialog(
            onDismissRequest = { showAddDialog = false },
            title = { Text( text = "Enter Member Information") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(  8.dp)) {
                    OutlinedTextField(
                        value = textFieldName,
                        onValueChange = { textFieldName = it },
                        label = { Text(  "Name") },
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = textFieldEmail,
                        onValueChange = { textFieldEmail = it },
                        label = { Text(  "Email") },
                        singleLine = true
                    )
                    Text("Gender:")
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = radioGender == "Male",
                            onClick = { radioGender = "Male" }
                        )
                        Text(text = "Male")
                        Spacer(modifier = Modifier.width(  5.dp))
                        RadioButton(
                            selected = radioGender == "Female",
                            onClick = { radioGender = "Female" }
                        )
                        Text(text = "Female")
                        Spacer(modifier = Modifier.width(  5.dp))
                        RadioButton(
                            selected = radioGender == "Other",
                            onClick = { radioGender = "Other" }
                        )
                        Text(text = "Other")
                        Spacer(modifier = Modifier.width(  5.dp))
                    }
                    OutlinedTextField(
                        value = textFieldSalary,
                        onValueChange = { textFieldSalary = it },
                        label = { Text("Salary") },
                        singleLine = true
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        if (textFieldName.isNotEmpty() && textFieldEmail.isNotEmpty()) {

                            memberItemsList.add(Member(textFieldName, textFieldEmail, textFieldSalary.toInt(), radioGender))
                            Toast.makeText(contextForToast,  "Member added",
                                Toast.LENGTH_SHORT).show()
                            showAddDialog = false
                        } else {
                            Toast.makeText(contextForToast,  "Please fill all fields",
                                Toast.LENGTH_SHORT).show()
                        }
                    }
                ) { Text(  "Save") }
            },
            dismissButton = {
                TextButton(onClick = { showAddDialog = false }) { Text(  "Cancel") }
            }
        )
    }

    if (showDeleteDialog && memberToDelete != null) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text(  "Warning") },
            text = { Text(  "Are you sure you want to delete ${memberToDelete?.name}?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        memberItemsList.remove( memberToDelete)
                        Toast.makeText(contextForToast,
                            "${memberToDelete?.name} is deleted",
                            Toast.LENGTH_SHORT).show()
                        showDeleteDialog = false
                        memberToDelete = null
                    }
                ) { Text(  "Yes", color = Color.Red) }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) { Text(  "No") }
            }
        )
    }
}