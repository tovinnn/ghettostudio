package com.example.ui

import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.StudioSession
import com.example.ui.theme.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*
import com.example.R
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.drawscope.rotate

@Composable
fun CapsulePillIcon(modifier: Modifier = Modifier, color1: Color = Color(0xFFEF5350), color2: Color = Color(0xFFAB47BC)) {
    Canvas(modifier = modifier.size(24.dp)) {
        val w = size.width
        val h = size.height
        rotate(degrees = 45f) {
            // Left half (pink medication color)
            drawRoundRect(
                color = color1,
                topLeft = Offset(w * 0.12f, h * 0.28f),
                size = androidx.compose.ui.geometry.Size(w * 0.38f, h * 0.44f),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(w * 0.2f, h * 0.2f)
            )
            // Right half (purple medication color)
            drawRoundRect(
                color = color2,
                topLeft = Offset(w * 0.5f, h * 0.28f),
                size = androidx.compose.ui.geometry.Size(w * 0.38f, h * 0.44f),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(w * 0.2f, h * 0.2f)
            )
            // Division split band
            drawRect(
                color = Color.Black.copy(alpha = 0.5f),
                topLeft = Offset(w * 0.48f, h * 0.28f),
                size = androidx.compose.ui.geometry.Size(w * 0.04f, h * 0.44f)
            )
        }
    }
}

@Composable
fun MedicineDropIcon(modifier: Modifier = Modifier, color: Color = Color(0xFFB388FF)) {
    Canvas(modifier = modifier.size(24.dp)) {
        val w = size.width
        val h = size.height
        val path = androidx.compose.ui.graphics.Path().apply {
            moveTo(w / 2f, h * 0.15f)
            cubicTo(w * 0.85f, h * 0.5f, w * 0.85f, h * 0.85f, w / 2f, h * 0.9f)
            cubicTo(w * 0.15f, h * 0.85f, w * 0.15f, h * 0.5f, w / 2f, h * 0.15f)
            close()
        }
        drawPath(path = path, color = color)
        // Shimmer shine
        drawCircle(
            color = Color.White.copy(alpha = 0.35f),
            radius = w * 0.07f,
            center = Offset(w * 0.4f, h * 0.62f)
        )
    }
}

@Composable
fun SyringeIcon(modifier: Modifier = Modifier, color: Color = Color(0xFFFFD54F)) {
    Canvas(modifier = modifier.size(24.dp)) {
        val w = size.width
        val h = size.height
        rotate(degrees = -45f) {
            // Needle line
            drawLine(
                color = Color.LightGray,
                start = Offset(w * 0.08f, h * 0.5f),
                end = Offset(w * 0.32f, h * 0.5f),
                strokeWidth = 1.5.dp.toPx()
            )
            // Barrel stroke
            drawRoundRect(
                color = Color.White.copy(alpha = 0.75f),
                topLeft = Offset(w * 0.32f, h * 0.38f),
                size = androidx.compose.ui.geometry.Size(w * 0.4f, h * 0.24f),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(1.5.dp.toPx()),
                style = androidx.compose.ui.graphics.drawscope.Stroke(width = 1.25.dp.toPx())
            )
            // Medicine dose filling
            drawRoundRect(
                color = color,
                topLeft = Offset(w * 0.32f, h * 0.4f),
                size = androidx.compose.ui.geometry.Size(w * 0.26f, h * 0.2f),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(1.dp.toPx())
            )
            // Plunger line
            drawLine(
                color = Color.White,
                start = Offset(w * 0.72f, h * 0.5f),
                end = Offset(w * 0.88f, h * 0.5f),
                strokeWidth = 2.dp.toPx()
            )
            // Plunger flat base
            drawLine(
                color = Color.White,
                start = Offset(w * 0.88f, h * 0.35f),
                end = Offset(w * 0.88f, h * 0.65f),
                strokeWidth = 2.dp.toPx()
            )
        }
    }
}

@Composable
fun RoundPillIcon(modifier: Modifier = Modifier, color: Color = Color(0xFF4DD0E1)) {
    Canvas(modifier = modifier.size(24.dp)) {
        val w = size.width
        val h = size.height
        val r = w * 0.4f
        drawCircle(color = color, radius = r, center = Offset(w / 2f, h / 2f))
        // Center split line
        drawLine(
            color = Color.Black.copy(alpha = 0.5f),
            start = Offset(w * 0.22f, h * 0.4f),
            end = Offset(w * 0.78f, h * 0.6f),
            strokeWidth = 1.5.dp.toPx()
        )
        // Shimmer gloss
        drawCircle(
            color = Color.White.copy(alpha = 0.3f),
            radius = r * 0.7f,
            center = Offset(w * 0.45f, h * 0.45f)
        )
    }
}

