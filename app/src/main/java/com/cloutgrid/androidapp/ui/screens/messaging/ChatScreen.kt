package com.cloutgrid.androidapp.ui.screens.messaging

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.Send
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.cloutgrid.androidapp.data.model.HeaderAction
import com.cloutgrid.androidapp.data.network.ApiConfig
import com.cloutgrid.androidapp.ui.components.CategoryList
import com.cloutgrid.androidapp.ui.components.CloutHeader
import com.cloutgrid.androidapp.ui.components.Empty
import com.cloutgrid.androidapp.ui.theme.OffWhite
import com.cloutgrid.androidapp.ui.theme.Second

@Composable
fun ChatScreen(
    chat: ChatManager = hiltViewModel(),
    onNavigateBack: () -> Boolean,
    onNavigateToMessages: (String, String, String) -> Unit
) {
    val conversations = chat.chats

    var query by remember { mutableStateOf("") }
    val results = chat.results

    val newChat = chat.newConversation

    LaunchedEffect(conversations) {
        if (conversations.isEmpty()) {
            chat.fetchConversations()
        }
    }

    LaunchedEffect(query) {
        if (query.isNotEmpty()) {
            chat.searchUsers(query)
        }
    }

    LaunchedEffect(newChat) {
        if (newChat != null) {
            onNavigateToMessages(
                newChat.id,
                newChat.user.profile.username,
                newChat.user.profile.profilePhoto
            )
            chat.clearNewConversation()
        }
    }

    Scaffold(
        containerColor = OffWhite,
        topBar = {
            CloutHeader(
                title = "Chats",
                icon = HeaderAction(
                    icon = Icons.AutoMirrored.Rounded.ArrowBack,
                    contentDescription = "Back button",
                    onClick = {
                        onNavigateBack()
                    },
                )
            )
        }
    ) { innerPadding ->
        Box(
            Modifier.fillMaxSize(),
        ) {
            LazyColumn(
                Modifier.fillMaxSize(),
                contentPadding = PaddingValues(
                    start = 15.dp,
                    top = innerPadding.calculateTopPadding(),
                    end = 15.dp,
                    bottom = 100.dp
                ),
                verticalArrangement = Arrangement.spacedBy(ListItemDefaults.SegmentedGap)
            ) {
                item {
                    OutlinedTextField(
                        value = query,
                        onValueChange = { query = it },
                        modifier = Modifier
                            .padding(bottom = 15.dp)
                            .fillMaxWidth(),
                        placeholder = {
                            Text("Search for users")
                        },
                        trailingIcon = {
                            if (query.isEmpty()) {
                                Icon(
                                    imageVector = Icons.Rounded.Search,
                                    contentDescription = "Search",
                                    tint = Second,
                                )
                            } else {
                                IconButton({
                                    query = ""
                                }) {
                                    Icon(
                                        imageVector = Icons.Rounded.Close,
                                        contentDescription = "Cancel",
                                        tint = Second,
                                    )
                                }
                            }
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedBorderColor = Second
                        ),
                        maxLines = 1,
                        shape = RoundedCornerShape(15.dp)
                    )
                }

                if (conversations.isEmpty()) {
                    item {
                        Empty(
                            type = "comment",
                            message = "No conversations yet",
                            isLoading = chat.isLoading
                        )
                    }
                }

                if (query.isEmpty()) {
                    itemsIndexed(conversations) { index, conversation ->
                        SegmentedListItem(
                            shapes = ListItemDefaults.segmentedShapes(
                                index = index, count = conversations.count()
                            ),
                            colors = ListItemDefaults.colors(
                                containerColor = Color.White,
                                selectedContainerColor = Second
                            ),
                            leadingContent = {
                                AsyncImage(
                                    model = ApiConfig.current.baseURL + conversation.user.profile.profilePhoto,
                                    contentDescription = "profile photo",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(50.dp)
                                        .clip(CircleShape)
                                )
                            },
                            supportingContent = {
                                Text(
                                    CategoryList.label(
                                        conversation.user.area ?: conversation.user.targetAudience ?: "")
                                )
                            },
                            onClick = {
                                onNavigateToMessages(
                                    conversation.id,
                                    conversation.user.profile.username,
                                    conversation.user.profile.profilePhoto
                                )
                            }
                        ) {
                            Text(
                                conversation.user.profile.name
                            )
                        }
                    }
                } else {
                    if (results.isEmpty()) {
                        item {
                            Empty(
                                type = "general",
                                message = "No users found",
                                isLoading = chat.isLoading
                            )
                        }
                    } else {
                        itemsIndexed(results) { index, user ->
                            SegmentedListItem(
                                shapes = ListItemDefaults.segmentedShapes(
                                    index = index, count = conversations.count()
                                ),
                                colors = ListItemDefaults.colors(
                                    containerColor = Color.White,
                                    selectedContainerColor = Second
                                ),
                                leadingContent = {
                                    AsyncImage(
                                        model = ApiConfig.current.baseURL + user.profile.profilePhoto,
                                        contentDescription = "profile photo",
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .size(50.dp)
                                            .clip(CircleShape)
                                    )
                                },
                                supportingContent = {
                                    Text(
                                        CategoryList.label(
                                            user.area ?: user.targetAudience ?: "")
                                    )
                                },
                                onClick = {
                                    chat.createConversation(user.profile.id)
                                }
                            ) {
                                Text(
                                    user.profile.name
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}