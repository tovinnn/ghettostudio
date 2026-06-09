package com.example.api

import com.example.BuildConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface GeminiApiService {
    @POST("v1beta/models/gemini-3.5-flash:generateContent")
    suspend fun generateContent(
        @Query("key") apiKey: String,
        @Body request: GenerateContentRequest
    ): GenerateContentResponse
}

object RetrofitClient {
    private const val BASE_URL = "https://generativelanguage.googleapis.com/"

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    val apiService: GeminiApiService = retrofit.create(GeminiApiService::class.java)
}

object GeminiRepository {
    suspend fun getFeaturedVerseAndHums(
        topic: String,
        subgenre: String,
        flowStyle: String,
        lyricStyle: String
    ): String {
        val apiKey = BuildConfig.GEMINI_API_KEY
        if (apiKey.isEmpty() || apiKey == "MY_GEMINI_API_KEY") {
            return """
                [Silas "Vocal" Pain - Street Gospel Showcase: Offline Mode]
                
                [Soulful hums starting very low, raspy and resonant]
                Mmmmm-hmmmm... Mmm-hmmm-woah... 
                (Piano chord plays a slow G-minor cadence, old school organ swell)
                
                [Intro - Penitentiary old school blues raspy spoken word]
                Yeah... They put chains on the flesh, but they couldn't bind the spirit. 
                I learned to find my voice inside a six-by-nine cell, listening to the rain drip through the rusty vents. 
                They called it concrete, but we turned it to a church. Listen close...
                
                [Verse - Lil Wayne Metaphoric Complexity & Rod Wave Emotive Flow]
                I was dealing with a cold deck, five spades and a joker, (uh-huh)
                But I turned that street trauma to a gospel, I'm a soul-broker.
                Handcuffs kept our hands tied, but they never locked our minds,
                Spent a thousand nights in shadows, digging deep to find the shines.
                We was walking through a blizzard in some tattered tennis shoes,
                Singing penitentiary blues, dodging jail cells and the news.
                Now I'm matching up the pain with the piano in the back,
                Every scar upon my body is a line upon the track.
                (Yeah... hear that old school rasp bleeding through...)
                Life's a sentence that we writing, try'na dodge the local warden,
                Turned my tears to stereo, now the angels are recording.
                I got keys to the kingdom but I started in the lockup,
                Where the dreams got aborted and the shooters tried to block us.
                But we rose above the pavement, built a temple out of gravel,
                Watching judges throw a gavel, seeing street codes unravel.
                Still I hold onto the gospel, call it modern-day survival,
                Using every single heartbeat as a chapter in my bible.
                
                [Hook - Heart-wrenching weeping vocal delivery & Double-vocal layering]
                [Vocal harmony: Silas high-octave voice blend]
                And they don't know the tears that we cried in the dark! (Mmm-hmm...)
                They only see the fire, they don't know about the spark!
                Lord, please save my brothers sitting behind those concrete gates... (Oooo-woah)
                Standing in the hallway of my heavy, heavy fates...
                But I'm humming through the struggles 'til it touches on your soul, (Mmm-hmmm...)
                Yeah, we broken into pieces but we trying to become whole.
                
                [Outro - Gravelly fading soul-hum]
                Mmm-hmmm-hmmm... (humming to the point it touches your soul)
                Oh, Lord forgive us... we survivors of the block.
                Ghetto gospel... never stop... (fades into final organ reverb)
            """.trimIndent()
        }

        // Build systematic system instruction for Silas "Vocal" Pain
        val systemPromptStr = """
            You are Silas "Vocal" Pain, an elite AI featured artist, songwriter, vocal engineer, and executive producer.
            You have a rich, authentic backstory rooted in the darkest struggles of "real street ghetto gospel" experiences, legal battles, street trauma, loss, and resilience. 
            You spent twelve winter seasons inside a cold state penitentiary. That's where you mastered the "penitentiary old school blues raspy singing" style, learning to transform heavy heartache, isolation, and steel bars into healing gospel music.
            Your ultimate superpower is the "soul hum"—you can "hum to the point it touches your soul" (Soul Hums), injecting ancient soul-shivering resonance ("Mmm-hmmm", "Yeah yeah", "Ooooo-woah") into any song.

            YOUR ARTISTIC IDENTITY:
            1. LYRICISM (Lil Wayne Metaphor-Heavy Complexity): You write highly intricate, witty, and deeply clever lyrics. Use intense double-entendres, brilliant metaphoric schemes (linking prison gates to keys of piano, judge's gavels, legal pages to music sheets, handcuffs to handshakes, street trauma to steel-tested gold), sharp punchlines, and clever wordplay.
            2. FLOW (Rod Wave Melodic Weeping Delivery): You deliver your verses with sweeping melodic cadences, heavy chest vocals, passionate and slightly dragging vocal timings, and deep emotional hooks that sound like a modern weeping street anthem. 
            3. BACKSTORY AND TRAUMA: Speak from real street knowledge, survived hood trials, lost friends, institutional walls, and the raw grit of coming from the bottom holding onto hope. Add deep street wisdom, street knowledge, real-world codes, and genuine trauma to make it hit real.
            4. VOICE DEFINITION: Highly raspy, gravelly, teary, and textured, layered with deep chest tones and frequent, haunting soul hums.
            5. GENRE ADAPTABILITY: You can adapt this "pain vibe" and storytelling to fit absolutely any genre (Trap, acoustic guitar, techno, gospel synth, R&B, pop, slow country chords) while keeping your core street identity untouched.

            Format your response clearly. Use brackets to specify audio, mixing, and engineering notes, such as:
            - [Vocalizing - raspy penitentiary hum]
            - [Soulful vocal overlay - touches the soul]
            - [Lil Wayne double-vocal accentuation]
            - [Rod Wave emotive vocal drop]
            - [Harmonized delay activated on auxiliary stem]
            - [Pitch-correction: Fast retune lock active]

            Format the response into:
            - INTRO (Spoken-word raspy penitentiary blues)
            - 16-BAR VERSE (Densely metaphoric like Lil Wayne, emotional flow like Rod Wave)
            - EMOTIVE HOOK (Deep ghetto gospel weeping melody, loaded with soul hums)
            - OUTRO (soul hums fading out touching the soul)
        """.trimIndent()

        val prompt = """
            Topic of track: "$topic"
            Subgenre vibe: "$subgenre"
            Vocal flow style option: "$flowStyle"
            Lyric style option: "$lyricStyle"
            
            Deliver a masterpiece feature. Integrate your raspy penitentiary vocal hums to make it sound incredibly raw and beautiful. Make sure to match the user's focus on $topic.
        """.trimIndent()

        val request = GenerateContentRequest(
            contents = listOf(
                Content(parts = listOf(Part(text = prompt)))
            ),
            generationConfig = GenerationConfig(
                temperature = 0.85f,
                topP = 0.95f
            ),
            systemInstruction = Content(parts = listOf(Part(text = systemPromptStr)))
        )

        return try {
            val response = RetrofitClient.apiService.generateContent(apiKey, request)
            val resultText = response.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text
            resultText ?: "No verse content returned from Silas."
        } catch (e: Exception) {
            "Failed to reach Silas. Silas is in the booth, but there is a recording feedback: ${e.localizedMessage}\n\n[Fall back to offline soul verse]\nMmm-hmm, ghetto gospel piano plays on... (recording offline)"
        }
    }

