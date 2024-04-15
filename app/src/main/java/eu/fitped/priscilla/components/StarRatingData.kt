package eu.fitped.priscilla.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Icon

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import eu.fitped.priscilla.R

@Composable
fun StarRating(
    modifier: Modifier = Modifier,
    numberOfStars: Int = 5,
    score: Int,
    maxScore: Int
) {
    val oneStar = maxScore / numberOfStars
    val filledStars = score / oneStar
    val shouldAddHalfStar = score % oneStar != 0
    Row(
        modifier = modifier
    ) {
        for (i in 0..<numberOfStars) {
            if (i < filledStars) {
                Icon(
                    painter = painterResource(R.drawable.star_fill_24px),
                    contentDescription = null,
                )
            } else if (i == filledStars && shouldAddHalfStar) {
                Icon(
                    painter = painterResource(R.drawable.star_half_24px),
                    contentDescription = null,
                )
            } else {
                Icon(
                    painter = painterResource(R.drawable.star_24px),
                    contentDescription = null,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StarRatingPreview() {
    Column {
        for (i in 0..10) {
            StarRatingData(score = i, maxScore = 10)
        }
    }
}

data class StarRatingData (
    val score: Int,
    val maxScore: Int
)
