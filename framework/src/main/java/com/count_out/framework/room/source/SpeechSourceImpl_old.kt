package com.count_out.framework.room.source

//class SpeechSourceImpl @Inject constructor(private val dao: SpeechDao): SpeechSource {
//
//    override fun get(id: Long): Flow<SpeechImplD?> = dao.get(id).map { it?.let { it1-> it1.toSpeech() } ?: null }
//
//    override fun copy(speech: SpeechImplD): Long? = dao.add(toSpeechTable(speech)) // idSpeech must be = 0
//
//    override fun update(speech: SpeechImplD) {
//        dao.update(toSpeechTable(speech, speech.idSpeech)) }// idSpeech must be != 0
//
//    override fun del(id: Long) { dao.del(id) }
////    override fun updateDuration(speech: SpeechImpl): Flow<SpeechImpl> {
////        dao.updateDuration(speech.duration, speech.idSpeech)
////        return get(speech.idSpeech)
////    }
//    private fun toSpeechTable(speech: SpeechImplD, idSpeech: Long = 0L) = SpeechTable(
//        idSpeech = idSpeech,
//        message = speech.message,
//        duration = speech.duration,
//        addMessage = speech.addMessage,
//    )
//}