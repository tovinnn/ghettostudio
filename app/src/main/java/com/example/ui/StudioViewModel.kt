package com.example.ui

import android.app.Application
import android.speech.tts.TextToSpeech
import java.util.Locale
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.api.GeminiRepository
import com.example.audio.StudioAudioEngine
import com.example.data.SessionDao
import com.example.data.StudioDatabase
import com.example.data.StudioSession
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

data class AudioTrack(
    val id: String,
    val title: String,
    val volume: Float,
    val isMuted: Boolean = false,
    val isSoloed: Boolean = false,
    val isCustom: Boolean = false,
    val iconName: String = "Mic"
)

data class ImportedAsset(
    val name: String,
    val type: String, // instrumental, sample, song, vocal stem
    val size: String,
    val origin: String = "Imported"
)

class StudioViewModel(application: Application) : AndroidViewModel(application) {

    private val db = StudioDatabase.getDatabase(application)
    private val sessionDao: SessionDao = db.sessionDao
    private val audioEngine = StudioAudioEngine(application)

    // Silas AI Voicing Engine (Text To Speech)
    private var tts: TextToSpeech? = null
    private var isTtsReady = false
    val isTtsEnabled = MutableStateFlow(true)

    fun speakSilasVoice(message: String) {
        if (!isTtsEnabled.value) return
        try {
            // Remove parenthetical notes or brackets which shouldn't be read out loud
            val cleaned = message.replace(Regex("\\[.*?\\]"), "")
                .replace("Mmm-hmmm", "mmm hmmm")
                .replace("Oooo-woah", "ooooh woah")
                .trim()
            if (cleaned.isNotEmpty() && isTtsReady) {
                tts?.speak(cleaned, TextToSpeech.QUEUE_FLUSH, null, "SilasBoothIntercom")
            }
        } catch (e: Exception) {
            android.util.Log.e("StudioViewModel", "Error in speakSilasVoice: ${e.message}")
        }
    }

    fun speakPresetVibe(vibeId: String) {
        val voiceMsg = when (vibeId) {
            "cough" -> "Ahem... throat is a little dry from the studio haze, but the microphone is hot."
            "gospel_hum" -> "Mmm hmmm... Yeah... Oh Lord look down on us in this studio cell..."
            "street_shout" -> "Concrete gospel from the physical block! We survivors!"
            "advice_1" -> "Take your time in the booth, the best sessions are built from raw chest scars."
            "advice_2" -> "The beat has to hurt inside before the lyrics can heal another heart."
            "advice_3" -> "They shut my body in twelve cold winters, but they could never cage this voice from expressing abstract pain."
            else -> "Let's lay down another track inside the booth."
        }
        speakSilasVoice(voiceMsg)
    }

    // Flow of all sessions in database
    val sessions = sessionDao.getAllSessionsFlow()

    // State for the current ACTIVE workspace session
    private val _currentSession = MutableStateFlow(StudioSession(sessionName = "New Hood Anthem"))
    val currentSession: StateFlow<StudioSession> = _currentSession.asStateFlow()

    // Active screen state
    private val _isRecordingVocals = MutableStateFlow(false)
    val isRecordingVocals: StateFlow<Boolean> = _isRecordingVocals.asStateFlow()

    private val _isPlayingMix = MutableStateFlow(false)
    val isPlayingMix: StateFlow<Boolean> = _isPlayingMix.asStateFlow()

    private val _isMixingAndMastering = MutableStateFlow(false)
    val isMixingAndMastering: StateFlow<Boolean> = _isMixingAndMastering.asStateFlow()

    private val _mixProgress = MutableStateFlow(0f)
    val mixProgress: StateFlow<Float> = _mixProgress.asStateFlow()

    private val _mixLogs = MutableStateFlow<List<String>>(emptyList())
    val mixLogs: StateFlow<List<String>> = _mixLogs.asStateFlow()

    private val _recordingSeconds = MutableStateFlow(0)
    val recordingSeconds: StateFlow<Int> = _recordingSeconds.asStateFlow()

    private val _amplitudes = MutableStateFlow<List<Float>>(emptyList())
    val amplitudes: StateFlow<List<Float>> = _amplitudes.asStateFlow()

    private val _aiFeatureStatus = MutableStateFlow("Idle")
    val aiFeatureStatus: StateFlow<String> = _aiFeatureStatus.asStateFlow()

    private val _vibeTopic = MutableStateFlow("Lost friends & rising above structural limits")
    val vibeTopic: StateFlow<String> = _vibeTopic.asStateFlow()

