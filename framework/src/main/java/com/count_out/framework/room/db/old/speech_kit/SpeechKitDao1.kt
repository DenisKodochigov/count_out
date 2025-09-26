package com.count_out.framework.room.db.old.speech_kit

//@Dao
//interface SpeechKitDao1:PrimeDao<SpeechKitTable> {
//    @Transaction
//    @Query("SELECT * FROM tb_speech_kit WHERE idSpeechKit = :id")
//    fun get(id: Long): Flow<SpeechKitRelo?>
//
//    @Query("DELETE FROM tb_speech_kit WHERE idSpeechKit = :id ")
//    fun del(id: Long): Int
//}
//    @Transaction
//    @TypeConverters(Converters::class)
//    @Query ("SELECT * FROM tb_speech_kit JOIN tb_speech ON idKit = :id WHERE idSpeechKit = :id ORDER BY idSpeech ASC")
//    fun get1(id: Long): List<SpeechTable>
//    @Transaction
//    @Query ("SELECT * FROM tb_set " +
//            "JOIN tb_speech_kit ON idSpeechKit = tb_set.speechId " +
//            "JOIN tb_speech ON idSpeechKit = idSpeech " +
//            "WHERE idSet = :id ORDER BY idSpeech ASC")
//    fun get2(id: Long): Test1