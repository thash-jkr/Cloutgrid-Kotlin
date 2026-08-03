package com.cloutgrid.androidapp.ui.screens.integration

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.automirrored.rounded.TrendingUp
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.outlined.EditNote
import androidx.compose.material.icons.outlined.PersonRemoveAlt1
import androidx.compose.material.icons.outlined.Report
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.rounded.BarChart
import androidx.compose.material.icons.rounded.Block
import androidx.compose.material.icons.rounded.BookmarkBorder
import androidx.compose.material.icons.rounded.DeleteSweep
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Sync
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.cloutgrid.androidapp.R
import com.cloutgrid.androidapp.data.model.HeaderAction
import com.cloutgrid.androidapp.data.model.InstagramMediaModel
import com.cloutgrid.androidapp.data.model.InstagramPageModel
import com.cloutgrid.androidapp.data.model.MenuAction
import com.cloutgrid.androidapp.data.model.ProfileInsightModel
import com.cloutgrid.androidapp.ui.components.CloutAlert
import com.cloutgrid.androidapp.ui.components.CloutHeader
import com.cloutgrid.androidapp.ui.components.Empty
import com.cloutgrid.androidapp.ui.theme.OffWhite

@Composable
fun Instagram(
    integration: IntegrationManager = hiltViewModel()
) {
    val user = integration.user

    var showDisconnectAlert by remember { mutableStateOf(false) }
    var showPurgeAlert by remember { mutableStateOf(false) }

    LaunchedEffect(user) {
        if (user?.instagramConnected == true && integration.instagramPage == null) {
            integration.loadOwnInstagramProfile(user.profile.username)
            integration.loadOwnInstagramMedia(user.profile.username)
        }
    }

    Scaffold(
        containerColor = OffWhite,
        topBar = {
            CloutHeader(
                title = "Instagram Insights",
                actions = listOf(
                    HeaderAction(
                        icon = Icons.Rounded.Menu,
                        contentDescription = "Menu",
                        onClick = {  },
                        menuItems = listOf(
                            MenuAction(
                                title = "Sync profile",
                                icon = Icons.Rounded.Sync,
                                onClick = {
                                    integration.fetchInstagramProfile()
                                    integration.fetchInstagramMedia()

                                    if (user != null) {
                                        integration.loadOwnInstagramProfile(user.profile.username)
                                        integration.loadOwnInstagramMedia(user.profile.username)
                                    }
                                }
                            ),
                            MenuAction(
                                title = "Disconnect Instagram",
                                icon = Icons.Rounded.Block,
                                onClick = {
                                    showDisconnectAlert = true
                                }
                            ),
                            MenuAction(
                                title = "Purge Instagram",
                                icon = Icons.Rounded.DeleteSweep,
                                onClick = {
                                    showPurgeAlert = true
                                }
                            ),
                        )
                    ),
                )
            )
        }
    ) { innerPadding ->
        Column(
            Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = innerPadding.calculateTopPadding(),
                        bottom = 100.dp
                    )
            ) {
                if (user?.instagramConnected == true) {
                    Column(
                        Modifier.padding(vertical = 15.dp),
                        verticalArrangement = Arrangement.spacedBy(15.dp)
                    ) {
                        if (integration.instagramPage != null) {
                            InstagramHeader(integration.instagramPage!!)

                            InstagramInsights(insights = integration.instagramPage!!.insights)
                        }

                        InstagramMedia(
                            igMedia = integration.instagramMedia,
                            type = "IMAGE"
                        )

                        InstagramMedia(
                            igMedia = integration.instagramMedia,
                            type = "VIDEO"
                        )
                    }

                    if (
                        ( integration.instagramPage == null ||
                                integration.instagramMedia.isEmpty()) &&
                        integration.isLoading
                    ) {
                        Empty(
                            type = "instagram",
                            message = "Loading...",
                            isLoading = integration.isLoading
                        )
                    }
                } else {
                    NotConnected()
                }
            }
        }
    }

    if (showDisconnectAlert) {
        CloutAlert(
            { integration.disconnectInstagram() },
            { showDisconnectAlert = false },
            "Disconnect Instagram?",
            "Are you sure you want to disconnect Instagram?",
            "Disconnect",
            Icons.Rounded.Block
        )
    }

    if (showPurgeAlert) {
        CloutAlert(
            { integration.purgeInstagram() },
            { showPurgeAlert = false },
            "Delete Instagram Data?",
            "Are you sure you want to delete all your Instagram data with us?",
            "Delete",
            Icons.Rounded.DeleteSweep
        )
    }
}

