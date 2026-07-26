package com.cloutgrid.androidapp.ui.screens.messaging

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.cloutgrid.androidapp.R
import com.cloutgrid.androidapp.data.model.HeaderAction
import com.cloutgrid.androidapp.data.model.UserContainer
import com.cloutgrid.androidapp.data.network.ApiConfig
import com.cloutgrid.androidapp.ui.components.CloutHeader
import com.cloutgrid.androidapp.ui.theme.First
import com.cloutgrid.androidapp.ui.theme.OffWhite
import com.cloutgrid.androidapp.ui.theme.Second
import androidx.compose.runtime.collectAsState

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun Messages(
    id: String,
    onNavigateBack: () -> Boolean,
    chat: ChatManager = hiltViewModel(),
    username: String,
    profilePhoto: String
) {
    val isKeyboardOpen = WindowInsets.isImeVisible

    LaunchedEffect(id) {
        chat.disconnectWebSocket()
        chat.fetchMessages(id)
        chat.connectWebSocket(id)
    }

    val messages = chat.messages

    var messageText by remember { mutableStateOf("") }

    Scaffold(
        containerColor = OffWhite,
        topBar = {
            CloutHeader(
                title = "@$username",
                icon = HeaderAction(
                    icon = Icons.AutoMirrored.Rounded.ArrowBack,
                    contentDescription = "back button",
                    onClick = {
                        onNavigateBack()
                    }
                )
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .imePadding()
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentPadding = PaddingValues(
                        top = innerPadding.calculateTopPadding(),
                        bottom = innerPadding.calculateBottomPadding() + 15.dp + 50.dp,
                        start = 15.dp,
                        end = 15.dp
                    ),
                    reverseLayout = true,
                ) {
                    items(messages) { message ->
                        ChatRow(
                            message.content,
                            message.timeAgo,
                            message.sender.profile.username != username,
                            profilePhoto
                        )
                    }
                }
            }

            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(
                        start = 15.dp,
                        end = 15.dp,
                        bottom = if (isKeyboardOpen) 15.dp else 0.dp
                    ),
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                verticalAlignment = Alignment.Top,
            ) {
                OutlinedTextField(
                    value = messageText,
                    onValueChange = { messageText = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding()
                        .imePadding(),
                    placeholder = {
                        Text("Write something...")
                    },
                    trailingIcon = {
                        IconButton({
                            chat.sendLiveMessage(messageText)
                            messageText = ""
                        }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Rounded.Send,
                                contentDescription = "Send message",
                                tint = if (messageText.isNotBlank()) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline,
                            )
                        }
                    },
                    leadingIcon = {
                        AsyncImage(
                            model = (ApiConfig.current.baseURL + chat.user.collectAsState().value?.profile?.profilePhoto),
                            contentDescription = "My Profile Photo",
                            modifier = Modifier
                                .size(30.dp)
                                .clip(CircleShape)
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    ),
                    maxLines = 5,
                    shape = RoundedCornerShape(15.dp)
                )
            }
        }
    }
}

@Composable
private fun ChatRow(
    content: String,
    date: String,
    isSender: Boolean,
    profilePhoto: String
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        if (isSender) {
            Spacer(modifier = Modifier.weight(1f))
        } else {
            AsyncImage(
                model = ApiConfig.current.baseURL + profilePhoto,
                contentDescription = "My Profile Photo",
                modifier = Modifier
                    .size(30.dp)
                    .clip(CircleShape)
            )
        }

        Column(
            modifier = Modifier.padding(
                start = if (isSender) 100.dp else 0.dp,
                end = if (isSender) 0.dp else 100.dp
            )
        ) {
            Text(
                content,
                color = Color.White,
                modifier = Modifier
                    .background(color = if (isSender) First else Second, shape = RoundedCornerShape(15.dp))
                    .padding(15.dp, 10.dp)
                    .widthIn(min = 50.dp)

            )
            Text(
                date,
                color = Color.Gray,
                fontSize = 10.sp,
                modifier = Modifier.padding(start = 10.dp)
            )
        }
    }
}