@Composable
fun PainCrossIcon(modifier: Modifier = Modifier, color: Color = Color(0xFFE57373)) {
    Canvas(modifier = modifier.size(24.dp)) {
        val w = size.width
        val h = size.height
        // Vertical tab
        drawRect(
            color = color,
            topLeft = Offset(w * 0.4f, h * 0.16f),
            size = androidx.compose.ui.geometry.Size(w * 0.2f, h * 0.68f)
        )
        // Horizontal tab
        drawRect(
            color = color,
            topLeft = Offset(w * 0.16f, h * 0.4f),
            size = androidx.compose.ui.geometry.Size(w * 0.68f, h * 0.2f)
        )
        // Broken jagged line in center for a shattered/pain emotion
        drawLine(
            color = Color.Black.copy(alpha = 0.65f),
            start = Offset(w * 0.5f, h * 0.3f),
            end = Offset(w * 0.47f, h * 0.5f),
            strokeWidth = 1.5.dp.toPx()
        )
        drawLine(
            color = Color.Black.copy(alpha = 0.65f),
            start = Offset(w * 0.47f, h * 0.5f),
            end = Offset(w * 0.52f, h * 0.7f),
            strokeWidth = 1.5.dp.toPx()
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudioDashboard(viewModel: StudioViewModel) {
    val currentSession by viewModel.currentSession.collectAsState()
    val sessions by viewModel.sessions.collectAsState(initial = emptyList())

    val isRecording by viewModel.isRecordingVocals.collectAsState()
    val isPlaying by viewModel.isPlayingMix.collectAsState()
    val isMixing by viewModel.isMixingAndMastering.collectAsState()
    val mixProgress by viewModel.mixProgress.collectAsState()
    val mixLogs by viewModel.mixLogs.collectAsState()

    val recordingSeconds by viewModel.recordingSeconds.collectAsState()
    val amplitudes by viewModel.amplitudes.collectAsState()
    val aiFeatureStatus by viewModel.aiFeatureStatus.collectAsState()

    val topic by viewModel.vibeTopic.collectAsState()
    val subgenre by viewModel.vibeSubgenre.collectAsState()
    val statusMessage by viewModel.recordingStatusMessage.collectAsState()

    val scope = rememberCoroutineScope()
    var activeTab by remember { mutableStateOf("STUDIO") } // "STUDIO", "MIXER", "WRITING BOARD", "HISTORY"
    var showNewSessionDialog by remember { mutableStateOf(false) }
    var newSessionName by remember { mutableStateOf("") }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.Transparent,
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "LIVE SESSION",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = TubeGold,
                            fontFamily = FontFamily.Monospace,
                            letterSpacing = 2.sp
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(7.dp)
                                    .clip(CircleShape)
                                    .background(if (isRecording) Color.Red else TextSecondary)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = currentSession.sessionName,
                                color = TextPrimary,
                                fontWeight = FontWeight.Light,
                                fontSize = 18.sp,
                                maxLines = 1
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "/ Vol. ${sessions.size + 1}",
                                color = TextSecondary,
                                fontWeight = FontWeight.Normal,
                                fontSize = 12.sp
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = TextPrimary
                ),
                actions = {
                    IconButton(
                        onClick = { showNewSessionDialog = true },
                        modifier = Modifier
                            .testTag("new_session_btn")
                            .border(BorderStroke(1.dp, GlassBorder), RoundedCornerShape(12.dp))
                            .background(StudioCard, RoundedCornerShape(12.dp))
                            .size(38.dp)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Create New Session", tint = TextPrimary, modifier = Modifier.size(18.dp))
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                }
            )
        },
        bottomBar = {
            // High-fidelity bottom navigation with exact rounded corners and top border
            NavigationBar(
                containerColor = StudioCard.copy(alpha = 0.95f),
                tonalElevation = 0.dp,
                modifier = Modifier
                    .border(width = 1.dp, color = GlassBorder, shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                    .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)),
                windowInsets = WindowInsets.navigationBars
            ) {
                val tabs = listOf(
                    Triple("STUDIO", Icons.Default.Mic, "Studio"),
                    Triple("LAB", Icons.Default.Waves, "Studio Lab"),
                    Triple("MIXER", Icons.Default.Tune, "FX Mixer"),
                    Triple("WRITING BOARD", Icons.Default.Edit, "Lyric Board"),
                    Triple("HISTORY", Icons.Default.Folder, "Past Projects")
                )
                tabs.forEach { (tabId, icon, label) ->
                    val selected = activeTab == tabId
                    NavigationBarItem(
                        selected = selected,
                        onClick = { activeTab = tabId },
                        label = { Text(label, fontSize = 10.sp, fontWeight = FontWeight.Black) },
                        icon = {
                            val activeColor = if (selected) TubeGold else TextSecondary
                            when (tabId) {
                                "STUDIO" -> CapsulePillIcon(modifier = Modifier.size(24.dp), color1 = activeColor, color2 = Color(0xFFC2185B))
                                "LAB" -> MedicineDropIcon(modifier = Modifier.size(24.dp), color = activeColor)
                                "MIXER" -> SyringeIcon(modifier = Modifier.size(24.dp), color = activeColor)
                                "WRITING BOARD" -> RoundPillIcon(modifier = Modifier.size(24.dp), color = activeColor)
                                "HISTORY" -> PainCrossIcon(modifier = Modifier.size(24.dp), color = activeColor)
                                else -> Icon(icon, contentDescription = label)
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.Black,
                            selectedTextColor = TubeGold,
                            indicatorColor = TubeGold,
                            unselectedIconColor = TextSecondary,
                            unselectedTextColor = TextSecondary
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // High-fidelity Procedural Vintage Wood Console
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .drawBehind {
                        val w = size.width
                        val h = size.height
                        
                        // Extra dark rich mahogany grounding base
                        drawRect(color = Color(0xFF110503))
                        
                        // Vertical wooden panel planks
                        val panelCount = 6
                        val panelWidth = w / panelCount
                        
                        for (i in 0 until panelCount) {
                            val leftX = i * panelWidth
                            
                            // Cherry / Walnut sheen gradient across each physical plank
                            drawRect(
                                brush = Brush.horizontalGradient(
                                    colors = listOf(
                                        Color(0xFF2C110A).copy(alpha = 0.9f),
                                        Color(0xFF1A0703).copy(alpha = 0.95f),
                                        Color(0xFF280F08).copy(alpha = 0.9f)
                                    ),
                                    startX = leftX,
                                    endX = leftX + panelWidth
                                ),
                                topLeft = Offset(leftX, 0f),
                                size = androidx.compose.ui.geometry.Size(panelWidth, h)
                            )
                            
                            // Physical shadow creases in joint bevels
                            drawLine(
                                color = Color(0xFF0A0201).copy(alpha = 0.85f),
                                start = Offset(leftX, 0f),
                                end = Offset(leftX, h),
                                strokeWidth = 1.52.dp.toPx()
                            )
                            drawLine(
                                color = Color(0xFF4A1F14).copy(alpha = 0.45f),
                                start = Offset(leftX + 1.dp.toPx(), 0f),
                                end = Offset(leftX + 1.dp.toPx(), h),
                                strokeWidth = 1.dp.toPx()
                            )
                            
                            // Organic vertical wavy wood grain accents
                            val linesOfGrain = 8
                            for (j in 0 until linesOfGrain) {
                                val grainBaseX = leftX + (panelWidth / (linesOfGrain + 1)) * j
                                val waveAmplitude = 5.dp.toPx()
                                val path = Path().apply {
                                    moveTo(grainBaseX, 0f)
                                    var currentY = 0f
                                    val step = 100.dp.toPx()
                                    while (currentY < h) {
                                        val nextY = (currentY + step).coerceAtMost(h)
                                        val sineCorrection = Math.sin((currentY * 0.006) + (i * 29.0) + (j * 19.1)).toFloat()
                                        val targetX = grainBaseX + sineCorrection * waveAmplitude
                                        quadraticTo(grainBaseX + sineCorrection * waveAmplitude * 0.5f, currentY + step * 0.5f, targetX, nextY)
                                        currentY = nextY
                                    }
                                }
                                drawPath(
                                    path = path,
                                    color = Color(0xFF0F0402).copy(alpha = 0.25f),
                                    style = Stroke(width = 0.8.dp.toPx())
                                )
                            }
                        }
                    }
            )
            
            // Filament Spotlight Warm Amber Glow Overlay
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .drawBehind {
                        drawCircle(
                            brush = Brush.radialGradient(
                                colors = listOf(
                                    Color(0xFFF59E0B).copy(alpha = 0.18f), // Hot incandescent yellow
                                    Color(0xFFDC2626).copy(alpha = 0.06f), // Warm amber halo bleed
                                    Color.Transparent
                                ),
                                center = Offset(size.width / 2f, 0f),
                                radius = size.width * 0.85f
                            ),
                            radius = size.width * 0.85f,
                            center = Offset(size.width / 2f, 0f)
                        )
                    }
            )


            Box(modifier = Modifier.fillMaxSize()) {
            when (activeTab) {
                "STUDIO" -> StudioMainTab(
                    viewModel = viewModel,
                    currentSession = currentSession,
                    isRecording = isRecording,
                    isPlaying = isPlaying,
                    isMixing = isMixing,
                    mixProgress = mixProgress,
                    mixLogs = mixLogs,
                    recordingSeconds = recordingSeconds,
                    amplitudes = amplitudes,
                    aiFeatureStatus = aiFeatureStatus,
                    statusMessage = statusMessage,
                    topic = topic,
                    subgenre = subgenre,
                    onStartProject = { name ->
                        scope.launch { viewModel.createNewSession(name) }
                    }
                )
                "LAB" -> StudioLabTab(viewModel = viewModel, currentSession = currentSession)
                "MIXER" -> FxMixerTab(viewModel = viewModel, currentSession = currentSession)
                "WRITING BOARD" -> LyricBoardTab(viewModel = viewModel, currentSession = currentSession)
                "HISTORY" -> HistoryProjectsTab(viewModel = viewModel, sessions = sessions, currentSession = currentSession)
            }
            }
        }
    }

    // New Session Dialog
    if (showNewSessionDialog) {
        AlertDialog(
            onDismissRequest = { showNewSessionDialog = false },
            title = {
                Text(
                    "Launch New Studio Session",
                    color = TubeGold,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Column {
                    Text(
                        "Set a theme name or track draft description:",
                        color = TextPrimary,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    OutlinedTextField(
                        value = newSessionName,
                        onValueChange = { newSessionName = it },
                        placeholder = { Text("e.g. Broken Promises, Lost Dreams") },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = TubeGold,
                            unfocusedBorderColor = GlassBorder,
                            focusedContainerColor = StudioDark,
                            unfocusedContainerColor = StudioDark,
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary
                        ),
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("session_name_input")
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (newSessionName.isNotBlank()) {
                            scope.launch {
                                viewModel.createNewSession(newSessionName)
                                newSessionName = ""
                                showNewSessionDialog = false
                                activeTab = "STUDIO"
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = TubeGold)
                ) {
                    Text("START BOOTH", fontWeight = FontWeight.Bold, color = Color.Black)
                }
            },
            dismissButton = {
                TextButton(onClick = { showNewSessionDialog = false }) {
                    Text("CANCEL", color = NeonOrange)
                }
            },
            containerColor = StudioCard
        )
    }
}

@Composable
fun StudioMainTab(
    viewModel: StudioViewModel,
    currentSession: StudioSession,
    isRecording: Boolean,
    isPlaying: Boolean,
    isMixing: Boolean,
    mixProgress: Float,
    mixLogs: List<String>,
    recordingSeconds: Int,
    amplitudes: List<Float>,
    aiFeatureStatus: String,
    statusMessage: String,
    topic: String,
    subgenre: String,
    onStartProject: (String) -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Active Session Badge & Status Console
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = StudioCard),
            border = BorderStroke(1.dp, GlassBorder)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "PROJECT WORKSPACE",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = TubeGold,
                            fontFamily = FontFamily.Monospace,
                            letterSpacing = 1.5.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = currentSession.sessionName,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Black,
                            color = TextPrimary
                        )
                    }
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (currentSession.isCompleted) NeonGreen.copy(alpha = 0.2f) else TubeGold.copy(alpha = 0.15f))
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = if (currentSession.isCompleted) "MASTERED" else "DRAFT STAGE",
                            fontSize = 10.sp,
                            color = if (currentSession.isCompleted) NeonGreen else TubeGold,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Tape/Master Console display message
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.Black.copy(alpha = 0.4f))
                        .border(1.dp, GlassBorder)
                        .padding(14.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "Console Log",
                            tint = NeonOrange,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = statusMessage,
                            fontFamily = FontFamily.Monospace,
                            fontSize = 12.sp,
                            color = NeonOrange,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }

        // Silas Studio Intercom & Soundboard (Ghetto Gospel Booth)
        val isTtsEnabledState by viewModel.isTtsEnabled.collectAsState()
        Card(
            modifier = Modifier.fillMaxWidth().testTag("silas_intercom_card"),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = StudioCard),
            border = BorderStroke(1.dp, GlassBorder)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Intercom Title
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .clip(CircleShape)
                                .background(if (isTtsEnabledState) Color(0xFF00FFCC) else Color.Red)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "STUDIO BOOTH INTERCOM & SOUNDBOARD",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = TubeGold,
                            fontFamily = FontFamily.Monospace,
                            letterSpacing = 1.2.sp
                        )
                    }
                    // Speech voice switch
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = if (isTtsEnabledState) "SPEAKER ACTIVE" else "MUTED",
                            fontSize = 9.sp,
                            fontFamily = FontFamily.Monospace,
                            color = if (isTtsEnabledState) Color(0xFF00FFCC) else Color.Red,
                            modifier = Modifier.padding(end = 6.dp)
                        )
                        Switch(
                            checked = isTtsEnabledState,
                            onCheckedChange = { viewModel.isTtsEnabled.value = it },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.Black,
                                checkedTrackColor = TubeGold,
                                uncheckedThumbColor = Color.White,
                                uncheckedTrackColor = Color.DarkGray
                            )
                        )
                    }
                }

                Text(
                    text = "Silas 'Vocal' Pain is in the booth behind the double-pane studio glass. Tap any preset button to hear him cough, hum, shout, or offer deep street-wisdom through your monitors in real-time.",
                    fontSize = 12.sp,
                    color = TextSecondary,
                    lineHeight = 16.sp
                )

                // Grid of soundboard triggers matching deep mysterious pain themes
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Cough booth check button
                    Button(
                        onClick = { viewModel.speakPresetVibe("cough") },
                        modifier = Modifier.weight(1f).height(40.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = StudioDark),
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.dp, GlassBorder)
                    ) {
                        Text("🤧 COUGH", fontSize = 10.sp, color = TextPrimary, fontWeight = FontWeight.Bold)
                    }

                    // Gospel hum
                    Button(
                        onClick = { viewModel.speakPresetVibe("gospel_hum") },
                        modifier = Modifier.weight(1.1f).height(40.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = StudioDark),
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.dp, GlassBorder)
                    ) {
                        Text("🎙️ SOUL HUM", fontSize = 10.sp, color = TextPrimary, fontWeight = FontWeight.Bold)
                    }

                    // Shoutout survival phrase
                    Button(
                        onClick = { viewModel.speakPresetVibe("street_shout") },
                        modifier = Modifier.weight(1f).height(40.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = StudioDark),
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.dp, GlassBorder)
                    ) {
                        Text("🗣️ SHOUTOUT", fontSize = 10.sp, color = TextPrimary, fontWeight = FontWeight.Bold)
                    }
                }

                // Concrete Wisdom selection bar
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = { viewModel.speakPresetVibe("advice_1") },
                        modifier = Modifier.weight(1f).height(38.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = StudioDark),
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(1.dp, GlassBorder)
                    ) {
                        Text("💭 Scars Advice", fontSize = 9.sp, color = TubeGold)
                    }

                    Button(
                        onClick = { viewModel.speakPresetVibe("advice_2") },
                        modifier = Modifier.weight(1f).height(38.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = StudioDark),
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(1.dp, GlassBorder)
                    ) {
                        Text("💔 Beat Hurts", fontSize = 9.sp, color = TubeGold)
                    }

                    Button(
                        onClick = { viewModel.speakPresetVibe("advice_3") },
                        modifier = Modifier.weight(1.2f).height(38.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = StudioDark),
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(1.dp, GlassBorder)
                    ) {
                        Text("🏛️ Twelve Winters", fontSize = 9.sp, color = TubeGold)
                    }
                }
            }
        }

        // Vibe Parameters setting
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = StudioCard),
            border = BorderStroke(1.dp, GlassBorder)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Creative Direction",
                        tint = TubeGold,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "VIBE & STORY DIRECTION (AI FEED)",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = TubeGold,
                        fontFamily = FontFamily.Monospace,
                        letterSpacing = 1.sp
                    )
                }

                OutlinedTextField(
                    value = topic,
                    onValueChange = { viewModel.updateVibeTopic(it) },
                    label = { Text("Describe Your Pain, Topic, or Background Story", color = TextSecondary) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = TubeGold,
                        unfocusedBorderColor = GlassBorder,
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary,
                        focusedContainerColor = StudioDark,
                        unfocusedContainerColor = StudioDark
                    ),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        var expandedGenre by remember { mutableStateOf(false) }
                        OutlinedCard(
                            onClick = { expandedGenre = true },
                            border = BorderStroke(1.dp, GlassBorder),
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(containerColor = StudioDark)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text("Subgenre Vibe", fontSize = 9.sp, color = TextSecondary)
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(subgenre, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                                }
                                Icon(Icons.Default.ArrowDropDown, contentDescription = "dropdown", tint = TubeGold)
                            }
                        }
                        DropdownMenu(
                            expanded = expandedGenre,
                            onDismissRequest = { expandedGenre = false },
                            modifier = Modifier
                                .background(StudioCard)
                                .border(1.dp, GlassBorder, RoundedCornerShape(8.dp))
                        ) {
                            val genres = listOf("Street Ghetto Gospel", "Penitentiary Blues Chords", "Lil Wayne Deep Metaphors", "Rod Wave Tears & Hope")
                            genres.forEach { g ->
                                DropdownMenuItem(
                                    text = { Text(g, color = TextPrimary, fontSize = 13.sp) },
                                    onClick = {
                                        viewModel.updateVibeSubgenre(g)
                                        expandedGenre = false
                                    }
                                )
                            }
                        }
                    }

                    Box(modifier = Modifier.weight(1f)) {
                        var expandedBeats by remember { mutableStateOf(false) }
                        OutlinedCard(
                            onClick = { expandedBeats = true },
                            border = BorderStroke(1.dp, GlassBorder),
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(containerColor = StudioDark)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text("Selected Instrumental", fontSize = 9.sp, color = TextSecondary)
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(currentSession.selectedBeat, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                                }
                                Icon(Icons.Default.ArrowDropDown, contentDescription = "dropdown", tint = TubeGold)
                            }
                        }
                        DropdownMenu(
                            expanded = expandedBeats,
                            onDismissRequest = { expandedBeats = false },
                            modifier = Modifier
                                .background(StudioCard)
                                .border(1.dp, GlassBorder, RoundedCornerShape(8.dp))
                        ) {
                            viewModel.studioBeats.forEach { beat ->
                                DropdownMenuItem(
                                    text = { Text(beat, color = TextPrimary, fontSize = 13.sp) },
                                    onClick = {
                                        viewModel.updateSelectedBeat(beat)
                                        expandedBeats = false
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }

        // RECORD & STUDIO BOARD (CONSOLE & WAVEFORM VISUALIZER)
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = StudioCard),
            border = BorderStroke(1.dp, GlassBorder)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "CONSOLE MONITOR",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = TubeGold,
                    fontFamily = FontFamily.Monospace,
                    modifier = Modifier.align(Alignment.Start)
                )

                // High-End Multi-Track Desk Representation (Sophisticated Dark Style)
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.Black.copy(alpha = 0.5f))
                        .border(1.dp, GlassBorder)
                        .padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    // Track 1: Lead Vocal
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "LEAD VOCAL (YOUR PAIN)",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextSecondary,
                                fontFamily = FontFamily.Monospace
                            )
                            Text(
                                text = if (isRecording) "-12.4dB" else "STABLE",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = if (isRecording) NeonOrange else TextSecondary,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        CanvasWaveformOscilloscope(
                            isRecording = isRecording,
                            isPlaying = isPlaying,
                            amplitudes = amplitudes,
                            color = NeonOrange
                        )
                    }

                    // Track 2: AI Soul Layer
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "AI SOUL LAYER (RASPY HARMONY)",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = TubeGold.copy(alpha = 0.8f),
                                fontFamily = FontFamily.Monospace
                            )
                            Text(
                                text = if (isMixing) "MASTERING..." else if (isPlaying) "PLAYBACK" else "STANDBY",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = TubeGold,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        CanvasWaveformOscilloscope(
                            isRecording = isRecording,
                            isPlaying = isPlaying,
                            amplitudes = amplitudes.map { (it * 0.8f).coerceIn(0.05f, 1.0f) },
                            color = TubeGold
                        )
                    }

                    // Console Info and Timer
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (isRecording) "LIVE MIX FEED" else "DAW DESK ACTIVE",
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isRecording) NeonOrange else TextSecondary,
                            fontFamily = FontFamily.Monospace,
                            letterSpacing = 1.sp
                        )

                        // Timer badge
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(StudioButton)
                                .border(1.dp, GlassBorder, RoundedCornerShape(6.dp))
                                .padding(horizontal = 8.dp, vertical = 3.dp)
                        ) {
                            val minutes = recordingSeconds / 60
                            val seconds = recordingSeconds % 60
                            Text(
                                text = String.format("%02d:%02d", minutes, seconds),
                                color = TubeGold,
                                fontSize = 11.sp,
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                // Control Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Big Tactile Record Button with Red Pulsing Shadow Glow
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(86.dp)
                            .drawBehind {
                                if (isRecording) {
                                    drawCircle(
                                        color = NeonOrange.copy(alpha = 0.25f),
                                        radius = size.minDimension / 1.4f
                                    )
                                }
                            }
                    ) {
                        Button(
                            onClick = { viewModel.toggleVoiceRecording() },
                            modifier = Modifier
                                .size(76.dp)
                                .testTag("record_vocals_toggle_btn"),
                            shape = CircleShape,
                            border = BorderStroke(4.dp, Color.Black),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isRecording) Color.White else NeonOrange
                            ),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            if (isRecording) {
                                Box(
                                    modifier = Modifier
                                        .size(22.dp)
                                        .background(Color.Black, RoundedCornerShape(3.dp))
                                )
                            } else {
                                Icon(
                                    imageVector = Icons.Default.Mic,
                                    contentDescription = "Record Studio Vocals",
                                    tint = Color.White,
                                    modifier = Modifier.size(28.dp)
                                )
                            }
                        }
                    }

                    // Playback Preview
                    Button(
                        onClick = { if (isPlaying) viewModel.stopMixPlayback() else viewModel.playMixPlayback() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isPlaying) NeonOrange else TubeGold
                        ),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .height(52.dp)
                            .testTag("play_mix_btn"),
                        border = BorderStroke(1.dp, Color.Black.copy(alpha = 0.15f))
                    ) {
                        Icon(
                            imageVector = if (isPlaying) Icons.Default.Stop else Icons.Default.PlayArrow,
                            contentDescription = "Play Mix",
                            tint = Color.Black
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (isPlaying) "STOP" else "PLAY MONITOR",
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            fontFamily = FontFamily.Monospace,
                            letterSpacing = 0.5.sp
                        )
                    }
                }
            }
        }

        // AI Artist Persona status card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = StudioCard),
            border = BorderStroke(1.dp, GlassBorder)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Avatar with gradient background
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color(0xFFD97706), Color(0xFF7F1D1D)) // Amber-600 to Red-900
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text("🎙️", fontSize = 24.sp)
                    // AI tag overlay at bottom-right
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .offset(x = 4.dp, y = 4.dp)
                            .size(20.dp)
                            .clip(CircleShape)
                            .background(TubeGold)
                            .border(1.5.dp, StudioDark, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "AI",
                            fontSize = 8.sp,
                            fontWeight = FontWeight.Black,
                            color = Color.Black
                        )
                    }
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Ghost Engineer",
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp,
                        color = Color(0xFFFEF3C7) // Amber-50
                    )
                    Text(
                        text = "Lil Wayne lyrics • Rod Wave soul",
                        fontSize = 11.sp,
                        color = TextSecondary
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(StudioButton)
                        .border(BorderStroke(1.dp, TubeGold.copy(alpha = 0.3f)), CircleShape)
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "PITCH: AUTO",
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        color = TubeGold,
                        fontFamily = FontFamily.Monospace
                    )
                }
            }
        }

        // FEATURED AI BOOTH - Silas "Vocal" Pain Track
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = StudioCard),
            border = BorderStroke(1.dp, GlassBorder)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.MusicVideo,
                            contentDescription = "Featured Silas booth",
                            tint = TubeGold,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "AI FEATURED ARTIST SLOT",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = TubeGold,
                            fontFamily = FontFamily.Monospace,
                            letterSpacing = 1.sp
                        )
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.Black)
                            .border(1.dp, GlassBorder, RoundedCornerShape(8.dp))
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "SILAS \"VOCAL\" PAIN",
                            color = NeonOrange,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }

                Text(
                    text = "Let Silas jump on your track. He writes deep street hymns like a hybrid of Lil Wayne's elite wordplay and Rod Wave's weeping gut-wrenching raspy singing flow.",
                    fontSize = 12.sp,
                    color = TextSecondary,
                    lineHeight = 16.sp
                )

                if (currentSession.aiLyrics.isEmpty()) {
                    Button(
                        onClick = { viewModel.generateFeaturedVerse() },
                        colors = ButtonDefaults.buttonColors(containerColor = TubeGold),
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .testTag("record_silas_feature_btn"),
                        enabled = aiFeatureStatus != "Writing Lyrics..."
                    ) {
                        if (aiFeatureStatus == "Writing Lyrics...") {
                            CircularProgressIndicator(modifier = Modifier.size(16.dp), color = Color.Black)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("SOUL INSPIRATION RECORDING...", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 11.sp, fontFamily = FontFamily.Monospace)
                        } else {
                            Icon(Icons.Default.AutoAwesome, contentDescription = "Generate Silas Feature", tint = Color.Black, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("RECORD SILAS' FEATURE VERSE", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 12.sp, fontFamily = FontFamily.Monospace)
                        }
                    }
                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(StudioDark)
                            .border(1.dp, GlassBorder)
                            .padding(14.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("SILAS' RECORDED CHANNELS", fontSize = 10.sp, color = NeonOrange, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                            IconButton(
                                onClick = { viewModel.clearFeaturedVerse() },
                                modifier = Modifier.size(24.dp)
                            ) {
                                Icon(Icons.Default.Delete, contentDescription = "Clear feature", tint = Color.Red, modifier = Modifier.size(16.dp))
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = currentSession.aiLyrics,
                            fontSize = 13.sp,
                            color = TextPrimary,
                            fontWeight = FontWeight.Medium,
                            fontFamily = FontFamily.Serif,
                            maxLines = 10,
                            modifier = Modifier.verticalScroll(rememberScrollState()),
                            lineHeight = 18.sp
                        )
                    }
                }
            }
        }

        // MIX & MASTER ENGINE CONTROL (Real Master Log scrolling)
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = StudioCard),
            border = BorderStroke(1.dp, GlassBorder)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.SettingsVoice,
                        contentDescription = "Master engine",
                        tint = TubeGold,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "STUDIO MIXING & MASTERING SUITE",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = TubeGold,
                        fontFamily = FontFamily.Monospace,
                        letterSpacing = 1.sp
                    )
                }

                Button(
                    onClick = { viewModel.runMidiMixAndMaster() },
                    colors = ButtonDefaults.buttonColors(containerColor = TubeGold),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .testTag("master_track_btn"),
                    enabled = !isMixing
                ) {
                    if (isMixing) {
                        CircularProgressIndicator(modifier = Modifier.size(16.dp), color = Color.Black)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("BOUNCING ANALOG CHANNELS...", color = Color.Black, fontSize = 12.sp, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold)
                    } else {
                        Icon(Icons.Default.Grain, contentDescription = "Run Master Mix", tint = Color.Black, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("AI MIX & MASTER CHANNELS", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 12.sp, fontFamily = FontFamily.Monospace)
                    }
                }

                if (isMixing || mixLogs.isNotEmpty()) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color.Black.copy(alpha = 0.5f))
                            .border(1.dp, GlassBorder)
                            .padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "MASTER CONSOLE STEPS",
                            fontFamily = FontFamily.Monospace,
                            fontSize = 10.sp,
                            color = NeonOrange,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )

                        LinearProgressIndicator(
                            progress = { mixProgress },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(4.dp),
                            color = TubeGold,
                            trackColor = Color.DarkGray
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        mixLogs.forEach { log ->
                            Text(
                                text = ">> $log",
                                fontFamily = FontFamily.Monospace,
                                fontSize = 11.sp,
                                color = TubeGold,
                                fontWeight = FontWeight.Medium,
                                lineHeight = 16.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AnalogVuNeedleMeter(
    modifier: Modifier = Modifier,
    value: Float,
    title: String = "Master Peak VU"
) {
    Column(
        modifier = modifier
            .background(Color.Black.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
            .border(1.dp, GlassBorder, RoundedCornerShape(12.dp))
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
        ) {
            val w = size.width
            val h = size.height
            val cx = w / 2f
            val cy = h - 4.dp.toPx()
            
            // Draw bronze copper bevel border
            drawArc(
                color = TubeGold.copy(alpha = 0.2f),
                startAngle = 180f,
                sweepAngle = 180f,
                useCenter = false,
                style = Stroke(width = 6.dp.toPx(), cap = StrokeCap.Round)
            )
            
            // Draw grid lines
            val ticks = 10
            for (i in 0..ticks) {
                val fraction = i.toFloat() / ticks
                val angleRad = Math.toRadians((200f + fraction * 140f).toDouble())
                val r1 = w * 0.35f
                val r2 = w * 0.4f
                val sx = cx + (Math.cos(angleRad) * r1).toFloat()
                val sy = cy + (Math.sin(angleRad) * r1).toFloat()
                val ex = cx + (Math.cos(angleRad) * r2).toFloat()
                val ey = cy + (Math.sin(angleRad) * r2).toFloat()
                
                drawLine(
                    color = if (fraction > 0.82f) NeonOrange else TubeGold.copy(alpha = 0.6f),
                    start = Offset(sx, sy),
                    end = Offset(ex, ey),
                    strokeWidth = 2.dp.toPx()
                )
            }
            
            // Draw physically lagging needle swing
            val limitValue = value.coerceIn(0f, 1f)
            val angleDeg = 200f + (limitValue * 140f)
            val rad = Math.toRadians(angleDeg.toDouble())
            val needleLen = w * 0.42f
            val nx = cx + (Math.cos(rad) * needleLen).toFloat()
            val ny = cy + (Math.sin(rad) * needleLen).toFloat()
            
            drawLine(
                color = if (limitValue > 0.8f) Color.Red else TubeGold,
                start = Offset(cx, cy),
                end = Offset(nx, ny),
                strokeWidth = 2.5.dp.toPx(),
                cap = StrokeCap.Round
            )
            
            // Brass pivot hub
            drawCircle(color = Color(0xFFC0A060), radius = 6.dp.toPx(), center = Offset(cx, cy))
            drawCircle(color = Color.Black, radius = 3.dp.toPx(), center = Offset(cx, cy))
        }
        
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = title,
            color = TextSecondary,
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Monospace,
            letterSpacing = 0.5.sp
        )
    }
}

@Composable
fun EqSpectrumAnalyzer(
    modifier: Modifier = Modifier,
    isSignalActive: Boolean,
    color: Color = TubeGold
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(75.dp)
            .background(Color.Black.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
            .border(1.dp, GlassBorder, RoundedCornerShape(12.dp))
            .padding(horizontal = 10.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        val bands = listOf("30", "100", "250", "1k", "4k", "8k", "16k")
        bands.forEachIndexed { i, band ->
            val factor = remember { mutableStateOf(0.1f) }
            LaunchedEffect(isSignalActive) {
                if (isSignalActive) {
                    while (true) {
                        factor.value = (Math.random() * 0.82 + 0.18).toFloat()
                        delay(75L + i * 15)
                    }
                } else {
                    factor.value = 0.05f
                }
            }
            
            val h = (factor.value * 48f).coerceIn(4f, 48f).dp
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom,
                modifier = Modifier.weight(1f)
            ) {
                Box(
                    modifier = Modifier
                        .width(13.dp)
                        .height(h)
                        .clip(RoundedCornerShape(2.dp))
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(color, color.copy(alpha = 0.3f))
                            )
                        )
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = band,
                    color = TextSecondary,
                    fontSize = 7.5.sp,
                    fontFamily = FontFamily.Monospace
                )
            }
        }
    }
}