@Composable
fun InstagramHeader(
    page: InstagramPageModel
) {
    Row(
        Modifier
            .padding(horizontal = 15.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            AsyncImage(
                model = page.profilePicture,
                contentDescription = "instagram profile photo",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape),
                placeholder = painterResource(id = R.drawable.default_profile)
            )

            Button({}) {
                Text("@${page.username}")
            }
        }

        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.spacedBy(5.dp),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            StatItem(value = "${page.followers}", label = "Followers")
            StatItem(value = "${page.followings}", label = "Followings")
            StatItem(value = "${page.mediaCount}", label = "Posts")
        }
    }
}

@Composable
fun InstagramInsights(
    insights: List<ProfileInsightModel>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = "Profile Insights",
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(start = 15.dp, bottom = 10.dp)
                .fillMaxWidth()
        )

        LazyRow(
            contentPadding = PaddingValues(horizontal = 15.dp),
            horizontalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            items(insights, key = { it.id }) { metric ->
                ElevatedCard(
                    colors = CardDefaults.elevatedCardColors(
                        containerColor = Color.White
                    ),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier
                        .width(200.dp)
                        .height(125.dp)
                        .padding(bottom = 20.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(
                            text = "${metric.totalValue.value}",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = metric.title,
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun InstagramMedia(
    igMedia: List<InstagramMediaModel>,
    type: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = if (type == "IMAGE") "Recent Posts" else "Recent Reels",
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, bottom = 10.dp)
        )

        val mediaList = igMedia.filter { it.mediaType == type }

        if (mediaList.isEmpty()) {
            Text(
                text = "No ${if (type == "IMAGE") "posts" else "reels"} found",
                modifier = Modifier.padding(start = 15.dp),
                color = Color.Gray
            )
        }

        LazyRow(
            contentPadding = PaddingValues(horizontal = 15.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            items(
                mediaList,
                key = { it.id }
            ) { media ->
                Box(
                    contentAlignment = Alignment.BottomStart,
                    modifier = Modifier.padding(bottom = 20.dp)
                ) {
                    AsyncImage(
                        model = if (media.mediaType == "VIDEO") media.thumbnailUrl else media.mediaUrl,
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .width(200.dp)
                            .clip(RoundedCornerShape(20.dp))
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .offset(x = 10.dp, y = (-10).dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color.White)
                            .padding(horizontal = 7.dp, vertical = 4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Favorite,
                            contentDescription = "Likes",
                            tint = Color.Red,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = "${media.likeCount}",
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(start = 2.dp)
                        )

                        media.insights.forEach { insight ->
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = getIconForMetric(insight.name),
                                    contentDescription = insight.name,
                                    modifier = Modifier
                                        .size(22.dp)
                                        .padding(start = 6.dp)
                                )

                                insight.values.forEach { insightValue ->
                                    Text(
                                        text = "${insightValue.value}",
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(start = 2.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

private fun getIconForMetric(metricName: String): ImageVector {
    return when (metricName.lowercase()) {
        "engagement" -> Icons.AutoMirrored.Rounded.TrendingUp
        "impressions", "views" -> Icons.Rounded.Visibility
        "reach" -> Icons.AutoMirrored.Rounded.TrendingUp
        "saves" -> Icons.Rounded.BookmarkBorder
        else -> Icons.Rounded.BarChart
    }
}

@Composable
private fun StatItem(
    value: String,
    label: String
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = value,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = label,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun NotConnected() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Button({}) {
                Text("Connect Instagram")
            }

            Text(
                "This feature is in development",
                fontSize = 10.sp,
                color = Color.Gray
            )
        }

        InstagramConstants()
    }
}