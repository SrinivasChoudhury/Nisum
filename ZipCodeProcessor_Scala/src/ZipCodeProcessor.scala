import scala.collection.mutable.SortedSet
import scala.collection.mutable.TreeSet
import scala.collection.Iterable
import scala.collection.Iterator
import scala.collection.JavaConversions._
import scala.util.control._

object ZipCodeProcessor 
{
  def main(args: Array[String]): Unit = {
    val input: String =
      "[94300,94398] [94200,94299] [94226,94399] [94133,94133] [94133,94133] [94130,94133] [94133,94135] [94100,94199] [94133,94133] [94130,94133] [94133,94135]"
    val individualRanges: Array[String] =
      (input.replace("] [", ";").replace("[", "").replace("]", "")).split(";")
    val start: SortedSet[Integer] = new TreeSet[Integer]()
    val end: SortedSet[Integer] = new TreeSet[Integer]()
    val firstRange: Array[String] = individualRanges(0).split(",")
    start.add(java.lang.Integer.valueOf(firstRange(0)))
    end.add(java.lang.Integer.valueOf(firstRange(1)))
    val loop = new Breaks;
    for (x <- 1 until individualRanges.length) {
      val i = start.iterator()
      val j = end.iterator()
      val currentRange: Array[String] = individualRanges(x).split(",")
      val newStart: Int = java.lang.Integer.valueOf(currentRange(0))
      val newEnd: Int = java.lang.Integer.valueOf(currentRange(1))
      var count: Int = 0

      loop.breakable {
      while (i.hasNext && j.hasNext) {
        val currentStart: Int = i.next().toInt
        val currentEnd: Int = j.next().toInt
        if (newStart >= currentStart && newEnd <= currentEnd) {
          loop.break
        } else if (newStart < currentStart && newEnd < currentStart) {
          start.add(newStart)
          end.add(newEnd)
          loop.break
        } else if (newStart < currentStart && newEnd <= currentEnd) {
          start.remove(currentStart)
          start.add(newStart)
          loop.break
        } else if (newStart >= currentStart && newEnd >= currentEnd && newStart <= currentEnd) {
          end.remove(currentEnd)
          end.add(newEnd)
          loop.break
        } else if (newStart < currentStart && newEnd > currentEnd) {
          start.remove(currentStart)
          end.remove(currentEnd)
          start.add(newStart)
          end.add(newEnd)
          loop.break
        } else if (newStart > currentEnd && newEnd > currentEnd) {
          { count += 1; count - 1 }
        }
      }
      }
      if (count == start.size) {
        start.add(newStart)
        end.add(newEnd)
      }
      val p = start.iterator()
      val q = end.iterator()
      if (p.hasNext) {
        p.next()
      }
      while (p.hasNext && q.hasNext) 
      {
        val nextStart: Int = p.next().toInt
        val currentEnd: Int = q.next().toInt
        if (nextStart <= currentEnd && start.size == end.size) 
        {
          start.remove(nextStart)
          end.remove(currentEnd)
        }
      }
    }
    val i = start.iterator()
    val j = end.iterator()
    println("Output:")
    while (i.hasNext && j.hasNext)
    { 
      print("[" + i.next() + "," + j.next() + "] ")
    }
    
  }
}
