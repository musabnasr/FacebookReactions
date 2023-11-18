package com.musabnasr.facebookreactions

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.musabnasr.facebookreactions.ui.theme.FacebookReactionsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FacebookReactionsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ReactionBanner()
                }
            }
        }
    }
}

val reactionsList = listOf(
    R.raw.like_reaction,
    R.raw.love_reaction,
    R.raw.care_reaction,
    R.raw.laugh_reaction,
    R.raw.wow_reaction,
    R.raw.sad_reaction,
    R.raw.angry_reaction
)

@Composable
fun ReactionBanner() {
    var isMenuExpanded by remember { mutableStateOf(false) }
    var selectedReaction by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Horizontal List with Reactions
        AnimatedVisibility(isMenuExpanded) {
            LazyRow(
                modifier = Modifier
                    .border(
                        1.dp, Color.Black,
                        shape = RoundedCornerShape(size = 20.dp)
                    ),
                contentPadding = PaddingValues(5.dp)
            ) {
                items(reactionsList) { reaction ->
                    val composition by rememberLottieComposition(
                        LottieCompositionSpec.RawRes(
                            reaction
                        )
                    )
                    LottieAnimation(
                        composition = composition,
                        iterations = LottieConstants.IterateForever,
                        modifier = Modifier
                            .size(40.dp)
                            .clickable {
                                isMenuExpanded = false
                                selectedReaction = reaction
                            }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        // Main Reaction Button
        ReactionButton(
            onClick = { isMenuExpanded = !isMenuExpanded },
            reaction = selectedReaction
        )
    }
}

@Composable
fun ReactionButton(
    onClick: () -> Unit,
    reaction: Int
) {
    if (reaction != 0) {
        val composition by rememberLottieComposition(
            LottieCompositionSpec.RawRes(
                reaction
            )
        )
        LottieAnimation(
            composition = composition,
            modifier = Modifier
                .size(50.dp)
                .clickable {
                    onClick()
                },
            iterations = LottieConstants.IterateForever,
        )
    } else {
        Button(onClick = onClick) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Outlined.ThumbUp,
                    contentDescription = "Add Reaction",
                    tint = Color.White
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = "Like")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewReactionBanner() {
    ReactionBanner()
}