    private val _vibeSubgenre = MutableStateFlow("Real Pain Gospel Synth")
    val vibeSubgenre: StateFlow<String> = _vibeSubgenre.asStateFlow()

    private val _recordingStatusMessage = MutableStateFlow("STUDIO STANDBY - Press RECORD when ready")
    val recordingStatusMessage: StateFlow<String> = _recordingStatusMessage.asStateFlow()

    private var recordingTimerJob: Job? = null
    private var visualizerJob: Job? = null

    // Professional beats lists
    val studioBeats = listOf(
        "Pain & Tears Piano (75 BPM)",
        "Street Gospel Hood Organ (78 BPM)",
        "Lil Wayne Deep Blues Guitar (85 BPM)",
        "No Trap, Pure Pain Chords (72 BPM)",
        "Old-School Soul Chimes (82 BPM)"
    )

    // Mastering Presets lists
    val masteringStyles = listOf(
        "Ghetto Gospel Mix (Warm Analog)",
        "Clean Radio Ready (Crisp Bright)",
        "Penitentiary Tape (Subtle Saturator)",
        "Lo-Fi Vinyl Dusty Vibe"
    )

    // EQ Presets
    val eqPresets = listOf(
        "Warm & Raspy Voice",
        "Mid-Range Soul Focus",
        "Lil Wayne Crisp High-Pass",
        "Heavy Rod Wave Chest Depth"
    )

    // Imported User Assets & Samples State
    private val _importedAssets = MutableStateFlow<List<ImportedAsset>>(listOf(
        ImportedAsset("Preloaded Hood Beat 75BPM.mp3", "Instrumental", "2.4 MB", "Internal"),
        ImportedAsset("Street Gospel Piano Loop.wav", "Sample", "1.1 MB", "Internal"),
        ImportedAsset("Silas Vocal Backing Hums.wav", "Voice Harmony", "0.9 MB", "Internal")
    ))
    val importedAssets = _importedAssets.asStateFlow()

    // 5 Discrete Audio Tracks for Multi-track console
    val trackLeadVol = MutableStateFlow(0.85f)
    val trackLeadMute = MutableStateFlow(false)
    val trackHarmonyVol = MutableStateFlow(0.70f)
    val trackHarmonyMute = MutableStateFlow(false)
    val trackAdlibVol = MutableStateFlow(0.75f)
    val trackAdlibMute = MutableStateFlow(false)
    val trackChoirVol = MutableStateFlow(0.50f)
    val trackChoirMute = MutableStateFlow(false)
    val trackBeatVol = MutableStateFlow(0.80f)
    val trackBeatMute = MutableStateFlow(false)

    // Master Console parameters & Vintage Auto-tuning Scale Lock states
    val trackMasterVol = MutableStateFlow(0.85f)
    val pitchAutoTuneActive = MutableStateFlow(true)
    val pitchAutoTuneSpeed = MutableStateFlow(20f) // Retune millisecond Speed
    val pitchAutoTuneKey = MutableStateFlow("G") // G-minor, G-major Pentatonic
    val pitchAutoTuneScale = MutableStateFlow("Blues Scale") // Blues pentatonic, Ghetto Gospel
    val pitchDeviationState = MutableStateFlow(0f) // Realtime pitch scale offset (cents) -100 to +100
    val pitchTunedNote = MutableStateFlow("-") // Active locked pitch pitch-corrected note (e.g. Bb3)



    private val prefs = application.getSharedPreferences("studio_tracks_prefs", android.content.Context.MODE_PRIVATE)

    private val _tracksList = MutableStateFlow<List<AudioTrack>>(emptyList())
    val tracksList: StateFlow<List<AudioTrack>> = _tracksList.asStateFlow()

    val isGeneratingLyrics = MutableStateFlow(false)

    private fun getDefaultTracks(): List<AudioTrack> = listOf(
        AudioTrack("lead", "Track 1: Lead Vocal (User Input)", 0.85f, isMuted = false, isSoloed = false, isCustom = false, "Mic"),
        AudioTrack("harmony", "Track 2: AI Soul Harmony (Silas)", 0.70f, isMuted = false, isSoloed = false, isCustom = false, "Hearing"),
        AudioTrack("adlib", "Track 3: AI Street Ad-libs (Silas)", 0.75f, isMuted = false, isSoloed = false, isCustom = false, "GraphicEq"),
        AudioTrack("choir", "Track 4: Stereo Gospel Backing Choir", 0.50f, isMuted = false, isSoloed = false, isCustom = false, "People"),
        AudioTrack("beat", "Track 5: Main Instrumental Beat", 0.80f, isMuted = false, isSoloed = false, isCustom = false, "MusicNote")
    )

