package com.example.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface SessionDao {
    @Query("SELECT * FROM studio_sessions ORDER BY createdAt DESC")
    fun getAllSessionsFlow(): Flow<List<StudioSession>>

    @Query("SELECT * FROM studio_sessions WHERE id = :id")
    suspend fun getSessionById(id: Int): StudioSession?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSession(session: StudioSession): Long

    @Update
    suspend fun updateSession(session: StudioSession)

    @Delete
    suspend fun deleteSession(session: StudioSession)
}