@Composable
fun FxMixerTab(viewModel: StudioViewModel, currentSession: StudioSession) {
    val masterVol by viewModel.trackMasterVol.collectAsState()
    val isPlayingMix by viewModel.isPlayingMix.collectAsState()
    val isRecordingVocals by viewModel.isRecordingVocals.collectAsState()
    val isMixingAndMastering by viewModel.isMixingAndMastering.collectAsState()
    
    val activeSignal = isPlayingMix || isRecordingVocals || isMixingAndMastering
    
    // AutoTune / Pitch properties from state
    val pitchActive by viewModel.pitchAutoTuneActive.collectAsState()
    val pitchSpeed by viewModel.pitchAutoTuneSpeed.collectAsState()
    val pitchKey by viewModel.pitchAutoTuneKey.collectAsState()
    val pitchScale by viewModel.pitchAutoTuneScale.collectAsState()
    val pitchTunedNote by viewModel.pitchTunedNote.collectAsState()
    val pitchDeviation by viewModel.pitchDeviationState.collectAsState()

    // Peak levels
    val leadVal by viewModel.meterLead.collectAsState()
    val beatVal by viewModel.meterBeat.collectAsState()
    val compositePeak = if (activeSignal) {
        ((leadVal * 0.5f + beatVal * 0.5f) * masterVol * 1.15f).coerceIn(0.01f, 1f)
    } else {
        0.015f
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Mixer Section Header
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = StudioCard),
            border = BorderStroke(1.dp, GlassBorder)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = "FX SIGNAL CHAINS MIXER",
                    color = TubeGold,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "Adjust genuine vocal engineering parameters including physical delays, vocal layering densities, and tube pitch speed.",
                    color = TextSecondary,
                    fontSize = 12.sp,
                    lineHeight = 16.sp
                )
            }
        }

        // Master output channel and live spectrum analyzer
        Card(
            modifier = Modifier.fillMaxWidth().testTag("master_channel_ui"),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = StudioCard),
            border = BorderStroke(1.5.dp, TubeGold.copy(alpha = 0.45f)) // Antique Brass Bevel look
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Tune, contentDescription = "Master icon", tint = TubeGold, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "ANALOG MASTER MODULE",
                            color = TubeGold,
                            fontWeight = FontWeight.Black,
                            fontSize = 13.sp,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                    
                    if (activeSignal) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(NeonOrange.copy(alpha = 0.25f))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text("SIGNAL PEAKING", color = NeonOrange, fontSize = 9.sp, fontWeight = FontWeight.Black, fontFamily = FontFamily.Monospace)
                        }
                    } else {
                        Text("STANDBY", color = MetallicGray, fontSize = 9.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                    }
                }

                HorizontalDivider(color = GlassBorder, thickness = 1.dp)

                // Master Meters & Displays
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    AnalogVuNeedleMeter(
                        modifier = Modifier.weight(1f),
                        value = compositePeak,
                        title = "24-BIT COMPOSITE"
                    )
                    EqSpectrumAnalyzer(
                        modifier = Modifier.weight(1f),
                        isSignalActive = activeSignal
                    )
                }

                // Master Fader Gain Slider
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "MASTER TAPE COMPENSATOR GAIN",
                            fontSize = 11.sp,
                            color = TextPrimary,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )
                        Text(
                            text = "+${(masterVol * 12 - 6).toInt().coerceAtLeast(0)} dB / ${(masterVol * 100).toInt()}%",
                            fontSize = 12.sp,
                            color = TubeGold,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Slider(
                        value = masterVol,
                        onValueChange = { viewModel.trackMasterVol.value = it },
                        colors = SliderDefaults.colors(
                            thumbColor = TubeGold,
                            activeTrackColor = TubeGold,
                            inactiveTrackColor = Color.DarkGray
                        ),
                        modifier = Modifier.testTag("master_volume_slider")
                    )
                }
            }
        }

        // Real-time Pitch Correction lock & auto-tune module
        Card(
            modifier = Modifier.fillMaxWidth().testTag("pitch_corrector_module"),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = StudioCard),
            border = BorderStroke(1.dp, GlassBorder)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.OfflineBolt, contentDescription = "Tuning", tint = TubeGold, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "OLD-SCHOOL PITCH AUTO-TUNE",
                            color = TubeGold,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                    
                    Switch(
                        checked = pitchActive,
                        onCheckedChange = { viewModel.pitchAutoTuneActive.value = it },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.Black,
                            checkedTrackColor = TubeGold,
                            uncheckedThumbColor = MetallicGray,
                            uncheckedTrackColor = StudioButton
                        )
                    )
                }

                HorizontalDivider(color = GlassBorder, thickness = 1.dp)

                if (pitchActive) {
                    // Physical Cent Needle Display
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Black.copy(alpha = 0.4f), RoundedCornerShape(12.dp))
                            .border(1.dp, GlassBorder, RoundedCornerShape(12.dp))
                            .padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text("LOCKED SCALE CENTER", fontSize = 9.sp, color = TextSecondary)
                                Text("$pitchKey $pitchScale", fontSize = 12.sp, color = TubeGold, fontWeight = FontWeight.Bold)
                            }
                            Column(horizontalAlignment = Alignment.End) {
                                Text("TUNED NOTE INTERVAL", fontSize = 9.sp, color = TextSecondary)
                                Text(
                                    text = if (pitchTunedNote != "-") "Locked ($pitchTunedNote)" else "Standby (Hum)",
                                    fontSize = 12.sp,
                                    color = if (pitchTunedNote != "-") NeonGreen else MetallicGray,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily.Monospace
                                )
                            }
                        }

                        // Cent Offset visual Needle bar
                        Column {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("-50 cents", fontSize = 8.sp, color = TextSecondary)
                                Text(
                                    text = if (pitchDeviation == 0f) "STABLE" else "DEV: ${if (pitchDeviation > 0x0) "+" else ""}${pitchDeviation.toInt()}c",
                                    fontSize = 9.sp,
                                    color = if (pitchDeviation == 0f) TextSecondary else if (Math.abs(pitchDeviation) < 15f) NeonGreen else NeonOrange,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily.Monospace
                                )
                                Text("+50 cents", fontSize = 8.sp, color = TextSecondary)
                            }
                            
                            val devProgress = ((pitchDeviation + 50f) / 100f).coerceIn(0f, 1f)
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(6.dp)
                                    .clip(CircleShape)
                                    .background(StudioButton)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .fillMaxWidth(devProgress)
                                        .background(if (Math.abs(pitchDeviation) < 15f) NeonGreen else NeonOrange)
                                )
                            }
                        }
                    }

                    // Key Selectors Row
                    Column {
                        Text("Target Base Tonic Key", fontSize = 11.sp, color = TextPrimary, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 6.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            val keys = listOf("G", "C", "D", "A", "E")
                            keys.forEach { targetK ->
                                val selected = pitchKey == targetK
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(34.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(if (selected) TubeGold else StudioButton)
                                        .clickable { viewModel.pitchAutoTuneKey.value = targetK }
                                        .testTag("scale_key_$targetK"),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = targetK,
                                        color = if (selected) Color.Black else TextPrimary,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = FontFamily.Monospace
                                    )
                                }
                            }
                        }
                    }

                    // Scale Profiles Row
                    Column {
                        Text("Blues & Gospel Tuning profiles", fontSize = 11.sp, color = TextPrimary, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 6.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            val profiles = listOf("Blues Scale", "Ghetto Gospel")
                            profiles.forEach { profile ->
                                val selected = pitchScale == profile
                                Button(
                                    onClick = { viewModel.pitchAutoTuneScale.value = profile },
                                    modifier = Modifier.weight(1f).height(38.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = if (selected) TubeGold else StudioButton
                                    ),
                                    shape = RoundedCornerShape(10.dp)
                                ) {
                                    Text(
                                        text = profile,
                                        color = if (selected) Color.Black else TextPrimary,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }

                    // Retune Speed milliseconds
                    Column {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Corrective Snap Speed", fontSize = 11.sp, color = TextPrimary)
                            Text(
                                text = if (pitchSpeed < 10f) "0 ms (T-Pain Snap)" else "${pitchSpeed.toInt()} ms",
                                fontSize = 11.sp,
                                color = TubeGold,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                        Slider(
                            value = pitchSpeed,
                            onValueChange = { viewModel.pitchAutoTuneSpeed.value = it },
                            valueRange = 0f..100f,
                            colors = SliderDefaults.colors(
                                thumbColor = TubeGold,
                                activeTrackColor = TubeGold,
                                inactiveTrackColor = Color.DarkGray
                            ),
                            modifier = Modifier.testTag("autotune_speed_slider")
                        )
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("ENGAGE ENGINE SWITCH FOR REAL-TIME AUTOTUNE LOCK", color = MetallicGray, fontSize = 10.sp, fontFamily = FontFamily.Monospace)
                    }
                }
            }
        }

        // Vocal Layering Suite (Double / Triple tracked vocals)
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = StudioCard),
            border = BorderStroke(1.dp, GlassBorder)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Layers, contentDescription = "Layering", tint = TubeGold, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("VOCAL LAYERING LAYOUT", color = TubeGold, fontWeight = FontWeight.Bold, fontSize = 13.sp, fontFamily = FontFamily.Monospace)
                }

                Text(
                    text = "Double tracker records separate takes or replicates secondary backing channels to give Rod Wave's weeping hooks maximum width and stereo power.",
                    color = TextSecondary,
                    fontSize = 11.sp,
                    lineHeight = 15.sp
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val styles = listOf("Off", "Double Vocal", "Triple Layer")
                    styles.forEach { style ->
                        val selected = currentSession.vocalLayeringMode == style
                        Button(
                            onClick = { viewModel.updateVocalLayeringMode(style) },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (selected) TubeGold else StudioButton
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                text = style,
                                color = if (selected) Color.Black else TextPrimary,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                maxLines = 1
                            )
                        }
                    }
                }
            }
        }

        // Reverb Studio Space Sliders and EQ Selection
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = StudioCard),
            border = BorderStroke(1.dp, GlassBorder)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.GraphicEq, contentDescription = "reverb space", tint = TubeGold, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("ANALOG EQ & REVERB SPACE", color = TubeGold, fontWeight = FontWeight.Bold, fontSize = 13.sp, fontFamily = FontFamily.Monospace)
                }

                Column {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Reverb Environment Space", fontSize = 12.sp, color = TextPrimary)
                        Text("${(currentSession.reverbLevel * 100).toInt()}% Delay", fontSize = 12.sp, color = TubeGold, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                    }
                    Slider(
                        value = currentSession.reverbLevel,
                        onValueChange = { viewModel.updateReverb(it) },
                        colors = SliderDefaults.colors(
                            thumbColor = TubeGold,
                            activeTrackColor = TubeGold,
                            inactiveTrackColor = Color.DarkGray
                        )
                    )
                }

                HorizontalDivider(color = GlassBorder, thickness = 1.dp)

                Column {
                    Text("Equalization Preset Style", fontSize = 12.sp, color = TextPrimary, modifier = Modifier.padding(bottom = 6.dp))

                    viewModel.eqPresets.forEach { eq ->
                        val selected = currentSession.eqPreset == eq
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { viewModel.updateSelectedEq(eq) }
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = selected,
                                onClick = { viewModel.updateSelectedEq(eq) },
                                colors = RadioButtonDefaults.colors(selectedColor = TubeGold, unselectedColor = GlassBorder)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = eq,
                                color = if (selected) TubeGold else TextPrimary,
                                fontSize = 13.sp,
                                fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LyricBoardTab(viewModel: StudioViewModel, currentSession: StudioSession) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = StudioCard),
            border = BorderStroke(1.dp, GlassBorder)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = "CO-WRITER & VOCAL COACH PAD",
                    color = TubeGold,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "Draft your lyrics inside the console. Hit 'CO-WRITE METAPHORS' and Silas will critique and help paint vivid metaphors inside his producer board below.",
                    color = TextSecondary,
                    fontSize = 12.sp,
                    lineHeight = 16.sp
                )
            }
        }

        OutlinedTextField(
            value = currentSession.userLyrics,
            onValueChange = { viewModel.updateUserLyrics(it) },
            placeholder = { Text("Draft your bars here, pain, hustle, betrayal...", color = TextSecondary) },
            label = { Text("Your Lyrics Pad", color = TubeGold) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = TubeGold,
                unfocusedBorderColor = GlassBorder,
                focusedTextColor = TextPrimary,
                unfocusedTextColor = TextPrimary,
                focusedContainerColor = StudioCard,
                unfocusedContainerColor = StudioCard
            ),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1.2f)
                .testTag("user_lyrics_field")
        )

        // Gemini Old School Blues & Gospel co-writing widget
        val isGeneratingLyrics by viewModel.isGeneratingLyrics.collectAsState()
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = StudioCard),
            border = BorderStroke(1.dp, GlassBorder.copy(alpha = 0.5f))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = "Gemini Writer",
                        tint = TubeGold,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "ANALOG GEMINI CO-WRITING BOARD",
                        color = TubeGold,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace,
                        letterSpacing = 0.5.sp
                    )
                }
                
                Text(
                    text = "Select an authentic style below to generate 12-bar acoustic blues layers or raw choir backing narratives with Gemini, written directly to your clipboard.",
                    fontSize = 10.sp,
                    color = TextSecondary,
                    lineHeight = 14.sp
                )
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = { viewModel.generateBluesGospelLyrics("old school blues") },
                        enabled = !isGeneratingLyrics,
                        modifier = Modifier
                            .weight(1f)
                            .height(40.dp)
                            .testTag("gen_blues_lyrics_btn"),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = NeonOrange.copy(alpha = 0.9f))
                    ) {
                        if (isGeneratingLyrics) {
                            CircularProgressIndicator(modifier = Modifier.size(14.dp), color = Color.White)
                        } else {
                            Icon(Icons.Default.MusicNote, contentDescription = "blues", tint = Color.White, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("12-BAR BLUES", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                    
                    Button(
                        onClick = { viewModel.generateBluesGospelLyrics("gospel") },
                        enabled = !isGeneratingLyrics,
                        modifier = Modifier
                            .weight(1f)
                            .height(40.dp)
                            .testTag("gen_gospel_lyrics_btn"),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Cyan.copy(alpha = 0.9f))
                    ) {
                        if (isGeneratingLyrics) {
                            CircularProgressIndicator(modifier = Modifier.size(14.dp), color = Color.Black)
                        } else {
                            Icon(Icons.Default.People, contentDescription = "gospel", tint = Color.Black, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("GHETTO GOSPEL", color = Color.Black, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = { viewModel.getLyricAssistance() },
                modifier = Modifier
                    .weight(1.5f)
                    .height(48.dp)
                    .testTag("ask_force_critique_btn"),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = TubeGold)
            ) {
                Icon(Icons.Default.Psychology, contentDescription = "Get Silas assistance", tint = Color.Black)
                Spacer(modifier = Modifier.width(6.dp))
                Text("CO-WRITE METAPHORS", color = Color.Black, fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }

            if (currentSession.notes.isNotEmpty()) {
                Button(
                    onClick = { viewModel.clearNotes() },
                    modifier = Modifier
                        .weight(0.9f)
                        .height(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = StudioButton)
                ) {
                    Text("CLEAR BOARD", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        if (currentSession.notes.isNotEmpty()) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Black.copy(alpha = 0.4f)),
                border = BorderStroke(1.dp, NeonOrange.copy(alpha = 0.3f))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(18.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "SILAS' COACHING MEMOS",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = NeonOrange,
                            fontFamily = FontFamily.Monospace,
                            letterSpacing = 1.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = currentSession.notes,
                        fontSize = 12.sp,
                        color = TextPrimary,
                        modifier = Modifier
                            .fillMaxWidth()
                            .verticalScroll(rememberScrollState()),
                        fontFamily = FontFamily.Monospace,
                        lineHeight = 16.sp
                    )
                }
            }
        }
    }
}

@Composable
fun HistoryProjectsTab(
    viewModel: StudioViewModel,
    sessions: List<StudioSession>,
    currentSession: StudioSession
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = StudioCard),
            border = BorderStroke(1.dp, GlassBorder)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = "STUDIO TAPE VAULT",
                    color = TubeGold,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "Load past vocal takes, generated street features, and track histories securely from the Room Local Database vault.",
                    color = TextSecondary,
                    fontSize = 12.sp,
                    lineHeight = 16.sp
                )
            }
        }

        if (sessions.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.HourglassEmpty, contentDescription = "Empty", tint = TextSecondary, modifier = Modifier.size(36.dp))
                    Spacer(modifier = Modifier.height(10.dp))
                    Text("No Studio Tracks Recorded Yet.", color = TextSecondary, fontSize = 14.sp)
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(sessions) { session ->
                    val isCurrent = session.id == currentSession.id
                    val formatter = remember { SimpleDateFormat("MM/dd HH:mm", Locale.getDefault()) }
                    val dateStr = formatter.format(Date(session.createdAt))

                    OutlinedCard(
                        onClick = { viewModel.selectSession(session) },
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("vault_item_${session.id}"),
                        border = BorderStroke(
                            width = if (isCurrent) 1.5.dp else 1.dp,
                            color = if (isCurrent) TubeGold else GlassBorder
                        ),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isCurrent) StudioCard else StudioDark
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = session.sessionName,
                                        color = if (isCurrent) TubeGold else TextPrimary,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp
                                    )
                                    if (isCurrent) {
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Box(
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(6.dp))
                                                .background(NeonOrange)
                                                .padding(horizontal = 6.dp, vertical = 2.dp)
                                        ) {
                                            Text("ACTIVE", color = Color.Black, fontSize = 8.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text("Beat: ${session.selectedBeat}", fontSize = 11.sp, color = TextSecondary)
                                    Box(
                                        modifier = Modifier
                                            .size(4.dp)
                                            .clip(CircleShape)
                                            .background(TextSecondary)
                                    )
                                    Text(dateStr, fontSize = 11.sp, color = TextSecondary)
                                }
                            }

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                if (session.isCompleted) {
                                    Icon(
                                        Icons.Default.Verified,
                                        contentDescription = "Master Completed",
                                        tint = NeonGreen,
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                }

                                IconButton(
                                    onClick = { viewModel.deleteSession(session) },
                                    modifier = Modifier.testTag("delete_session_${session.id}")
                                ) {
                                    Icon(Icons.Default.Clear, contentDescription = "Delete track", tint = Color.Red.copy(alpha = 0.8f))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LedLevelMeter(value: Float, modifier: Modifier = Modifier) {
    val segments = 12
    Row(
        modifier = modifier
            .height(10.dp)
            .fillMaxWidth()
            .background(Color.Black.copy(alpha = 0.5f), RoundedCornerShape(4.dp))
            .padding(horizontal = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 0 until segments) {
            val active = value > (i.toFloat() / segments.toFloat())
            val color = when {
                i < 7 -> if (active) NeonGreen else NeonGreen.copy(alpha = 0.12f)
                i < 10 -> if (active) NeonOrange else NeonOrange.copy(alpha = 0.12f)
                else -> if (active) Color.Red else Color.Red.copy(alpha = 0.12f)
            }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(0.6f)
                    .clip(RoundedCornerShape(1.dp))
                    .background(color)
            )
        }
    }
}

@Composable
fun StudioLabTab(viewModel: StudioViewModel, currentSession: StudioSession) {
    val scrollState = rememberScrollState()
    
    // Multi-track faders
    val tracksList by viewModel.tracksList.collectAsState()
    val isPlayingMix by viewModel.isPlayingMix.collectAsState()

    var showAddTrackDialog by remember { mutableStateOf(false) }
    var newTrackTitle by remember { mutableStateOf("") }
    var newTrackIconName by remember { mutableStateOf("Mic") }

    // Dynamic Meters
    val mLead by viewModel.meterLead.collectAsState()
    val mHarmony by viewModel.meterHarmony.collectAsState()
    val mAdlib by viewModel.meterAdlib.collectAsState()
    val mChoir by viewModel.meterChoir.collectAsState()
    val mBeat by viewModel.meterBeat.collectAsState()

    // Assets & FX
    val importedAssets by viewModel.importedAssets.collectAsState()
    val activeFX by viewModel.activeLabFX.collectAsState()

    // Exporter
    val isExporting by viewModel.isExportingTrack.collectAsState()
    val exportProg by viewModel.exportProgress.collectAsState()
    val exportLg by viewModel.exportLog.collectAsState()

    // Dialog state controllers
    var showImportDialog by remember { mutableStateOf(false) }
    var importName by remember { mutableStateOf("") }
    var importType by remember { mutableStateOf("Instrumental Beat") }
    var importSize by remember { mutableStateOf("4.2 MB") }

    var showExportDialog by remember { mutableStateOf(false) }
    var exportFileName by remember { mutableStateOf("${currentSession.sessionName}_master") }
    var exportFormat by remember { mutableStateOf("WAV") }
    var exportBitrate by remember { mutableStateOf("24-bit Studio Master") }
    var exportDither by remember { mutableStateOf(true) }

    var showExportProgressDialog by remember { mutableStateOf(false) }
    var showExportSuccessDialog by remember { mutableStateOf(false) }
    var finalSavedPath by remember { mutableStateOf("") }

    var isStoryExpanded by remember { mutableStateOf(false) }

    // Selected FX parameter tweaks (simulated infinite parameters slider!)
    var fxParamLabel by remember { mutableStateOf("Warm Saturation Drive") }
    var fxParamValue by remember { mutableStateOf(0.65f) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Lab Section Header
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = StudioCard),
            border = BorderStroke(1.dp, GlassBorder)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "VIBE LAB & MULTIPLEX PLAYGROUND",
                        color = TubeGold,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace,
                        letterSpacing = 1.sp
                    )
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(NeonOrange.copy(alpha = 0.2f))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text("AUX ROOM LIVE", color = NeonOrange, fontSize = 9.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                    }
                }
                Text(
                    text = "A flexible live sandbox workspace. Patch on unlimited digital filters, mix 5 distinct tracking levels with active LED decibel meters, consolidate custom instrumentals, and export stereo masters to local storage.",
                    color = TextSecondary,
                    fontSize = 12.sp,
                    lineHeight = 16.sp
                )
            }
        }

        // Silas Pain Story and Backstory (Pain & Resilience, street knowledge, trauma)
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = StudioDark),
            border = BorderStroke(1.dp, TubeGold.copy(alpha = 0.3f))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { isStoryExpanded = !isStoryExpanded },
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Waves, contentDescription = "Silas Icon", tint = TubeGold, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(
                                text = "ARTIST DOSSIER: SILAS 'VOCAL' PAIN",
                                color = TubeGold,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace,
                                letterSpacing = 0.5.sp
                            )
                            Text(
                                text = "Streets, Prison Penitentiary, and Resilience",
                                color = TextPrimary,
                                fontSize = 11.sp
                            )
                        }
                    }
                    Text(
                        text = if (isStoryExpanded) "HIDE" else "DETAILS",
                        color = TubeGold,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.ExtraBold,
                        fontFamily = FontFamily.Monospace
                    )
                }

                if (isStoryExpanded) {
                    Spacer(modifier = Modifier.height(12.dp))
                    HorizontalDivider(color = GlassBorder)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "ROOTS & STRUGGLE\n" +
                                "Silas 'Vocal' Pain grew up on the gravel concrete corners of northern Jacksonville, surviving turf wars and structural lockouts. After losing his three closest neighborhood friends to street gunfire, he spent twelve consecutive winter seasons locked inside a cold state penitentiary. Behind steel bars, he had no instruments—only his bare throat and a leaky radiator pipe. He developed a deep, raspy, gravelly old-school blues tone that vibrates with raw, unfiltered trauma, finding that deep soul humming was his only path to sanity and spiritual survival.\n\n" +
                                "THE SIGNATURE CODES\n" +
                                "• Penitentiary Old-School Blues: Silas has a signature gravelly rasp that emulates suffering and heavy chest tears.\n" +
                                "• Soul Hum: Silas's humming is deeply hypnotic. He can hum to a point that touches your soul instantly, blending sorrow with street redemption.\n" +
                                "• Lil Wayne Complexity: Silas writes deep double-entendres and heavy metaphors, weaving tales of cell doors, padlock scales, courtroom gavels, and gold-plated records.\n" +
                                "• Rod Wave Flow: Sweeping low-end melodic weeping vocal delivery which can fit onto Trap, Synth, Acoustic, or classic R&B beats without losing its core pain and trauma vibe.",
                        fontSize = 11.sp,
                        color = TextSecondary,
                        lineHeight = 16.sp
                    )
                }
            }
        }

        // Live Multitrack Console
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = StudioCard),
            border = BorderStroke(1.dp, GlassBorder)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Tune, contentDescription = "mixer", tint = TubeGold, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "LIVE MULTI-TRACK DESK (24-BIT STEREO)",
                            color = TubeGold,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            fontFamily = FontFamily.Monospace,
                            letterSpacing = 1.sp
                        )
                    }
                    
                    Text(
                        text = "SESSION PERSISTED",
                        color = NeonGreen,
                        fontSize = 8.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )
                }

                // Dynamic list of dynamic tracks with Mute / Solo / Delete
                tracksList.forEach { track ->
                    val trackIcon = when (track.iconName) {
                        "Mic" -> Icons.Default.Mic
                        "Hearing" -> Icons.Default.Hearing
                        "GraphicEq" -> Icons.Default.GraphicEq
                        "People" -> Icons.Default.People
                        "MusicNote" -> Icons.Default.MusicNote
                        "Piano" -> Icons.Default.Piano
                        "Waves" -> Icons.Default.Waves
                        else -> Icons.Default.MusicNote
                    }

                    val trackColor = when (track.id) {
                        "lead" -> TubeGold
                        "harmony" -> NeonOrange
                        "adlib" -> NeonGreen
                        "choir" -> Color.Cyan
                        "beat" -> Color.Magenta
                        else -> Color(0xFF9B59B6) // Purple velvet theme
                    }

                    val meterVal = when (track.id) {
                        "lead" -> mLead
                        "harmony" -> mHarmony
                        "adlib" -> mAdlib
                        "choir" -> mChoir
                        "beat" -> mBeat
                        else -> if (isPlayingMix) {
                            ((Math.random() * 0.35 + 0.45).toFloat() * track.volume).coerceIn(0.01f, 1.0f)
                        } else {
                            0.001f
                        }
                    }

                    MultitrackStrip(
                        title = track.title,
                        icon = trackIcon,
                        color = trackColor,
                        volume = track.volume,
                        onVolumeChange = { viewModel.updateTrackVolume(track.id, it) },
                        isMuted = track.isMuted,
                        onMuteToggle = { viewModel.toggleTrackMute(track.id) },
                        isSoloed = track.isSoloed,
                        onSoloToggle = { viewModel.toggleTrackSolo(track.id) },
                        isDeletable = track.isCustom,
                        onDelete = { viewModel.deleteTrack(track.id) },
                        meterVal = meterVal
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))
                
                Button(
                    onClick = { showAddTrackDialog = true },
                    colors = ButtonDefaults.buttonColors(containerColor = StudioButton),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp)
                        .testTag("add_custom_track_btn")
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add patch", tint = Color.White)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "PATCH CUSTOM ANALOG CHANNEL",
                        color = Color.White,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )
                }
            }
        }

        // Custom Add Track Dialog Popup
        if (showAddTrackDialog) {
            AlertDialog(
                onDismissRequest = { showAddTrackDialog = false },
                title = {
                    Text(
                        text = "ADD ANALOG STUDIO CHANNEL",
                        color = TubeGold,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )
                },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text(
                            text = "Choose a title and select a hardware design block for your custom recording overdub.",
                            color = TextSecondary,
                            fontSize = 11.sp
                        )
                        OutlinedTextField(
                            value = newTrackTitle,
                            onValueChange = { newTrackTitle = it },
                            placeholder = { Text("e.g. Silas Raspy Overdub", color = TextSecondary) },
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = TubeGold,
                                unfocusedBorderColor = GlassBorder,
                                focusedTextColor = TextPrimary,
                                unfocusedTextColor = TextPrimary
                            ),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.fillMaxWidth().testTag("add_track_title_input")
                        )
                        Text(
                            text = "Channel Hardware Icon",
                            color = TubeGold,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )
                        
                        val iconsList = listOf("Mic", "Hearing", "GraphicEq", "People", "MusicNote", "Piano", "Waves")
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            iconsList.forEach { iconName ->
                                val selected = newTrackIconName == iconName
                                Box(
                                    modifier = Modifier
                                        .size(34.dp)
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(if (selected) TubeGold else StudioButton)
                                        .clickable { newTrackIconName = iconName },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = when (iconName) {
                                            "Mic" -> Icons.Default.Mic
                                            "Hearing" -> Icons.Default.Hearing
                                            "GraphicEq" -> Icons.Default.GraphicEq
                                            "People" -> Icons.Default.People
                                            "MusicNote" -> Icons.Default.MusicNote
                                            "Piano" -> Icons.Default.Piano
                                            "Waves" -> Icons.Default.Waves
                                            else -> Icons.Default.MusicNote
                                        },
                                        contentDescription = iconName,
                                        tint = if (selected) Color.Black else Color.White,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }
                        }
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            viewModel.addCustomTrack(newTrackTitle, newTrackIconName)
                            newTrackTitle = ""
                            showAddTrackDialog = false
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = TubeGold),
                        modifier = Modifier.testTag("submit_add_track_btn")
                    ) {
                        Text("PATCH CHANNEL", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showAddTrackDialog = false }) {
                        Text("CANCEL", color = TextSecondary, fontSize = 11.sp)
                    }
                },
                containerColor = StudioCard
            )
        }

        // Unlimited Vocal FX Signal Rack (Combos)
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = StudioCard),
            border = BorderStroke(1.dp, GlassBorder)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Memory, contentDescription = "Signal Chain", tint = TubeGold, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "INFINITE VOCAL SIGNAL CHAINS",
                        color = TubeGold,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        fontFamily = FontFamily.Monospace,
                        letterSpacing = 1.sp
                    )
                }
                
                Text(
                    text = "Apply unlimited concurrent digital and physical hardware emulation filters to your vocal channel during the live tracking. Click to toggle active.",
                    color = TextSecondary,
                    fontSize = 11.sp
                )

                // Safe columns of faders grid to bypass experimental layout dependencies
                viewModel.availableLabFX.chunked(2).forEach { chunk ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        chunk.forEach { fx ->
                            val selected = activeFX.contains(fx)
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(12.dp))
                                    .border(
                                        width = 1.dp,
                                        color = if (selected) TubeGold else GlassBorder,
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    .background(if (selected) TubeGold.copy(alpha = 0.25f) else StudioButton)
                                    .clickable { 
                                        viewModel.toggleLabFX(fx) 
                                        fxParamLabel = "$fx Parameter Depth"
                                    }
                                    .padding(horizontal = 12.dp, vertical = 10.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box(
                                        modifier = Modifier
                                            .size(6.dp)
                                            .clip(CircleShape)
                                            .background(if (selected) NeonGreen else Color.DarkGray)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = fx,
                                        color = if (selected) Color.White else TextPrimary,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        maxLines = 1
                                    )
                                }
                            }
                        }
                    }
                }

                HorizontalDivider(color = GlassBorder)

                // Virtual Hardware Parameters Controls (Unlimited modifiers)
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Sandbox Tweak: $fxParamLabel",
                            fontSize = 12.sp,
                            color = TextPrimary
                        )
                        Text(
                            text = "${(fxParamValue * 100).toInt()}% Intensity",
                            fontSize = 12.sp,
                            color = TubeGold,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                    Slider(
                        value = fxParamValue,
                        onValueChange = { fxParamValue = it },
                        colors = SliderDefaults.colors(
                            thumbColor = TubeGold,
                            activeTrackColor = TubeGold,
                            inactiveTrackColor = Color.DarkGray
                        )
                    )
                    Text(
                        text = "Fine-tunes the physical analog impedance parameters in Silas's rack in real-time.",
                        fontSize = 10.sp,
                        color = TextSecondary
                    )
                }
            }
        }

        // Live Audio Importer Section
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = StudioCard),
            border = BorderStroke(1.dp, GlassBorder)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.CloudQueue, contentDescription = "Import Loader", tint = TubeGold, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "BEAT & SAMPLE ASSET LOADER",
                            color = TubeGold,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            fontFamily = FontFamily.Monospace,
                            letterSpacing = 1.sp
                        )
                    }
                    Button(
                        onClick = { showImportDialog = true },
                        colors = ButtonDefaults.buttonColors(containerColor = StudioButton),
                        shape = RoundedCornerShape(12.dp),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 2.dp)
                    ) {
                        Icon(Icons.Default.Publish, contentDescription = "Import", tint = TubeGold, modifier = Modifier.size(12.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("IMPORT ASSET", color = Color.White, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                    }
                }

                Text(
                    text = "Consolidate custom beats, rhythm loops, vocal acapellas, or sound design stems. These sync as master sources for Track 5.",
                    color = TextSecondary,
                    fontSize = 11.sp
                )

                // Loaded assets listing
                if (importedAssets.isEmpty()) {
                    Text("No external assets loaded yet.", color = TextSecondary, fontSize = 11.sp)
                } else {
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        importedAssets.forEach { asset ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color.Black.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
                                    .border(1.dp, GlassBorder, RoundedCornerShape(12.dp))
                                    .padding(horizontal = 12.dp, vertical = 10.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = if (asset.type.contains("Beat") || asset.type.contains("Instrumental")) Icons.Default.MusicNote else Icons.Default.Hearing,
                                        contentDescription = "Track emblem",
                                        tint = TubeGold,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Column {
                                        Text(asset.name, color = TextPrimary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp), verticalAlignment = Alignment.CenterVertically) {
                                            Text(asset.type, color = TubeGold, fontSize = 9.sp, fontFamily = FontFamily.Monospace)
                                            Box(modifier = Modifier.size(3.dp).clip(CircleShape).background(TextSecondary))
                                            Text(asset.size, color = TextSecondary, fontSize = 9.sp)
                                            Box(modifier = Modifier.size(3.dp).clip(CircleShape).background(TextSecondary))
                                            Text(asset.origin, color = TextSecondary, fontSize = 9.sp)
                                        }
                                    }
                                }
                                IconButton(
                                    onClick = { viewModel.removeImportedAsset(asset) },
                                    modifier = Modifier.size(24.dp)
                                ) {
                                    Icon(Icons.Default.Clear, contentDescription = "Delete", tint = Color.Red.copy(alpha = 0.7f), modifier = Modifier.size(14.dp))
                                }
                            }
                        }
                    }
                }
            }
        }

        // Master Exporter
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = StudioCard),
            border = BorderStroke(1.dp, NeonOrange.copy(alpha = 0.3f))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.OfflineBolt, contentDescription = "Mastering", tint = NeonOrange, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "DAW RENDER & PROFESSIONAL MASTER",
                        color = NeonOrange,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        fontFamily = FontFamily.Monospace,
                        letterSpacing = 1.sp
                    )
                }
                
                Text(
                    text = "Bounces down the 5 active fader strips, merges physical analog FX emulations, injects Silas Pain's soul hum algorithms, and exports a professionally mastered stereo file to your Android downloads foldering system.",
                    color = TextSecondary,
                    fontSize = 12.sp,
                    lineHeight = 16.sp
                )

                Button(
                    onClick = { showExportDialog = true },
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = NeonOrange)
                ) {
                    Icon(Icons.Default.Save, contentDescription = "Export file", tint = Color.Black)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("BOUNCE & EXPORT MASTERED TRACK", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }
            }
        }
    }

    // --- ALERTS AND COMPONENT DIALOGS ---

    // 1. Asset Importer Dialog
    if (showImportDialog) {
        AlertDialog(
            onDismissRequest = { showImportDialog = false },
            title = {
                Text(
                    "Import Custom Studio Stems",
                    color = TubeGold,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace,
                    fontSize = 16.sp
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("Upload any beat, instrumental, song, or vocal session sample to use inside your multi-track desk.", fontSize = 12.sp, color = TextPrimary)
                    
                    OutlinedTextField(
                        value = importName,
                        onValueChange = { importName = it },
                        label = { Text("Asset Name (e.g., StreetBeat_80BPM)") },
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = TubeGold,
                            unfocusedBorderColor = GlassBorder,
                            focusedLabelColor = TubeGold
                        ),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Text("Asset Type", fontSize = 11.sp, color = TubeGold, fontWeight = FontWeight.Bold)
                    val types = listOf("Instrumental Beat", "Instrumental", "Vocal Stem", "Sample Loop")
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        types.take(2).forEach { t ->
                            val active = importType == t
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(if (active) TubeGold else StudioButton)
                                    .border(1.dp, if (active) TubeGold else GlassBorder, RoundedCornerShape(8.dp))
                                    .clickable { importType = t }
                                    .padding(vertical = 8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(t, color = if (active) Color.Black else TextPrimary, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        types.drop(2).forEach { t ->
                            val active = importType == t
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(if (active) TubeGold else StudioButton)
                                    .border(1.dp, if (active) TubeGold else GlassBorder, RoundedCornerShape(8.dp))
                                    .clickable { importType = t }
                                    .padding(vertical = 8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(t, color = if (active) Color.Black else TextPrimary, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }

                    OutlinedTextField(
                        value = importSize,
                        onValueChange = { importSize = it },
                        label = { Text("Simulated Size (e.g., 3.8 MB)") },
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = TubeGold,
                            unfocusedBorderColor = GlassBorder,
                            focusedLabelColor = TubeGold
                        ),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (importName.trim().isNotEmpty()) {
                            viewModel.importCustomAsset(importName.trim(), importType, importSize)
                            showImportDialog = false
                            importName = ""
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = TubeGold)
                ) {
                    Text("IMPORT STEM", color = Color.Black, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showImportDialog = false }) {
                    Text("CANCEL", color = TextSecondary)
                }
            },
            containerColor = StudioCard
        )
    }

    // 2. Export Master Config Dialog
    if (showExportDialog) {
        AlertDialog(
            onDismissRequest = { showExportDialog = false },
            title = {
                Text(
                    "Master File Export Configuration",
                    color = NeonOrange,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace,
                    fontSize = 16.sp
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("Configure your professional master bounce before saving to storage.", fontSize = 12.sp, color = TextPrimary)
                    
                    OutlinedTextField(
                        value = exportFileName,
                        onValueChange = { exportFileName = it },
                        label = { Text("Master Track Filename") },
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = NeonOrange,
                            unfocusedBorderColor = GlassBorder,
                            focusedLabelColor = NeonOrange
                        ),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Text("Output Format", fontSize = 11.sp, color = TextSecondary)
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            listOf("WAV", "FLAC", "MP3").forEach { f ->
                                val active = exportFormat == f
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(if (active) NeonOrange else StudioButton)
                                        .clickable { exportFormat = f }
                                        .padding(horizontal = 10.dp, vertical = 4.dp)
                                ) {
                                    Text(f, color = if (active) Color.Black else TextPrimary, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Text("Mastering Quality Preset", fontSize = 11.sp, color = TextSecondary)
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            listOf("16-bit CD", "24-bit Studio", "32-bit Float").forEach { q ->
                                val active = exportBitrate.contains(q.take(6))
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(if (active) NeonOrange else StudioButton)
                                        .clickable { exportBitrate = "$q Master" }
                                        .padding(horizontal = 8.dp, vertical = 4.dp)
                                ) {
                                    Text(q, color = if (active) Color.Black else TextPrimary, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Dither Mastering Noise", fontSize = 12.sp, color = TextPrimary)
                            Text("Blends lower bit conversion boundaries beautifully", fontSize = 10.sp, color = TextSecondary)
                        }
                        Switch(
                            checked = exportDither,
                            onCheckedChange = { exportDither = it },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = NeonOrange,
                                checkedTrackColor = NeonOrange.copy(alpha = 0.4f)
                            )
                        )
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val nameStr = if (exportFileName.trim().isEmpty()) "vocal_clip" else exportFileName.trim()
                        showExportDialog = false
                        showExportProgressDialog = true
                        
                        viewModel.triggerExportSimulation(
                            fileName = nameStr,
                            format = exportFormat,
                            bitRate = exportBitrate,
                            dither = exportDither,
                            onDone = {
                                finalSavedPath = "/storage/emulated/0/Downloads/StudioLab/$nameStr.$exportFormat"
                                showExportProgressDialog = false
                                showExportSuccessDialog = true
                            }
                        )
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = NeonOrange)
                ) {
                    Text("START RENDER", color = Color.Black, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showExportDialog = false }) {
                    Text("CANCEL", color = TextSecondary)
                }
            },
            containerColor = StudioCard
        )
    }

    // 3. Render Progress Dialog
    if (showExportProgressDialog) {
        AlertDialog(
            onDismissRequest = {}, // Force block cancel
            title = {
                Text(
                    "CONSOLIDATING DIGITAL STEM MIXDOWN",
                    color = TubeGold,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace,
                    fontSize = 14.sp
                )
            },
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    CircularProgressIndicator(color = TubeGold, strokeWidth = 4.dp)
                    LinearProgressIndicator(
                        progress = exportProg,
                        color = TubeGold,
                        trackColor = Color.DarkGray,
                        modifier = Modifier.fillMaxWidth().height(6.dp)
                    )
                    Text(
                        text = exportLg,
                        color = TextPrimary,
                        fontSize = 11.sp,
                        fontFamily = FontFamily.Monospace,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "${(exportProg * 100).toInt()}% completed",
                        color = TubeGold,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )
                }
            },
            confirmButton = {}, // No controls
            containerColor = StudioCard
        )
    }

    // 4. Render Completed Success Dialog
    if (showExportSuccessDialog) {
        AlertDialog(
            onDismissRequest = { showExportSuccessDialog = false },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Verified, contentDescription = "Success", tint = NeonGreen)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        "Master Completed Successfully!",
                        color = NeonGreen,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("Next-level 24-bit mastered professional track is now registered to android's storage registers.", fontSize = 12.sp, color = TextPrimary)
                    
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Black.copy(alpha = 0.4f), RoundedCornerShape(12.dp))
                            .border(1.dp, GlassBorder, RoundedCornerShape(12.dp))
                            .padding(12.dp)
                    ) {
                        Column {
                            Text("SAVE PATH:", color = TubeGold, fontSize = 9.sp, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold)
                            Text(finalSavedPath, color = TextPrimary, fontSize = 11.sp, fontFamily = FontFamily.Monospace)
                        }
                    }

                    Text("Silas mixed this down using the warm analog studio console algorithms, fusing all active physical delay configurations, vocal layered pitch scaling registers, and soulful hum chimes into the bounce.", fontSize = 11.sp, color = TextSecondary)
                }
            },
            confirmButton = {
                Button(
                    onClick = { showExportSuccessDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = NeonGreen)
                ) {
                    Text("OK, GOT IT!", color = Color.Black, fontWeight = FontWeight.Bold)
                }
            },
            containerColor = StudioCard
        )
    }
}

