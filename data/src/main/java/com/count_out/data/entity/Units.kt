package com.count_out.data.entity


//open class TimeUData: TimeUnit(){
//    class H(): TimeUData()
//    class M(): TimeUData()
//    class S(): TimeUData()
//}
abstract class WeightU{
    class KG(): WeightU()
    class GR(): WeightU()
}
abstract class DistanceU{
    class KM(): WeightU()
    class M(): WeightU()
}
abstract class Goal {
    class Distance() : WeightU()
    class Duration() : WeightU()
    class Count() : WeightU()
    class CountGroup() : WeightU()
}

abstract class Zone {
    class Min(var upPulse: Int = 80) : WeightU()
    class Low(var upPulse: Int = 100) : WeightU()
    class Medium(var upPulse: Int = 135) : WeightU()
    class High(var upPulse: Int = 160) : WeightU()
    class Max(var upPulse: Int = 180) : WeightU()
}