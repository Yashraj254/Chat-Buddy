package com.yashraj.chat_presentation


import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissState
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.yashraj.chat_domain.model.common.Message
import com.yashraj.chat_presentation.components.ChatItem
import com.yashraj.chat_presentation.components.DeleteChatDialog
import com.yashraj.chat_presentation.components.LogoutDialog
import com.yashraj.chat_presentation.components.NewChatDialog
import com.yashraj.ui.R
import com.yashraj.ui.TypingAnimation
import com.yashraj.utils.LightGrey
import com.yashraj.utils.TextColor
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class
)
@Composable
fun ChatScreen(viewModel: ChatViewModel = hiltViewModel(), navController: NavController) {
    var text by remember { mutableStateOf("") }
    val state by viewModel.state.collectAsState()
    val list = state.chats!!
    val allChats = viewModel.allChats.collectAsState().value
    val logoutState = viewModel.logoutState.collectAsState().value
    val chatResponse = viewModel.chatResponse.collectAsState().value
    var showCreateChatDialog by remember { mutableStateOf(false) }
    var showDeleteChatDialog by remember { mutableStateOf(false) }
    var showLogoutDialog by remember { mutableStateOf(false) }
    var chatToDelete by remember { mutableStateOf("") }

    val listState = rememberLazyListState()
// Remember a CoroutineScope to be able to launch
    val coroutineScope = rememberCoroutineScope()


    coroutineScope.launch {
        // Animate scroll to the first item
        if (list.isNotEmpty())
            listState.animateScrollToItem(list.size - 1)
    }

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val items = allChats.chats!!
    val selectedItem = remember {
        mutableStateOf("")
    }

    if (items.isEmpty()) {
        selectedItem.value = ""
    }

    if (items.isNotEmpty()) {

        LaunchedEffect(key1 = items.size) {
            if (!items.contains(selectedItem.value)) {
                selectedItem.value = items[0]
            }
            if (selectedItem.value.isBlank()) {
                selectedItem.value = items[0]
            }
            viewModel.getTitleChats(selectedItem.value)
        }
    }


    if (showCreateChatDialog) {
        NewChatDialog(
            onDismiss = {
                showCreateChatDialog = false
            },
            chatName = {
                viewModel.createNewChat(it)
                showCreateChatDialog = false
            }
        )
    }

    if (showDeleteChatDialog) {
        DeleteChatDialog(
            onDismiss = {
                showDeleteChatDialog = false
            },
            delete = {
                viewModel.deleteChat(chatToDelete)
                showDeleteChatDialog = false
            }
        )
    }

    if (showLogoutDialog) {
        LogoutDialog(
            onDismiss = {
                showLogoutDialog = false
            },
            logout = {
                viewModel.logout()
                showLogoutDialog = false

            }
        )
    }

    if (logoutState.isLoggedOut) {
        LaunchedEffect(key1 = true) {
            navController.navigate("login") {
                popUpTo("chat") {
                    inclusive = true
                }
            }
        }
    }
    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet {
                Spacer(Modifier.height(12.dp))
                NavigationDrawerItem(
                    label = {
                        Text(
                            text = "New Chat",
                            color = Blue,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 20.sp
                        )
                    },
                    selected = false,
                    onClick = {
                        showCreateChatDialog = true
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add Chat",
                            tint = Color.White,
                            modifier = Modifier
                                .size(36.dp)
                                .clip(
                                    RoundedCornerShape(8.dp)
                                )
                                .background(color = Blue)
                        )
                    },
                )
                items.forEach { item ->
                    val dismissState = remember(list) {
                        DismissState(DismissValue.Default) { it ->
                            if (it == DismissValue.DismissedToEnd) {
                                showDeleteChatDialog = true
                                chatToDelete = item
                            }
                            false
                        }
                    }
                    SwipeToDismiss(
                        state = dismissState,
                        directions = setOf(DismissDirection.StartToEnd),
                        background = {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Delete Chat",
                                    tint = Color.Red
                                )
                                Text(
                                    text = "Delete",
                                    color = Color.Red
                                )
                            }
                        },
                    ) {
                        NavigationDrawerItem(
                            label = { Text(item) },
                            selected = item == selectedItem.value,
                            onClick = {
                                scope.launch { drawerState.close() }
                                selectedItem.value = item
                                viewModel.getTitleChats(item)
                            },
                            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                        )
                    }

                }
            }
        },
        drawerState = drawerState,
    ) {
        Scaffold(
            topBar = {
                Surface(shadowElevation = 4.dp) {
                    TopAppBar(
                        title = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Image(
                                    painter = painterResource(id = R.drawable.walle_icon),
                                    modifier = Modifier
                                        .size(44.dp)
                                        .padding(end = 8.dp),
                                    contentDescription = null
                                )
                                Column {
                                    Text(text = selectedItem.value.ifBlank { "Chat Buddy" })

                                    if (chatResponse.isLoading)
                                        Row {
                                            Text(text = "Typing", color = Blue, fontSize = 12.sp)
                                            TypingAnimation(
                                                text = "...",
                                                color = Blue,
                                                fontSize = 12.sp,
                                                duration = 300L
                                            )
                                        }
                                    else
                                        Text(text = "Online", color = Blue, fontSize = 12.sp)
                                }
                            }

                        },

                        navigationIcon = {
                            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                Icon(
                                    imageVector = Icons.Default.Menu,
                                    contentDescription = null
                                )
                            }

                        },
                        // Logout Button
                        actions = {
                            IconButton(onClick = {
                                showLogoutDialog = true

                            }) {
                                Icon(
                                    imageVector = Icons.Default.Logout,
                                    contentDescription = null
                                )
                            }
                        }
                    )
                }
            },
            ) {

            Box(modifier = Modifier.padding(it))
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Bottom
            ) {
                val focusRequester = remember { FocusRequester() }

                if (state.isLoading)
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(100.dp)
                    )

                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .padding(top = 56.dp),
                    state = listState,
                    verticalArrangement = Arrangement.Bottom

                ) {
                    items(list.size) { it ->
                        ChatItem(list[it])
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
//                        .heightIn(min = 56.dp, max = 150.dp)
                        .padding(horizontal = 12.dp)
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom

                ) {
                    OutlinedTextField(
                        value = text, onValueChange = { it ->
                            text = it

                        },

                        placeholder = { Text(text = "Enter Message...") },
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 4.dp)
                            .focusRequester(focusRequester)
                            .onFocusEvent { it ->
                                if (it.hasFocus) {
                                    coroutineScope.launch {
                                        // Animate scroll to the first item
                                        if (list.isNotEmpty())
                                            listState.animateScrollToItem(list.size - 1)
                                    }
                                }
                            },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            unfocusedBorderColor = LightGrey,
                            textColor = TextColor,
                            containerColor = LightGrey
                        ),
                        shape = RoundedCornerShape(12.dp)

                    )

                    IconButton(
                        onClick = {
                            if (items.isNotEmpty()) {
                                val messages = list.toMutableList()
                                messages.add(
                                    Message(
                                        content = text,
                                        role = "user"
                                    )
                                )
                                if (!chatResponse.isLoading and text.isNotBlank())
                                    viewModel.getChatResponse(messages, selectedItem.value)
                                text = ""
                            } else
                                showCreateChatDialog = true
                        },
                        modifier = Modifier
                            .then(Modifier.size(50.dp))
                            .border(2.dp, Blue, shape = CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Send,
                            contentDescription = null,
                            tint = if (chatResponse.isLoading) LightGrey else Blue,
                        )

                    }
                }
            }
        }
    }
}

