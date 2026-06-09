package com.example.data

import androidx.room.*

@Entity(tableName = "studio_sessions")
data class StudioSession(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val sessionName: String,
    val createdAt: Long = System.currentTimeMillis(),
    val selectedBeat: String = "Pain & Hope Piano (75 BPM)",
    val notes: String = "",
    val userLyrics: String = "",
    val aiLyrics: String = "",
    val eqPreset: String = "Warm & Raspy",
    val reverbLevel: Float = 0.5f,
    val pitchCorrectionSpeed: Int = 40, // 0 to 100
    val pitchCorrectionAmount: Int = 80, // 0 to 100
    val vocalLayeringMode: String = "Double Vocal", // None, Double Vocal, Triple Layer
    val masteringPreset: String = "Ghetto Gospel Mix (Warm Analog)", // Ghetto Gospel, Clean Radio, Tape Saturation
    val isCompleted: Boolean = false,
    val audioFilePath: String? = null
)