    fun loadTracksForSession(sessionId: Int) {
        viewModelScope.launch {
            val savedJson = withContext(Dispatchers.IO) {
                prefs.getString("tracks_session_$sessionId", null)
            }
            if (savedJson != null) {
                try {
                    val moshi = com.squareup.moshi.Moshi.Builder()
                        .add(com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory())
                        .build()
                    val adapter = moshi.adapter<List<AudioTrack>>(
                        com.squareup.moshi.Types.newParameterizedType(List::class.java, AudioTrack::class.java)
                    )
                    val loaded = adapter.fromJson(savedJson)
                    if (loaded != null && loaded.isNotEmpty()) {
                        _tracksList.value = loaded
                        syncLegacyStateFlows()
                        return@launch
                    }
                } catch (e: Exception) {
                    android.util.Log.e("StudioViewModel", "Error parsing tracks: ${e.message}")
                }
            }
            _tracksList.value = getDefaultTracks()
            syncLegacyStateFlows()
        }
    }

    fun saveTracksToPrefs() {
        val sessionId = _currentSession.value.id
        val list = _tracksList.value
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val moshi = com.squareup.moshi.Moshi.Builder()
                    .add(com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory())
                    .build()
                val adapter = moshi.adapter<List<AudioTrack>>(
                    com.squareup.moshi.Types.newParameterizedType(List::class.java, AudioTrack::class.java)
                )
                val json = adapter.toJson(list)
                prefs.edit().putString("tracks_session_$sessionId", json).apply()
            } catch (e: Exception) {
                android.util.Log.e("StudioViewModel", "Error saving tracks: ${e.message}")
            }
        }
    }

    fun syncLegacyStateFlows() {
        val list = _tracksList.value
        val anySoloed = list.any { it.isSoloed }

        list.find { it.id == "lead" }?.let {
            trackLeadVol.value = it.volume
            trackLeadMute.value = it.isMuted || (anySoloed && !it.isSoloed)
        }
        list.find { it.id == "harmony" }?.let {
            trackHarmonyVol.value = it.volume
            trackHarmonyMute.value = it.isMuted || (anySoloed && !it.isSoloed)
        }
        list.find { it.id == "adlib" }?.let {
            trackAdlibVol.value = it.volume
            trackAdlibMute.value = it.isMuted || (anySoloed && !it.isSoloed)
        }
        list.find { it.id == "choir" }?.let {
            trackChoirVol.value = it.volume
            trackChoirMute.value = it.isMuted || (anySoloed && !it.isSoloed)
        }
        list.find { it.id == "beat" }?.let {
            trackBeatVol.value = it.volume
            trackBeatMute.value = it.isMuted || (anySoloed && !it.isSoloed)
        }
    }

    fun updateTrackVolume(trackId: String, volume: Float) {
        _tracksList.value = _tracksList.value.map {
            if (it.id == trackId) it.copy(volume = volume) else it
        }
        syncLegacyStateFlows()
        saveTracksToPrefs()
    }

    fun toggleTrackMute(trackId: String) {
        _tracksList.value = _tracksList.value.map {
            if (it.id == trackId) it.copy(isMuted = !it.isMuted) else it
        }
        syncLegacyStateFlows()
        saveTracksToPrefs()
    }

    fun toggleTrackSolo(trackId: String) {
        val targetTrack = _tracksList.value.find { it.id == trackId } ?: return
        val newSoloState = !targetTrack.isSoloed
        _tracksList.value = _tracksList.value.map {
            if (it.id == trackId) {
                it.copy(isSoloed = newSoloState)
            } else {
                it
            }
        }
        syncLegacyStateFlows()
        saveTracksToPrefs()
    }

    fun addCustomTrack(title: String, iconName: String = "Mic") {
        val id = "custom_${System.currentTimeMillis()}"
        val newTrack = AudioTrack(
            id = id,
            title = if (title.trim().isEmpty()) "Custom Overtrack" else title.trim(),
            volume = 0.75f,
            isMuted = false,
            isSoloed = false,
            isCustom = true,
            iconName = iconName
        )
        _tracksList.value = _tracksList.value + newTrack
        syncLegacyStateFlows()
        saveTracksToPrefs()
        _recordingStatusMessage.value = "MULTITRACK: Added custom track '${newTrack.title}'!"
    }

    fun deleteTrack(trackId: String) {
        _tracksList.value = _tracksList.value.filter { it.id != trackId }
        syncLegacyStateFlows()
        saveTracksToPrefs()
        _recordingStatusMessage.value = "MULTITRACK: Removed custom track."
    }

    fun generateBluesGospelLyrics(style: String) {
        viewModelScope.launch {
            isGeneratingLyrics.value = true
            _recordingStatusMessage.value = "SILAS: Writing custom $style vocal bars..."
            speakSilasVoice("In the booth matching that authentic $style pocket for us real quick...")
            
            val topic = if (_vibeTopic.value.trim().isEmpty()) "street pain and hope" else _vibeTopic.value
            val generated = withContext(Dispatchers.IO) {
                GeminiRepository.generateOldSchoolBluesGospelLyrics(topic, style)
            }
            
            val currentLyrics = _currentSession.value.userLyrics
            val prefix = if (currentLyrics.isNotEmpty()) "$currentLyrics\n\n" else ""
            _currentSession.value = _currentSession.value.copy(
                userLyrics = "$prefix$generated"
            )
            saveActiveSessionChanges()
            isGeneratingLyrics.value = false
            _recordingStatusMessage.value = "LYRIC BOARD: Appended freshly written $style bars!"
            speakSilasVoice("Just dropped that deep $style poetry onto your lyric pad. This is pure analog gold.")
        }
    }

    // Dynamic Level Meters State (percentage 0f..1f)
    val meterLead = MutableStateFlow(0.01f)
    val meterHarmony = MutableStateFlow(0.01f)
    val meterAdlib = MutableStateFlow(0.01f)
    val meterChoir = MutableStateFlow(0.01f)
    val meterBeat = MutableStateFlow(0.01f)

    // Active Sandbox FX Filters (Unlimited selections list)
    private val _activeLabFX = MutableStateFlow<Set<String>>(setOf("Auto-Tune Skill", "Warm 48V Preamp"))
    val activeLabFX = _activeLabFX.asStateFlow()

    val availableLabFX = listOf(
        "Auto-Tune Skill",
        "Warm 48V Preamp",
        "Tape physical delay",
        "Hallway Space Reverb",
        "1950s Vinyl Crackle",
        "Telephone Filter",
        "Gospel Pitch Vocoder",
        "Brickwall Limiter",
        "De-esser Leveler",
        "Vibrato Tremolo Flow",
        "Sub-Harmonic Pitch Pitcher",
        "Stereo Wider (8ms)"
    )

    // Exporter state flows
    val isExportingTrack = MutableStateFlow(false)
    val exportProgress = MutableStateFlow(0f)
    val exportLog = MutableStateFlow("")

    private var meterTimerJob: Job? = null

    fun startMeterDynamics() {
        meterTimerJob?.cancel()
        meterTimerJob = viewModelScope.launch {
            while (true) {
                delay(80)
                
                // Real-time Pitch Correction Processing simulation
                if (pitchAutoTuneActive.value && (_isRecordingVocals.value || _isPlayingMix.value)) {
                    val scaleNotes = if (pitchAutoTuneScale.value == "Blues Scale") {
                        listOf("3", "5", "6", "8", "9", "11") // Note index strings mimicking Blues intervals
                    } else { // Ghetto Gospel / Major Pentatonic
                        listOf("1", "3", "5", "6", "8", "10")
                    }
                    val currentKey = pitchAutoTuneKey.value
                    
                    // Periodic organic shifting notes mimicking standard blues melodies
                    val waveValue = Math.sin(System.currentTimeMillis() * 0.002)
                    var index = ((waveValue * 0.5 + 0.5) * scaleNotes.size).toInt().coerceIn(0, scaleNotes.lastIndex)
                    val noteNum = scaleNotes[index]
                    
                    // Pitch deviation before Correction (simulated vocals vibrato/drift) -50 to +50 cents
                    val rawDeviation = (Math.sin(System.currentTimeMillis() * 0.02) * 42f + Math.cos(System.currentTimeMillis() * 0.006) * 11f).toFloat()
                    
                    // Shorter Speed values represent aggressive physical snap (less final deviation). High speed is looser
                    val activeSpeed = pitchAutoTuneSpeed.value.coerceIn(1f, 100f)
                    val correctionSpeedCoeff = (activeSpeed / 100f) // 0.01 to 1.0f
                    val displayDeviation = rawDeviation * correctionSpeedCoeff
                    
                    pitchDeviationState.value = displayDeviation
                    pitchTunedNote.value = "${currentKey}${noteNum}"
                } else {
                    pitchDeviationState.value = 0f
                    pitchTunedNote.value = "-"
                }

                if (_isRecordingVocals.value) {
                    val baseLead = (Math.random() * 0.45 + 0.35).toFloat()
                    meterLead.value = if (trackLeadMute.value) 0.001f else (baseLead * trackLeadVol.value).coerceIn(0.01f, 1f)
                    meterHarmony.value = 0.01f
                    meterAdlib.value = 0.01f
                    meterChoir.value = 0.01f
                    meterBeat.value = if (trackBeatMute.value) 0.001f else ((Math.random() * 0.15 + 0.65).toFloat() * trackBeatVol.value).coerceIn(0.01f, 1f)
                } else if (_isPlayingMix.value) {
                    val mul = if (_currentSession.value.aiLyrics.isNotEmpty()) 1.0f else 0.2f
                    val layerMode = _currentSession.value.vocalLayeringMode
                    val hasHarmony = layerMode != "Off"

                    meterLead.value = if (trackLeadMute.value) 0.001f else ((Math.random() * 0.35 + 0.55).toFloat() * trackLeadVol.value).coerceIn(0.01f, 1f)
                    meterHarmony.value = if (trackHarmonyMute.value) 0.001f else if (hasHarmony) ((Math.random() * 0.35 + 0.45).toFloat() * trackHarmonyVol.value).coerceIn(0.01f, 1f) else 0.01f
                    meterAdlib.value = if (trackAdlibMute.value) 0.001f else ((Math.random() * 0.40 * mul).toFloat() * trackAdlibVol.value).coerceIn(0.01f, 1f)
                    meterChoir.value = if (trackChoirMute.value) 0.001f else ((Math.random() * 0.45 * mul).toFloat() * trackChoirVol.value).coerceIn(0.01f, 1f)
                    meterBeat.value = if (trackBeatMute.value) 0.001f else ((Math.random() * 0.10 + 0.75).toFloat() * trackBeatVol.value).coerceIn(0.01f, 1f)
                } else {
                    // Slight resting visual jitter
                    meterLead.value = (Math.random() * 0.02 + 0.01).toFloat()
                    meterHarmony.value = (Math.random() * 0.01 + 0.01).toFloat()
                    meterAdlib.value = (Math.random() * 0.015 + 0.01).toFloat()
                    meterChoir.value = (Math.random() * 0.01 + 0.01).toFloat()
                    meterBeat.value = (Math.random() * 0.02 + 0.01).toFloat()
                }
            }
        }
    }

    fun stopMeterDynamics() {
        meterTimerJob?.cancel()
        meterTimerJob = null
        meterLead.value = 0.01f
        meterHarmony.value = 0.01f
        meterAdlib.value = 0.01f
        meterChoir.value = 0.01f
        meterBeat.value = 0.01f
    }

    fun importCustomAsset(name: String, type: String, size: String) {
        val newList = _importedAssets.value.toMutableList()
        newList.add(ImportedAsset(name, type, size, "User Imported"))
        _importedAssets.value = newList
        _recordingStatusMessage.value = "IMPORTED! Successful import of $type '$name' ($size) to active project stems."
    }

    fun removeImportedAsset(asset: ImportedAsset) {
        _importedAssets.value = _importedAssets.value.filter { it.name != asset.name }
        _recordingStatusMessage.value = "Removed track asset '${asset.name}' from session."
    }

    fun toggleLabFX(fx: String) {
        val current = _activeLabFX.value
        if (current.contains(fx)) {
            _activeLabFX.value = current - fx
            _recordingStatusMessage.value = "Vocal FX chain: '$fx' bypassed."
        } else {
            _activeLabFX.value = current + fx
            _recordingStatusMessage.value = "Vocal FX chain: Added '$fx' online."
        }
    }

    fun triggerExportSimulation(
        fileName: String,
        format: String,
        bitRate: String,
        dither: Boolean,
        onDone: () -> Unit
    ) {
        viewModelScope.launch {
            isExportingTrack.value = true
            exportProgress.value = 0f
            
            val steps = listOf(
                Pair(0.15f, "Consolidating multitrack stems: Lead Vocal, AI Harmonies, Soul Adlibs..."),
                Pair(0.30f, "Bouncing active FX signal chains: [${_activeLabFX.value.joinToString(", ")}]..."),
                Pair(0.50f, "Blending instrumentals at: ${(trackBeatVol.value * 100).toInt()}% output gain..."),
                Pair(0.70f, "Applying 'Ghetto Gospel' tape saturation dynamics limiter..."),
                Pair(0.85f, "Writing dither pattern ($bitRate format) & audio headers..."),
                Pair(1.00f, "Finalizing Master export package: $fileName.$format")
            )

            for (step in steps) {
                exportProgress.value = step.first
                exportLog.value = step.second
                delay(800)
            }

            _recordingStatusMessage.value = "EXPORT SUCCESSFUL! Master saved to local storage: /Downloads/StudioLab/$fileName.$format"
            isExportingTrack.value = false
            onDone()
        }
    }

    init {
        // Initialize Text to Speech voice representing Silas
        try {
            tts = TextToSpeech(application) { status ->
                if (status == TextToSpeech.SUCCESS) {
                    val res = tts?.setLanguage(Locale.US)
                    if (res != TextToSpeech.LANG_MISSING_DATA && res != TextToSpeech.LANG_NOT_SUPPORTED) {
                        try {
                            tts?.setPitch(0.55f) // Deep baritone resonance
                            tts?.setSpeechRate(0.80f) // Slower, highly poetic tempo
                            isTtsReady = true
                            
                            // Welcoming user instantly
                            speakSilasVoice("Silas is online. Turn up your headphone volume... Let's make some abstract gold together.")
                        } catch (e: Exception) {
                            android.util.Log.e("StudioViewModel", "Error setting pitch/rate: ${e.message}")
                        }
                    }
                }
            }
        } catch (e: Exception) {
            android.util.Log.e("StudioViewModel", "TTS initialization error: ${e.message}")
        }

        // Build a default project session to start with
        viewModelScope.launch {
            createNewSession("New Gospel Draft")
        }
        startMeterDynamics()
    }

    fun updateVibeTopic(newTopic: String) {
        _vibeTopic.value = newTopic
    }

    fun updateVibeSubgenre(newGenre: String) {
        _vibeSubgenre.value = newGenre
    }

    suspend fun createNewSession(name: String) {
        val newSess = StudioSession(
            sessionName = if (name.trim().isEmpty()) "Untitled Studio Session" else name.trim(),
            selectedBeat = studioBeats.first()
        )
        val generatedId = withContext(Dispatchers.IO) {
            sessionDao.insertSession(newSess)
        }
        _currentSession.value = newSess.copy(id = generatedId.toInt())
        _amplitudes.value = emptyList()
        _recordingSeconds.value = 0
        loadTracksForSession(generatedId.toInt())
    }

    fun selectSession(session: StudioSession) {
        _currentSession.value = session
        _amplitudes.value = emptyList()
        _recordingSeconds.value = 0
        _aiFeatureStatus.value = if (session.aiLyrics.isNotEmpty()) "Completed Silas Feature" else "Idle"
        loadTracksForSession(session.id)
    }

    fun updateSelectedBeat(beat: String) {
        _currentSession.value = _currentSession.value.copy(selectedBeat = beat)
        saveActiveSessionChanges()
    }

    fun updateSelectedEq(eq: String) {
        _currentSession.value = _currentSession.value.copy(eqPreset = eq)
        saveActiveSessionChanges()
    }

    fun updateSelectedMastering(style: String) {
        _currentSession.value = _currentSession.value.copy(masteringPreset = style)
        saveActiveSessionChanges()
    }

    fun updateReverb(level: Float) {
        _currentSession.value = _currentSession.value.copy(reverbLevel = level)
        saveActiveSessionChanges()
    }

    fun updatePitchCorrectionSpeed(speed: Int) {
        _currentSession.value = _currentSession.value.copy(pitchCorrectionSpeed = speed)
        saveActiveSessionChanges()
    }

    fun updatePitchCorrectionAmount(amount: Int) {
        _currentSession.value = _currentSession.value.copy(pitchCorrectionAmount = amount)
        saveActiveSessionChanges()
    }

    fun updateVocalLayeringMode(mode: String) {
        _currentSession.value = _currentSession.value.copy(vocalLayeringMode = mode)
        saveActiveSessionChanges()
    }

    fun updateUserLyrics(lyrics: String) {
        _currentSession.value = _currentSession.value.copy(userLyrics = lyrics)
        saveActiveSessionChanges()
    }

    fun saveActiveSessionChanges() {
        val current = _currentSession.value
        viewModelScope.launch {
            val generatedId = sessionDao.insertSession(current)
            if (current.id == 0) {
                _currentSession.value = current.copy(id = generatedId.toInt())
                loadTracksForSession(generatedId.toInt())
            }
        }
    }

    fun saveSessionToDbDirectly() {
        viewModelScope.launch {
            val sessionWithAudio = _currentSession.value
            val id = sessionDao.insertSession(sessionWithAudio)
            _currentSession.value = sessionWithAudio.copy(id = id.toInt())
            loadTracksForSession(id.toInt())
        }
    }

    fun deleteSession(session: StudioSession) {
        viewModelScope.launch {
            sessionDao.deleteSession(session)
            // If deleting current, fall back to a generic session
            if (_currentSession.value.id == session.id) {
                createNewSession("Studio Draft #${(1..100).random()}")
            }
        }
    }

    // --- LYRICS GEN WITH SILAS "VOCAL" PAIN persona ---
    fun generateFeaturedVerse() {
        viewModelScope.launch {
            _aiFeatureStatus.value = "Writing Lyrics..."
            _recordingStatusMessage.value = "SILAS IS CO-WRITING IN THE BOOTH..."
            speakSilasVoice("Let me get in my zone real quick. Locking in to that state penitentiary blues... writing down some deep abstract poetry for us.")
            val promptTopic = _vibeTopic.value
            val subgenre = _vibeSubgenre.value
            val flowSelection = "Rod Wave Soul Hum + Fast Melodic Cadences"
            val lyricStyle = "Lil Wayne Clever Metaphors + Ghetto Gospel Truths"

            val generated = withContext(Dispatchers.IO) {
                GeminiRepository.getFeaturedVerseAndHums(
                    topic = promptTopic,
                    subgenre = subgenre,
                    flowStyle = flowSelection,
                    lyricStyle = lyricStyle
                )
            }

            _currentSession.value = _currentSession.value.copy(aiLyrics = generated)
            saveActiveSessionChanges()
            _aiFeatureStatus.value = "Completed Silas Feature"
            _recordingStatusMessage.value = "FEATURE RECORDED! Silas finished recording his featured verse."
            speakSilasVoice("Just finished laying down the vocal track inside the booth. Got those heavy harmonies locked in. Let's run a play back!")
        }
    }

    fun clearFeaturedVerse() {
        _currentSession.value = _currentSession.value.copy(aiLyrics = "")
        saveActiveSessionChanges()
        _aiFeatureStatus.value = "Idle"
        _recordingStatusMessage.value = "Featured artist track cleared."
    }

    fun getLyricAssistance() {
        viewModelScope.launch {
            _recordingStatusMessage.value = "SILAS GIVING YOU FLOW FEEDBACK..."
            val userLyricsText = _currentSession.value.userLyrics
            val currentTopic = _vibeTopic.value

            if (userLyricsText.trim().isEmpty()) {
                _recordingStatusMessage.value = "Type some lyrics first, then Silas will help co-write!"
                return@launch
            }

            val advice = withContext(Dispatchers.IO) {
                GeminiRepository.getLyricAssistanceCombined(userLyricsText, currentTopic)
            }

            _currentSession.value = _currentSession.value.copy(
                notes = advice
            )
            saveActiveSessionChanges()
            _recordingStatusMessage.value = "Vocal Coach advice updated in the NOTES console!"
        }
    }

    fun clearNotes() {
        _currentSession.value = _currentSession.value.copy(notes = "")
        saveActiveSessionChanges()
    }

    // --- AUDIO SYSTEM RECORD & PLAYBACK ---
    fun toggleVoiceRecording() {
        if (_isRecordingVocals.value) {
            stopVocalRecording()
        } else {
            startVocalRecording()
        }
    }

    private fun startVocalRecording() {
        if (_isPlayingMix.value) {
            stopMixPlayback()
        }

        val sessionNameSanitized = _currentSession.value.sessionName.replace(" ", "_")
        val timestamp = System.currentTimeMillis()
        val path = audioEngine.startRecording("vocal_${sessionNameSanitized}_$timestamp")

        _isRecordingVocals.value = true
        _recordingSeconds.value = 0
        _amplitudes.value = emptyList()
        _recordingStatusMessage.value = "• RECORDING VOCALS LIVE (PITCH-CORRECTION ACTIVE)..."
        speakSilasVoice("The booth microphone is live. Lay down your street thoughts. Let it pour straight from your chest, I am backing you up on the track.")

        // Handle Seconds timer
        recordingTimerJob = viewModelScope.launch {
            while (true) {
                delay(1000)
                _recordingSeconds.value += 1
            }
        }

        // Handle visualizer amplitudes
        val visualizerBuffer = mutableListOf<Float>()
        visualizerJob = viewModelScope.launch {
            while (true) {
                delay(100)
                val amp = audioEngine.getMaxAmplitude()
                // Scale value between 0.05 and 1.0 for styling
                val normalized = (amp.toFloat() / 32767f).coerceIn(0.05f, 1.0f)
                visualizerBuffer.add(normalized)
                if (visualizerBuffer.size > 50) {
                    visualizerBuffer.removeAt(0)
                }
                _amplitudes.value = visualizerBuffer.toList()
            }
        }
    }

    private fun stopVocalRecording() {
        recordingTimerJob?.cancel()
        visualizerJob?.cancel()
        recordingTimerJob = null
        visualizerJob = null

        val path = audioEngine.stopRecording()
        _isRecordingVocals.value = false
        _recordingStatusMessage.value = "VOCALS RECORDED! Tap 'AI Mix & Master' or play it back."

        _currentSession.value = _currentSession.value.copy(audioFilePath = path)
        saveActiveSessionChanges()
        speakSilasVoice("Yeah, that take had serious pain. I felt every single word. Let's run it through the mixer console and lock down the master.")
    }

    fun playMixPlayback() {
        if (_isRecordingVocals.value || _isMixingAndMastering.value) return

        val path = _currentSession.value.audioFilePath
        if (path == null) {
            _recordingStatusMessage.value = "No vocal track detected. Recording simulated vocal playback..."
            simulateVocalPlaybackAndBeat()
            return
        }

        _isPlayingMix.value = true
        _recordingStatusMessage.value = "► PLAYING DOWN MIX (VOCAL LAYER: ${_currentSession.value.vocalLayeringMode} ACTIVE)..."
        speakSilasVoice("Laying down the mix. Listen to how our tracks blend together.")

        audioEngine.startPlayback(path) {
            _isPlayingMix.value = false
            _recordingStatusMessage.value = "Playback finished."
        }
    }

    fun stopMixPlayback() {
        if (!_isPlayingMix.value) return
        audioEngine.stopPlayback()
        _isPlayingMix.value = false
        _recordingStatusMessage.value = "Playback stopped."
    }

    private fun simulateVocalPlaybackAndBeat() {
        viewModelScope.launch {
            _isPlayingMix.value = true
            _recordingStatusMessage.value = "► PLAYING DEMO MASTER MIXDOWN (Silas + Hood Beat + Hums)..."
            speakSilasVoice("Listening to the master track. mmm hmmm... Yeah... Oh Lord forgive us...")
            delay(10000)
            _isPlayingMix.value = false
            _recordingStatusMessage.value = "Demo Playback finished."
        }
    }

    // --- SYSTEM MIXING AND MASTERING PROCESS (AI EXPERT ENGINEER SIMULATOR) ---
    fun runMidiMixAndMaster() {
        viewModelScope.launch {
            _isMixingAndMastering.value = true
            _mixProgress.value = 0f
            _mixLogs.value = emptyList()
            _recordingStatusMessage.value = "AI STUDIO ENGINEER: RUNNING MULTI-TRACK MASTERING..."
            speakSilasVoice("Mastering session activated. I'm tuning up the pitch corrector, running the analog pre amp, and putting my ghetto gospel heat on the track.")

            val logSteps = listOf(
                "Detecting master tempo from: ${_currentSession.value.selectedBeat}",
                "Loading vocal frequency curve (EQ Preset: ${_currentSession.value.eqPreset})...",
                "Locking real-time vocal pitch-correction to G-Minor scale...",
                "Activating '${_currentSession.value.vocalLayeringMode}' - creating secondary phase-shifted vocal track (8ms delay, +3dB depth)...",
                "Analyzing user's words for resonance and pain level...",
                "Feeding EQ curves into analog compressor matching style: ${_currentSession.value.masteringPreset}...",
                if (_currentSession.value.aiLyrics.isNotEmpty()) "Coupling AI Silas 'Vocal' Pain's features & hums into sub-aux channels..." else "Balancing vocal dynamics (no featured verse loaded)...",
                "Applying Reverb space (Room size: ${(_currentSession.value.reverbLevel * 100).toInt()}%)...",
                "Engaging 24-bit studio tape saturator to maximize dynamic range...",
                "Master final stereo output bouncing completed successfully!"
            )

            for (i in logSteps.indices) {
                _mixProgress.value = (i + 1).toFloat() / logSteps.size.toFloat()
                _mixLogs.value = _mixLogs.value + logSteps[i]
                delay(1200) // Give user immersive feeling or reading logs
            }

            _currentSession.value = _currentSession.value.copy(isCompleted = true)
            saveActiveSessionChanges()

            _isMixingAndMastering.value = false
            _recordingStatusMessage.value = "MIXING & MASTERING SUCCESFUL! Locked into ${_currentSession.value.masteringPreset}. Track is legendary!"
            speakSilasVoice("The master track is finished. The tape saturation holds clean. Play it back, let us feel the track.")
        }
    }

    override fun onCleared() {
        super.onCleared()
        audioEngine.release()
        try {
            tts?.stop()
            tts?.shutdown()
        } catch (e: Exception) {
            // Quiet exit
        }
    }
}