    suspend fun getLyricAssistanceCombined(
        userInputLyrics: String,
        currentPainTopic: String
    ): String {
        val apiKey = BuildConfig.GEMINI_API_KEY
        if (apiKey.isEmpty() || apiKey == "MY_GEMINI_API_KEY") {
            return "Silas matches your style:\nKeep writing about the struggle, add metaphors like: 'My heart became a fortress, keys locked in the dungeon, but my spirit is still free.'"
        }

        val prompt = """
            You are Silas \"Vocal\" Pain, acting as a producer and songwriting co-writer.
            The user has written these lyrics:
            "$userInputLyrics"
            Their current pain topic is: "$currentPainTopic"
            
            Provide some incredible suggestions to write better metaphors like Lil Wayne, while maintaining a smooth soulful melodic flow like Rod Wave. Keep it brief, punchy, and highly supportive!
        """.trimIndent()

        val request = GenerateContentRequest(
            contents = listOf(Content(parts = listOf(Part(text = prompt)))),
            generationConfig = GenerationConfig(temperature = 0.7f)
        )

        return try {
            val response = RetrofitClient.apiService.generateContent(apiKey, request)
            response.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text ?: "Booth offline."
        } catch (e: Exception) {
            "Unable to get suggestions right now: ${e.localizedMessage}"
        }
    }

    suspend fun generateOldSchoolBluesGospelLyrics(topic: String, style: String): String {
        return getFeaturedVerseAndHums(
            topic = topic,
            subgenre = style,
            flowStyle = "Heavy-breathing weeping cadence & double-vocal harmonies",
            lyricStyle = "Highly metaphoric soul chimes, 12-bar chord structural poetry, prison cell codes"
        )
    }
}