@Composable
fun MultitrackStrip(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    color: Color,
    volume: Float,
    onVolumeChange: (Float) -> Unit,
    isMuted: Boolean,
    onMuteToggle: () -> Unit,
    isSoloed: Boolean = false,
    onSoloToggle: () -> Unit = {},
    isDeletable: Boolean = false,
    onDelete: () -> Unit = {},
    meterVal: Float
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black.copy(alpha = 0.2f), RoundedCornerShape(16.dp))
            .border(1.dp, if (isMuted) Color.Transparent else GlassBorder, RoundedCornerShape(16.dp))
            .padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(icon, contentDescription = title, tint = if (isMuted) TextSecondary else color, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = title,
                    color = if (isMuted) TextSecondary else TextPrimary,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace
                )
            }
            
            // Console Buttons S/M (Solo/Mute) & Trash delete
            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(26.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (isMuted) Color.Red else StudioButton)
                        .clickable { onMuteToggle() },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "M",
                        color = Color.White,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Black,
                        fontFamily = FontFamily.Monospace
                    )
                }
                Box(
                    modifier = Modifier
                        .size(26.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (isSoloed) Color(0xFFF1C40F) else StudioButton)
                        .clickable { onSoloToggle() },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "S",
                        color = if (isSoloed) Color.Black else TextSecondary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Black,
                        fontFamily = FontFamily.Monospace
                    )
                }
                
                if (isDeletable) {
                    IconButton(
                        onClick = onDelete,
                        modifier = Modifier.size(26.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete track",
                            tint = Color.Red.copy(alpha = 0.8f),
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }
 
        // Fader Volume Control
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Slider(
                value = if (isMuted) 0f else volume,
                onValueChange = { if (!isMuted) onVolumeChange(it) },
                colors = SliderDefaults.colors(
                    thumbColor = if (isMuted) Color.Gray else color,
                    activeTrackColor = if (isMuted) Color.DarkGray else color,
                    inactiveTrackColor = Color.DarkGray
                ),
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "${if (isMuted) 0 else (volume * 100).toInt()}%",
                color = if (isMuted) TextSecondary else color,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace,
                modifier = Modifier.width(32.dp),
                textAlign = TextAlign.End
            )
        }
 
        // Live LED Level Meter
        LedLevelMeter(value = if (isMuted) 0.001f else meterVal)
    }
}

