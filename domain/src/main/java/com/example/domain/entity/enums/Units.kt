package com.example.domain.entity.enums

//sealed class TimeUnit {
//    data class H(val rId: Int): TimeUnit()
//    data class M(val rId: Int): TimeUnit()
//    data class S(val rId: Int): TimeUnit()
//}
//abstract class TimeUnit{
//    class H(): TimeUnit()
//    class M(): TimeUnit()
//    class S(): TimeUnit()
//}
//abstract class WeightUnit{
//    class KG(): WeightUnit()
//    class GR(): WeightUnit()
//}
//abstract class DistanceUnit{
//    class KM(): DistanceUnit()
//    class M(): DistanceUnit()
//}
//abstract class Goal {
//    class Distance() : Goal()
//    class Duration() : Goal()
//    class Count() : Goal()
//    class CountGroup() : Goal()
//}
//
//abstract class Zone {
//    class Min(var upPulse: Int = 80) : Zone()
//    class Low(var upPulse: Int = 100) : Zone()
//    class Medium(var upPulse: Int = 135) : Zone()
//    class High(var upPulse: Int = 160) : Zone()
//    class Max(var upPulse: Int = 180) : Zone()
//}
enum class Goal {Distance, Duration, Count, CountGroup}
enum class DistanceUnit {KM, M, }
enum class TimeUnit {H, M, S}
enum class WeightUnit {KG, GR, }
enum class Zone {Min, Low, Medium,High,Max}

enum class RoundType {WorkUp, WorkOut, WorkDown}