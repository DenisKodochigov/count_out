package com.count_out.domain.entity.enums

enum class Goal { Distance, Duration, Count, CountGroup}
enum class Units(var id: Int) {H(0), M(0), S(0), KM(0), MT(0), KG(0), GR(0)}
enum class Zone {Min, Low, Medium,High,Max}
enum class RoundType {WorkUp, WorkOut, WorkDown}
enum class RunningState { Binding, Started, Paused, Stopped }
//interface Uni
//fun interface GetId{ fun ex(u: Uni): Int}
//sealed class Units {
//    data class S(var id: Int): Units()
//    data class M(var id: Int): Units()
//    data class H(var id: Int): Units()
//    data class MT(var id: Int): Units()
//    data class KM(var id: Int): Units()
//    data class KG(var id: Int): Units()
//    data class GR(var id: Int): Units()
//}
//enum class DistanceUnit(var id: Int): Uni { KM(0), M(0), }
//enum class DurationUnit(var id: Int): Uni {H(0), M(0), S(0)}
//enum class TimeUnit(var id: Int): Uni {H(0), M(0), S(0)}
//enum class WeightUnit(var id: Int): Uni {KG(0), GR(0) }
//
//sealed class Duration: Uni{
//    data class S(var id: Int): Duration()
//    data class M(var id: Int): Duration()
//    data class H(var id: Int): Duration()
//}
//sealed class Distance: Uni{
//    data class KM(var id: Int): Distance()
//    data class M(var id: Int): Distance()
//}
//sealed class Weight: Uni{
//    data class KG(var id: Int): Weight()
//    data class GR(var id: Int): Weight()
//}

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