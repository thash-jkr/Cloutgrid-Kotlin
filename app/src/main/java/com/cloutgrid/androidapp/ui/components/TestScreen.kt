package com.cloutgrid.androidapp.ui.components

import android.text.Layout
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.automirrored.rounded.Send
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Work
import androidx.compose.material.icons.outlined.ArrowCircleRight
import androidx.compose.material.icons.rounded.ArrowCircleUp
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingToolbarDefaults
import androidx.compose.material3.FloatingToolbarDefaults.ScreenOffset
import androidx.compose.material3.FloatingToolbarExitDirection.Companion.Bottom
import androidx.compose.material3.HorizontalFloatingToolbar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedListItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldLabelPosition
import androidx.compose.material3.TooltipAnchorPosition
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.paneTitle
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil3.compose.AsyncImage
import com.cloutgrid.androidapp.R
import com.cloutgrid.androidapp.data.network.ApiConfig
import com.cloutgrid.androidapp.ui.theme.First
import com.cloutgrid.androidapp.ui.theme.OffWhite
import com.cloutgrid.androidapp.ui.theme.Second
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@Composable
fun TestScreen() {
    Column(
        modifier = Modifier
            .background(OffWhite)
            .padding(15.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        ChatInputBar(
            "",
            {},
            {}
        )

        OutlinedTextField(
            value = "",
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth(),
            label = {
                Text("Write something...")
            },
            trailingIcon = {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.Send,
                    contentDescription = "Send message",
                    tint = MaterialTheme.colorScheme.primary,
                )
            }
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            verticalAlignment = Alignment.Top
        ) {
            OutlinedTextField(
                value = "",
                onValueChange = {  },
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .imePadding(),
                placeholder = {
                    Text("Write something...")
                },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.Send,
                        contentDescription = "Send message",
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                ),
                maxLines = 5,
                leadingIcon = {
                    Image(
                        painter = painterResource(id = R.drawable.master_chief),
                        contentDescription = "Test Image",
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(30.dp)
                    )
                },
                shape = RoundedCornerShape(15.dp)
            )
        }
    }
}

@Composable
private fun ChatInputBar(
    message: String,
    onMessageChange: (String) -> Unit,
    onSendClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 1.dp,
                shape = CircleShape,
                clip = true
            )
            .background(Color.White, shape = CircleShape)
            .padding(horizontal = 6.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.master_chief),
            contentDescription = "Test Image",
            modifier = Modifier
                .clip(CircleShape)
                .size(50.dp)
        )

        BasicTextField(
            value = message,
            onValueChange = onMessageChange,
            modifier = Modifier
                .weight(1f)
                .height(50.dp)
                .background(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.06f),
                    shape = RoundedCornerShape(10.dp)
                )
                .border(1.dp, Color.Black.copy(alpha = 0.15f), RoundedCornerShape(10.dp))
                .padding(horizontal = 12.dp),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.CenterStart
                ) {
                    if (message.isEmpty()) {
                        Text(
                            text = "Start typing",
                        )
                    }
                    innerTextField()
                }
            },
        )

        IconButton(
            onClick = onSendClick,
            enabled = message.isNotBlank(),
            modifier = Modifier.size(50.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Rounded.Send,
                contentDescription = "Send message",
                tint = if (message.isNotBlank()) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp)
            )
        }
    }
}

@Composable
fun Chat() {
    Column(
        Modifier
            .fillMaxSize()
            .background(OffWhite)
            .padding(15.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        ChatRow(
            LoremIpsum(words = 5).values.first(),
            "Just now",
            true
        )

        ChatRow(
            LoremIpsum(words = 1).values.first(),
            "Just now",
            false
        )

        ChatRow(
            LoremIpsum(words = 15).values.first(),
            "Just now",
            true
        )

        ChatRow(
            LoremIpsum(words = 1).values.first(),
            "Just now",
            false
        )

        ChatRow(
            LoremIpsum(words = 35).values.first(),
            "Just now",
            false
        )
    }
}

@Composable
fun ChatRow(
    content: String,
    date: String,
    isSender: Boolean
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        if (isSender) {
            Spacer(modifier = Modifier.weight(1f))
        } else {
            Image(
                painter = painterResource(id = R.drawable.master_chief),
                contentDescription = "Test Image",
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

@Composable
@Preview
fun TestScreenPreview() {
    TestScreen()
}