@Composable
fun CanvasWaveformOscilloscope(
    modifier: Modifier = Modifier,
    isRecording: Boolean,
    isPlaying: Boolean,
    amplitudes: List<Float>,
    color: Color = NeonOrange
) {
    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.Black.copy(alpha = 0.5f))
            .border(1.dp, GlassBorder, RoundedCornerShape(12.dp))
    ) {
        val width = size.width
        val height = size.height
        val centerY = height / 2f
        
        // Draw grid lines like a hardware studio oscilloscope
        val gridColor = Color.White.copy(alpha = 0.05f)
        for (i in 0..10) {
            val x = (width / 10) * i
            drawLine(color = gridColor, start = Offset(x, 0f), end = Offset(x, height), strokeWidth = 1f)
        }
        for (i in 0..4) {
            val y = (height / 4) * i
            drawLine(color = gridColor, start = Offset(0f, y), end = Offset(width, y), strokeWidth = 1f)
        }
        
        // Horizontal centerline representing 0dB floor
        drawLine(
            color = Color.White.copy(alpha = 0.15f),
            start = Offset(0f, centerY),
            end = Offset(width, centerY),
            strokeWidth = 2f
        )
        
        if (isRecording || isPlaying) {
            // Draw real-time oscillating sine-wave / frequency wave representable of AnalyserNode
            val waveColor = if (isRecording) NeonOrange else TubeGold
            val points = mutableListOf<Offset>()
            val samplesCount = 120
            val dataPoints = if (amplitudes.isNotEmpty()) amplitudes else List(20) { 0.15f }
            
            for (i in 0 until samplesCount) {
                val x = (width / (samplesCount - 1)) * i
                
                // Frequency formula: sine wave combined with actual mic/model amplitude data
                val fraction = i.toFloat() / samplesCount
                val dataIndex = (fraction * dataPoints.size).toInt().coerceIn(0, dataPoints.lastIndex)
                val amp = dataPoints[dataIndex]
                
                // Add oscillating sine complexity
                val timeOffset = System.currentTimeMillis() * 0.012f
                val sineValue = Math.sin((fraction * Math.PI * 12) + timeOffset).toFloat()
                
                // Add minor noise for raw visual authenticity
                val y = centerY + (sineValue * amp * (height * 0.45f))
                
                points.add(Offset(x, y.coerceIn(2f, height - 2f)))
            }
            
            // Draw spline path
            val path = Path().apply {
                if (points.isNotEmpty()) {
                    moveTo(points[0].x, points[0].y)
                    for (k in 1 until points.size) {
                        val pPrev = points[k - 1]
                        val pCurr = points[k]
                        val xc = (pPrev.x + pCurr.x) / 2
                        val yc = (pPrev.y + pCurr.y) / 2
                        quadraticTo(pPrev.x, pPrev.y, xc, yc)
                    }
                    lineTo(points.last().x, points.last().y)
                }
            }
            
            drawPath(
                path = path,
                color = waveColor,
                style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
            )
            
            // Glow effect with lower opacity
            drawPath(
                path = path,
                color = waveColor.copy(alpha = 0.35f),
                style = Stroke(width = 8.dp.toPx(), cap = StrokeCap.Round)
            )
        } else {
            // Standby silent line with slight organic resting murmur
            val path = Path().apply {
                moveTo(0f, centerY)
                val segments = 80
                for (i in 0..segments) {
                    val x = (width / segments) * i
                    val dy = Math.sin((i.toFloat() / segments * Math.PI * 4) + (System.currentTimeMillis() * 0.003f)).toFloat() * 1.5f
                    lineTo(x, centerY + dy)
                }
            }
            drawPath(path = path, color = MetallicGray.copy(alpha = 0.4f), style = Stroke(width = 2.dp.toPx()))
        }
    }
}
