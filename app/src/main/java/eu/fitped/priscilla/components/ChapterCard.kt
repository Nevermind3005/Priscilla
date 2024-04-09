package eu.fitped.priscilla.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import eu.fitped.priscilla.R
import eu.fitped.priscilla.model.ChapterDto

@Composable
fun ChapterCard(
    modifier: Modifier = Modifier,
    chapterDto: ChapterDto,
    onClick: (() -> Unit)? = null
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(enabled = onClick != null, onClick = onClick ?: {}),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp)
        )
    ) {
        Text(
            text = chapterDto.chapterName,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(.5f).padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                ScoreIcon(
                    count = "${chapterDto.tasksFinished + chapterDto.tasksNotFinished}",
                    passed = "${chapterDto.tasksFinished}",
                    iconPainter = painterResource(R.drawable.quiz_24px)
                )
                LinearProgressIndicator(
                    progress = 100.0f / (chapterDto.tasksFinished + chapterDto.tasksNotFinished) * chapterDto.tasksFinished / 100.0f
                )
            }
            Column(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                ScoreIcon(
                    count = "${chapterDto.programsFinished + chapterDto.programsNotFinished}",
                    passed = "${chapterDto.programsFinished}",
                    iconPainter = painterResource(R.drawable.code_24px)
                )
                LinearProgressIndicator(
                    progress = 100.0f / (chapterDto.programsFinished + chapterDto.programsNotFinished) * chapterDto.programsFinished / 100.0f
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChapterCardPreview() {
    val chapterDto = ChapterDto(
        chapterOrder = 13,
        chapterId = 54,
        chapterName = "Jazyk Java",
        chapterIcon = "data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0idXRmLTgiPz4KPCEtLSBHZW5lcmF0b3I6IEFkb2JlIElsbHVzdHJhdG9yIDI0LjEuMywgU1ZHIEV4cG9ydCBQbHVnLUluIC4gU1ZHIFZlcnNpb246IDYuMDAgQnVpbGQgMCkgIC0tPgo8c3ZnIHZlcnNpb249IjEuMSIgaWQ9IlZyc3R2YV8xIiB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIiB4PSIwcHgiIHk9IjBweCIKCSB2aWV3Qm94PSIwIDAgNDUgNDUiIHN0eWxlPSJlbmFibGUtYmFja2dyb3VuZDpuZXcgMCAwIDQ1IDQ1OyIgeG1sOnNwYWNlPSJwcmVzZXJ2ZSI+CjxzdHlsZSB0eXBlPSJ0ZXh0L2NzcyI+Cgkuc3Qwe2ZpbGw6I0U1M0EzNTt9Cjwvc3R5bGU+CjxnIGlkPSJqYXZhLWljb24iPgoJPHBhdGggY2xhc3M9InN0MCIgZD0iTTIyLjMsOS4xYy0yLDEuNC00LjIsMy01LjQsNS42Yy0yLjEsNC41LDQuMiw5LjUsNC41LDkuN2MwLjIsMC4xLDAuNCwwLjEsMC41LTAuMWMwLjEtMC4xLDAuMS0wLjMsMC0wLjQKCQljMCwwLTIuMy00LjUtMi4yLTcuNmMwLTEuMSwxLjYtMi40LDMuMi0zLjdjMS42LTEuMiwzLTIuNiw0LjItNC4yYzIuMi0zLjYtMC4yLTctMC4zLTcuMWMtMC4xLTAuMi0wLjMtMC4yLTAuNS0wLjEKCQljLTAuMSwwLjEtMC4yLDAuMi0wLjEsMC40YzAuMiwxLjYtMC4xLDMuMy0wLjgsNC43QzI0LjUsNy40LDIzLjUsOC40LDIyLjMsOS4xeiIvPgoJPHBhdGggY2xhc3M9InN0MCIgZD0iTTMwLjYsMTAuNmMwLjItMC4xLDAuMi0wLjMsMC4xLTAuNUMzMC41LDEwLDMwLjQsMTAsMzAuMiwxMGMtMC4zLDAuMS04LjIsMy4yLTguMiw2LjhjMCwxLjgsMC42LDMuNSwxLjksNC44CgkJYzAuMywwLjMsMC41LDAuNiwwLjcsMWMwLjEsMS0wLjEsMS45LTAuNiwyLjhjLTAuMSwwLjIsMCwwLjQsMC4yLDAuNWMwLjEsMC4xLDAuMiwwLDAuMywwYzAuMS0wLjEsMy4zLTIuMywyLjctNQoJCWMtMC4yLTAuOS0wLjYtMS43LTEuMi0yLjRjLTAuNy0xLTEuMi0xLjgtMC40LTMuMUMyNi41LDEzLjcsMzAuNSwxMC43LDMwLjYsMTAuNnoiLz4KCTxwYXRoIGNsYXNzPSJzdDAiIGQ9Ik0xMC4xLDI2Yy0wLjEsMC40LTAuMSwwLjgsMC4yLDEuMmMwLjksMS4yLDMuOSwxLjgsOC41LDEuOGwwLDBoMmM3LjQtMC4zLDEwLjEtMi42LDEwLjItMi43CgkJYzAuMS0wLjEsMC4xLTAuNCwwLTAuNWMtMC4xLTAuMS0wLjItMC4xLTAuNC0wLjFjLTMuNiwwLjctNy4yLDEuMS0xMC44LDFjLTMuOCwwLTUuNy0wLjMtNi4yLTAuNWMxLjEtMC43LDIuMy0xLjEsMy42LTEuMwoJCWMwLjIsMCwwLjMtMC4yLDAuMy0wLjRjMC0wLjItMC4yLTAuMy0wLjMtMC4zQzE2LjEsMjQuMiwxMC43LDI0LjMsMTAuMSwyNnoiLz4KCTxwYXRoIGNsYXNzPSJzdDAiIGQ9Ik0zNC45LDIzLjZjLTEsMC4xLTIuMSwwLjMtMywwLjhjLTAuMiwwLjEtMC4yLDAuMy0wLjEsMC41YzAuMSwwLjEsMC4yLDAuMiwwLjMsMC4yYzAsMCwzLjIsMCwzLjUsMS45CgkJYzAuMywxLjYtMyw0LjItNC4zLDVjLTAuMiwwLjEtMC4yLDAuMy0wLjEsMC41YzAuMSwwLjEsMC4yLDAuMiwwLjMsMC4yaDAuMWMwLjMtMC4xLDcuNS0xLjcsNi44LTUuOQoJCUMzNy44LDI0LjIsMzYuMiwyMy42LDM0LjksMjMuNnoiLz4KCTxwYXRoIGNsYXNzPSJzdDAiIGQ9Ik0yOS45LDMxLjVjMC0wLjEsMC0wLjMtMC4xLTAuNGwtMS43LTEuMmMtMC4xLTAuMS0wLjItMC4xLTAuMy0wLjFjLTEuNSwwLjQtMywwLjYtNC41LDAuOAoJCWMtMS4xLDAuMS0yLjMsMC4yLTMuNCwwLjJjLTIuNiwwLTQuMy0wLjMtNC42LTAuNWMwLDAsMC0wLjEsMC0wLjFjMC4xLTAuMiwwLjMtMC4zLDAuNC0wLjNjMC4yLTAuMSwwLjMtMC4zLDAuMi0wLjQKCQljLTAuMS0wLjItMC4yLTAuMy0wLjQtMC4yYy0xLjcsMC40LTIuNiwxLTIuNSwxLjdjMC4xLDEuMywzLjEsMiw1LjcsMi4yYzAuNCwwLDAuOCwwLDEuMiwwbDAsMGMzLjMtMC4xLDYuNi0wLjYsOS44LTEuNAoJCUMyOS44LDMxLjcsMjkuOSwzMS42LDI5LjksMzEuNXoiLz4KCTxwYXRoIGNsYXNzPSJzdDAiIGQ9Ik0xNi45LDM0LjNjMC4yLTAuMSwwLjItMC4zLDAuMS0wLjVjLTAuMS0wLjEtMC4yLTAuMi0wLjMtMC4yYy0wLjIsMC0yLjMsMC4xLTIuNCwxLjRjMCwwLjQsMC4xLDAuOCwwLjMsMS4xCgkJYzAuNywwLjksMi43LDEuNCw2LDEuNmMwLjQsMCwwLjgsMCwxLjIsMGMyLjUsMCw0LjktMC40LDcuMi0xLjRjMC4yLTAuMSwwLjItMC4zLDAuMi0wLjVjMCwwLDAsMCwwLDBjMC0wLjEtMC4xLTAuMS0wLjEtMC4yCgkJbC0yLjItMS4zYy0wLjEtMC4xLTAuMi0wLjEtMC4zLDBjMCwwLTEuNCwwLjMtMy41LDAuNmMtMC41LDAuMS0xLDAuMS0xLjUsMC4xYy0xLjYsMC0zLjMtMC4yLTQuOS0wLjYKCQlDMTYuOCwzNC40LDE2LjgsMzQuMywxNi45LDM0LjN6Ii8+Cgk8cGF0aCBjbGFzcz0ic3QwIiBkPSJNMTkuOSw0MS43YzkuNywwLDE0LjktMS43LDE1LjktMi44YzAuMy0wLjMsMC40LTAuNiwwLjQtMWMtMC4xLTAuNC0wLjMtMC44LTAuNy0xYy0wLjEtMC4xLTAuMy0wLjEtMC41LDAuMQoJCWMtMC4xLDAuMS0wLjEsMC4zLDAsMC41YzAuMSwwLjEsMC4xLDAuMi0wLjEsMC40Yy0wLjQsMC40LTQuNSwxLjUtMTEuMiwxLjljLTAuOSwwLTEuOSwwLjEtMi45LDAuMWMtNiwwLTEwLjUtMC44LTExLTEuMwoJCWMxLjEtMC42LDIuMi0xLDMuNC0xLjFjMC4yLDAsMC4zLTAuMiwwLjMtMC40YzAtMC4yLTAuMi0wLjMtMC40LTAuM2gtMC40Yy0yLjcsMC4yLTUuOSwwLjUtNi4xLDJjLTAuMSwwLjQsMC4xLDAuOCwwLjQsMS4yCgkJQzcuOSw0MC42LDEwLDQxLjgsMTkuOSw0MS43TDE5LjksNDEuN3oiLz4KCTxwYXRoIGNsYXNzPSJzdDAiIGQ9Ik0zNy43LDM5LjJjLTAuMS0wLjEtMC4zLDAtMC40LDAuMWMwLDAtMS41LDEuNS01LjksMi40Yy0zLjEsMC40LTYuMiwwLjYtOS40LDAuNWMtNC41LDAtOC45LTAuMi04LjktMC4yCgkJYy0wLjIsMC0wLjMsMC4xLTAuNCwwLjNjMCwwLjIsMC4xLDAuMywwLjMsMC4zYzMuNSwwLjcsNy4yLDEuMSwxMC44LDEuMWMyLjksMCw1LjgtMC4yLDguNi0wLjdjNS4xLTAuOSw1LjUtMy40LDUuNS0zLjUKCQlDMzcuOSwzOS40LDM3LjksMzkuMywzNy43LDM5LjJ6Ii8+CjwvZz4KPC9zdmc+Cg==",
        chapterIconSecondary = "",
        tasksNotFinished = 0,
        tasksFinished = 10,
        programsNotFinished = 10,
        programsFinished = 10,
    )
    ChapterCard(chapterDto = chapterDto)
}